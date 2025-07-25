package com.jaesoo.treasuremap.application.factory;

import com.jaesoo.treasuremap.application.port.in.dto.ExplorerDTO;
import com.jaesoo.treasuremap.application.port.in.dto.MountainDTO;
import com.jaesoo.treasuremap.application.port.in.dto.TreasureDTO;
import com.jaesoo.treasuremap.domain.model.map.TreasureMap;

import java.util.List;


/**
 * Factory pour assembler une instance de {@link TreasureMap} à partir des données d’entrée (dimensions, montagnes, trésors, explorateurs).
 */
public interface MapFactory {

    /**
     * Crée une carte de taille {@code width}×{@code height}, initialise les montagnes, répartit les trésors et place les explorateurs.
     *
     * @param width      nombre de colonnes de la carte
     * @param height     nombre de lignes de la carte
     * @param mountains  liste des emplacements de montagnes
     * @param treasures  liste des emplacements et quantités de trésors
     * @param explorers  liste des données d’explorateurs à positionner
     * @return une {@link TreasureMap} prête à être simulée
     * @throws IllegalArgumentException si width≤0 ou height≤0
     */
    TreasureMap create(int width, int height, List<MountainDTO> mountains, List<TreasureDTO> treasures, List<ExplorerDTO> explorers);
}
