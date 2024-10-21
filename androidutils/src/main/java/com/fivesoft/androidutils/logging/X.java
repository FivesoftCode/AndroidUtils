package com.fivesoft.androidutils.logging;

import androidx.annotation.NonNull;

import com.fivesoft.javautils.RateLimiter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/**
 * A simple logging utility for Android and Java applications.
 * Provides shortcuts for logging messages with different priorities
 * and auto-generated tags featuring class names and method names.
 */
public class X {

    public static final Logger DEFAULT_ANDROID_LOGGER = new SystemOutLogger() {
        @Override
        public void printMessage(@NonNull Priority priority, @Nullable String tag, @Nullable Object message) {
            android.util.Log.println(priority.value, tag, String.valueOf(message));
        }
    };

    public static final Logger DEFAULT_JAVA_LOGGER = new SystemOutLogger() {
        @Override
        public void printMessage(@NotNull Priority priority, @Nullable String tag, @Nullable Object message) {
            System.out.println("[" + priority + "] " + tag + ": " + message);
        }
    };

    public static final String TAG_AUTO = null;
    public static final String TAG_CODE_FLOW = "";

    private static boolean productionMode = false;
    private static boolean loggingEnabled = true;
    private static boolean shortenClassName = false;
    private static boolean includeMethodName = true;
    private static boolean includeLineNumber = false;

    /*
     * We use an array to store loggers because it is faster than a list.
     * The array is initialized with a size of 20, which is the maximum number of loggers that can be added.
     */
    private static final Logger[] loggers = new Logger[20];

    static {
        addLogger(DEFAULT_ANDROID_LOGGER);
    }

    /**
     * Enables/disables logging globally. No loggers will receive log messages if logging is disabled.
     *
     * @param enabled True to enable logging, false to disable.
     */
    public static void setLoggingEnabled(boolean enabled) {
        loggingEnabled = enabled;
    }

    /**
     * Sets whether the application is in production mode.
     *
     * @param enabled The logging mode to set.
     */
    public static void setProductionMode(boolean enabled) {
        productionMode = enabled;
        if (productionMode) {
            //Remove non-production loggers
            for (Logger logger : loggers) {
                if (logger != null && !logger.isProductionLogger()) {
                    removeLogger(logger);
                }
            }
        }
    }

    /**
     * Checks if logging is enabled.
     *
     * @return True if logging is enabled, false if disabled.
     * @see #setLoggingEnabled(boolean)
     */
    public static boolean isLoggingEnabled() {
        return loggingEnabled;
    }

    /**
     * Checks if the application is in production mode.
     *
     * @return True if the application is in production mode, false if not.
     * @see #setProductionMode(boolean)
     */
    public static boolean isInProductionMode() {
        return productionMode;
    }

    /**
     * Determines whether auto-generated tags should contain shortened class names.
     * <b>Note: </b>Shortening class names requires additional processing and may affect performance.
     *
     * @param shorten True to shorten class names, false to use full class names.
     */
    public static void setShortenClassName(boolean shorten) {
        shortenClassName = shorten;
    }

    /**
     * Determines whether auto-generated tags should contain method names.
     *
     * @param include True to include method names, false to exclude.
     */
    public static void setIncludeMethodName(boolean include) {
        includeMethodName = include;
    }

    /**
     * Determines whether auto-generated tags should contain line numbers.
     * @param include whether to include line numbers in the tag
     */
    public static void setIncludeLineNumber(boolean include) {
        includeLineNumber = include;
    }

    /**
     * Add a logger to receive log messages.
     *
     * @param logger The logger to add.
     * @return True if the logger was added, false if it was already added.
     * @throws IllegalStateException If more than 20 loggers are added.
     */
    public static boolean addLogger(@NotNull Logger logger) {
        //Don't add non-production loggers in production mode
        if (productionMode && !logger.isProductionLogger()) {
            return false;
        }
        //Check if the logger is already added
        for (Logger l : loggers) {
            if (l == logger)
                return false;
        }
        //Add the logger
        for (int i = 0; i < loggers.length; i++) {
            if (loggers[i] == null) {
                loggers[i] = logger;
                optimizeLoggers();
                return true;
            }
        }
        throw new IllegalStateException("Cannot add more than 20 loggers.");
    }

