package com.jaesoo.treasuremap.application.port.out;

import com.jaesoo.treasuremap.domain.model.map.TreasureMap;

public interface MapWriterPort {
    void writeMap(TreasureMap map, String outputPath);
}
