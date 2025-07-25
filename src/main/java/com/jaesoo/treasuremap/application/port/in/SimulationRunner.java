package com.jaesoo.treasuremap.application.port.in;

import com.jaesoo.treasuremap.application.port.out.MapLoaderPort;
import com.jaesoo.treasuremap.application.port.out.MapWriterPort;

/**
 * Chef d'orchestre de la simulation: lit une carte depuis une source, exécute les déplacements des explorateurs, puis écrit le résultat final.
 */
public interface SimulationRunner {
    /**
     * Lance la simulation complète:
     * Charge la carte depuis {@code inputPath} via un {@link MapLoaderPort}
     * Écrit l’état final de la carte et des explorateurs dans {@code outputPath} via un {@link MapWriterPort}.
     *
     * @param inputPath  chemin vers le fichier d’entrée (format texte décrit dans la doc)
     * @param outputPath chemin du fichier à générer contenant le résultat final.
     * @throws RuntimeException en cas d’erreur de lecture, d’écriture ou de simulation
     */
    void runSimulation(String inputPath, String outputPath);
}