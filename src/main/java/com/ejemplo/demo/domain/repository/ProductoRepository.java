package com.ejemplo.demo.domain.repository;

import com.ejemplo.demo.domain.model.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Optional<Producto> findBySku(String sku);
    Page<Producto> findByCategoriaId(Long categoriaId, Pageable pageable);
}
