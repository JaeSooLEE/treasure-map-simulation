package com.jaesoo.treasuremap.domain.model.map;

import com.jaesoo.treasuremap.domain.model.exception.InvalidMapException;
import com.jaesoo.treasuremap.domain.model.exception.MapValidationErrorCode;
import com.jaesoo.treasuremap.domain.model.explorer.Explorer;
import com.jaesoo.treasuremap.domain.model.geometry.Position;

import java.util.ArrayList;
import java.util.List;

import static com.jaesoo.treasuremap.domain.model.exception.MapValidationErrorCode.*;

public class TreasureMap {
    private int width;
    private int height;
    List<Explorer> explorers = new ArrayList<>();

    private MapCell[][] grid;

    public TreasureMap(int width, int height) {
        this.width = width;
        this.height = height;
        grid =  new MapCell[width][height];
        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
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

    public MapCell[][] getGrid() {
        return grid;
    }

    public MapCell getCellAt(Position position) {
        return grid[position.getX()][position.getY()];
    }

    public void setCellAt(MapCell cell, int x, int y) {
        grid[x][y] = cell;
    }

    public void addExplorer(Explorer explorer) {
        Position startingPosition = explorer.getPosition();
        validate(startingPosition);
        MapCell startingCell = getCellAt(explorer.getPosition());
        if(canEnter(startingCell, explorer)) {
            explorers.add(explorer);
            enterAndCollect(startingCell, explorer);
        }
    }

    public boolean isOutOfBounds(Position position) {
        return position.getX() >= width || position.getY() >= height;
    }

    private void validate(Position position) {
        if (isOutOfBounds(position)) {
            throw new InvalidMapException(
                    OUT_OF_BOUNDS, position.getX(), position.getY()
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
         if(canEnter(targetCell, explorer)) {
            getCellAt(explorer.getPosition()).getOccupancy().leave(explorer);
            explorer.move(targetPosition);
            enterAndCollect(targetCell, explorer);
        }
    }

    private void validateNotNull(Object obj, MapValidationErrorCode code, int x, int y) {
        if(obj == null) {
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
