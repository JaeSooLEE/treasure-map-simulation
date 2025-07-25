package com.jaesoo.treasuremap.config;

import com.jaesoo.treasuremap.adapter.out.file.FileMapWriter;
import com.jaesoo.treasuremap.adapter.out.file.FileMapLoader;
import com.jaesoo.treasuremap.application.factory.ExplorerFactory;
import com.jaesoo.treasuremap.application.factory.ExplorerFactoryImpl;
import com.jaesoo.treasuremap.application.factory.MapFactory;
import com.jaesoo.treasuremap.application.factory.MapFactoryImpl;
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

@Configuration
public class BeanConfiguration {

    @Bean
    @Profile("file")
    public MapLoaderPort mapLoaderPort(Validator validator, MapFactory mapFactory) {
        return new FileMapLoader(validator, mapFactory);
    }

    @Bean
    @Profile("file")
    public MapWriterPort fileWriterPort() {
        return new FileMapWriter();
    }

    @Bean
    public ExplorerFactory explorerFactory() {
        return new ExplorerFactoryImpl();
    }

    @Bean
    public MapFactory mapFactory(ExplorerFactory explorerFactory) {
        return new MapFactoryImpl(explorerFactory);
    }

    @Bean
    public ExecuteActionUseCase executeActionUseCase() {
        return new ExecuteActionUseCaseImpl();
    }

    @Bean
    public SimulationRunner simulationRunner(MapLoaderPort mapLoader, MapWriterPort fileWriter, ExecuteActionUseCase executeActionCase) {
        return new SimulationRunnerImpl(mapLoader, fileWriter, executeActionCase);
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
