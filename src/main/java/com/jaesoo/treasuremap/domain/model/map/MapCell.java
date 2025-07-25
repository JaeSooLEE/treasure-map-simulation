package com.jaesoo.treasuremap.domain.model.map;

import com.jaesoo.treasuremap.domain.model.explorer.Explorer;

/**
 * Représente une case de la carte:
 * un type de terrain ({@link TerrainType}) – plaine, montagne
 * un nombre de trésors présents
 * une occupation par des {@link Explorer}
*/
public class MapCell {
    private final TerrainType terrainType;
    private int treasureCount;
    private final CellOccupancy occupancy = new CellOccupancy();

    public MapCell(TerrainType terrainType, int treasureCount) {
        this.terrainType = terrainType;
        this.treasureCount = treasureCount;
    }

    public TerrainType getTerrainType() {
        return terrainType;
    }

    public int getTreasureCount() {
        return treasureCount;
    }

    public boolean decreaseTreasureCount() {
        if(treasureCount > 0) {
            treasureCount--;
            return true;
        }
        return false;
    }

    public CellOccupancy getOccupancy() {
        return occupancy;
    }

    public void setTreasureCount(int treasureCount) {
        this.treasureCount = treasureCount;
    }
}
