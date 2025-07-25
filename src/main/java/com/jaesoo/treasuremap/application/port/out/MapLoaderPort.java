package com.jaesoo.treasuremap.application.port.out;

import com.jaesoo.treasuremap.domain.model.map.TreasureMap;

/**
 * Port abstrait pour charger une carte. L’implémentation concrète peut provenir d’un fichier, d’une base de données, d’un service web, etc.
 */
public interface MapLoaderPort {

    /**
     * Charge une carte et ses explorateurs depuis la source spécifiée.
     *
     * @param source chaîne identifiant la source (par exemple un chemin de fichier)
     * @return une instance de {@link TreasureMap} prête à être simulée
     * @throws IllegalArgumentException si la source est invalide ou mal formée
     */
    TreasureMap loadMap(String source);
}
