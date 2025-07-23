package com.jaesoo.treasuremap.domain.model.map;

import com.jaesoo.treasuremap.domain.model.explorer.Explorer;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CellOccupancy {

    private final Set<Explorer> occupants = new HashSet<>();

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
