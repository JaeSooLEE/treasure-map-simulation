package com.jaesoo.treasuremap.adapter.out.file;

import com.jaesoo.treasuremap.application.port.in.dto.ExplorerDTO;
import com.jaesoo.treasuremap.application.port.in.dto.MountainDTO;
import com.jaesoo.treasuremap.application.port.in.dto.TreasureDTO;
import com.jaesoo.treasuremap.application.factory.MapFactory;
import com.jaesoo.treasuremap.application.port.out.MapLoaderPort;
import com.jaesoo.treasuremap.domain.model.explorer.Action;
import com.jaesoo.treasuremap.domain.model.explorer.ExplorerType;
import com.jaesoo.treasuremap.domain.model.geometry.Orientation;
import com.jaesoo.treasuremap.domain.model.map.TreasureMap;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Lecteur de carte depuis un fichier texte selon le format spécifié (lignes C, M, T, A).
 * Extrait des DTO, les valide, puis délègue à une {@link MapFactory}.
 */
public class FileMapLoader implements MapLoaderPort {

    private final Validator validator;
    private final MapFactory mapFactory;

    public FileMapLoader(Validator validator, MapFactory mapFactory) {
        this.validator = validator;
        this.mapFactory = mapFactory;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TreasureMap loadMap(String inputPath) {

        int width = 0;
        int height = 0;

        List<MountainDTO> mountains = new ArrayList<>();
        List<TreasureDTO> treasures = new ArrayList<>();
        List<ExplorerDTO> explorers = new ArrayList<>();

        List<String> lines = readAndFilterLines(inputPath);

        for (String currentLine : lines) {
            String[] sections = currentLine.split("\\s*-\\s*");
            switch (sections[0]) {
                case "C" -> {
                    width = Integer.parseInt(sections[1]);
                    height = Integer.parseInt(sections[2]);
                    if (width <= 0 || height <= 0) {
                        throw new IllegalArgumentException("Invalid map dimensions : (" + width + ", " + height + ")");
                    }
                }
                case "M" -> {
                    int x = Integer.parseInt(sections[1]);
                    int y = Integer.parseInt(sections[2]);
                    MountainDTO mountainDto = new MountainDTO(x, y);
                    Set<ConstraintViolation<MountainDTO>> violations = validator.validate(mountainDto);
                    if (!violations.isEmpty()) {
                        throw new IllegalArgumentException("Invalid mountain DTO : " + violations);
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
                        throw new IllegalArgumentException("Invalid treasure DTO : " + violations);
                    }
                    treasures.add(new TreasureDTO(x, y, count));
                }
                case "A" -> {
                    String name = sections[1];
                    int x = Integer.parseInt(sections[2]);
                    int y = Integer.parseInt(sections[3]);
                    char orientation = sections[4].charAt(0);
                    String actions = sections[5];

                    ExplorerDTO expDto = new ExplorerDTO(name, x, y, orientationFromChar(orientation), actions == null || actions.isBlank()
                            ? new LinkedList<>()
                            : actions.chars()
                            .mapToObj(c -> actionFromChar((char) c))
                            .collect(Collectors.toCollection(LinkedList::new)), ExplorerType.ADVENTURER);
                    Set<ConstraintViolation<ExplorerDTO>> violations = validator.validate(expDto);
                    if (!violations.isEmpty()) {
                        throw new IllegalArgumentException("Invalid explorer DTO : " + violations);
                    }
                    explorers.add(expDto);
                }
                default -> throw new IllegalArgumentException("Unrecognized input field : " + sections[0]);
            }
        }

        return mapFactory.create(width, height, mountains, treasures, explorers);
    }

    public List<String> readAndFilterLines(String inputPath) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputPath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.strip();
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }
                lines.add(line);
            }
            return lines;
        } catch (IOException e) {
            throw new RuntimeException("Error while reading file " + inputPath, e);
        }
    }

    public Action actionFromChar(char character) {
        return switch(character) {
            case 'A' -> Action.A;
            case 'G' -> Action.G;
            case 'D' -> Action.D;
            default -> throw new IllegalArgumentException("Invalid action entry");
        };
    }

    public Orientation orientationFromChar(char c) {
        return switch (c) {
            case 'N' -> Orientation.N;
            case 'S' -> Orientation.S;
            case 'E' -> Orientation.E;
            case 'O' -> Orientation.W;
            default  -> throw new IllegalArgumentException("Invalid orientation: " + c);
        };
    }
}
