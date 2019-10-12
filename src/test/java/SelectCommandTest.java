import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SelectCommandTest {

    private SelectCommand command;
    private static String TEXT1 = "Hello World";

    @Test
    public void test_select_until_last_char() {
        command = new SelectCommand("v$", TEXT1, new Selection(0, 0));
        Selection actual = command.execute();
        Selection expected = new Selection(0, TEXT1.length() - 1);
        assertEquals(expected, actual);
    }
}
