package com.jaesoo.treasuremap.adapter.out.file.dto;

import com.jaesoo.treasuremap.domain.model.geometry.Orientation;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.validation.annotation.Validated;

import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

public record ExplorerDTO(@NotBlank String name, @Min(0) int x, @Min(0) int y,
                          @NotNull Orientation orientation, @NotNull Queue<@NotNull Character> actions,
                          @NotNull char type) {

    public ExplorerDTO(String name, int x, int y, char orientation, String actions, char type) {
        this(name, x, y, Orientation.fromChar(orientation), actions == null || actions.isBlank()
                ? new LinkedList<>()
                : actions.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toCollection(LinkedList::new)), type);
    }

}
