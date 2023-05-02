package at.adesso.leagueapi.commons.errorhandling.exceptions;

import at.adesso.leagueapi.commons.errorhandling.error.CommonError;
import at.adesso.leagueapi.commons.errorhandling.error.Error;

public class TechnicalException extends ApiException {
    public TechnicalException(final Error error, final Throwable cause) {
        super(error, cause);
    }

    public TechnicalException(final Throwable cause) {
        super(CommonError.UNKNOWN_SERVER_ERROR, cause);
    }

    public TechnicalException() {
        super(CommonError.UNKNOWN_SERVER_ERROR);
    }
}
