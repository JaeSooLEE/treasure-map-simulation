package com.jaesoo.treasuremap.adapter.out.file.lineHandlers;

import com.jaesoo.treasuremap.application.port.in.validator.MountainValidator;
import com.jaesoo.treasuremap.domain.model.geometry.Position;
import com.jaesoo.treasuremap.domain.model.map.MapCell;
import com.jaesoo.treasuremap.domain.model.map.TerrainType;
import com.jaesoo.treasuremap.domain.model.map.TreasureMap;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import java.util.Set;

public class MountainHandler implements LineHandler {

    @Override
    public String getRecordType() {
        return "M";
    }

    @Override
    public void handle(TreasureMap map, String[] sections, Validator validator) {
        int x = Integer.parseInt(sections[1]);
        int y = Integer.parseInt(sections[2]);
        MountainValidator mountainValidator = new MountainValidator(x, y);
        Set<ConstraintViolation<MountainValidator>> violations = validator.validate(mountainValidator);
        if (!violations.isEmpty()) {
            throw new IllegalArgumentException("Invalid mountain data : " + violations);
        }
        map.setCellAt(new MapCell(TerrainType.MOUNTAIN, map.getCellAt(new Position(x, y)).getTreasureCount()), x, y);
    }
}
