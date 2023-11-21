package com.example.carrinhopetshop.dto.client;

import jakarta.validation.constraints.NotBlank;

public record ClientRequest(@NotBlank String name) {
}
