package com.jaesoo.treasuremap.application.port.in.validator;

import jakarta.validation.constraints.Min;

public record TreasureValidator(@Min(0) int x, @Min(0) int y, @Min(0) int count) {
}