    /**
     * Remove a logger from receiving log messages.
     *
     * @param logger The logger to remove.
     * @return True if the logger was removed, false if it was not found.
     */
    public static boolean removeLogger(@NotNull Logger logger) {
        //Remove the logger
        for (int i = 0; i < loggers.length; i++) {
            if (Objects.equals(loggers[i], logger)) {
                loggers[i] = null;
                return true;
            }
        }
        return false;
    }

    /**
     * Log a message with a specific priority and tag.
     *
     * @param priority The priority of the message. Use constants from android.util.Log class.
     * @param tag      The tag of the message.
     * @param message  The message to log.
     * @param stackTrace  The stack trace from which the log was called. May be null if not provided.
     * @param stackTraceOffset The offset of the stack trace to use. 0 is the current method, 1 is the method that called the current method, and so on.
     */
    public static void log(@NotNull Priority priority, @Nullable String tag,
                           @Nullable Object message, @Nullable StackTraceElement[] stackTrace,
                           int stackTraceOffset) {
        //Check if logging is enabled
        if (!loggingEnabled)
            return;
        //Pass the log message to all loggers
        for (Logger logger : loggers) {
            if (logger == null) {
                //Loggers are sorted by nullable values,
                // so if we encounter a null logger, we can break the loop
                break;
            }
            if (productionMode && !logger.isProductionLogger()) {
                //App is in production mode, skip non-production loggers
                continue;
            }
            logger.log(priority, tag, message, stackTrace, stackTraceOffset, 1);
        }
    }

    /**
     * Log a message with a specific priority and tag.
     *
     * @param priority The priority of the message. Use constants from android.util.Log class.
     * @param tag      The tag of the message.
     * @param message  The message to log.
     * @param stackTrace  The stack trace from which the log was called. May be null if not provided.
     */
    public static void log(@NotNull Priority priority, @Nullable String tag,
                           @Nullable Object message, @Nullable StackTraceElement[] stackTrace) {
        log(priority, tag, message, stackTrace, 0);
    }
    
    /**
     * Log a message with a specific priority and tag.
     *
     * @param priority The priority of the message. Use constants from android.util.Log class.
     * @param tag      The tag of the message.
     * @param message  The message to log.
     * @param stackTraceOffset The offset of the stack trace to use. 0 is the current method, 1 is the method that called the current method, and so on.
     */
    public static void log(@NotNull Priority priority, @Nullable String tag,
                           @Nullable Object message, int stackTraceOffset) {
        log(priority, tag, message, Thread.currentThread().getStackTrace(), 3 + stackTraceOffset);
    }
    
    /**
     * Log a message with a specific priority and tag.
     *
     * @param priority The priority of the message. Use constants from android.util.Log class.
     * @param tag      The tag of the message.
     * @param message  The message to log.
     */
    public static void log(@NotNull Priority priority, @Nullable String tag, @Nullable Object message) {
        log(priority, tag, message, null, 0);
    }

    /**
     * Log a message with a {@link Priority#VERBOSE} priority.
     *
     * @param tag     The tag of the message.
     * @param message The message to log.
     */
    public static void v(@Nullable String tag, @Nullable Object message) {
        log(Priority.VERBOSE, tag, message);
    }

    /**
     * Log a message with a {@link Priority#DEBUG} priority.
     *
     * @param tag     The tag of the message.
     * @param message The message to log.
     */
    public static void d(@Nullable String tag, @Nullable Object message) {
        log(Priority.DEBUG, tag, message);
    }

    /**
     * Log a message with a {@link Priority#INFO} priority.
     *
     * @param tag     The tag of the message.
     * @param message The message to log.
     */
    public static void i(@Nullable String tag, @Nullable Object message) {
        log(Priority.INFO, tag, message);
    }

    /**
     * Log a message with a {@link Priority#WARN} priority.
     *
     * @param tag     The tag of the message.
     * @param message The message to log.
     */
    public static void w(@Nullable String tag, @Nullable Object message) {
        log(Priority.WARN, tag, message);
    }

