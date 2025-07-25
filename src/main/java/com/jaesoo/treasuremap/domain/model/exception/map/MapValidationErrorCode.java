package com.jaesoo.treasuremap.domain.model.exception.map;

public enum MapValidationErrorCode {
    NULL_GRID("Grid is null."),
    INVALID_HEIGHT("Incorrect map height."),
    INVALID_WIDTH("Incorrect line width."),
    NULL_LINE("Line null"),
    NULL_CELL("Cell null."),
    INVALID_TERRAIN("Invalid cell terrain."),
    OUT_OF_BOUNDS("Position out of bounds.");

    private final String defaultMessage;

    MapValidationErrorCode(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}