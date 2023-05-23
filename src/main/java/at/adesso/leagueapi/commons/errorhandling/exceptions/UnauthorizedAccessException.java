package at.adesso.leagueapi.commons.errorhandling.exceptions;

import at.adesso.leagueapi.commons.errorhandling.error.CommonError;
import at.adesso.leagueapi.commons.errorhandling.error.Error;

public class UnauthorizedAccessException extends ApiException {
    public UnauthorizedAccessException() {
        super(CommonError.UNAUTHORIZED);
    }

    public UnauthorizedAccessException(final Error error) {
        super(error);
    }
}
