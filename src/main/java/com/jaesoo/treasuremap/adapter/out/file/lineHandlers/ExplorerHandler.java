package com.jaesoo.treasuremap.adapter.out.file.lineHandlers;

import com.jaesoo.treasuremap.domain.model.explorer.Action;
import com.jaesoo.treasuremap.domain.model.geometry.Orientation;

public interface ExplorerHandler extends LineHandler{
    default Action actionFromChar(char character) {
        return switch(character) {
            case 'A' -> Action.A;
            case 'G' -> Action.G;
            case 'D' -> Action.D;
            default -> throw new IllegalArgumentException("Invalid action entry");
        };
    }

    default Orientation orientationFromChar(char c) {
        return switch (c) {
            case 'N' -> Orientation.N;
            case 'S' -> Orientation.S;
            case 'E' -> Orientation.E;
            case 'O' -> Orientation.W;
            default  -> throw new IllegalArgumentException("Invalid orientation: " + c);
        };
    }
}
