package com.example.springboot.programacionreactiva.controllers;

import com.example.springboot.programacionreactiva.models.entities.Producto;
import com.example.springboot.programacionreactiva.models.services.ProductoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    private ProductoService productoService;

    private static final Logger log = LoggerFactory.getLogger(ProductoRestController.class);

    @GetMapping( "/productos")
    public Flux<Producto> listaProductos() {
        Flux<Producto> productos = productoService
                                        .findAll()
                                        .doOnNext(prod -> log.info(prod.getNombre()));
        return productos;
    }

    @GetMapping( "/productos-custom")
    public Mono<ResponseEntity<Flux<Producto>>> listaProductosReactiva() {
        return Mono.just(
                ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(productoService.findAllCustom())
         );
    }

    @GetMapping("/{id}")
    public Mono<Producto> detalleProducto(@PathVariable String id){
        Flux<Producto> productos = productoService.findAll();

        // Filtramos el producto y retornamos un Mono
        Mono<Producto> producto = productos.filter(p -> p.getId().equals(id)).next();
        return producto;
    }
}
