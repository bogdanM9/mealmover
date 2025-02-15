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

    public static String capitalize(String str){
        return str.substring(0,1).toUpperCase() + str.substring(1).toLowerCase();
    }
}
