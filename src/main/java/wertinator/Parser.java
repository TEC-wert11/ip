package wertinator;

/**
 * Parser class to handle parsing of input.
 */
public class Parser {

    /**
     * take the input string and find the first word (separated by " ")
     * return the word
     * @param fullCommand
     * @return
     */
    public static String getCommandWord(String fullCommand) {
        String trimmed = fullCommand.trim();

        if (trimmed.isEmpty()) {
            return "";
        }

        String[] parts = trimmed.split(" ", 2);
        return parts[0];
    }

    /**
     * take the input string and find the phrase after the first word (separated by " ")
     * return the phrase
     * @param fullCommand
     * @return
     */
    public static String getArguments(String fullCommand) {
        String trimmed = fullCommand.trim();

        String[] parts = trimmed.split(" ", 2);

        if (parts.length < 2) {
            return "";
        }
        return parts[1];
    }
}