package at.adesso.leagueapi.commons.errorhandling.error;


import org.springframework.http.HttpStatus;

public interface Error {

    String getMessage();

    String getCode();

    HttpStatus getDefaultHttpStatus();

    boolean isLogged();
}
