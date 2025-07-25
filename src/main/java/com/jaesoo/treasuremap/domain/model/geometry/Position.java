package com.jaesoo.treasuremap.domain.model.geometry;

import java.util.Objects;


/**
 * Représente une position immuable sur la carte (coordonnées x, y). Les deux valeurs doivent être ≥ 0.
 *
 * @param x abscisse (0 = ouest)
 * @param y ordonnée (0 = nord)
 */
public record Position(int x, int y) {

    public Position {
        if (x < 0 || y < 0)
            throw new IllegalArgumentException("Coordinates must be positive");
    }


    /**
     * Renvoie la position en avançant d’une case selon l’orientation donnée.
     *
     * @param orientation direction du déplacement
     * @return nouvelle Position après mouvement
     */
    public Position simpleMovement(Orientation orientation) {
        Objects.requireNonNull(orientation, "Orientation null");
        return new Position(x + orientation.getDx(), y + orientation.getDy());
    }
}
