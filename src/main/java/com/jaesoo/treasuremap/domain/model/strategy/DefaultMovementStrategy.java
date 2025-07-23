package com.jaesoo.treasuremap.domain.model.strategy;

import com.jaesoo.treasuremap.domain.model.geometry.Orientation;
import com.jaesoo.treasuremap.domain.model.geometry.Position;

public class DefaultMovementStrategy implements MovementStrategy{
    @Override
    public Position ComputeNextPosition(Position current, Orientation orientation) {
        return current.simpleMovement(orientation);
    }
}
