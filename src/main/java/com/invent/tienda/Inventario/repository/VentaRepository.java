package com.invent.tienda.Inventario.repository;

import com.invent.tienda.Inventario.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepository extends JpaRepository <Venta, Integer> {
}