    /**
     * Log a message with a {@link Priority#ERROR} priority.
     *
     * @param tag     The tag of the message.
     * @param message The message to log.
     */
    public static void e(@Nullable String tag, @Nullable Object message) {
        log(Priority.ERROR, tag, message);
    }

    /**
     * Log a message with a {@link Priority#ASSERT} priority.
     *
     * @param tag     The tag of the message.
     * @param message The message to log.
     */
    public static void a(@Nullable String tag, @Nullable Object message) {
        log(Priority.ASSERT, tag, message);
    }

    /**
     * Log a message with a {@link Priority#VERBOSE} priority and auto-generated tag.
     *
     * @param message The message to log.
     */
    public static void v(@Nullable Object message) {
        v(TAG_AUTO, message);
    }

    /**
     * Log a message with a {@link Priority#DEBUG} priority and auto-generated tag.
     *
     * @param message The message to log.
     */
    public static void d(@Nullable Object message) {
        d(TAG_AUTO, message);
    }

    /**
     * Log a message with a {@link Priority#INFO} priority and auto-generated tag.
     *
     * @param message The message to log.
     */
    public static void i(@Nullable Object message) {
        i(TAG_AUTO, message);
    }

    /**
     * Log a message with a {@link Priority#WARN} priority and auto-generated tag.
     *
     * @param message The message to log.
     */
    public static void w(@Nullable Object message) {
        w(TAG_AUTO, message);
    }

    /**
     * Log a message with a {@link Priority#ERROR} priority and auto-generated tag.
     *
     * @param message The message to log.
     */
    public static void e(@Nullable Object message) {
        e(TAG_AUTO, message);
    }

    /**
     * Log a message with a {@link Priority#ASSERT} priority and auto-generated tag.
     *
     * @param message The message to log.
     */
    public static void a(@Nullable Object message) {
        a(TAG_AUTO, message);
    }

    /**
     * Log a current class name and method name with a {@link Priority#VERBOSE} priority
     * AND {@link #TAG_CODE_FLOW} tag.
     */
    public static void v() {
        v(TAG_CODE_FLOW, null);
    }

    /**
     * Log a current class name and method name with a {@link Priority#DEBUG} priority
     * AND {@link #TAG_CODE_FLOW} tag.
     */
    public static void d() {
        d(TAG_CODE_FLOW, null);
    }

    /**
     * Log a current class name and method name with a {@link Priority#INFO} priority
     * AND {@link #TAG_CODE_FLOW} tag.
     */
    public static void i() {
        i(TAG_CODE_FLOW, null);
    }

    /**
     * Log a current class name and method name with a {@link Priority#WARN} priority
     * AND {@link #TAG_CODE_FLOW} tag.
     */
    public static void w() {
        w(TAG_CODE_FLOW, null);
    }

    /**
     * Log a current class name and method name with a {@link Priority#ERROR} priority
     * AND {@link #TAG_CODE_FLOW} tag.
     */
    public static void e() {
        e(TAG_CODE_FLOW, null);
    }

    /**
     * Log a current class name and method name with a {@link Priority#ASSERT} priority
     * AND {@link #TAG_CODE_FLOW} tag.
     */
    public static void a() {
        a(TAG_CODE_FLOW, null);
    }

