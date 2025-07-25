package com.jaesoo.treasuremap.application.port.in.dto;

import com.jaesoo.treasuremap.domain.model.explorer.Action;
import com.jaesoo.treasuremap.domain.model.explorer.ExplorerType;
import com.jaesoo.treasuremap.domain.model.geometry.Orientation;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Queue;

public record ExplorerDTO(@NotBlank String name, @Min(0) int x, @Min(0) int y,
                          @NotNull Orientation orientation, @NotNull Queue<@NotNull Action> actions,
                          @NotNull ExplorerType type) {
}
