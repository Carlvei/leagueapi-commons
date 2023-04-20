package at.adesso.leagueapi.commons.errorhandling;

import at.adesso.leagueapi.commons.errorhandling.exception.ResourceNotFoundException;
import at.adesso.leagueapi.commons.errorhandling.exception.TechnicalException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;

public class JsonErrorController {

    @GetMapping("/error")
    public void handleError(final HttpServletRequest request) {
        final Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            final int statusCode = Integer.parseInt(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                throw new ResourceNotFoundException();
            }
        }

        // Should actually not happen
        throw new TechnicalException();
    }
}
