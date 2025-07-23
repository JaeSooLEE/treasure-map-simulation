package com.jaesoo.treasuremap.domain.model.map;

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
