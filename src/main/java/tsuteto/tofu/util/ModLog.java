package tsuteto.tofu.util;

import org.slf4j.Logger;
import tsuteto.tofu.TofuCraftMod;

/**
 * Logging utility wrapper for TofuCraft.
 * Delegates to the mod's SLF4J logger with optional debug mode
 * controlled by the system property "tofucraft.debug".
 */
public final class ModLog {

    private static final Logger LOGGER = TofuCraftMod.LOGGER;
    private static final boolean DEBUG_ENABLED;

    static {
        String debugProp = System.getProperty("tofucraft.debug");
        DEBUG_ENABLED = "true".equalsIgnoreCase(debugProp);
    }

    private ModLog() {
        // Utility class; no instantiation
    }

    /**
     * Returns whether debug logging is active.
     * Controlled by {@code -Dtofucraft.debug=true}.
     */
    public static boolean isDebugEnabled() {
        return DEBUG_ENABLED;
    }

    /**
     * Logs an informational message.
     *
     * @param message the message pattern (SLF4J style)
     * @param args    arguments for the pattern
     */
    public static void info(String message, Object... args) {
        LOGGER.info(message, args);
    }

    /**
     * Logs a debug message. Only emitted when the system property
     * {@code tofucraft.debug} is set to {@code true}.
     *
     * @param message the message pattern (SLF4J style)
     * @param args    arguments for the pattern
     */
    public static void debug(String message, Object... args) {
        if (DEBUG_ENABLED) {
            LOGGER.info("[DEBUG] " + message, args);
        }
    }

    /**
     * Logs a warning message.
     *
     * @param message the message pattern (SLF4J style)
     * @param args    arguments for the pattern
     */
    public static void warn(String message, Object... args) {
        LOGGER.warn(message, args);
    }

    /**
     * Logs an error message.
     *
     * @param message the message pattern (SLF4J style)
     * @param args    arguments for the pattern
     */
    public static void error(String message, Object... args) {
        LOGGER.error(message, args);
    }

    /**
     * Logs an error message together with a throwable.
     *
     * @param message   the message
     * @param throwable the associated throwable
     */
    public static void error(String message, Throwable throwable) {
        LOGGER.error(message, throwable);
    }
}
