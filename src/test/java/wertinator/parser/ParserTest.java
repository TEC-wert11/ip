package wertinator;

import org.junit.jupiter.api.Test;
import wertinator.parser.Parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for Parser command parsing.
 */
public class ParserTest {

    @Test
    public void getCommandWord_withArguments_returnsCommandWord() {
        assertEquals("todo", Parser.getCommandWord("todo read book"));
    }

    @Test
    public void getArguments_withArguments_returnsArgumentString() {
        assertEquals("read book", Parser.getArguments("todo read book"));
    }
}