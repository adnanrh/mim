/**
 * Class to represent a vim selection command.
 */
public class SelectCommand extends Command {

    private static String SELECT_COMMAND_PREFIX = "v";
    private static String SELECT_FROM_FIRST_COMMAND = "0";
    private static String SELECT_UNTIL_LAST_COMMAND = "$";
    private static String SELECT_UNTIL_WORD_END_COMMAND = "e";
    private static String SELECT_UNTIL_NEXT_MATCH_COMMAND = "t";

    public SelectCommand(String command, String text, Selection selection) {
        super(command, text, selection);
    }

    /**
     * Performs the command represented by this instance.
     * @return the new text selection after command execution.
     * @throws IllegalArgumentException on unsupported selection command.
     */
    @Override
    public Selection execute() throws IllegalArgumentException {
        if (!command.startsWith(SELECT_COMMAND_PREFIX))
            throw new IllegalArgumentException(String.format(ILLEGAL_ARGUMENT_MESSAGE, command));

        if (command.length() == 2 && command.endsWith(SELECT_FROM_FIRST_COMMAND))
            return selectFromFirstChar();
        if (command.length() == 2 && command.endsWith(SELECT_UNTIL_LAST_COMMAND))
            return selectUntilLastChar();
        if (command.length() == 2 && command.endsWith(SELECT_UNTIL_WORD_END_COMMAND))
            return selectUntilWordEnd();
        if (command.length() == 3 && command.indexOf(SELECT_UNTIL_NEXT_MATCH_COMMAND) == 1)
            return selectUntilNextMatch(command.charAt(2));

        throw new IllegalArgumentException(String.format(ILLEGAL_ARGUMENT_MESSAGE, command));
    }

    /**
     * Returns a selection from the start of the current selection to the first
     * character in the text.
     * @return a selection.
     */
    private Selection selectFromFirstChar() {
        return new Selection(selection.getStartIndex(), 0);
    }

    /**
     * Returns a selection from the start of the current selection to the last
     * character in the text.
     * @return a selection.
     */
    private Selection selectUntilLastChar() {
        return new Selection(selection.getStartIndex(), text.length() - 1);
    }

    /**
     * Returns a selection from the start of the current selection to the next
     * word ending that appears after the end of the current selection. Uses the
     * definition of a word in vim defined in https://www.fprintf.net/vimCheatSheet.html.
     * @return a selection.
     */
    private Selection selectUntilWordEnd() {
        int newEndIndex = text.length() - 1;
        for (int i = selection.getEndIndex() + 1; i < text.length() - 1; i++) {
            char ch = text.charAt(i);
            char nextCh = text.charAt(i + 1);
            if ((isLetterDigitOrUnderscore(ch) && !isLetterDigitOrUnderscore(nextCh)) ||
                    (isOtherChar(ch) && !isOtherChar(nextCh))) {
                newEndIndex = i;
                break;
            }
        }

        return new Selection(selection.getStartIndex(), newEndIndex);
    }

    /**
     * Returns a selection from the start of the current selection to the character
     * appearing immediately before the first occurrence of the specified character
     * after the end of the current selection.
     * @return a selection.
     */
    private Selection selectUntilNextMatch(char findChar) {
        int newEndIndex = text.indexOf(findChar, selection.getEndIndex() + 1);
        newEndIndex = newEndIndex == -1 ? selection.getEndIndex() : newEndIndex;
        newEndIndex = newEndIndex == selection.getEndIndex() ? newEndIndex : newEndIndex - 1;
        return new Selection(selection.getStartIndex(), newEndIndex);
    }
}
