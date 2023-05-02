package at.adesso.leagueapi.commons.errorhandling.exceptions;


import at.adesso.leagueapi.commons.errorhandling.error.CommonError;

public class ResourceNotFoundException extends ApiException {

    public ResourceNotFoundException() {
        super(CommonError.RESOURCE_NOT_FOUND);
    }

    public ResourceNotFoundException(final String message) {
        super(CommonError.RESOURCE_NOT_FOUND, message);
    }
}
