package com.jaesoo.treasuremap.application.factory;

import com.jaesoo.treasuremap.application.port.in.dto.ExplorerDTO;
import com.jaesoo.treasuremap.application.port.in.dto.MountainDTO;
import com.jaesoo.treasuremap.application.port.in.dto.TreasureDTO;
import com.jaesoo.treasuremap.domain.model.explorer.Explorer;
import com.jaesoo.treasuremap.domain.model.geometry.Position;
import com.jaesoo.treasuremap.domain.model.map.MapCell;
import com.jaesoo.treasuremap.domain.model.map.TerrainType;
import com.jaesoo.treasuremap.domain.model.map.TreasureMap;

import java.util.List;

public class MapFactoryImpl implements MapFactory{

    private final ExplorerFactory explorerFactory;

    public MapFactoryImpl(ExplorerFactory explorerFactory) {
        this.explorerFactory = explorerFactory;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public TreasureMap create(int width, int height, List<MountainDTO> mountains, List<TreasureDTO> treasures, List<ExplorerDTO> explorers) {
        TreasureMap resultMap = new TreasureMap(width, height);
        mountains.forEach(mountain ->
            resultMap.setCellAt(new MapCell(TerrainType.MOUNTAIN, 0), mountain.x(), mountain.y())
        );
        treasures.forEach(treasure ->
            resultMap.getCellAt(new Position(treasure.x(), treasure.y())).setTreasureCount(treasure.count())
        );
        explorers.forEach(e -> {
            Explorer explorer = explorerFactory.create(e);
            resultMap.addExplorer(explorer);
        });
        resultMap.validate();
        return resultMap;
    }
}
