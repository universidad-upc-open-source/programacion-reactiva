package com.example.springboot.programacionreactiva.controllers;

import com.example.springboot.programacionreactiva.models.entities.Producto;
import com.example.springboot.programacionreactiva.models.repositories.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;

@Controller
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping({"/productos", "/"})
    public String listarProductos(Model model){
        Flux<Producto> productos = productoRepository.findAll();
        model.addAttribute("productos", productos);
        model.addAttribute("titulo", "Nuestros productos");
        return "productos";
    }
}
