package at.adesso.leagueapi.commons.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import ch.qos.logback.core.LayoutBase;
import ch.qos.logback.core.util.CachingDateFormatter;


public class LoggingLayout extends LayoutBase<ILoggingEvent> {

    private static final String SEPARATOR_REPLACING_SPECIAL_CHARS = " --- ";
    private static final CachingDateFormatter CACHING_DATE_FORMATTER = new CachingDateFormatter("yyyy-MM-dd HH:mm:ss");

    private String prefix = "[Default Log Prefix]";

    public void setPrefix(final String prefix) {
        this.prefix = prefix;
    }

    private String sanitizeMessage(final String inputString) {
        if (inputString == null) {
            return null;
        }
        return inputString
                .replace("\n", SEPARATOR_REPLACING_SPECIAL_CHARS)
                .replace("\r", SEPARATOR_REPLACING_SPECIAL_CHARS)
                .replace("\t", SEPARATOR_REPLACING_SPECIAL_CHARS)
                .replace("\f", SEPARATOR_REPLACING_SPECIAL_CHARS);
    }

    private String getMessageWithExceptionInfo(final ILoggingEvent event) {
        final StringBuilder sb = new StringBuilder(event.getFormattedMessage());
        final IThrowableProxy exc = event.getThrowableProxy();

        if (exc != null) {
            if (sb.length() > 0) {
                sb.append(" | ");
            }
            // Add all causes
            sb.append(getReadableInfoForSingleException(exc, 5));
            IThrowableProxy cause = exc.getCause();
            if (cause != null) {
                sb.append(" <- ");
                while (cause != null) {
                    sb.append(getReadableInfoForSingleException(cause, 5));
                    cause = cause.getCause();
                    if (cause != null) {
                        sb.append(" <- ");
                    }
                }
            }
        }

        return sb.toString();
    }

    private String getReadableInfoForSingleException(
            final IThrowableProxy throwableProxy,
            final int numberOfStackTraceElements) {

        final StringBuilder sb = new StringBuilder();
        sb.append(throwableProxy.getClassName()).append(": ").append(throwableProxy.getMessage());

        final StackTraceElementProxy[] stackTraceElements = throwableProxy.getStackTraceElementProxyArray();

        if (stackTraceElements != null && stackTraceElements.length > 0) {
            sb.append(" [ Stack Trace:");
            final int count = Math.min(numberOfStackTraceElements, stackTraceElements.length);
            for (int i = 0; i < count; i++) {
                sb.append(" ").append(stackTraceElements[i].toString());
                if (i < count - 1) {
                    sb.append(" <- ");
                }
            }
            sb.append(" ]");
        }
        return sb.toString();
    }

    @Override
    public String doLayout(final ILoggingEvent event) {
        final String correlationId = CorrelationContext.getId() == null ? "" : CorrelationContext.getId();

        return prefix + " " +
                CACHING_DATE_FORMATTER.format(event.getTimeStamp()) + " " +
                event.getThreadName() + ", " +
                correlationId + " " +
                String.format("[%-5s]", event.getLevel()) + " " +
                event.getLoggerName() + " - " +
                sanitizeMessage(getMessageWithExceptionInfo(event)) + "\n";
    }
}

