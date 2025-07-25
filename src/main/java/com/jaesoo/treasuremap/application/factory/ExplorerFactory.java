package com.jaesoo.treasuremap.application.factory;

import com.jaesoo.treasuremap.application.port.in.dto.ExplorerDTO;
import com.jaesoo.treasuremap.domain.model.explorer.Explorer;

public interface ExplorerFactory {

    Explorer create(ExplorerDTO explorerData);
}
