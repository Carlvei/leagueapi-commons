package at.adesso.leagueapi.commons.errorhandling.error;


import org.springframework.http.HttpStatus;

public interface Error {
    /**
     * @return The error message.
     */
    String getMessage();

    /**
     * @return The internal error code.
     */
    String getCode();

    /**
     * @return The default HTTP Status of this error.
     */
    HttpStatus getDefaultHttpStatus();

    /**
     * @return {@code true} in case the error should be logged.
     */
    boolean isLogged();
}
