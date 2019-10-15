import java.util.Scanner;

/**
 * Class to emulate a simple subset of vim navigation and selection commands.
 */
public class Mim {

    private String text; // input text
    private Selection selection; // represents the current selection of characters
    private static String SELECTION_START_CHAR = "[";
    private static String SELECTION_END_CHAR = "]";
    private static String EXIT_CHAR = "q";
    private static String IMPROPER_ARGUMENTS_MESSSAGE = "Please provide a single runtime argument.";
    private static String EMPTY_STRING = "";

    public Mim(String text) {
        this.text = text != null ? text : EMPTY_STRING;
        this.selection = new Selection(0, 0);
    }

    /**
     * Continuously reads user input from System.in and displays command
     * results until the exit character is entered.
     */
    public void start() {
        System.out.println(getDisplay());
        Scanner reader = new Scanner(System.in);
        String input = reader.nextLine();
        while (input.compareTo(EXIT_CHAR) != 0) {
            String response;
            try {
                executeCommand(input);
                response = getDisplay();
            }
            catch (IllegalArgumentException ex) {
                response = ex.getMessage();
            }
            System.out.println(response);
            input = reader.nextLine();
        }
    }

    /**
     * Processes the specified vim command and updates the selection state.
     * @param command a vim command as a String.
     * @throws IllegalArgumentException if command is an unsupported vim navigation or selection command.
     */
    public void executeCommand(String command) throws IllegalArgumentException {
        if (text.equals(EMPTY_STRING))
            return;

        Command mimCommand;
        if (command.startsWith("v"))
            mimCommand = new SelectCommand(command, text, selection);
        else
            mimCommand = new NavigationCommand(command, text, selection);

        selection = mimCommand.execute();
    }

    /**
     * Generates and returns the display string corresponding to the current state of
     * the selection, with the '[' and ']' characters added before the first and after
     * the last selection characters, respectively.
     * @return the display string.
     */
    public String getDisplay() {
        if (text.equals(EMPTY_STRING))
            return SELECTION_START_CHAR + SELECTION_END_CHAR;

        StringBuilder builder = new StringBuilder();
        int start = Math.min(selection.getStartIndex(), selection.getEndIndex());
        int end = Math.max(selection.getStartIndex(), selection.getEndIndex());
        builder.append(text.substring(0, start)).append(SELECTION_START_CHAR);
        builder.append(text.substring(start, end+1)).append(SELECTION_END_CHAR);
        if (end != text.length() - 1)
            builder.append(text.substring(end+1, text.length()));

        return builder.toString();
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println(IMPROPER_ARGUMENTS_MESSSAGE);
            return;
        }
        Mim mim = new Mim(args[0]);
        mim.start();
    }
}
