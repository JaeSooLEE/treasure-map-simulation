package com.jaesoo.treasuremap.domain.model.explorer;

import com.jaesoo.treasuremap.domain.model.behaviour.CellAccessPolicy;
import com.jaesoo.treasuremap.domain.model.behaviour.Movable;
import com.jaesoo.treasuremap.domain.model.behaviour.Turnable;
import com.jaesoo.treasuremap.domain.model.geometry.Orientation;
import com.jaesoo.treasuremap.domain.model.geometry.Position;


public interface Explorer extends CellAccessPolicy, Turnable, Movable {
    String getName();
    Position getPosition();
    Orientation getOrientation();
    boolean hasActions();
    Character getNextAction();
    void obtainTreasure();
    int getCollectedTreasureCount();
}
