package com.ejemplo.demo.api.dto;

import com.ejemplo.demo.domain.model.Categoria;
import java.time.Instant;

public record CategoriaResponse(
        Long id,
        String nombre,
        String descripcion,
        Instant creadoEn,
        Instant actualizadoEn
) {
    public static CategoriaResponse desde(Categoria c) {
        return new CategoriaResponse(
                c.getId(),
                c.getNombre(),
                c.getDescripcion(),
                c.getCreadoEn(),
                c.getActualizadoEn()
        );
    }
}
