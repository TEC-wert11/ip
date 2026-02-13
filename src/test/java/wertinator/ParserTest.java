package wertinator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ParserTest {

    @Test
    public void getCommandWord_trimsAndExtractsFirstWord() {
        assertEquals("list", Parser.getCommandWord("list"));
        assertEquals("todo", Parser.getCommandWord("   todo read book"));
        assertEquals("deadline", Parser.getCommandWord("deadline  return book /by 2026-02-01"));
    }

    @Test
    public void getCommandWord_emptyOrSpaces_returnsEmptyString() {
        assertEquals("", Parser.getCommandWord(""));
        assertEquals("", Parser.getCommandWord("     "));
    }

    @Test
    public void getArguments_noArgs_returnsEmptyString() {
        assertEquals("", Parser.getArguments("list"));
        assertEquals("", Parser.getArguments("   list   "));
    }

    @Test
    public void getArguments_returnsEverythingAfterFirstSpace() {
        assertEquals("read book", Parser.getArguments("todo read book"));
        assertEquals("return book /by 2026-02-01", Parser.getArguments("deadline return book /by 2026-02-01"));
    }
}
