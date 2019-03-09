package com.github.ksnz;


import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DirectionTest {

    @Test
    public void numberOfSides() {
        int length = Direction.values().length;
        assertEquals(8, length, "Число направлений должно быть равно 8.");
    }

    @Test
    public void numberOfOposideSides() {
        int size = Arrays.stream(Direction.values()).map(Direction::getOpposite).collect(Collectors.toSet()).size();
        assertEquals(8, size, "Число обратных направлений должно быть равно 8.");
    }

    @Test
    public void numberLabelsOfDirections() {
        int size = Arrays.stream(Direction.values()).map(Direction::getLabel).collect(Collectors.toSet()).size();
        assertEquals(8, size, "Число лейблов направлений должно быть равно 8.");
    }
}