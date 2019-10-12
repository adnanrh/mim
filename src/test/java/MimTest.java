import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MimTest {

    private static Mim mim;
    private static String TEXT1 = "Hello World";

    @BeforeClass
    public static void classSetUp() {
        mim = new Mim(TEXT1);
    }

    @Test
    public void test_string_output_move_to_first() {
        String actual = mim.executeCommand("0");
        String expected = "[H]ello World";
        assertEquals(expected, actual);
    }
}
