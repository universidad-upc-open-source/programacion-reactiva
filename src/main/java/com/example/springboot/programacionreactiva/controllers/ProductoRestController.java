package com.example.springboot.programacionreactiva.controllers;

import com.example.springboot.programacionreactiva.models.entities.Producto;
import com.example.springboot.programacionreactiva.models.repositories.ProductoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/api")
public class ProductoRestController {
    @Autowired
    private ProductoRepository productoRepository;

    private static final Logger log = LoggerFactory.getLogger(ProductoRestController.class);

    @GetMapping(path = "/productos")
    public Flux<Producto> index() {
        Flux<Producto> productos = productoRepository
                .findAll()
                .delayElements(Duration.ofSeconds(1))
                .doOnNext(prod -> log.info(prod.getNombre()));
        return productos;
    }

    @GetMapping("/{id}")
    public Mono<Producto> detalleProducto(@PathVariable String id){
        Flux<Producto> productos = productoRepository.findAll();

        // Filtramos el producto y retornamos un Mono
        Mono<Producto> producto = productos.filter(p -> p.getId().equals(id)).next();
        return producto;
    }
}
