package com.jaesoo.treasuremap.domain.model.exception.map;

public class InvalidMapException extends RuntimeException{
    private final MapValidationErrorCode errorCode;
    private final int x;
    private final int y;

    public InvalidMapException(MapValidationErrorCode errorCode, int x, int y) {
        super(errorCode.getDefaultMessage() + "(x= " + x + ", y= " + y + ")");
        this.errorCode = errorCode;
        this.x = x;
        this.y = y;
    }

    public MapValidationErrorCode getErrorCode() {
        return errorCode;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
