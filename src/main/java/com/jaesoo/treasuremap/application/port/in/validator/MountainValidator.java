package com.jaesoo.treasuremap.application.port.in.validator;

import jakarta.validation.constraints.Min;

public record MountainValidator(@Min(0) int x, @Min(0) int y) {
}
