package mealmover.backend.utils;

public class StringUtils {
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

    /**
     * Converts a string from uppercase with underscores to properly capitalized text.
     * Examples:
     * - "SPECIALIZED_ASSESSMENT" -> "Specialized assessment"
     * - "MALE" -> "Male"
     * - "Admin" -> "Admin" (no change needed)
     * - "A_B_C" -> "A b c"
     *
     * @param str The string to convert
     * @return The converted string
     */
    public static String toCapitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        String spacedString = str.replace('_', ' ');

        String lowercaseString = spacedString.toLowerCase();

        return Character.toUpperCase(lowercaseString.charAt(0)) + lowercaseString.substring(1);
    }

    /**
     * Converts a string from camelCase to UPPER_CASE_WITH_UNDERSCORES.
     * Examples:
     * - "resetPassword" -> "RESET_PASSWORD"
     * - "access" -> "ACCESS"
     * - "someComplexIdentifier" -> "SOME_COMPLEX_IDENTIFIER"
     *
     * @param str The camelCase string to convert
     * @return The UPPER_CASE_WITH_UNDERSCORES string
     */
    public static String fromCamelCase(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        StringBuilder result = new StringBuilder();

        // Add the first character (uppercase)
        result.append(Character.toUpperCase(str.charAt(0)));

        // Process the rest of the string
        for (int i = 1; i < str.length(); i++) {
            char c = str.charAt(i);

            // If uppercase letter found, add underscore before it
            if (Character.isUpperCase(c)) {
                result.append('_');
            }

            // Always add the uppercase version of the character
            result.append(Character.toUpperCase(c));
        }

        return result.toString();
    }

    /**
     * Converts a string from properly capitalized text to uppercase with underscores.
     * Examples:
     * - "Specialized assessment" -> "SPECIALIZED_ASSESSMENT"
     * - "Male" -> "MALE"
     * - "Admin" -> "ADMIN"
     * - "A b c" -> "A_B_C"
     *
     * @param str The string to convert
     * @return The converted string
     */
    public static String fromCapitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        String underscoredString = str.replace(' ', '_');

        return underscoredString.toUpperCase();
    }
}