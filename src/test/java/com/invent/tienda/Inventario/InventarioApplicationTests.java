package com.invent.tienda.Inventario;

import com.invent.tienda.Inventario.model.DetalleVenta;
import com.invent.tienda.Inventario.model.Producto;
import com.invent.tienda.Inventario.model.Venta;
import com.invent.tienda.Inventario.repository.ProductoRepository;
import com.invent.tienda.Inventario.service.VentaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class InventarioApplicationTests {

	@Autowired
	private VentaService ventaService;

	@Autowired
	private ProductoRepository productoRepository;

	@Test
	void probarRegistrarVenta() {
		// 1. Buscamos un producto que sepamos que existe en la BD (ej. el ID 1)
		Producto producto = productoRepository.findById(2)
				.orElseThrow(() -> new RuntimeException("Cargá al menos un producto en la BD primero"));

		System.out.println("STOCK INICIAL: " + producto.getStock());

		// 2. Armamos la Venta y sus Detalles en memoria
		Venta venta = new Venta();
		List<DetalleVenta> detalles = new ArrayList<>();

		DetalleVenta detalle1 = new DetalleVenta();
		detalle1.setProducto(producto);
		detalle1.setCantidad(3);

		detalles.add(detalle1);
		venta.setDetalles(detalles);

		// 3. Ejecutamos el servicio de registrar venta
		Venta ventaGuardada = ventaService.registrarVenta(venta);

		// 4. Imprimimos el resultado en la consola de ejecución
		System.out.println("✅ VENTA REGISTRADA CON ÉXITO!");
		System.out.println("ID Venta: " + ventaGuardada.getIdVenta());
		System.out.println("Total Cobrado: $" + ventaGuardada.getTotal());
		System.out.println("Fecha/Hora: " + ventaGuardada.getFechaHora());

		// Volvemos a consultar el producto para ver si bajó el stock
		Producto productoActualizado = productoRepository.findById(2).get();
		System.out.println("STOCK FINAL: " + productoActualizado.getStock());
	}
}
