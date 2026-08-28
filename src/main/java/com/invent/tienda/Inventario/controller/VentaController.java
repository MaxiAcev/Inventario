package com.invent.tienda.Inventario.controller;

import com.invent.tienda.Inventario.model.Venta;
import com.invent.tienda.Inventario.repository.ProductoRepository;
import com.invent.tienda.Inventario.repository.VentaRepository;
import com.invent.tienda.Inventario.service.VentaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ventas")
public class VentaController {
    private final VentaService ventaService;
    private final ProductoRepository productoRepository;
    private final VentaRepository ventaRepository;

    public  VentaController(VentaService ventaService, ProductoRepository productoRepository, VentaRepository ventaRepository) {
        this.ventaService = ventaService;
        this.productoRepository = productoRepository;
        this.ventaRepository = ventaRepository;
    }

    @GetMapping("/nueva")
    public String mostrarFormularioVenta(Model model) {
        model.addAttribute("productos", productoRepository.findAll());
        model.addAttribute("venta", new Venta());

        return "registrar-venta";
    }

    @PostMapping("/guardar")
    public String guardarVenta(@ModelAttribute("venta") Venta venta) {
        ventaService.registrarVenta(venta);

        return "redirect:/productos";
    }

    @GetMapping
    public String listarVentas(Model model) {
        model.addAttribute("ventas", ventaRepository.findAll());

        return "ventas";
    }
}
