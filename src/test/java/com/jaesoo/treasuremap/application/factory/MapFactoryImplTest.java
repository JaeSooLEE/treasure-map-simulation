package com.jaesoo.treasuremap.application.factory;

import static org.assertj.core.api.Assertions.*;

import com.jaesoo.treasuremap.application.port.in.dto.ExplorerDTO;
import com.jaesoo.treasuremap.application.port.in.dto.MountainDTO;
import com.jaesoo.treasuremap.application.port.in.dto.TreasureDTO;
import com.jaesoo.treasuremap.domain.model.explorer.Action;
import com.jaesoo.treasuremap.domain.model.explorer.ExplorerType;
import com.jaesoo.treasuremap.domain.model.geometry.Orientation;
import com.jaesoo.treasuremap.domain.model.geometry.Position;
import com.jaesoo.treasuremap.domain.model.map.TerrainType;
import com.jaesoo.treasuremap.domain.model.map.TreasureMap;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

class MapFactoryImplTest {

    private final ExplorerFactory explorerFactory = new ExplorerFactoryImpl();
    private final MapFactoryImpl factory = new MapFactoryImpl(explorerFactory);

    @Test
    void createSimpleMap() {
        List<MountainDTO> mountainDto = List.of(new MountainDTO(0, 0));
        List<TreasureDTO> treasureDto = List.of(new TreasureDTO(1, 1, 3));
        ExplorerDTO explorerDto = new ExplorerDTO(
                "Alice", 2, 2, Orientation.S, new LinkedList<>(List.of(Action.A, Action.A, Action.G)), ExplorerType.ADVENTURER
        );

        TreasureMap map = factory.create(3, 3, mountainDto, treasureDto, List.of(explorerDto));

        assertThat(map.getWidth()).isEqualTo(3);
        assertThat(map.getHeight()).isEqualTo(3);
        assertThat(map.getCellAt(new Position(0, 0)).getTerrainType()).isEqualTo(TerrainType.MOUNTAIN);
        assertThat(map.getCellAt(new Position(1, 1)).getTreasureCount()).isEqualTo(3);
        assertThat(map.getExplorers()).hasSize(1);
        assertThat(map.getExplorers().getFirst().getName()).isEqualTo("Alice");
    }
}