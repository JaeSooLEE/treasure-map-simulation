package com.jaesoo.treasuremap.adapter.out.file.dto;

import jakarta.validation.constraints.Min;

public record MountainDTO(@Min(0) int x, @Min(0) int y) {
}
