package at.adesso.leagueapi.commons.errorhandling.exceptions;


import at.adesso.leagueapi.commons.errorhandling.error.CommonError;

public class ValidationFailedException extends ApiException {

    public ValidationFailedException() {
        super(CommonError.VALIDATION_ERROR);
    }

    public ValidationFailedException(final String message) {
        super(CommonError.VALIDATION_ERROR, message);
    }
}
