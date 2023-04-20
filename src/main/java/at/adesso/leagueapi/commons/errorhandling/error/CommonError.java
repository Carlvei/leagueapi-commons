package at.adesso.leagueapi.commons.errorhandling.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CommonError implements Error {

    UNKNOWN_SERVER_ERROR("ERR-CMN-001", "Unexpected server error.", HttpStatus.INTERNAL_SERVER_ERROR, true),
    CLIENT_ERROR("ERR-CMN-002", "Client error.", HttpStatus.BAD_REQUEST, true),
    RESOURCE_NOT_FOUND("ERR-CMN-003", "The requested resource is not available.", HttpStatus.NOT_FOUND, false),
    VALIDATION_ERROR("ERR-CMN-004", "The request did not validate successfully.", HttpStatus.BAD_REQUEST, true),
    UNAUTHORIZED("ERR-AUTH-001", "Invalid or missing authentication.", HttpStatus.UNAUTHORIZED, true),
    ACCESS_DENIED("ERR-AUTH-002", "Not authorized to access this resource.", HttpStatus.FORBIDDEN, true);
    private final String code;
    private final String message;
    private final HttpStatus defaultHttpStatus;
    private final boolean logged;

    CommonError(final String code, final String message, final HttpStatus defaultHttpStatus, final boolean logged) {
        this.code = code;
        this.message = message;
        this.defaultHttpStatus = defaultHttpStatus;
        this.logged = logged;
    }
}
