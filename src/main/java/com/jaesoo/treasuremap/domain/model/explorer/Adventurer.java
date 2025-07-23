package com.jaesoo.treasuremap.domain.model.explorer;

import com.jaesoo.treasuremap.domain.model.map.MapCell;
import com.jaesoo.treasuremap.domain.model.geometry.Orientation;
import com.jaesoo.treasuremap.domain.model.geometry.Position;
import com.jaesoo.treasuremap.domain.model.map.TerrainType;
import com.jaesoo.treasuremap.domain.model.strategy.DefaultMovementStrategy;
import com.jaesoo.treasuremap.domain.model.strategy.MovementStrategy;

import java.util.Queue;
import java.util.Set;

public class Adventurer implements Explorer{

    private final String name;
    private Position position;
    private Orientation orientation;
    private final Queue<Character> actions;
    private final MovementStrategy movementStrategy = new DefaultMovementStrategy();
    private int treasureCount = 0;

    public Adventurer(String name, Position position, Orientation orientation, Queue<Character> actions) {
        this.name = name;
        this.position = position;
        this.orientation = orientation;
        this.actions = actions;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public Orientation getOrientation() {
        return orientation;
    }

    @Override
    public boolean hasActions() {
        return !actions.isEmpty();
    }

    @Override
    public Character getNextAction() {
        return actions.poll();
    }

    @Override
    public void obtainTreasure() {
        treasureCount++;
    }

    @Override
    public int getCollectedTreasureCount() {
        return treasureCount;
    }

    @Override
    public boolean canAccess(MapCell cell) {
        return cell.getTerrainType() != TerrainType.MOUNTAIN;
    }

    @Override
    public boolean canShareWith(Set<Explorer> occupants) {
        return occupants.isEmpty();
    }

    @Override
    public void move(Position position) {
        this.position = position;
    }

    @Override
    public Position getNextPosition() {
        return movementStrategy.ComputeNextPosition(position, orientation);
    }

    @Override
    public void turnRight() {
        orientation = orientation.turnRight();
    }

    @Override
    public void turnLeft() {
        orientation = orientation.turnLeft();
    }
}
