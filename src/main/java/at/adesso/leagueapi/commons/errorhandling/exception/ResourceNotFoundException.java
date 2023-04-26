package at.adesso.leagueapi.commons.errorhandling.exception;


import at.adesso.leagueapi.commons.errorhandling.error.CommonError;

public class ResourceNotFoundException extends ApiException {

    public ResourceNotFoundException() {
        super(CommonError.RESOURCE_NOT_FOUND);
    }
}
