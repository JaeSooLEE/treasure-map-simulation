package com.jaesoo.treasuremap.application.port.out;

import com.jaesoo.treasuremap.domain.model.map.TreasureMap;

public interface MapLoaderPort {
    TreasureMap loadMap(String inputPath);
}
