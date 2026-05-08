package com.ejemplo.demo.api.controller;

import com.ejemplo.demo.domain.repository.CategoriaRepository;
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
class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @BeforeEach
    void limpiarBD() {
        categoriaRepository.deleteAll();
    }

    @Test
    @DisplayName("Crear categoría válida debe retornar 201")
    void crearCategoriaValida() throws Exception {
        mockMvc.perform(post("/api/v1/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"Bebidas\",\"descripcion\":\"Gaseosas y jugos\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nombre").value("Bebidas"))
                .andExpect(header().exists("Location"));
    }

    @Test
    @DisplayName("Crear categoría con nombre vacío debe retornar 400")
    void crearCategoriaInvalida() throws Exception {
        mockMvc.perform(post("/api/v1/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"\",\"descripcion\":\"desc\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.codigo").value("VALIDATION_ERROR"));
    }

    @Test
    @DisplayName("Obtener categoría inexistente debe retornar 404")
    void obtenerCategoriaInexistente() throws Exception {
        mockMvc.perform(get("/api/v1/categorias/9999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.codigo").value("NOT_FOUND"));
    }

    @Test
    @DisplayName("Listar categorías debe retornar 200")
    void listarCategorias() throws Exception {
        mockMvc.perform(post("/api/v1/categorias")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Electronica\",\"descripcion\":\"Gadgets\"}"));

        mockMvc.perform(get("/api/v1/categorias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());
    }

    @Test
    @DisplayName("Eliminar categoría inexistente debe retornar 404")
    void eliminarCategoriaInexistente() throws Exception {
        mockMvc.perform(delete("/api/v1/categorias/9999"))
                .andExpect(status().isNotFound());
    }
}
