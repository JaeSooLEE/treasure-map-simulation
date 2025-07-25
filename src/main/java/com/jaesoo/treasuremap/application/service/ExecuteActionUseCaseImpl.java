package com.jaesoo.treasuremap.application.service;

import com.jaesoo.treasuremap.application.port.in.ExecuteActionUseCase;
import com.jaesoo.treasuremap.domain.model.explorer.Action;
import com.jaesoo.treasuremap.domain.model.explorer.Explorer;
import com.jaesoo.treasuremap.domain.model.map.TreasureMap;

public class ExecuteActionUseCaseImpl implements ExecuteActionUseCase {
    public void executeAction(Explorer explorer, TreasureMap map) {
        Action action = explorer.getNextAction();
        if (action == null) {
            throw new IllegalStateException("No available actions for explorer");
        }
        switch (action) {
            case Action.A -> map.moveExplorer(explorer);
            case Action.G -> explorer.turnLeft();
            case Action.D -> explorer.turnRight();
        }
    }
}
