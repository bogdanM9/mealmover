package mealmover.backend.utils;

public class StringUtils {
    /**
     * Converts a string from uppercase with underscores to properly capitalized text.
     * Examples:
     * - "SPECIALIZED_ASSESSMENT" -> "Specialized assessment"
     * - "MALE" -> "Male"
     * - "Admin" -> "Admin" (no change needed)
     * - "A_B_C" -> "A b c"
     *
     * @param input The string to convert
     * @return The converted string
     */
    public static String toConvert(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        String spacedString = input.replace('_', ' ');

        String lowercaseString = spacedString.toLowerCase();

        return Character.toUpperCase(lowercaseString.charAt(0)) + lowercaseString.substring(1);
    }

    /**
     * Converts a string from properly capitalized text to uppercase with underscores.
     * Examples:
     * - "Specialized assessment" -> "SPECIALIZED_ASSESSMENT"
     * - "Male" -> "MALE"
     * - "Admin" -> "ADMIN"
     * - "A b c" -> "A_B_C"
     *
     * @param input The string to convert
     * @return The converted string
     */
    public static String fromConvert(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        String underscoredString = input.replace(' ', '_');

        return underscoredString.toUpperCase();
    }

    public static String toCamelCase(String str){
        if(!str.contains("_")) {
            return str.toLowerCase();
        }

        String[] words = str.split("_");

        StringBuilder result = new StringBuilder(words[0].toLowerCase());

        for(int i = 1; i < words.length; i++) {
            String word = words[i];
            if(!word.isEmpty()) {
                result
                    .append(Character.toUpperCase(word.charAt(0)))
                    .append(word.substring(1).toLowerCase());
            }
        }

        return result.toString();
    }
}
