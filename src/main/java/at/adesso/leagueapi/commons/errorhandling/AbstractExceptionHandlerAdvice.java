package at.adesso.leagueapi.commons.errorhandling;

import at.adesso.leagueapi.commons.errorhandling.error.CommonError;
import at.adesso.leagueapi.commons.errorhandling.error.Error;
import at.adesso.leagueapi.commons.errorhandling.exceptions.ApiException;
import at.adesso.leagueapi.commons.errorhandling.exceptions.UnauthorizedAccessException;
import at.adesso.leagueapi.commons.errorhandling.model.ErrorResponse;
import at.adesso.leagueapi.commons.util.HttpHeadersBuilder;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public abstract class AbstractExceptionHandlerAdvice {

    private final ErrorResponseFactory responseFactory;

    protected AbstractExceptionHandlerAdvice(final ErrorResponseFactory responseFactory) {
        this.responseFactory = responseFactory;
    }

    protected abstract Logger getLogger();

    @ExceptionHandler(value = {ApiException.class})
    protected ResponseEntity<Object> handleApiException(final ApiException exception) {
        return handleException(exception.getError(), exception,
                () -> responseFactory.createErrorResponse(exception));
    }

    @ExceptionHandler(value = {AccessDeniedException.class})
    protected ResponseEntity<Object> handleAccessDeniedException(final AccessDeniedException exception) {
        return handleException(CommonError.ACCESS_DENIED, exception,
                () -> responseFactory.createErrorResponse(CommonError.ACCESS_DENIED));
    }

    @ExceptionHandler(value = {UnauthorizedAccessException.class})
    protected ResponseEntity<Object> handleAccessDeniedException(final UnauthorizedAccessException exception) {
        return handleException(CommonError.UNAUTHORIZED, exception,
                () -> responseFactory.createErrorResponse(CommonError.UNAUTHORIZED, exception.getDetails()));
    }


    @ExceptionHandler(value = {RuntimeException.class})
    protected ResponseEntity<Object> handleUnexpectedException(final RuntimeException exception) {
        return handleException(CommonError.UNKNOWN_SERVER_ERROR, exception,
                () -> responseFactory.createErrorResponse(CommonError.UNKNOWN_SERVER_ERROR));
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    protected ResponseEntity<Object> handleValidationExceptions(final MethodArgumentNotValidException exception) {
        final List<String> validationErrors = new ArrayList<>();

        exception.getBindingResult().getAllErrors().forEach(error -> {
            if (error instanceof FieldError fieldError) {
                final String fieldName = fieldError.getField();
                final String errorMessage = fieldError.getDefaultMessage();

                validationErrors.add(String.format("%s: %s", fieldName, errorMessage));
            } else {
                validationErrors.add(error.getDefaultMessage());
            }
        });

        return handleException(CommonError.VALIDATION_ERROR, exception,
                () -> responseFactory.createErrorResponse(CommonError.VALIDATION_ERROR, validationErrors));
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<Object> handleConstraintViolationException(final ConstraintViolationException exception) {
        return handleException(CommonError.VALIDATION_ERROR, exception,
                () -> responseFactory.createErrorResponse(CommonError.VALIDATION_ERROR, List.of(exception.getMessage())));
    }

    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    protected ResponseEntity<Object> handleUnexpectedException(final HttpRequestMethodNotSupportedException exception) {
        return handleClientException(HttpStatus.METHOD_NOT_ALLOWED, exception);
    }

    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    protected ResponseEntity<Object> handleUnexpectedException(final HttpMediaTypeNotSupportedException exception) {
        return handleClientException(HttpStatus.UNSUPPORTED_MEDIA_TYPE, exception);
    }

    @ExceptionHandler({HttpMediaTypeNotAcceptableException.class})
    protected ResponseEntity<Object> handleUnexpectedException(final HttpMediaTypeNotAcceptableException exception) {
        return handleClientException(HttpStatus.NOT_ACCEPTABLE, exception);
    }

    @ExceptionHandler({MissingServletRequestParameterException.class, ServletRequestBindingException.class, TypeMismatchException.class,
            HttpMessageNotReadableException.class, MissingServletRequestPartException.class, BindException.class,
            MultipartException.class})
    protected ResponseEntity<Object> handleUnexpectedException(final Exception exception) {
        return handleClientException(HttpStatus.BAD_REQUEST, exception);
    }

    private ResponseEntity<Object> handleClientException(final HttpStatus status, final Exception exception) {
        return handleException(CommonError.CLIENT_ERROR, status, exception,
                () -> responseFactory.createErrorResponse(CommonError.CLIENT_ERROR, status.getReasonPhrase()));
    }

    private ResponseEntity<Object> handleException(
            final Error error, final HttpStatus httpStatus, final Throwable exception,
            final Supplier<ErrorResponse> errorResponseSupplier) {

        if (error.isLogged()) {
            getLogger().error(error.getMessage(), exception);
        }

        return new ResponseEntity<>(errorResponseSupplier.get(), HttpHeadersBuilder.withJsonContentType().build(), httpStatus);
    }

    private ResponseEntity<Object> handleException(
            final Error error, final Throwable exception,
            final Supplier<ErrorResponse> errorResponseSupplier) {
        return handleException(error, error.getDefaultHttpStatus(), exception, errorResponseSupplier);
    }
}
