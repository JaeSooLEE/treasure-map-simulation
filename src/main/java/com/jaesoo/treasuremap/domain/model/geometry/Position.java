package com.jaesoo.treasuremap.domain.model.geometry;

import java.util.Objects;

public record Position(int x, int y) {

    public Position {
        if (x < 0 || y < 0)
            throw new IllegalArgumentException("Coordinates must be positive");
    }

    public Position simpleMovement(Orientation orientation) {
        Objects.requireNonNull(orientation, "Orientation null");
        return new Position(x + orientation.getDx(), y + orientation.getDy());
    }
}
