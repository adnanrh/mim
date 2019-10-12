import java.util.Scanner;

public class Mim {

    private String text;
    private Selection selection;
    private static String SELECTION_START_CHAR = "[";
    private static String SELECTION_END_CHAR = "]";
    private static String EXIT_CHAR = "q";
    private static String IMPROPER_ARGUMENTS_MESSSAGE = "Please provide a single runtime argument.";

    public Mim(String text) {
        this.text = text;
        this.selection = new Selection(0, 0);
    }

    public void start() {
        Scanner reader = new Scanner(System.in);
        String input = reader.nextLine();
        while (input.compareTo(EXIT_CHAR) != 0) {
            try {
                System.out.println(executeCommand(input));
            }
            catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
            }
            input = reader.nextLine();
        }
    }

    public String executeCommand(String command) throws IllegalArgumentException {
        Command mimCommand;
        if (command.startsWith("v"))
            mimCommand = new SelectCommand(command, text, selection);
        else
            mimCommand = new NavigationCommand(command, text, selection);

        selection = mimCommand.execute();
        return buildOutput();
    }

    private String buildOutput() {
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
