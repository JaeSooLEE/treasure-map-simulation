package com.jaesoo.treasuremap.domain.model.behaviour;

import com.jaesoo.treasuremap.domain.model.geometry.Position;

public interface Movable {
    void move(Position position);
    Position getNextPosition();
}