    /**
     * Get a string representation of a stack trace.
     *
     * @param tr The stack trace to get the string from.
     * @return The stack trace as a string.
     */
    @NotNull
    public static String getStackTraceString(@NotNull StackTraceElement[] tr) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : tr) {
            sb.append("\tat ").append(element).append("\n");
        }
        return sb.toString();
    }

    /**
     * Get a string representation of a throwable's stack trace.
     *
     * @param tr The throwable to get the stack trace from.
     * @return The stack trace as a string.
     */
    @NotNull
    public static String getStackTraceString(@NotNull Throwable tr) {
        return getStackTraceString(tr.getStackTrace());
    }

    /**
     * Generates a tag for the log message using the current stack trace.
     *
     * @param forCodeFlow True to generate a tag for code flow logging.
     * @return The generated tag.
     */
    private static String generateTag(boolean forCodeFlow,
                                      @NotNull StackTraceElement element) {
        //Return the class name and method name
        String className = element.getClassName();
        if (shortenClassName) {
            int startInx = className.lastIndexOf('.') + 1;
            if (startInx > 0 && startInx < className.length()) {
                className = className.substring(startInx);
            }
        }
        if (forCodeFlow) {
            return className + "." + element.getMethodName() + "(" + element.getFileName() + ":" + element.getLineNumber() + ")";
        } else {
            return className + (includeMethodName ? ("." + element.getMethodName()) : "");
        }
    }

    @NotNull
    private static String generateMessage(@NotNull StackTraceElement element,
                                          @Nullable Object message) {
        if(includeLineNumber) {
            return message + " [" + element.getFileName() + ":" + element.getLineNumber() + "]";
        } else {
            return String.valueOf(message);
        }
    }

    @NotNull
    private static StackTraceElement findStackTraceElement(
            @NotNull StackTraceElement[] stackTrace, int stackTraceOffset) {
        StackTraceElement element = null;
        //Find first non anonymous class
        for (int i = stackTraceOffset; i < stackTrace.length; i++) {
            StackTraceElement e = stackTrace[i];
            if (!e.getClassName().contains("$")) {
                element = e;
                break;
            }
        }
        //Return the last element
        if (element == null) {
            element = stackTrace[stackTrace.length - 1];
        }
        return element;
    }

    /**
     * Optimizes the loggers array by removing null elements.
     * This method is called automatically when a logger is added or removed.
     */
    private static void optimizeLoggers() {
        List<Logger> list = new ArrayList<>(Arrays.asList(loggers));
        list.removeIf(Objects::isNull);
        System.arraycopy(
                list.toArray(loggers), 0, loggers, 0, loggers.length
        );
    }

    /**
     * An interface for receiving log events.
     * Implement this interface to receive log messages.
     */
    public interface Logger {

        /**
         * Called when a log message is received.
         *
         * @param priority The priority of the message.
         * @param tag      The tag of the message.
         * @param message  The message to log.
         */
        void log(@NotNull Priority priority,
                 @Nullable String tag,
                 @Nullable Object message,
                 @Nullable StackTraceElement[] stackTrace,
                 int stackTraceOffset,
                 int sameMessageCount);

        /**
         * Determines whether this logger is a production logger.
         * Production loggers are loggers that are used in production code.
         *
         * @return True if this logger is a production logger, false if not.
         */
        default boolean isProductionLogger() {
            return true;
        }

    }

    /**
     * Priority levels for logging.
     */
    public enum Priority {
        VERBOSE(2),
        DEBUG(3),
        INFO(4),
        WARN(5),
        ERROR(6),
        ASSERT(7);

        private final int value;

        Priority(int value) {
            this.value = value;
        }

    }

    private static abstract class SystemOutLogger implements Logger {
        @Override
        public final void log(@NotNull Priority priority, @Nullable String tag,
                              @Nullable Object message, @Nullable StackTraceElement[] stacktrace,
                              int stackTraceOffset, int sameMessageCount) {
            boolean codeFlowMode = Objects.equals(tag, TAG_CODE_FLOW);
            boolean autoGeneratedTag = Objects.equals(tag, TAG_AUTO);
            StackTraceElement element;
            if (stacktrace != null && (codeFlowMode || autoGeneratedTag || includeLineNumber)) {
                element = findStackTraceElement(stacktrace, stackTraceOffset);
            } else {
                element = null;
            }
            if (codeFlowMode) {
                //Code flow mode: tag is set to "CodeFlow" and the
                // message is the the class name and method name (if enabled)
                tag = "CodeFlow";
                if (stacktrace != null) {
                    message = generateTag(true, element);
                }
            } else if(autoGeneratedTag) {
                //Empty tag: use the class name and method name
                if (stacktrace != null) {
                    tag = generateTag(false, element);
                }
            }
            if(element != null && !codeFlowMode && includeLineNumber){
                message = generateMessage(element, message);
            }
            if (tag != null) {
                //If tag is null here, it means logging is disabled
                printMessage(priority, tag, message);
            }
        }

        @Override
        public final boolean isProductionLogger() {
            return false;
        }

        public abstract void printMessage(@NotNull Priority priority, @NotNull String tag, @Nullable Object message);

    }

}
