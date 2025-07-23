package com.jaesoo.treasuremap.domain.model.strategy;

import com.jaesoo.treasuremap.domain.model.geometry.Orientation;
import com.jaesoo.treasuremap.domain.model.geometry.Position;

public interface MovementStrategy {
    Position ComputeNextPosition(Position current, Orientation orientation);
}
