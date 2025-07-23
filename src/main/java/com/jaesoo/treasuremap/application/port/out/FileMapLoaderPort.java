package com.jaesoo.treasuremap.application.port.out;

import com.jaesoo.treasuremap.domain.model.map.TreasureMap;

public interface FileMapLoaderPort {
    TreasureMap loadMap(String inputPath);
}
