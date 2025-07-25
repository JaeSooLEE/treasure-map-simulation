package com.jaesoo.treasuremap.application.service;

import com.jaesoo.treasuremap.application.port.in.ExecuteActionUseCase;
import com.jaesoo.treasuremap.domain.model.explorer.Action;
import com.jaesoo.treasuremap.domain.model.explorer.Adventurer;
import com.jaesoo.treasuremap.domain.model.explorer.Explorer;
import com.jaesoo.treasuremap.domain.model.geometry.Orientation;
import com.jaesoo.treasuremap.domain.model.geometry.Position;
import com.jaesoo.treasuremap.domain.model.map.TreasureMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ExecuteActionUseCaseImplTest {


    private final ExecuteActionUseCase executor = new ExecuteActionUseCaseImpl();
    private TreasureMap map;
    private Explorer explorer;

    @BeforeEach
    void setUp() {
        map = new TreasureMap(2, 2);
    }

    @Test
    void turnLeftRotatesOrientation() {
        explorer = new Adventurer("Stan", new Position(0, 0),
                Orientation.N,
                new LinkedList<>(List.of(Action.G)));
        map.addExplorer(explorer);
        executor.executeAction(explorer, map);

        assertThat(explorer.getOrientation()).isEqualTo(Orientation.W);
    }

    @Test
    void turnRightRotatesOrientation() {
        explorer = new Adventurer("Butters", new Position(0, 0),
                Orientation.N,
                new LinkedList<>(List.of(Action.D)));
        map.addExplorer(explorer);
        executor.executeAction(explorer, map);

        assertThat(explorer.getOrientation()).isEqualTo(Orientation.E);
    }

    @Test
    void advanceMovesPosition() {
        explorer = new Adventurer("Token", new Position(0, 0),
                Orientation.S,
                new LinkedList<>(List.of(Action.A)));
        map.addExplorer(explorer);
        executor.executeAction(explorer, map);

        assertThat(explorer.getPosition()).isEqualTo(new Position(0, 1));
    }
}
