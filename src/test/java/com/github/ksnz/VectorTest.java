package com.github.ksnz;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VectorTest {

    @Test
    void getFromDirection() {
        Vector vector = new Vector(0, 0);
        int size = Arrays.stream(Direction.values()).map(vector::getFromDirection).collect(Collectors.toSet()).size();
        assertEquals(8, size, "Число векторов должно быть равно 8.");
    }

    @Test
    void reflectionTest() {
        Vector vector = new Vector(1, 0);
        boolean equals = vector.equals(vector);
        assertTrue(equals, "Ошибка рефлексивности.");
    }

    @Test
    void cloneTest() {
        Vector vector = new Vector(-1, 1);
        boolean equals = vector.equals(vector.clone());
        assertTrue(equals, "Ошибка клонирования.");
    }

    @Test
    void symmetryTest() {
        Vector vector = new Vector(1, 5);
        Vector clone = vector.clone();
        boolean equals = vector.equals(clone) && clone.equals(vector);
        assertTrue(equals, "Ошибка симметричности.");
    }

    @Test
    void pathTest() {
        Vector vector = new Vector(0, 0);
        Vector sout = vector.getFromDirection(Direction.SOUTH);
        Vector southThenEast = sout.getFromDirection(Direction.EAST);
        Vector southEast = vector.getFromDirection(Direction.SOUTHEAST);
        boolean equals = southEast.equals(southThenEast);
        assertTrue(equals, "Ошибка равенства Г образного и диагонального пути.");
    }
}