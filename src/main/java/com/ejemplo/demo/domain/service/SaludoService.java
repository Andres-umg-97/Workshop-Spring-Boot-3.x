package com.ejemplo.demo.domain.service;

import com.ejemplo.demo.api.dto.SaludoResponse;
import org.springframework.stereotype.Service;

@Service
public class SaludoService {

    public SaludoResponse crearSaludo(String nombre) {
        String limpio = normalizarNombre(nombre);
        return new SaludoResponse("Hola " + limpio);
    }

    private String normalizarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("Nombre inválido");
        }

        String limpio = nombre.trim().toLowerCase();
        return limpio.substring(0,1).toUpperCase() + limpio.substring(1);
    }
}