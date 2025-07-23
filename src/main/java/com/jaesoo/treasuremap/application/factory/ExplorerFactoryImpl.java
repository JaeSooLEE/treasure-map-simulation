package com.jaesoo.treasuremap.application.factory;

import com.jaesoo.treasuremap.adapter.out.file.dto.ExplorerDTO;
import com.jaesoo.treasuremap.domain.model.explorer.Adventurer;
import com.jaesoo.treasuremap.domain.model.explorer.Explorer;
import com.jaesoo.treasuremap.domain.model.geometry.Position;

public class ExplorerFactoryImpl implements ExplorerFactory{

    @Override
    public Explorer create(ExplorerDTO explorerData) {
        char type = explorerData.type();
        switch(type) {
            case 'A' -> {
                return  new Adventurer(explorerData.name(), new Position(explorerData.x(), explorerData.y()), explorerData.orientation(), explorerData.actions());
            }
            default -> throw new IllegalArgumentException("Type d'explorateur inconnu : " + type);
        }
    }
}
