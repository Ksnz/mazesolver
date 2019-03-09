package com.github.ksnz;

public enum Direction {
    NORTH("u") {
        @Override
        Direction getOpposite() {
            return SOUTH;
        }
    },
    NORTHEAST("ur") {
        @Override
        Direction getOpposite() {
            return SOUTHWEST;
        }
    },
    EAST("r") {
        @Override
        Direction getOpposite() {
            return WEST;
        }
    },
    SOUTHEAST("dr") {
        @Override
        Direction getOpposite() {
            return NORTHWEST;
        }
    },
    SOUTH("d") {
        @Override
        Direction getOpposite() {
            return NORTH;
        }
    },
    SOUTHWEST("dl") {
        @Override
        Direction getOpposite() {
            return NORTHEAST;
        }
    },
    WEST("l") {
        @Override
        Direction getOpposite() {
            return EAST;
        }
    },
    NORTHWEST("ul") {
        @Override
        Direction getOpposite() {
            return SOUTHEAST;
        }
    };

    private final String label;

    Direction(String u) {
        this.label = u;
    }

    abstract Direction getOpposite();

    public String getLabel() {
        return label;
    }
}