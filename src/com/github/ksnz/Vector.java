package com.github.ksnz;

public class Vector {

    final int x;

    final int y;

    Vector() {
        y = 0;
        x = 0;
    }

    Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    Vector(Vector v) {
        this.x = v.x;
        this.y = v.y;
    }

    boolean isInBounds(int[][] visited) {
        if (this.x >= 0 && this.y >= 0) {
            if (this.x < visited.length) {
                return this.y < visited[0].length;
            }
        }
        return false;
    }


    @Override
    public Vector clone() {
        return new Vector(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Vector) {
            Vector v = (Vector) obj;
            return (x == v.x) && (y == v.y);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + x;
        hash = 31 * hash + y;
        return hash;
    }

    @Override
    public String toString() {
        return "Vector[" + x + ", " + y + "]";
    }

    public Vector getFromDirection(Direction direction) {
        switch (direction) {
            case EAST:
                return new Vector(x, y + 1);
            case WEST:
                return new Vector(x, y - 1);
            case NORTH:
                return new Vector(x - 1, y);
            case SOUTH:
                return new Vector(x + 1, y);
            case NORTHEAST:
                return new Vector(x - 1, y + 1);
            case NORTHWEST:
                return new Vector(x - 1, y - 1);
            case SOUTHEAST:
                return new Vector(x + 1, y + 1);
            case SOUTHWEST:
                return new Vector(x + 1, y - 1);
            default:
                throw new IllegalArgumentException("Кто-то добавил новый енам");
        }
    }
}