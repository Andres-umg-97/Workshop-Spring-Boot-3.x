package com.ejemplo.demo.api.controller;

import com.ejemplo.demo.api.dto.*;
import com.ejemplo.demo.domain.service.PrestamoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/simulaciones")
public class PrestamoController {

    private final PrestamoService service;

    public PrestamoController(PrestamoService service) {
        this.service = service;
    }

    @PostMapping("/prestamo")
    public PrestamoResponse simular(@Valid @RequestBody PrestamoRequest request) {
        return service.simular(
                request.monto(),
                request.tasaAnual(),
                request.meses()
        );
    }
}