import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MimTest {

    private static Mim mim;
    private static String TEXT1 = "Hello World";
    private static String TEXT2 = "Hello, World.";
    private static String EMPTY_STRING = "";

    @Before
    public void setUp() {
        mim = new Mim(TEXT1);
    }

    @Test
    public void test_empty_text_display() {
        mim = new Mim(EMPTY_STRING);
        assertEquals("[]", mim.getDisplay());
    }

    @Test
    public void test_null_text_sets_empty_string() {
        mim = new Mim(null);
        assertEquals("[]", mim.getDisplay());
    }

    @Test
    public void test_invalid_command_no_state_change() {
        testExecuteInvalidCommand("abc");
        mim.executeCommand("e");
        assertEquals("Hell[o] World", mim.getDisplay());
        testExecuteInvalidCommand("cba");
        mim.executeCommand("e");
        assertEquals("Hello Worl[d]", mim.getDisplay());
        testExecuteInvalidCommand("vba");
        mim.executeCommand("v0");
        assertEquals("[Hello World]", mim.getDisplay());
        testExecuteInvalidCommand("vz");
        mim.executeCommand("tW");
        assertEquals("Hello[ ]World", mim.getDisplay());
    }

    @Test
    public void test_chain_command_repeat_word_end() {
        mim.executeCommand("e");
        assertEquals("Hell[o] World", mim.getDisplay());
        mim.executeCommand("e");
        assertEquals("Hello Worl[d]", mim.getDisplay());
        mim.executeCommand("0");
        assertEquals("[H]ello World", mim.getDisplay());
        mim.executeCommand("ve");
        assertEquals("[Hello] World", mim.getDisplay());
        mim.executeCommand("ve");
        assertEquals("[Hello World]", mim.getDisplay());
    }

    @Test
    public void test_chain_command_repeat_word_end_different_word_types() {
        mim = new Mim(TEXT2);
        mim.executeCommand("e");
        assertEquals("Hell[o], World.", mim.getDisplay());
        mim.executeCommand("e");
        assertEquals("Hello[,] World.", mim.getDisplay());
        mim.executeCommand("e");
        assertEquals("Hello, Worl[d].", mim.getDisplay());
        mim.executeCommand("e");
        assertEquals("Hello, World[.]", mim.getDisplay());
        mim.executeCommand("0");
        assertEquals("[H]ello, World.", mim.getDisplay());
        mim.executeCommand("ve");
        assertEquals("[Hello], World.", mim.getDisplay());
        mim.executeCommand("ve");
        assertEquals("[Hello,] World.", mim.getDisplay());
        mim.executeCommand("ve");
        assertEquals("[Hello, World].", mim.getDisplay());
        mim.executeCommand("ve");
        assertEquals("[Hello, World.]", mim.getDisplay());
    }

    @Test
    public void test_chain_command_repeat_next_match_and_no_match() {
        mim.executeCommand("to");
        assertEquals("Hel[l]o World", mim.getDisplay());
        mim.executeCommand("to");
        assertEquals("Hel[l]o World", mim.getDisplay());
        mim.executeCommand("tz");
        assertEquals("Hel[l]o World", mim.getDisplay());
        mim.executeCommand("tl");
        assertEquals("Hello Wo[r]ld", mim.getDisplay());
        mim.executeCommand("0");
        assertEquals("[H]ello World", mim.getDisplay());
        mim.executeCommand("vto");
        assertEquals("[Hell]o World", mim.getDisplay());
        mim.executeCommand("vto");
        assertEquals("[Hell]o World", mim.getDisplay());
        mim.executeCommand("vtz");
        assertEquals("[Hell]o World", mim.getDisplay());
        mim.executeCommand("vtl");
        assertEquals("[Hello Wor]ld", mim.getDisplay());
    }

    @Test
    public void test_chain_command_forward_backward_selections() {
        mim.executeCommand("v$");
        assertEquals("[Hello World]", mim.getDisplay());
        mim.executeCommand("tW");
        assertEquals("Hello Worl[d]", mim.getDisplay());
        mim.executeCommand("v0");
        assertEquals("[Hello World]", mim.getDisplay());
        mim.executeCommand("tW");
        assertEquals("Hello[ ]World", mim.getDisplay());
        mim.executeCommand("v0");
        assertEquals("[Hello ]World", mim.getDisplay());
        mim.executeCommand("vto");
        assertEquals("Hel[lo ]World", mim.getDisplay());
        mim.executeCommand("v$");
        assertEquals("Hello[ World]", mim.getDisplay());
        mim.executeCommand("v0");
        assertEquals("[Hello ]World", mim.getDisplay());
        mim.executeCommand("0");
        assertEquals("[H]ello World", mim.getDisplay());
        mim.executeCommand("v$");
        assertEquals("[Hello World]", mim.getDisplay());
        mim.executeCommand("v0");
        assertEquals("[H]ello World", mim.getDisplay());
    }

    private void testExecuteInvalidCommand(String command) {
        String lastDisplay = mim.getDisplay();
        try {
            mim.executeCommand(command);
        }
        catch (IllegalArgumentException ex) {
            assertEquals(String.format("Command \"%s\" invalid.", command), ex.getMessage());
            assertEquals(lastDisplay, mim.getDisplay());
        }
    }
}
