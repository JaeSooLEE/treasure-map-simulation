package com.jaesoo.treasuremap.adapter.out.file.lineHandlers;

import com.jaesoo.treasuremap.application.port.in.validator.ExplorerValidator;
import com.jaesoo.treasuremap.domain.model.explorer.Action;
import com.jaesoo.treasuremap.domain.model.explorer.Adventurer;
import com.jaesoo.treasuremap.domain.model.geometry.Orientation;
import com.jaesoo.treasuremap.domain.model.geometry.Position;
import com.jaesoo.treasuremap.domain.model.map.TreasureMap;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import java.util.LinkedList;
import java.util.Set;
import java.util.stream.Collectors;

public class AdventurerHandler implements ExplorerHandler{
    @Override
    public String getRecordType() {
        return "A";
    }

    @Override
    public void handle(TreasureMap map, String[] sections, Validator validator) {
        String name = sections[1];
        int x = Integer.parseInt(sections[2]);
        int y = Integer.parseInt(sections[3]);
        char orientationChar = sections[4].charAt(0);
        Orientation orientation = orientationFromChar(orientationChar);
        String actionsString = sections[5];
        LinkedList<Action> actions = actionsString == null || actionsString.isBlank()
                ? new LinkedList<>()
                : actionsString.chars()
                .mapToObj(c -> actionFromChar((char) c))
                .collect(Collectors.toCollection(LinkedList::new));

        ExplorerValidator expValidator = new ExplorerValidator(name, x, y, orientation, actions);
        Set<ConstraintViolation<ExplorerValidator>> violations = validator.validate(expValidator);
        if (!violations.isEmpty()) {
            throw new IllegalArgumentException("Invalid adventurer data : " + violations);
        }
        map.addExplorer(new Adventurer(name,new Position(x, y), orientation, actions));
    }
}
