package com.ejemplo.demo.api.dto;

import jakarta.validation.constraints.NotBlank;

public record SaludoRequest(
        @NotBlank(message = "El nombre es obligatorio")
        String nombre
) {}