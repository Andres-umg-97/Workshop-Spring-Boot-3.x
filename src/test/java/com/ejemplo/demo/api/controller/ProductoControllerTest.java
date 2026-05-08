package com.ejemplo.demo.api.controller;

import com.ejemplo.demo.domain.model.Categoria;
import com.ejemplo.demo.domain.repository.CategoriaRepository;
import com.ejemplo.demo.domain.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    private Long categoriaId;

    @BeforeEach
    void limpiarBD() {
        productoRepository.deleteAll();
        categoriaRepository.deleteAll();
        Categoria cat = categoriaRepository.save(new Categoria("Bebidas", "Gaseosas"));
        categoriaId = cat.getId();
    }

    @Test
    @DisplayName("Crear producto válido debe retornar 201")
    void crearProductoValido() throws Exception {
        mockMvc.perform(post("/api/v1/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format(
                                "{\"nombre\":\"Coca-Cola\",\"descripcion\":\"500ml\",\"sku\":\"CC-001\",\"precio\":5.50,\"categoriaId\":%d}",
                                categoriaId)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.sku").value("CC-001"))
                .andExpect(jsonPath("$.categoriaNombre").value("Bebidas"))
                .andExpect(header().exists("Location"));
    }

    @Test
    @DisplayName("Crear producto con precio inválido debe retornar 400")
    void crearProductoInvalido() throws Exception {
        mockMvc.perform(post("/api/v1/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format(
                                "{\"nombre\":\"Coca-Cola\",\"sku\":\"CC-002\",\"precio\":0,\"categoriaId\":%d}",
                                categoriaId)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.codigo").value("VALIDATION_ERROR"));
    }

    @Test
    @DisplayName("Obtener producto inexistente debe retornar 404")
    void obtenerProductoInexistente() throws Exception {
        mockMvc.perform(get("/api/v1/productos/9999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.codigo").value("NOT_FOUND"));
    }

    @Test
    @DisplayName("Crear producto con categoría inexistente debe retornar 404")
    void crearProductoConCategoriaInexistente() throws Exception {
        mockMvc.perform(post("/api/v1/productos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Coca-Cola\",\"sku\":\"CC-003\",\"precio\":5.50,\"categoriaId\":9999}"))
                .andExpect(status().isNotFound());
    }
}
