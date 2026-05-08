package com.ejemplo.demo.api.dto;

import com.ejemplo.demo.domain.model.Producto;
import java.math.BigDecimal;
import java.time.Instant;

public record ProductoResponse(
        Long id,
        String nombre,
        String descripcion,
        String sku,
        BigDecimal precio,
        Long categoriaId,
        String categoriaNombre,
        Instant creadoEn,
        Instant actualizadoEn
) {
    public static ProductoResponse desde(Producto p) {
        return new ProductoResponse(
                p.getId(),
                p.getNombre(),
                p.getDescripcion(),
                p.getSku(),
                p.getPrecio(),
                p.getCategoria().getId(),
                p.getCategoria().getNombre(),
                p.getCreadoEn(),
                p.getActualizadoEn()
        );
    }
}
