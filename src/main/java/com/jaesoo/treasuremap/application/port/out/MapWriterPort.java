package com.jaesoo.treasuremap.application.port.out;

import com.jaesoo.treasuremap.domain.model.map.TreasureMap;


/**
 * Port abstrait pour écrire le résultat d’une simulation de carte.
 */
public interface MapWriterPort {

    /**
     * Écrit dans la destination spécifiée l’état final de la carte et des explorateurs au format texte.
     *
     * @param map        carte après exécution de la simulation
     * @param destination chemin où créer/écraser le fichier de sortie
     * @throws RuntimeException en cas d’erreur d’I/O
     */
    void writeMap(TreasureMap map, String destination);
}
