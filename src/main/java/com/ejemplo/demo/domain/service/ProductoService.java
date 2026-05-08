package com.ejemplo.demo.domain.service;

import com.ejemplo.demo.api.dto.ProductoRequest;
import com.ejemplo.demo.api.dto.ProductoResponse;
import com.ejemplo.demo.domain.model.Categoria;
import com.ejemplo.demo.domain.model.Producto;
import com.ejemplo.demo.domain.repository.CategoriaRepository;
import com.ejemplo.demo.domain.repository.ProductoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final CategoriaRepository categoriaRepository;

    public ProductoService(ProductoRepository productoRepository,
                           CategoriaRepository categoriaRepository) {
        this.productoRepository = productoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Transactional(readOnly = true)
    public Page<ProductoResponse> listar(Pageable pageable) {
        return productoRepository.findAll(pageable).map(ProductoResponse::desde);
    }

    @Transactional(readOnly = true)
    public ProductoResponse obtenerPorId(Long id) {
        return ProductoResponse.desde(buscarOFallar(id));
    }

    @Transactional
    public ProductoResponse crear(ProductoRequest request) {
        productoRepository.findBySku(request.sku()).ifPresent(p -> {
            throw new IllegalArgumentException("Ya existe un producto con el SKU: " + request.sku());
        });
        Categoria categoria = categoriaRepository.findById(request.categoriaId())
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con id: " + request.categoriaId()));

        Producto nuevo = new Producto(
                request.nombre(),
                request.descripcion(),
                request.sku(),
                request.precio(),
                categoria
        );
        return ProductoResponse.desde(productoRepository.save(nuevo));
    }

    @Transactional
    public ProductoResponse actualizar(Long id, ProductoRequest request) {
        Producto existente = buscarOFallar(id);

        productoRepository.findBySku(request.sku()).ifPresent(p -> {
            if (!p.getId().equals(id)) {
                throw new IllegalArgumentException("Ya existe otro producto con el SKU: " + request.sku());
            }
        });

        Categoria categoria = categoriaRepository.findById(request.categoriaId())
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con id: " + request.categoriaId()));

        existente.setNombre(request.nombre());
        existente.setDescripcion(request.descripcion());
        existente.setSku(request.sku());
        existente.setPrecio(request.precio());
        existente.setCategoria(categoria);

        return ProductoResponse.desde(productoRepository.save(existente));
    }

    @Transactional
    public void eliminar(Long id) {
        buscarOFallar(id);
        productoRepository.deleteById(id);
    }

    private Producto buscarOFallar(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con id: " + id));
    }
}
