package games.negative.apexcore.core.util;

import org.jetbrains.annotations.NotNull;

public class TextUtil {

    /**
     * Combine an array of strings into one string.
     * @param args The array of strings.
     * @return The combined string.
     */
    public static String combine(@NotNull String[] args) {
        StringBuilder builder = new StringBuilder();

        for (String arg : args) {
            builder.append(arg).append(" ");
        }

        return builder.toString().trim();
    }

}
