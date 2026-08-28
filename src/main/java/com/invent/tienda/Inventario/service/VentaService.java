package com.invent.tienda.Inventario.service;

import com.invent.tienda.Inventario.model.DetalleVenta;
import com.invent.tienda.Inventario.model.Producto;
import com.invent.tienda.Inventario.model.Venta;
import com.invent.tienda.Inventario.repository.ProductoRepository;
import com.invent.tienda.Inventario.repository.VentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Service
public class VentaService {
    private final ProductoRepository productoRepository;
    private final VentaRepository ventaRepository;

    public VentaService (ProductoRepository productoRepository, VentaRepository ventaRepository) {
        this.productoRepository = productoRepository;
        this.ventaRepository = ventaRepository;
    }

    @Transactional
    public Venta registrarVenta(Venta venta) {
        BigDecimal totalVenta = BigDecimal.ZERO;
        for (DetalleVenta detalle : venta.getDetalles()) {
            Producto producto = productoRepository.findById(detalle.getProducto().getIdProducto())
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con el ID: " + detalle.getProducto().getIdProducto()));
            if (producto.getStock() < detalle.getCantidad()) {
                throw new IllegalArgumentException("Stock insuficiente para el producto: " + producto.getNombre());
            }
            producto.setStock(producto.getStock() - detalle.getCantidad());
            productoRepository.save(producto);

            BigDecimal subTotal = producto.getPrecio().multiply(BigDecimal.valueOf(detalle.getCantidad()));
            detalle.setPrecioUnitario(producto.getPrecio());
            detalle.setSubTotal(subTotal);

            detalle.setVenta(venta);
            totalVenta = totalVenta.add(subTotal);
        }
        venta.setTotal(totalVenta);
        venta.setFechaHora(LocalDateTime.now());

        return ventaRepository.save(venta);
    }
}
