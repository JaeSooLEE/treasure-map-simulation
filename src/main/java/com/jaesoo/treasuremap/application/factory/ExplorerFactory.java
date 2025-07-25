package com.jaesoo.treasuremap.application.factory;

import com.jaesoo.treasuremap.application.port.in.dto.ExplorerDTO;
import com.jaesoo.treasuremap.domain.model.explorer.Explorer;

/**
 * Factory d’instances {@link Explorer} à partir des DTO fournis par un adapter.
 */
public interface ExplorerFactory {

    /**
     * Construit un explorateur de type adapté aux données en paramètre.
     *
     * @param explorerData DTO contenant nom, position initiale, orientation et séquence d’actions
     * @return une nouvelle instance de {@link Explorer}
     * @throws IllegalArgumentException si {@code explorerData.type()} est inconnu ou invalide
     */
    Explorer create(ExplorerDTO explorerData);
}
