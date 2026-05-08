package com.ejemplo.demo.api.controller;

import com.ejemplo.demo.api.dto.PrestamoResponse;
import com.ejemplo.demo.domain.service.PrestamoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PrestamoController.class)
class PrestamoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PrestamoService prestamoService;

    @Test
    @DisplayName("Debe simular prestamo exitosamente con datos válidos")
    void debeSimularPrestamoExitosamente() throws Exception {
        BigDecimal monto = new BigDecimal("10000");
        BigDecimal tasa = new BigDecimal("12");
        int meses = 12;

        PrestamoResponse respuesta = new PrestamoResponse(
                new BigDecimal("888.49"),
                new BigDecimal("661.85"),
                new BigDecimal("10661.85")
        );

        when(prestamoService.simular(monto, tasa, meses)).thenReturn(respuesta);

        mockMvc.perform(post("/api/v1/simulaciones/prestamo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"monto\":10000,\"tasaAnual\":12,\"meses\":12}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cuotaMensual").value(888.49))
                .andExpect(jsonPath("$.interesTotal").value(661.85))
                .andExpect(jsonPath("$.totalPagar").value(10661.85));
    }

    @Test
    @DisplayName("Debe retornar 400 cuando monto es inválido")
    void debeRetornar400CuandoMontoEsInvalido() throws Exception {
        mockMvc.perform(post("/api/v1/simulaciones/prestamo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"monto\":0,\"tasaAnual\":12,\"meses\":12}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.codigo").value("VALIDATION_ERROR"));
    }

    @Test
    @DisplayName("Debe retornar 400 cuando meses está fuera del rango permitido")
    void debeRetornar400CuandoMesesEstaFueraDeRango() throws Exception {
        mockMvc.perform(post("/api/v1/simulaciones/prestamo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"monto\":10000,\"tasaAnual\":12,\"meses\":0}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.codigo").value("VALIDATION_ERROR"));
    }
}
