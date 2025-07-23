package com.jaesoo.treasuremap.adapter.out.file.dto;

import jakarta.validation.constraints.Min;

public record TreasureDTO(@Min(0) int x, @Min(0) int y, @Min(0) int count) {
}
