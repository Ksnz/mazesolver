package com.github.ksnz;

import org.junit.Test;

import java.util.Arrays;
import java.util.Map;

public class MainTest {

    @Test
    public void testParser() {
        Map<String, String> options = Main.parseOptions("-i test.maze -d -e -o file".split(" "));
        System.out.println(options);
    }

    @Test
    public void testEnum() {
        Vector[][] maze = new Vector[3][3];
        Vector vector = new Vector(1, 1);
        maze[vector.x][vector.y] = vector;
        for (Vector.Direction direction : Vector.Direction.values()) {
            Vector side = vector.getFromDirection(direction);
            //System.out.println(vector);
            maze[side.x][side.y] = side;
        }
        System.out.println(Arrays.deepToString(maze));
        System.out.println(Arrays.toString(maze[0]));
    }
}