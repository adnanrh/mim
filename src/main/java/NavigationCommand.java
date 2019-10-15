public class NavigationCommand extends Command {

    private static String MOVE_TO_FIRST_COMMAND = "0";
    private static String MOVE_TO_LAST_COMMAND = "$";
    private static String MOVE_TO_WORD_END_COMMAND = "e";
    private static String MOVE_TO_MATCH_COMMAND = "t";

    public NavigationCommand(String command, String text, Selection selection) {
        super(command, text, selection);
    }

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

    private Selection moveToFirstChar() {
        return new Selection(0, 0);
    }

    private Selection moveToLastChar() {
        return new Selection(text.length() - 1, text.length() - 1);
    }

    private Selection moveToWordEnd() {
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

        return new Selection(newEndIndex, newEndIndex);
    }

    private Selection moveToNextMatch(char findChar) {
        int newEndIndex = text.indexOf(findChar, selection.getEndIndex() + 1);
        newEndIndex = newEndIndex == -1 ? selection.getEndIndex() : newEndIndex;
        newEndIndex = newEndIndex == selection.getEndIndex() ? newEndIndex : newEndIndex - 1;
        return new Selection(newEndIndex, newEndIndex);
    }
}
