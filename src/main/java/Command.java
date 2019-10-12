public abstract class Command {

    protected String command;
    protected String text;
    protected Selection selection;
    protected static String ILLEGAL_ARGUMENT_MESSAGE = "Command \"%s\" invalid.";

    public Command(String command, String text, Selection selection) {
        this.command = command;
        this.text = text;
        this.selection = selection;
    }

    public abstract Selection execute() throws IllegalArgumentException;
}
