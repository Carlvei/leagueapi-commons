package at.adesso.leagueapi.commons.errorhandling.exceptions;

import at.adesso.leagueapi.commons.errorhandling.error.Error;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ApiException extends RuntimeException {

    @Getter
    private final Error error;

    @Getter
    private final List<String> details;

    public ApiException(final Error error, final String details) {
        super(error.getMessage());

        this.details = Collections.singletonList(details);
        this.error = error;
    }

    public ApiException(final Error error, final Throwable exception) {
        super(exception);

        this.details = Collections.emptyList();
        this.error = error;
    }

    public ApiException(final Error error, final String... details) {
        super(error.getMessage());

        this.details = Arrays.asList(details);
        this.error = error;
    }

    public ApiException(final Error error, final List<String> details) {
        super(error.getMessage());

        this.details = details;
        this.error = error;
    }

    public ApiException(final Error error) {
        super(error.getMessage());

        this.details = Collections.emptyList();
        this.error = error;
    }
}
