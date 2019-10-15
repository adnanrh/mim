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

    public abstract Selection execute() throws IllegalArgumentException;

    protected boolean isLetterDigitOrUnderscore(char ch) {
        return Character.isLetterOrDigit(ch) || ch == '_';
    }

    protected boolean isOtherChar(char ch) {
        return !(isLetterDigitOrUnderscore(ch) || Character.isWhitespace(ch));
    }
}
