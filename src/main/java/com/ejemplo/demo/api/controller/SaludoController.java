package com.ejemplo.demo.api.controller;

import com.ejemplo.demo.api.dto.*;
import com.ejemplo.demo.domain.service.SaludoService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class SaludoController {

    private final SaludoService saludoService;

    public SaludoController(SaludoService saludoService) {
        this.saludoService = saludoService;
    }

    @GetMapping
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of(
                "estado", "ok",
                "mensaje", "Workshop Spring Boot activo"
        ));
    }

    @GetMapping("/saludos")
    public ResponseEntity<SaludoResponse> saludar(
            @RequestParam(defaultValue = "Mundo") String nombre
    ) {
        return ResponseEntity.ok(saludoService.crearSaludo(nombre));
    }

    @PostMapping("/saludos")
    public ResponseEntity<SaludoResponse> saludarPost(
            @Valid @RequestBody SaludoRequest request
    ) {
        return ResponseEntity.ok(saludoService.crearSaludo(request.nombre()));
    }
}