package com.jaesoo.treasuremap.application.factory;

import com.jaesoo.treasuremap.application.port.in.dto.ExplorerDTO;
import com.jaesoo.treasuremap.domain.model.explorer.Action;
import com.jaesoo.treasuremap.domain.model.explorer.Adventurer;
import com.jaesoo.treasuremap.domain.model.explorer.Explorer;
import com.jaesoo.treasuremap.domain.model.explorer.ExplorerType;
import com.jaesoo.treasuremap.domain.model.geometry.Orientation;
import com.jaesoo.treasuremap.domain.model.geometry.Position;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ExplorerFactoryImplTest {
    private final ExplorerFactory factory = new ExplorerFactoryImpl();

    @Test
    void createSimpleExplorer() {
        ExplorerDTO explorerDto = new ExplorerDTO(
                "Heidi", 2, 2, Orientation.S, new LinkedList<>(List.of(Action.A, Action.A, Action.G)), ExplorerType.ADVENTURER
        );
        Explorer explorer = factory.create(explorerDto);
        assertThat(explorer.getPosition()).isEqualTo(new Position(2,2));
        assertThat(explorer.getOrientation()).isEqualTo(Orientation.S);
        assertThat(explorer.getNextAction()).isEqualTo(Action.A);
        assertThat(explorer.getNextAction()).isEqualTo(Action.A);
        assertThat(explorer.getNextAction()).isEqualTo(Action.A);
        assertThat(explorer).isInstanceOf(Adventurer.class);


    }
}
