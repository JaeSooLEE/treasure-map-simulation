package com.jaesoo.treasuremap.adapter.out.file.lineHandlers;

import com.jaesoo.treasuremap.application.port.in.validator.TreasureValidator;
import com.jaesoo.treasuremap.domain.model.geometry.Position;
import com.jaesoo.treasuremap.domain.model.map.TreasureMap;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import java.util.Set;

public class TreasureHandler implements LineHandler{
    @Override
    public String getRecordType() {
        return "T";
    }

    @Override
    public void handle(TreasureMap map, String[] sections, Validator validator) {
        int x = Integer.parseInt(sections[1]);
        int y = Integer.parseInt(sections[2]);
        int count = Integer.parseInt(sections[3]);
        TreasureValidator treasureValidator = new TreasureValidator(x, y, count);
        Set<ConstraintViolation<TreasureValidator>> violations = validator.validate(treasureValidator);
        if (!violations.isEmpty()) {
            throw new IllegalArgumentException("Invalid treasure data : " + violations);
        }
        map.getCellAt(new Position(x, y)).setTreasureCount(count);
    }
}
