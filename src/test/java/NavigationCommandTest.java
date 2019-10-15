import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class NavigationCommandTest {

    private NavigationCommand command;
    private static String TEXT1 = "Hello World";

    @Test(expected=IllegalArgumentException.class)
    public void test_invalid_argument() {
        command = new NavigationCommand("a", TEXT1, new Selection(0, 0));
        command.execute();
    }

    @Test
    public void test_move_to_first_char() {
        command = new NavigationCommand("0", TEXT1, new Selection(TEXT1.length() - 1, TEXT1.length() - 1));
        Selection actual = command.execute();
        Selection expected = new Selection(0, 0);
        assertEquals(expected, actual);
    }

    @Test
    public void test_move_to_first_char_from_multi_selection() {
        command = new NavigationCommand("0", TEXT1, new Selection(0, 4));
        Selection actual = command.execute();
        Selection expected = new Selection(0, 0);
        assertEquals(expected, actual);
    }

    @Test
    public void test_move_to_first_char_from_multi_selection_backwards() {
        command = new NavigationCommand("0", TEXT1, new Selection(4, 0));
        Selection actual = command.execute();
        Selection expected = new Selection(0, 0);
        assertEquals(expected, actual);
    }

    @Test
    public void test_move_to_last_char() {
        command = new NavigationCommand("$", TEXT1, new Selection(0, 0));
        Selection actual = command.execute();
        Selection expected = new Selection(TEXT1.length() - 1, TEXT1.length() - 1);
        assertEquals(expected, actual);
    }

    @Test
    public void test_move_to_last_char_from_multi_selection() {
        command = new NavigationCommand("$", TEXT1, new Selection(1, 6));
        Selection actual = command.execute();
        Selection expected = new Selection(TEXT1.length() - 1, TEXT1.length() - 1);
        assertEquals(expected, actual);
    }

    @Test
    public void test_move_to_last_char_from_multi_selection_backwards() {
        command = new NavigationCommand("$", TEXT1, new Selection(6, 1));
        Selection actual = command.execute();
        Selection expected = new Selection(TEXT1.length() - 1, TEXT1.length() - 1);
        assertEquals(expected, actual);
    }

    @Test
    public void test_move_to_word_end() {
        command = new NavigationCommand("e", TEXT1, new Selection(0, 0));
        Selection actual = command.execute();
        Selection expected = new Selection(4, 4);
        assertEquals(expected, actual);
    }

    @Test
    public void test_move_to_word_end_from_multi_selection() {
        command = new NavigationCommand("e", TEXT1, new Selection(0, 4));
        Selection actual = command.execute();
        Selection expected = new Selection(TEXT1.length() - 1, TEXT1.length() - 1);
        assertEquals(expected, actual);
    }

    @Test
    public void test_move_to_word_end_from_multi_selection_backwards() {
        command = new NavigationCommand("e", TEXT1, new Selection(4, 0));
        Selection actual = command.execute();
        Selection expected = new Selection(4, 4);
        assertEquals(expected, actual);
    }

    @Test
    public void test_move_to_next_match() {
        command = new NavigationCommand("tl", TEXT1, new Selection(0, 0));
        Selection actual = command.execute();
        Selection expected = new Selection(1, 1);
        assertEquals(expected, actual);
    }

    @Test
    public void test_move_to_next_match_from_multi_selection() {
        command = new NavigationCommand("tl", TEXT1, new Selection(0, 4));
        Selection actual = command.execute();
        Selection expected = new Selection(8, 8);
        assertEquals(expected, actual);
    }

    @Test
    public void test_move_to_next_match_from_multi_selection_backwards() {
        command = new NavigationCommand("tl", TEXT1, new Selection(4, 0));
        Selection actual = command.execute();
        Selection expected = new Selection(1, 1);
        assertEquals(expected, actual);
    }
}
