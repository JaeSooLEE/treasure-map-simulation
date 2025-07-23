package com.jaesoo.treasuremap.domain.model.exception;

public enum MapValidationErrorCode {
    NULL_GRID("La grille est null."),
    INVALID_HEIGHT("La hauteur de la carte est incorrecte."),
    INVALID_WIDTH("La largeur de la ligne est incorrecte."),
    NULL_LINE("La ligne est null"),
    NULL_CELL("La cellule est null."),
    INVALID_TERRAIN("La cellule a un terrain invalide."),
    OUT_OF_BOUNDS("Position hors des limites de la carte.");

    private final String defaultMessage;

    MapValidationErrorCode(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}