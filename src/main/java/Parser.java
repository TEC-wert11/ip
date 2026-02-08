public class Parser {

    public static String getCommandWord(String fullCommand) {
        String trimmed = fullCommand.trim();

        if (trimmed.isEmpty()) {
            return "";
        }

        String[] parts = trimmed.split(" ", 2);
        return parts[0];
    }

    public static String getArguments(String fullCommand) {
        String trimmed = fullCommand.trim();

        String[] parts = trimmed.split(" ", 2);

        if (parts.length < 2) {
            return "";
        }
        return parts[1];
    }
}