/**
 * Class to represent a vim navigation command.
 */
public class NavigationCommand extends Command {

    private static String MOVE_TO_FIRST_COMMAND = "0";
    private static String MOVE_TO_LAST_COMMAND = "$";
    private static String MOVE_TO_WORD_END_COMMAND = "e";
    private static String MOVE_TO_MATCH_COMMAND = "t";

    public NavigationCommand(String command, String text, Selection selection) {
        super(command, text, selection);
    }

    /**
     * Performs the command represented by this instance.
     * @return the new text selection after command execution.
     * @throws IllegalArgumentException on unsupported navigation command.
     */
    @Override
    public Selection execute() throws IllegalArgumentException {
        if (command.equals(MOVE_TO_FIRST_COMMAND))
            return moveToFirstChar();
        if (command.equals(MOVE_TO_LAST_COMMAND))
            return moveToLastChar();
        if (command.equals(MOVE_TO_WORD_END_COMMAND))
            return moveToWordEnd();
        if (command.startsWith(MOVE_TO_MATCH_COMMAND) && command.length() == 2)
            return moveToNextMatch(command.charAt(1));

        throw new IllegalArgumentException(String.format(ILLEGAL_ARGUMENT_MESSAGE, command));
    }

    /**
     * Returns a selection of the first character in the text.
     * @return a selection.
     */
    private Selection moveToFirstChar() {
        return new Selection(0, 0);
    }

    /**
     * Returns a selection of the last character in the text.
     * @return a selection.
     */
    private Selection moveToLastChar() {
        return new Selection(text.length() - 1, text.length() - 1);
    }

    /**
     * Returns a selection of the next word ending that appears after the end
     * of the current selection. Uses the definition of a word in vim defined
     * in https://www.fprintf.net/vimCheatSheet.html.
     * @return a selection.
     */
    private Selection moveToWordEnd() {
        int newPosition = getNextWordEnd();
        return new Selection(newPosition, newPosition);
    }

    /**
     * Returns a selection of the character appearing immediately before the
     * first occurrence of the specified character after the end of the current
     * selection.
     * @param findChar the character to match.
     * @return a selection.
     */
    private Selection moveToNextMatch(char findChar) {
        int newPosition = getNextMatch(findChar);
        return new Selection(newPosition, newPosition);
    }
}
