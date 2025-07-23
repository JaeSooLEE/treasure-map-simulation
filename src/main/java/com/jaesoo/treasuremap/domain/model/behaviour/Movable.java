package com.jaesoo.treasuremap.domain.model.behaviour;

import com.jaesoo.treasuremap.domain.model.geometry.Position;

public interface Movable {
    public void move(Position position);
    public Position getNextPosition();
}
