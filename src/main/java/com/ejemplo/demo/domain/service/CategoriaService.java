package com.ejemplo.demo.domain.service;

import com.ejemplo.demo.api.dto.CategoriaRequest;
import com.ejemplo.demo.api.dto.CategoriaResponse;
import com.ejemplo.demo.domain.model.Categoria;
import com.ejemplo.demo.domain.repository.CategoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoriaService {

    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Page<CategoriaResponse> listar(Pageable pageable) {
        return repository.findAll(pageable).map(CategoriaResponse::desde);
    }

    @Transactional(readOnly = true)
    public CategoriaResponse obtenerPorId(Long id) {
        return CategoriaResponse.desde(buscarOFallar(id));
    }

    @Transactional
    public CategoriaResponse crear(CategoriaRequest request) {
        repository.findByNombre(request.nombre()).ifPresent(c -> {
            throw new IllegalArgumentException("Ya existe una categoría con el nombre: " + request.nombre());
        });
        Categoria nueva = new Categoria(request.nombre(), request.descripcion());
        return CategoriaResponse.desde(repository.save(nueva));
    }

    @Transactional
    public CategoriaResponse actualizar(Long id, CategoriaRequest request) {
        Categoria existente = buscarOFallar(id);
        repository.findByNombre(request.nombre()).ifPresent(c -> {
            if (!c.getId().equals(id)) {
                throw new IllegalArgumentException("Ya existe otra categoría con el nombre: " + request.nombre());
            }
        });
        existente.setNombre(request.nombre());
        existente.setDescripcion(request.descripcion());
        return CategoriaResponse.desde(repository.save(existente));
    }

    @Transactional
    public void eliminar(Long id) {
        buscarOFallar(id);
        repository.deleteById(id);
    }

    private Categoria buscarOFallar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con id: " + id));
    }
}
