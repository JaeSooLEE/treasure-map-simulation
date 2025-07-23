package com.jaesoo.treasuremap.application.service;

import com.jaesoo.treasuremap.application.port.in.ExecuteActionUseCase;
import com.jaesoo.treasuremap.application.port.in.SimulationRunner;
import com.jaesoo.treasuremap.application.port.out.FileWriterPort;
import com.jaesoo.treasuremap.application.port.out.MapLoaderPort;
import com.jaesoo.treasuremap.domain.model.explorer.Explorer;
import com.jaesoo.treasuremap.domain.model.map.TreasureMap;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class SimulationRunnerImpl implements SimulationRunner {

    private MapLoaderPort mapLoader;
    private FileWriterPort fileWriter;
    private ExecuteActionUseCase executeActionCase;

    public SimulationRunnerImpl(MapLoaderPort mapLoader, FileWriterPort fileWriter, ExecuteActionUseCase executeActionCase) {
        this.fileWriter = fileWriter;
        this.executeActionCase = executeActionCase;
        this.mapLoader = mapLoader;
    }



    @Override
    public void runSimulation(String inputPath, String outputPath) {
        TreasureMap map = mapLoader.loadMap(inputPath);
        List<Explorer> explorers = map.getExplorers();
        boolean actionsLeft = true;
        while (actionsLeft) {
            actionsLeft = false;
            for(Explorer explorer : explorers) {
                if (explorer.hasActions()) {
                    executeActionCase.executeAction(explorer, map);
                    actionsLeft = true;
                }
            }
        }
        fileWriter.writeMap(map, outputPath);
    }
}
