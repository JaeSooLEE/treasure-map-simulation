package com.jaesoo.treasuremap.domain.model.explorer;

import com.jaesoo.treasuremap.domain.model.behaviour.CellAccessPolicy;
import com.jaesoo.treasuremap.domain.model.behaviour.Movable;
import com.jaesoo.treasuremap.domain.model.behaviour.Turnable;
import com.jaesoo.treasuremap.domain.model.geometry.Orientation;
import com.jaesoo.treasuremap.domain.model.geometry.Position;


/**
 * Comportement générique d’un explorateur:
 * peut se déplacer, tourner, collecter des trésors et décider de l’accessibilité d’une cellule.
 */
public interface Explorer extends CellAccessPolicy, Turnable, Movable {
    String getName();
    Position getPosition();
    Orientation getOrientation();
    boolean hasActions();
    Action getNextAction();
    void obtainTreasure();
    int getCollectedTreasureCount();
}
