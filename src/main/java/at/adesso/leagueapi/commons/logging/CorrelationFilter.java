package at.adesso.leagueapi.commons.logging;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

public class CorrelationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response, final FilterChain filterChain)
            throws ServletException, IOException {
        final String correlationId = getCorrelationIdFromHeader(request);

        CorrelationContext.setId(correlationId);

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        CorrelationContext.removeId();
    }

    private String getCorrelationIdFromHeader(final HttpServletRequest request) {
        final String correlationId = request.getHeader(CorrelationContext.CORRELATION_ID_HEADER);

        return StringUtils.isBlank(correlationId) ? generateUniqueCorrelationId() : correlationId;
    }

    private String generateUniqueCorrelationId() {
        return UUID.randomUUID().toString();
    }

}
