/**
 * Class to represent a vim command.
 */
public abstract class Command {

    protected String command;
    protected String text;
    protected Selection selection;
    protected static String ILLEGAL_ARGUMENT_MESSAGE = "Command \"%s\" invalid.";

    protected Command(String command, String text, Selection selection) {
        this.command = command;
        this.text = text;
        this.selection = selection;
    }

    /**
     * Performs the command represented by this instance. Must be overridden.
     * @return the new text selection after command execution.
     * @throws IllegalArgumentException on unsupported command.
     */
    public abstract Selection execute() throws IllegalArgumentException;

    /**
     * Gets the index of the next word end appearing after the end of the selection.
     * @return index of next word end.
     */
    protected int getNextWordEnd() {
        int nextWordEnd = text.length() - 1;
        for (int i = selection.getEndIndex() + 1; i < text.length() - 1; i++) {
            char ch = text.charAt(i);
            char nextCh = text.charAt(i + 1);
            if ((isLetterDigitOrUnderscore(ch) && !isLetterDigitOrUnderscore(nextCh)) ||
                    (isOtherChar(ch) && !isOtherChar(nextCh))) {
                nextWordEnd = i;
                break;
            }
        }
        return nextWordEnd;
    }

    /**
     * Gets the index of the first occurrence of the specified character after the end
     * of the selection.
     * @param ch the character to search for.
     * @return the index of the matching character if found, otherwise the selection's
     * end index.
     */
    protected int getNextMatch(char ch) {
        int matchIndex = text.indexOf(ch, selection.getEndIndex() + 1);
        matchIndex = matchIndex == -1 ? selection.getEndIndex() : matchIndex;
        matchIndex = matchIndex == selection.getEndIndex() ? matchIndex : matchIndex - 1;
        return matchIndex;
    }

    /**
     * Checks if the specified character is a letter, digit, or underscore.
     * @param ch character to check.
     * @return true if ch is a letter, digit, or underscore.
     */
    private boolean isLetterDigitOrUnderscore(char ch) {
        return Character.isLetterOrDigit(ch) || ch == '_';
    }

    /**
     * Checks if the specified character is a non-whitespace character that
     * is not a letter, digit, or underscore.
     * @param ch character to check.
     * @return true if ch is a non-whitespace character that is not a
     * letter, digit, or underscore.
     */
    private boolean isOtherChar(char ch) {
        return !(isLetterDigitOrUnderscore(ch) || Character.isWhitespace(ch));
    }
}
