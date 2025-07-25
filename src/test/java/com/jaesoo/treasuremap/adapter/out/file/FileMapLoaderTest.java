package com.jaesoo.treasuremap.adapter.out.file;

import com.jaesoo.treasuremap.application.factory.ExplorerFactoryImpl;
import com.jaesoo.treasuremap.application.factory.MapFactoryImpl;
import com.jaesoo.treasuremap.application.port.out.MapLoaderPort;
import com.jaesoo.treasuremap.domain.model.explorer.Action;
import com.jaesoo.treasuremap.domain.model.explorer.Explorer;
import com.jaesoo.treasuremap.domain.model.geometry.Orientation;
import com.jaesoo.treasuremap.domain.model.geometry.Position;
import com.jaesoo.treasuremap.domain.model.map.TerrainType;
import com.jaesoo.treasuremap.domain.model.map.TreasureMap;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class FileMapLoaderTest {
    private MapLoaderPort loader;


    @BeforeEach
    void setUp() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            loader = new FileMapLoader(factory.getValidator(), new MapFactoryImpl(new ExplorerFactoryImpl()));
        }
    }

    @Test
    void loadMapFromTempFile(@TempDir Path tempDir) throws IOException {
        String content = """
                # comment
                C - 2 - 2
                M - 0 - 1
                T - 1 - 1 - 2
                A - Kenny - 0 - 0 - E - AAD
                """;

        Path tmp = tempDir.resolve("test.txt");
        Files.writeString(tmp, content);

        TreasureMap map = loader.loadMap(tmp.toString());

        assertThat(map.getWidth()).isEqualTo(2);
        assertThat(map.getHeight()).isEqualTo(2);
        assertThat(map.getCellAt(new Position(0, 1)).getTerrainType()).isEqualTo(TerrainType.MOUNTAIN);
        assertThat(map.getCellAt(new Position(1, 1)).getTreasureCount()).isEqualTo(2);
        assertThat(map.getExplorers()).hasSize(1);

        Explorer explorer = map.getExplorers().getFirst();

        assertThat(explorer.getName()).isEqualTo("Kenny");
        assertThat(explorer.getPosition()).isEqualTo(new Position(0,0));
        assertThat(explorer.getOrientation()).isEqualTo(Orientation.E);
        assertThat(explorer.hasActions()).isTrue();
        assertThat(explorer.getNextAction()).isEqualTo(Action.A);
        assertThat(explorer.getNextAction()).isEqualTo(Action.A);
        assertThat(explorer.getNextAction()).isEqualTo(Action.D);
    }

    @Test
    void loadMapThrowsOnInvalidLine(@TempDir Path tempDir) throws IOException {
        Path tmp = tempDir.resolve("bad.txt");
        Files.writeString(tmp, "X - invalid - line");

        assertThatThrownBy(() -> loader.loadMap(tmp.toString()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unrecognized input field ");
    }

}
