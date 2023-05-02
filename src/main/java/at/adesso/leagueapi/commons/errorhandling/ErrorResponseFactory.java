package at.adesso.leagueapi.commons.errorhandling;

import at.adesso.leagueapi.commons.errorhandling.error.Error;
import at.adesso.leagueapi.commons.errorhandling.exceptions.ApiException;
import at.adesso.leagueapi.commons.errorhandling.model.ErrorResponse;
import at.adesso.leagueapi.commons.logging.CorrelationContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Component
public class ErrorResponseFactory {

    public ErrorResponse createErrorResponse(final ApiException apiException) {
        final ErrorResponse response = createNewResponse();

        response.setMessage(apiException.getError().getMessage());
        response.setCode(apiException.getError().getCode());
        response.setDetails(apiException.getDetails());

        return response;
    }

    public ErrorResponse createErrorResponse(final Error error) {
        final ErrorResponse response = createNewResponse();

        response.setMessage(error.getMessage());
        response.setCode(error.getCode());
        response.setDetails(Collections.emptyList());

        return response;
    }

    public ErrorResponse createErrorResponse(final Error error, final String detail) {
        final ErrorResponse response = createErrorResponse(error);

        if (detail != null) {
            response.setDetails(Collections.singletonList(detail));
        }

        return response;
    }

    public ErrorResponse createErrorResponse(final Error error, final List<String> details) {
        final ErrorResponse response = createErrorResponse(error);

        response.setDetails(details);

        return response;
    }

    private ErrorResponse createNewResponse() {
        final ErrorResponse response = new ErrorResponse();

        response.setTimeStamp(LocalDateTime.now());
        response.setCorrelationId(CorrelationContext.getId());

        return response;
    }
}
