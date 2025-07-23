package com.jaesoo.treasuremap.domain.model.geometry;

public class Position {
    private int x;
    private int y;

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
