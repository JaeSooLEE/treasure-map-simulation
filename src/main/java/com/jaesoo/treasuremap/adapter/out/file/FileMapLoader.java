package com.jaesoo.treasuremap.adapter.out.file;

import com.jaesoo.treasuremap.adapter.out.file.lineHandlers.LineHandler;
import com.jaesoo.treasuremap.application.port.out.MapLoaderPort;
import com.jaesoo.treasuremap.domain.model.map.TreasureMap;
import jakarta.validation.Validator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


/**
 * Lecteur de carte depuis un fichier texte selon le format spécifié (lignes C, M, T, A).
 * Extrait les données, les valide puis génère la carte.
 */
public class FileMapLoader implements MapLoaderPort {

    private final Validator validator;
    private final Map<String, LineHandler> handlers;

    public FileMapLoader(Validator validator, Map<String, LineHandler> allHandlers) {
        this.validator = validator;
        this.handlers = allHandlers;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TreasureMap loadMap(String inputPath) {

        List<String> lines = readAndFilterLines(inputPath);
        TreasureMap loadedMap = null;

        String dimensionLine = null;

        for (String currentLine : lines) {
            String[] sections = currentLine.split("\\s*-\\s*");
            if (sections[0].equals("C")) {
                loadedMap = new TreasureMap(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]));
                dimensionLine = currentLine;
            }
        }
        lines.remove(dimensionLine);
        if (loadedMap == null) {
            throw new IllegalArgumentException("Map dimensions missing.");
        }
        for (String currentLine : lines) {
            String[] sections = currentLine.split("\\s*-\\s*");
            LineHandler currentHandler = handlers.get(sections[0]);
            if (currentHandler == null) {
                throw new IllegalArgumentException("Unrecognized input field : " + sections[0]);
            }
            handlers.get(sections[0]).handle(loadedMap, sections, validator);
        }
        return loadedMap;
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


}
