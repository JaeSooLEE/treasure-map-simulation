package com.jaesoo.treasuremap.application.service;

import com.jaesoo.treasuremap.application.port.in.ExecuteActionUseCase;
import com.jaesoo.treasuremap.domain.model.explorer.Explorer;
import com.jaesoo.treasuremap.domain.model.map.TreasureMap;

public class ExecuteActionUseCaseImpl implements ExecuteActionUseCase {
    public void executeAction(Explorer explorer, TreasureMap map) {
        Character action = explorer.getNextAction();
        if (action == null) {
            throw new IllegalStateException("Aucune action disponible pour l'explorateur");
        }
        switch (action) {
            case 'A' -> map.moveExplorer(explorer);
            case 'G' -> explorer.turnLeft();
            case 'D' -> explorer.turnRight();
            default  -> throw new IllegalArgumentException("Action inconnue pour l'explorateur : " + action);
        }
    }
}
