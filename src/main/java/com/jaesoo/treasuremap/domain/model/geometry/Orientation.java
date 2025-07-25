package com.jaesoo.treasuremap.domain.model.geometry;


/**
 * Représente une direction cardinale sur la carte:
 * Nord, Est, Sud, Ouest.
 * Chaque orientation définit un delta (dx, dy) pour le déplacement d’une case, et fournit les orientations suivant un virage à gauche ou à droite.
 */
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
