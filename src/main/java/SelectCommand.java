public class SelectCommand extends Command {

    private static String SELECT_COMMAND_PREFIX = "v";
    private static String SELECT_FROM_FIRST_COMMAND = "0";
    private static String SELECT_UNTIL_LAST_COMMAND = "$";
    private static String SELECT_UNTIL_WORD_END_COMMAND = "e";
    private static String SELECT_UNTIL_NEXT_MATCH_COMMAND = "t";

    public SelectCommand(String command, String text, Selection selection) {
        super(command, text, selection);
    }

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

    private Selection selectFromFirstChar() {
        return new Selection(selection.getStartIndex(), 0);
    }

    private Selection selectUntilLastChar() {
        return new Selection(selection.getStartIndex(), text.length() - 1);
    }

    private Selection selectUntilWordEnd() {
        int newEndIndex = text.length() - 1;
        for (int i = selection.getEndIndex() + 1; i < text.length() - 1; i++) {
            if (!Character.isWhitespace(text.charAt(i)) && Character.isWhitespace(text.charAt(i+1))) {
                newEndIndex = i;
                break;
            }
        }

        return new Selection(selection.getStartIndex(), newEndIndex);
    }

    private Selection selectUntilNextMatch(char findChar) {
        int newEndIndex = text.indexOf(findChar, selection.getEndIndex());
        newEndIndex = newEndIndex == -1 ? selection.getEndIndex() : newEndIndex;
        newEndIndex = newEndIndex == selection.getEndIndex() ? newEndIndex : newEndIndex - 1;
        return new Selection(selection.getStartIndex(), newEndIndex);
    }
}
