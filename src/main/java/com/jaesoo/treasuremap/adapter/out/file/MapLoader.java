package com.jaesoo.treasuremap.adapter.out.file;

import com.jaesoo.treasuremap.adapter.out.file.dto.ExplorerDTO;
import com.jaesoo.treasuremap.adapter.out.file.dto.MountainDTO;
import com.jaesoo.treasuremap.adapter.out.file.dto.TreasureDTO;
import com.jaesoo.treasuremap.application.factory.ExplorerFactory;
import com.jaesoo.treasuremap.application.factory.MapFactory;
import com.jaesoo.treasuremap.application.port.out.MapLoaderPort;
import com.jaesoo.treasuremap.domain.model.map.TreasureMap;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Component
public class MapLoader implements MapLoaderPort {

    private final Validator validator;
    private final MapFactory mapFactory;

    public MapLoader(Validator validator, MapFactory mapFactory) {
        this.validator = validator;
        this.mapFactory = mapFactory;
    }

    @Override
    public TreasureMap loadMap(String inputPath) {

        int width = 0;
        int height = 0;

        List<MountainDTO> mountains = new ArrayList<>();
        List<TreasureDTO> treasures = new ArrayList<>();
        List<ExplorerDTO> explorers = new ArrayList<>();

        // Lire et filtrer les lignes
        List<String> lines = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(inputPath))) {
            String line;
            while((line = reader.readLine()) != null) {
                line = line.strip();
                if(line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                lines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erreur de lecture de fichier " + inputPath, e);
        }

        // On parse les lignes
        for(String currentLine : lines) {
            String[] sections = currentLine.split("\\s*-\\s*");
            switch(sections[0]) {
                case "C" -> {
                    width = Integer.parseInt(sections[1]);
                    height = Integer.parseInt(sections[2]);
                    if(width <= 0 || height <= 0) {
                        throw new IllegalArgumentException("Dimensions carte invalide : (" + width + ", " + height + ")");
                    }
                }
                case "M" -> {
                    int x = Integer.parseInt(sections[1]);
                    int y = Integer.parseInt(sections[2]);
                    MountainDTO mountainDto = new MountainDTO(x, y);
                    Set<ConstraintViolation<MountainDTO>> violations = validator.validate(mountainDto);
                    if (!violations.isEmpty()) {
                        throw new IllegalArgumentException("DTO montagne invalide : " + violations);
                    }
                    mountains.add(new MountainDTO(x, y));
                }
                case "T" -> {
                    int x = Integer.parseInt(sections[1]);
                    int y = Integer.parseInt(sections[2]);
                    int count = Integer.parseInt(sections[3]);
                    TreasureDTO treasureDto = new TreasureDTO(x, y, count);
                    Set<ConstraintViolation<TreasureDTO>> violations = validator.validate(treasureDto);
                    if (!violations.isEmpty()) {
                        throw new IllegalArgumentException("DTO trésor invalide : " + violations);
                    }
                    treasures.add(new TreasureDTO(x, y, count));
                }
                case "A" -> {
                    String name = sections[1];
                    int x = Integer.parseInt(sections[2]);
                    int y = Integer.parseInt(sections[3]);
                    char orientation = sections[4].charAt(0);
                    String actions = sections[5];

                ExplorerDTO expDto = new ExplorerDTO(name, x, y, orientation, actions, 'A');
                    Set<ConstraintViolation<ExplorerDTO>> violations = validator.validate(expDto);
                    if (!violations.isEmpty()) {
                        throw new IllegalArgumentException("DTO explorateur invalide : " + violations);
                    }
                    explorers.add(expDto);
                }
                default -> throw new IllegalArgumentException("Entrée du fichier non reconnue : " + sections[0]);
            }
        }

        return mapFactory.create(width, height, mountains, treasures, explorers);
    }
}
