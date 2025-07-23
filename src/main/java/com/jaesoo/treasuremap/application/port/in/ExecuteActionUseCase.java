package com.jaesoo.treasuremap.application.port.in;

import com.jaesoo.treasuremap.domain.model.explorer.Explorer;
import com.jaesoo.treasuremap.domain.model.map.TreasureMap;

public interface ExecuteActionUseCase {

    void executeAction(Explorer explorer, TreasureMap map);

}
