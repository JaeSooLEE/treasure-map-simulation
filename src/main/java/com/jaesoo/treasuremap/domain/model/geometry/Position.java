package com.jaesoo.treasuremap.domain.model.geometry;

public class Position {
    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Position simpleMovement(Orientation orientation) {
        return new Position(x + orientation.getDx(), y + orientation.getDy());
    }
}
