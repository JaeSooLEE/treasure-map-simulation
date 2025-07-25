package com.jaesoo.treasuremap.adapter.in.integration;

import com.jaesoo.treasuremap.application.port.in.SimulationRunner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class SimulationRunnerIntegrationTest {

    @Autowired
    private SimulationRunner simulationRunner;



    @Test
    void fullSimulationWorks(@TempDir Path tmpDir) throws Exception {
        Path input = tmpDir.resolve("input.txt");
        String map = """
            C - 4 - 2
            M - 2 - 0
            T - 2 - 1 - 2
            A - Garrison - 0 - 1 - S - GAGADADAGA
            """;
        Files.writeString(input, map);

        Path output = tmpDir.resolve("output.txt");

        simulationRunner.runSimulation(input.toString(), output.toString());
        List<String> lines = Files.readAllLines(output);
        assertThat(lines)
                .hasSize(4)
                .containsExactlyInAnyOrder(
                        "C - 4 - 2",
                        "M - 2 - 0",
                        "T - 2 - 1 - 1",
                        "A - Garrison - 2 - 1 - E - 1"
                );

    }
}