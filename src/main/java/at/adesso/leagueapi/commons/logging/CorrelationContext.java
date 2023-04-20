package at.adesso.leagueapi.commons.logging;

import org.slf4j.MDC;

public class CorrelationContext {

    public static final String CORRELATION_ID_HEADER = "correlation-id";
    public static final String CORRELATION_ID_VAR = "correlationId";

    private CorrelationContext() {
        // Static methods only
    }

    public static void setId(final String correlationId) {
        MDC.put(CORRELATION_ID_VAR, correlationId);
    }

    public static String getId() {
        return MDC.get(CORRELATION_ID_VAR);
    }

    public static void removeId() {
        MDC.remove(CORRELATION_ID_VAR);
    }
}
