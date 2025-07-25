package com.jaesoo.treasuremap.application.factory;

import com.jaesoo.treasuremap.application.port.in.dto.ExplorerDTO;
import com.jaesoo.treasuremap.domain.model.explorer.Adventurer;
import com.jaesoo.treasuremap.domain.model.explorer.Explorer;
import com.jaesoo.treasuremap.domain.model.explorer.ExplorerType;
import com.jaesoo.treasuremap.domain.model.geometry.Position;

public class ExplorerFactoryImpl implements ExplorerFactory {


    /**
     * {@inheritDoc}
     */
    @Override
    public Explorer create(ExplorerDTO explorerData) {
        ExplorerType type = explorerData.type();
        if (type == null) {
            throw new IllegalArgumentException("Explorer type null");
        }
        return switch (type) {
            case ExplorerType.ADVENTURER ->
                    new Adventurer(explorerData.name(), new Position(explorerData.x(), explorerData.y()), explorerData.orientation(), explorerData.actions());
        };
    }
}
