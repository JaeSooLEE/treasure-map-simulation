package com.jaesoo.treasuremap.domain.model.geometry;

public enum Orientation {
    N(0, -1), E(1, 0), S(0, 1), W(-1, 0);

    private final int dx;
    private final int dy;

    Orientation(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public Orientation turnLeft() {
        return switch(this) {
            case N -> W;
            case W -> S;
            case S -> E;
            case E -> N;
        };
    }

    public Orientation turnRight() {
        return switch (this) {
            case N -> E;
            case E -> S;
            case S -> W;
            case W -> N;
        };
    }
}
