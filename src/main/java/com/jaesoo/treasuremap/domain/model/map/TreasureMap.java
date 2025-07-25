package com.jaesoo.treasuremap.domain.model.map;

import com.jaesoo.treasuremap.domain.model.exception.explorer.InvalidExplorerPlacementException;
import com.jaesoo.treasuremap.domain.model.exception.map.InvalidMapException;
import com.jaesoo.treasuremap.domain.model.exception.map.MapValidationErrorCode;
import com.jaesoo.treasuremap.domain.model.explorer.Explorer;
import com.jaesoo.treasuremap.domain.model.geometry.Position;

import java.util.ArrayList;
import java.util.List;

import static com.jaesoo.treasuremap.domain.model.exception.map.MapValidationErrorCode.*;

public class TreasureMap {
    private final int width;
    private final int height;
    private final List<Explorer> explorers = new ArrayList<>();

    private final MapCell[][] grid;

    public TreasureMap(int width, int height) {
        this.width = width;
        this.height = height;
        grid = new MapCell[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                setCellAt(new MapCell(TerrainType.PLAIN, 0), x, y);
            }
        }
    }

    public List<Explorer> getExplorers() {
        return explorers;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public MapCell getCellAt(Position position) {
        return grid[position.x()][position.y()];
    }

    public void setCellAt(MapCell cell, int x, int y) {
        grid[x][y] = cell;
    }

    public void addExplorer(Explorer explorer) {
        Position startingPosition = explorer.getPosition();
        validate(startingPosition);
        MapCell startingCell = getCellAt(explorer.getPosition());
        if (canEnter(startingCell, explorer)) {
            explorers.add(explorer);
            enterAndCollect(startingCell, explorer);
        } else {
            throw new InvalidExplorerPlacementException("Invalid explorer starting position : " + explorer.getName() + ", (" + startingPosition.x() + ", " + startingPosition.y() + ")");
        }
    }

    public boolean isOutOfBounds(Position position) {
        return position.x() >= width || position.y() >= height;
    }

    private void validate(Position position) {
        if (isOutOfBounds(position)) {
            throw new InvalidMapException(
                    OUT_OF_BOUNDS, position.x(), position.y()
            );
        }
    }

    private boolean canEnter(MapCell cell, Explorer explorer) {
        return explorer.canAccess(cell)
                && cell.getOccupancy().canBeOccupiedBy(explorer);
    }

    private void enterAndCollect(MapCell cell, Explorer explorer) {
        cell.getOccupancy().enter(explorer);
        if (cell.decreaseTreasureCount()) {
            explorer.obtainTreasure();
        }
    }


    public void moveExplorer(Explorer explorer) {
        Position targetPosition = explorer.getNextPosition();
        validate(targetPosition);
        MapCell targetCell = getCellAt(targetPosition);
        if (canEnter(targetCell, explorer)) {
            getCellAt(explorer.getPosition()).getOccupancy().leave(explorer);
            explorer.move(targetPosition);
            enterAndCollect(targetCell, explorer);
        }
    }

    private void validateNotNull(Object obj, MapValidationErrorCode code, int x, int y) {
        if (obj == null) {
            throw new InvalidMapException(code, x, y);
        }
    }

    private void validateEqual(int actual, int expected, MapValidationErrorCode code, int x, int y) {
        if (actual != expected) {
            throw new InvalidMapException(code, x, y);
        }
    }

    public void validate() {
        validateNotNull(grid, NULL_GRID, 0, 0);
        validateEqual(grid.length, width, INVALID_WIDTH, 0, 0);

        for (int x = 0; x < width; x++) {
            validateNotNull(grid[x], NULL_LINE, x, 0);
            validateEqual(grid[x].length, height, INVALID_HEIGHT, x, 0);

            for (int y = 0; y < height; y++) {
                MapCell cell = grid[x][y];
                validateNotNull(cell, NULL_CELL, x, y);
                validateNotNull(cell.getTerrainType(), INVALID_TERRAIN, x, y);
            }
        }
    }
}
