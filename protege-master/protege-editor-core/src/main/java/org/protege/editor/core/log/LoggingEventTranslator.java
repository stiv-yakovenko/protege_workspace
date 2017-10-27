package org.protege.editor.core.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import com.google.common.collect.ImmutableList;
import org.slf4j.Marker;

import java.util.Optional;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 05/11/15
 */
public class LoggingEventTranslator {

    public LogRecord toLogRecord(ILoggingEvent event) {
        return new LogRecord(
                toLogLevel(event.getLevel()),
                event.getTimeStamp(),
                getMessage(event),
                toThrowableInfo(event),
                event.getThreadName()
        );
    }

    private String getMessage(ILoggingEvent event) {
        Marker marker = event.getMarker();
        StringBuilder sb = new StringBuilder();
        if (marker != null) {
            sb.append("[");
            sb.append(marker.getName());
            sb.append("]  ");
        }
        sb.append(event.getFormattedMessage());
        return sb.toString();
    }


    private static LogLevel toLogLevel(Level level) {
        if (level.equals(Level.ERROR)) {
            return LogLevel.ERROR;
        }
        else if (level.equals(Level.WARN)) {
            return LogLevel.WARN;
        }
        else if (level.equals(Level.INFO)) {
            return LogLevel.INFO;
        }
        else if (level.equals(Level.DEBUG)) {
            return LogLevel.DEBUG;
        }
        else {
            return LogLevel.TRACE;
        }
    }

    private static Optional<ThrowableInfo> toThrowableInfo(ILoggingEvent event) {
        IThrowableProxy throwableProxy = event.getThrowableProxy();
        if(throwableProxy == null) {
            return Optional.empty();
        }
        ThrowableInfo toThrowableInfo = toThrowableInfo(throwableProxy);
        return Optional.of(toThrowableInfo);
    }

    private static ThrowableInfo toThrowableInfo(IThrowableProxy throwableProxy) {
        ImmutableList.Builder<StackTraceElement> result = ImmutableList.builder();
        for (StackTraceElementProxy proxy : throwableProxy.getStackTraceElementProxyArray()) {
            result.add(proxy.getStackTraceElement());
        }
        final Optional<ThrowableInfo> cause;
        if (throwableProxy.getCause() != null) {
            cause = Optional.of(toThrowableInfo(throwableProxy.getCause()));
        }
        else {
            cause = Optional.empty();
        }
        return new ThrowableInfo(
                throwableProxy.getClassName(),
                throwableProxy.getMessage(),
                result.build(),
                cause
        );
    }
}
