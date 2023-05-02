package at.adesso.leagueapi.commons.errorhandling.exceptions;

import at.adesso.leagueapi.commons.errorhandling.error.CommonError;

public class UnauthorizedAccessException extends ApiException {
    public UnauthorizedAccessException() {
        super(CommonError.UNAUTHORIZED);
    }

    public UnauthorizedAccessException(final String message) {
        super(CommonError.UNAUTHORIZED, message);
    }
}
