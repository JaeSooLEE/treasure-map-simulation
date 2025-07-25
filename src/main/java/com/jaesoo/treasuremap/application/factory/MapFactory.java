package com.jaesoo.treasuremap.application.factory;

import com.jaesoo.treasuremap.application.port.in.dto.ExplorerDTO;
import com.jaesoo.treasuremap.application.port.in.dto.MountainDTO;
import com.jaesoo.treasuremap.application.port.in.dto.TreasureDTO;
import com.jaesoo.treasuremap.domain.model.map.TreasureMap;

import java.util.List;

public interface MapFactory {
    TreasureMap create(int width, int height, List<MountainDTO> mountains, List<TreasureDTO> treasures, List<ExplorerDTO> explorers);
}
