package com.example.springboot.programacionreactiva.controllers;

import com.example.springboot.programacionreactiva.models.entities.Producto;
import com.example.springboot.programacionreactiva.models.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Controller
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping({"/productos", "/"})
    public String listarProductos(Model model){
        Flux<Producto> productos = productoService.findAll().delayElements(Duration.ofSeconds(1));

        model.addAttribute("productos", productos);
        model.addAttribute("titulo", "Nuestros productos");
        return "productos";
    }

    @GetMapping("/productos-stream")
    public String listarProductosStream(Model model){
        Flux<Producto> productos = productoService.findAll().delayElements(Duration.ofSeconds(1));

        model.addAttribute("productos", new ReactiveDataDriverContextVariable(productos, 2));
        model.addAttribute("titulo", "Nuestros productos");
        return "productos";
    }
}
