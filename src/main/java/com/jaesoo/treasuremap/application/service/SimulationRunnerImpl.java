package com.jaesoo.treasuremap.application.service;

import com.jaesoo.treasuremap.application.port.in.ExecuteActionUseCase;
import com.jaesoo.treasuremap.application.port.in.SimulationRunner;
import com.jaesoo.treasuremap.application.port.out.MapWriterPort;
import com.jaesoo.treasuremap.application.port.out.MapLoaderPort;
import com.jaesoo.treasuremap.domain.model.explorer.Explorer;
import com.jaesoo.treasuremap.domain.model.map.TreasureMap;

import java.util.List;

public class SimulationRunnerImpl implements SimulationRunner {

    private final MapLoaderPort mapLoader;
    private final MapWriterPort mapWriter;
    private final ExecuteActionUseCase executeActionCase;

    public SimulationRunnerImpl(MapLoaderPort mapLoader, MapWriterPort mapWriter, ExecuteActionUseCase executeActionCase) {
        this.mapWriter = mapWriter;
        this.executeActionCase = executeActionCase;
        this.mapLoader = mapLoader;
    }


    /**
     * {@inheritDoc}
     */
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
        mapWriter.writeMap(map, outputPath);
    }
}
