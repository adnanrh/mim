import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NavigationCommandTest {

    private NavigationCommand command;
    private static String TEXT1 = "Hello World";

    @Test
    public void test_move_to_last_char() {
        command = new NavigationCommand("$", TEXT1, new Selection(0, 0));
        Selection actual = command.execute();
        Selection expected = new Selection(TEXT1.length() - 1, TEXT1.length() - 1);
        assertEquals(expected, actual);
    }
}
