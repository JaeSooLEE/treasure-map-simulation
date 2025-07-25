package com.jaesoo.treasuremap.domain.model.map;

import com.jaesoo.treasuremap.domain.model.explorer.Explorer;

import java.util.HashSet;
import java.util.Set;


/**
 * Gère l’occupation d’une {@link MapCell}:
 * tests d'accès selon la politique d'accès de chaque explorateur.
 */
public class CellOccupancy {

    private final Set<Explorer> occupants = new HashSet<>();

    public boolean isOccupied() {
        return !occupants.isEmpty();
    }

    public void enter(Explorer explorer) {
        occupants.add(explorer);
    }

    public void leave(Explorer explorer) {
        occupants.remove(explorer);
    }

    public boolean canBeOccupiedBy(Explorer explorer) {
        return explorer.canShareWith(occupants);
    }


}
