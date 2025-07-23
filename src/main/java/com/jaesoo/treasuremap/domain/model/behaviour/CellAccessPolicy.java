package com.jaesoo.treasuremap.domain.model.behaviour;

import com.jaesoo.treasuremap.domain.model.explorer.Explorer;
import com.jaesoo.treasuremap.domain.model.map.MapCell;

import java.util.Set;

public interface CellAccessPolicy {
    boolean canAccess(MapCell cell);
    boolean canShareWith(Set<Explorer> occupants);
}
