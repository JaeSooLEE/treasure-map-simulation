package com.jaesoo.treasuremap.adapter.out.file;

import com.jaesoo.treasuremap.domain.model.explorer.Action;
import com.jaesoo.treasuremap.domain.model.explorer.Adventurer;
import com.jaesoo.treasuremap.domain.model.geometry.Orientation;
import com.jaesoo.treasuremap.domain.model.geometry.Position;
import com.jaesoo.treasuremap.domain.model.map.MapCell;
import com.jaesoo.treasuremap.domain.model.map.TerrainType;
import com.jaesoo.treasuremap.domain.model.map.TreasureMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class FileMapWriterTest {


    private FileMapWriter writer;

    @BeforeEach
    void setUp() {
        writer = new FileMapWriter();
    }

    @Test
    void writeMapOnlyDimensions(@TempDir Path tempDir) throws IOException {
        TreasureMap map = new TreasureMap(1, 1);

        Path output = tempDir.resolve("out.txt");
        writer.writeMap(map, output.toString());

        List<String> lines = Files.readAllLines(output);
        assertThat(lines).hasSize(1).containsExactly("C - 1 - 1");
    }

    @Test
    void writeMapInvalidPathShouldThrow() {
        TreasureMap map = new TreasureMap(1,1);
        String badPath = "/goin/down/to/south/park/output.txt";

        assertThatThrownBy(() -> writer.writeMap(map, badPath))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Cannot write result file");
    }

    @Test
    void writeMapFullWrite(@TempDir Path tempDir) throws IOException {
        TreasureMap map = new TreasureMap(2, 2);
        map.setCellAt(new MapCell(TerrainType.MOUNTAIN, 0), 1, 0);
        map.getCellAt(new Position(0, 1)).setTreasureCount(3);
        Queue<Action> emptyActions = new LinkedList<>();
        Adventurer explorer = new Adventurer("Chef",
                new Position(0, 0),
                Orientation.E,
                emptyActions);
        explorer.obtainTreasure();
        explorer.obtainTreasure();
        map.addExplorer(explorer);

        Path output = tempDir.resolve("output.txt");
        writer.writeMap(map, output.toString());

        List<String> lines = Files.readAllLines(output);
        assertThat(lines).hasSize(4);
        assertThat(lines).containsExactlyInAnyOrder(
                "C - 2 - 2",
                "M - 1 - 0",
                "T - 0 - 1 - 3",
                "A - Chef - 0 - 0 - E - 2"
        );
    }



}
