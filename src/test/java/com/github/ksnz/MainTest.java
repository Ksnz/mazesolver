package com.github.ksnz;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class MainTest {

    public static final String[] ARGS = "-i test.maze -d -e -o file".split(" ");

    @Test
    public void testParser() {
        Map<String, String> options = Main.parseOptions(ARGS);
        assertEquals(options.size(), 4, String.format("Число аргументов в строке %s должно быть равно 4.", Arrays.toString(ARGS)));
    }
}