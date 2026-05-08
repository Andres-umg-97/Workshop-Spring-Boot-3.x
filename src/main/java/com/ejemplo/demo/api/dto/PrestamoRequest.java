package com.ejemplo.demo.api.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record PrestamoRequest(
        @NotNull @DecimalMin("0.01") BigDecimal monto,
        @NotNull @DecimalMin("0.01") BigDecimal tasaAnual,
        @Min(1) @Max(360) int meses
) {}