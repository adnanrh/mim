import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SelectCommandTest {

    private SelectCommand command;
    private static String TEXT1 = "Hello World";

    @Test(expected=IllegalArgumentException.class)
    public void test_invalid_argument() {
        command = new SelectCommand("vp", TEXT1, new Selection(0, 0));
        command.execute();
    }

    @Test
    public void test_select_until_first_char() {
        command = new SelectCommand("v0", TEXT1, new Selection(TEXT1.length() - 1, TEXT1.length() - 1));
        Selection actual = command.execute();
        Selection expected = new Selection(TEXT1.length() - 1, 0);
        assertEquals(expected, actual);
    }

    @Test
    public void test_select_until_first_char_from_multi_selection() {
        command = new SelectCommand("v0", TEXT1, new Selection(3, 7));
        Selection actual = command.execute();
        Selection expected = new Selection(3, 0);
        assertEquals(expected, actual);
    }

    @Test
    public void test_select_until_first_char_from_multi_selection_backwards() {
        command = new SelectCommand("v0", TEXT1, new Selection(7, 3));
        Selection actual = command.execute();
        Selection expected = new Selection(7, 0);
        assertEquals(expected, actual);
    }

    @Test
    public void test_select_until_last_char() {
        command = new SelectCommand("v$", TEXT1, new Selection(0, 0));
        Selection actual = command.execute();
        Selection expected = new Selection(0, TEXT1.length() - 1);
        assertEquals(expected, actual);
    }

    @Test
    public void test_select_until_last_char_from_multi_selection() {
        command = new SelectCommand("v$", TEXT1, new Selection(3, 7));
        Selection actual = command.execute();
        Selection expected = new Selection(3, TEXT1.length() - 1);
        assertEquals(expected, actual);
    }

    @Test
    public void test_select_until_last_char_from_multi_selection_backwards() {
        command = new SelectCommand("v$", TEXT1, new Selection(7, 3));
        Selection actual = command.execute();
        Selection expected = new Selection(7, TEXT1.length() - 1);
        assertEquals(expected, actual);
    }

    @Test
    public void test_select_until_word_end() {
        command = new SelectCommand("ve", TEXT1, new Selection(0, 0));
        Selection actual = command.execute();
        Selection expected = new Selection(0, 4);
        assertEquals(expected, actual);
    }

    @Test
    public void test_select_until_word_end_from_multi_selection() {
        command = new SelectCommand("ve", TEXT1, new Selection(1, 4));
        Selection actual = command.execute();
        Selection expected = new Selection(1, TEXT1.length() - 1);
        assertEquals(expected, actual);
    }

    @Test
    public void test_select_until_word_end_from_multi_selection_backwards() {
        command = new SelectCommand("ve", TEXT1, new Selection(4, 1));
        Selection actual = command.execute();
        Selection expected = new Selection(4, 4);
        assertEquals(expected, actual);
    }

    @Test
    public void test_select_until_next_match() {
        command = new SelectCommand("vto", TEXT1, new Selection(0, 0));
        Selection actual = command.execute();
        Selection expected = new Selection(0, 3);
        assertEquals(expected, actual);
    }

    @Test
    public void test_select_until_next_match_from_multi_selection() {
        command = new SelectCommand("vto", TEXT1, new Selection(0, 4));
        Selection actual = command.execute();
        Selection expected = new Selection(0, 6);
        assertEquals(expected, actual);
    }

    @Test
    public void test_select_until_next_match_from_multi_selection_backwards() {
        command = new SelectCommand("vto", TEXT1, new Selection(4, 0));
        Selection actual = command.execute();
        Selection expected = new Selection(4, 3);
        assertEquals(expected, actual);
    }
}
