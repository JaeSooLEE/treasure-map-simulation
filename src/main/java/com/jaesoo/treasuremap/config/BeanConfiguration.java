package com.jaesoo.treasuremap.config;

import com.jaesoo.treasuremap.adapter.out.file.FileMapWriter;
import com.jaesoo.treasuremap.adapter.out.file.FileMapLoader;
import com.jaesoo.treasuremap.adapter.out.file.lineHandlers.AdventurerHandler;
import com.jaesoo.treasuremap.adapter.out.file.lineHandlers.LineHandler;
import com.jaesoo.treasuremap.adapter.out.file.lineHandlers.MountainHandler;
import com.jaesoo.treasuremap.adapter.out.file.lineHandlers.TreasureHandler;
import com.jaesoo.treasuremap.application.port.in.ExecuteActionUseCase;
import com.jaesoo.treasuremap.application.port.in.SimulationRunner;
import com.jaesoo.treasuremap.application.port.out.MapWriterPort;
import com.jaesoo.treasuremap.application.port.out.MapLoaderPort;
import com.jaesoo.treasuremap.application.service.ExecuteActionUseCaseImpl;
import com.jaesoo.treasuremap.application.service.SimulationRunnerImpl;
import jakarta.validation.Validator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class BeanConfiguration {

    @Bean
    @Profile("file")
    public Map<String, LineHandler> lineHandlers(List<LineHandler> allHandlers) {
        return allHandlers.stream().collect(Collectors.toMap(LineHandler::getRecordType, h -> h));
    }


    @Bean
    @Profile("file")
    public MapLoaderPort mapLoaderPort(Validator validator,Map<String, LineHandler> lineHandlers) {
        return new FileMapLoader(validator, lineHandlers);
    }

    @Bean
    @Profile("file")
    public LineHandler mountainHandler() {
        return new MountainHandler();
    }

    @Bean
    @Profile("file")
    public LineHandler treasureHandler() {
        return new TreasureHandler();
    }

    @Bean
    @Profile("file")
    public LineHandler AdventurerHandler() {
        return new AdventurerHandler();
    }

    @Bean
    @Profile("file")
    public MapWriterPort fileMapWriter() {
        return new FileMapWriter();
    }

    @Bean
    public ExecuteActionUseCase executeActionUseCase() {
        return new ExecuteActionUseCaseImpl();
    }

    @Bean
    public SimulationRunner simulationRunner(MapLoaderPort mapLoader, MapWriterPort fileMapWriter, ExecuteActionUseCase executeActionCase) {
        return new SimulationRunnerImpl(mapLoader, fileMapWriter, executeActionCase);
    }

    @Bean
    @Profile("file")
    public CommandLineRunner cliRunner(SimulationRunner simulationRunner) {
        return args -> {
            if (args.length < 2) {
                System.err.println("Use: java -jar treasuremap.jar <inputPath> <outputPath>");
                return;
            }
            simulationRunner.runSimulation(args[0], args[1]);
            System.out.println("Simulation complete. Result in: " + args[1]);
        };
    }

}
