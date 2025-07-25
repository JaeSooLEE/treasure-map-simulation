package com.jaesoo.treasuremap.application.port.in;

import com.jaesoo.treasuremap.domain.model.explorer.Explorer;
import com.jaesoo.treasuremap.domain.model.map.TreasureMap;

/**
 * Use case pour exécuter une unique action (avancer ou tourner) d’un explorateur sur la carte.
 */
public interface ExecuteActionUseCase {


    /**
     * Exécute la prochaine action de {@code explorer} sur {@code map}.
     * 'A' → {@link TreasureMap#moveExplorer(Explorer)}
     * 'G' → {@link Explorer#turnLeft()}
     * 'D' → {@link Explorer#turnRight()}
     *
     * @param explorer l’explorateur dont on applique la prochaine action
     * @param map      la carte de simulation
     * @throws IllegalArgumentException si l’action récupérée n’est pas 'A', 'G' ou 'D'
     * @throws RuntimeException         si le déplacement lève une exception métier
     */
    void executeAction(Explorer explorer, TreasureMap map);

}
