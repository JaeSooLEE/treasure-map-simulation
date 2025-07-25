package com.jaesoo.treasuremap.domain.model.map;

import com.jaesoo.treasuremap.application.service.ExecuteActionUseCaseImpl;
import com.jaesoo.treasuremap.domain.model.exception.explorer.InvalidExplorerPlacementException;
import com.jaesoo.treasuremap.domain.model.exception.map.InvalidMapException;
import com.jaesoo.treasuremap.domain.model.explorer.Action;
import com.jaesoo.treasuremap.domain.model.explorer.Adventurer;
import com.jaesoo.treasuremap.domain.model.explorer.Explorer;
import com.jaesoo.treasuremap.domain.model.geometry.Orientation;
import com.jaesoo.treasuremap.domain.model.geometry.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static com.jaesoo.treasuremap.domain.model.exception.map.MapValidationErrorCode.NULL_CELL;
import static com.jaesoo.treasuremap.domain.model.exception.map.MapValidationErrorCode.OUT_OF_BOUNDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TreasureMapTest {

    private TreasureMap map;

    @BeforeEach
    void setUp() {
        map = new TreasureMap(3, 3);
    }

    @Test
    void mapInitializedEmptyPlains() {
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                MapCell cell = map.getCellAt(new Position(x, y));
                assertThat(cell.getTerrainType()).isEqualTo(TerrainType.PLAIN);
                assertThat(cell.getTreasureCount()).isZero();
                assertThat(cell.getOccupancy().isOccupied()).isFalse();
            }
        }
    }

    @Test
    void addExplorerThrowsWhenCellInaccessible() {
        Position position = new Position(1, 1);
        MapCell mountainCell = new MapCell(TerrainType.MOUNTAIN, 0);
        map.setCellAt(mountainCell, 1, 1);
        Explorer explorer = new Adventurer("John", position, Orientation.N, new LinkedList<>());
        assertThatThrownBy(() -> map.addExplorer(explorer))
                .isInstanceOf(InvalidExplorerPlacementException.class)
                .hasMessageContaining("Invalid explorer starting position");
    }

    @Test
    void moveExplorerNoTreasure() {
        Explorer explorer = new Adventurer("Bob", new Position(0, 0), Orientation.E, new LinkedList<>());
        map.addExplorer(explorer);
        map.moveExplorer(explorer);

        assertThat(explorer.getPosition()).isEqualTo(new Position(1, 0));
        assertThat(explorer.getCollectedTreasureCount()).isZero();
    }

    @Test
    void moveExplorerWithTreasure() {
        map.setCellAt(new MapCell(TerrainType.PLAIN, 1), 1, 0);
        Explorer explorer = new Adventurer("Paul", new Position(0, 0), Orientation.E, new LinkedList<>());
        map.addExplorer(explorer);
        map.moveExplorer(explorer);

        Position targetPosition = new Position(1, 0);
        assertThat(explorer.getPosition()).isEqualTo(targetPosition);
        assertThat(explorer.getCollectedTreasureCount()).isOne();
        assertThat(map.getCellAt(targetPosition).getTreasureCount()).isZero();
    }

    @Test
    void collectMultipleTreasuresFromCell() {
        map.setCellAt(new MapCell(TerrainType.PLAIN, 2), 1, 0);
        Explorer explorer = new Adventurer("Marie", new Position(0, 0), Orientation.E, new LinkedList<>(List.of(Action.A, Action.A, Action.G, Action.G, Action.A)));
        ExecuteActionUseCaseImpl actionExecuter = new ExecuteActionUseCaseImpl();
        while(explorer.hasActions()) {
            actionExecuter.executeAction(explorer, map);
        }
        Position targetPosition = new Position(1, 0);
        assertThat(explorer.getPosition()).isEqualTo(targetPosition);
        assertThat(explorer.getCollectedTreasureCount()).isEqualTo(2);
        assertThat(map.getCellAt(targetPosition).getTreasureCount()).isZero();
    }

    @Test
    void outOfBoundsMoveThrows() {
        Explorer explorer = new Adventurer("Claire", new Position(2,2), Orientation.S, new LinkedList<>());
        map.addExplorer(explorer);
        assertThatThrownBy(() -> map.moveExplorer(explorer))
                .isInstanceOf(InvalidMapException.class)
                .hasMessageContaining(OUT_OF_BOUNDS.getDefaultMessage());
    }

    @Test
    void addExplorerOutOfBoundsThrows() {
        Explorer explorer = new Adventurer("David", new Position(3,2), Orientation.S, new LinkedList<>());
        assertThatThrownBy(() -> map.addExplorer(explorer))
                .isInstanceOf(InvalidMapException.class)
                .hasMessageContaining(OUT_OF_BOUNDS.getDefaultMessage());
    }

    @Test
    void noCrossingInaccessibleCell() {
        map.setCellAt(new MapCell(TerrainType.MOUNTAIN, 0), 1, 2);
        Position startingPosition = new Position(2,2);
        Explorer explorer = new Adventurer("Romain", startingPosition, Orientation.W, new LinkedList<>());
        map.addExplorer(explorer);
        map.moveExplorer(explorer);

        assertThat(explorer.getPosition()).isEqualTo(startingPosition);
    }

    @Test
    void occupationConflict() {
        Position startingPositionElvis = new Position(2,2);
        Position startingPositionArthur = new Position(0,2);
        Explorer elvis = new Adventurer("Elvis", startingPositionElvis, Orientation.W, new LinkedList<>());
        Explorer arthur = new Adventurer("Arthur", startingPositionArthur, Orientation.E, new LinkedList<>());
        map.addExplorer(elvis);
        map.addExplorer(arthur);
        map.moveExplorer(elvis);
        map.moveExplorer(arthur);

        Position targetPosition = new Position(1,2);
        assertThat(elvis.getPosition()).isEqualTo(targetPosition);
        assertThat(arthur.getPosition()).isEqualTo(startingPositionArthur);
    }

    @Test
    void occupancyUpdateAfterMove() {
        Position startingPosition = new Position(2,2);
        Explorer explorer = new Adventurer("Danny", startingPosition, Orientation.W, new LinkedList<>());

        map.addExplorer(explorer);
        map.moveExplorer(explorer);

        assertThat(map.getCellAt(startingPosition).getOccupancy().isOccupied()).isFalse();
        assertThat(map.getCellAt(explorer.getPosition()).getOccupancy().isOccupied()).isTrue();
    }

    @Test
    void invalidMapThrows() {
        map.setCellAt(null, 0, 0);
        assertThatThrownBy(() -> map.validate())
                .isInstanceOf(InvalidMapException.class)
                .hasMessageContaining(NULL_CELL.getDefaultMessage());

    }

}
