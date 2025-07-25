package com.jaesoo.treasuremap.adapter.out.file;

import com.jaesoo.treasuremap.application.port.out.MapWriterPort;
import com.jaesoo.treasuremap.domain.model.explorer.Explorer;
import com.jaesoo.treasuremap.domain.model.geometry.Position;
import com.jaesoo.treasuremap.domain.model.map.MapCell;
import com.jaesoo.treasuremap.domain.model.map.TerrainType;
import com.jaesoo.treasuremap.domain.model.map.TreasureMap;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileMapWriter implements MapWriterPort {

    @Override
    public void writeMap(TreasureMap map, String outputPath) {
        Path path = Path.of(outputPath);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write("C - " + map.getWidth() + " - " + map.getHeight());
            writer.newLine();

            for (int x = 0; x < map.getWidth(); x++) {
                for(int y = 0; y < map.getHeight(); y++) {
                    MapCell cell = map.getCellAt(new Position(x, y));
                    if (cell.getTerrainType() == TerrainType.MOUNTAIN) {
                        writer.write("M - " + x + " - " + y);
                        writer.newLine();
                    }
                    int treasureCount = cell.getTreasureCount();
                    if (treasureCount > 0) {
                        writer.write("T - " + x + " - " + y + " - " + treasureCount);
                        writer.newLine();
                   }
                }
            }

            for (Explorer explorer : map.getExplorers()) {
                writer.write("A - "
                        + explorer.getName() + " - "
                        + explorer.getPosition().x() + " - "
                        + explorer.getPosition().y() + " - "
                        + explorer.getOrientation() + " - "
                        + explorer.getCollectedTreasureCount()
                );
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Cannot write result file: " + outputPath, e);
        }
    }
}
