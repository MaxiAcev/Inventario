package com.invent.tienda.Inventario.repository;

import com.invent.tienda.Inventario.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository <Producto, Integer> {
    Optional<Producto> findByCodBarras(String codBarras);
}
