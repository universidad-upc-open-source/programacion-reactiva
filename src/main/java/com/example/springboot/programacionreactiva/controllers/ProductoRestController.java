package com.example.springboot.programacionreactiva.controllers;

import com.example.springboot.programacionreactiva.models.entities.Producto;
import com.example.springboot.programacionreactiva.models.services.ProductoService;

import java.net.URI;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
@CrossOrigin({ "*" })
public class ProductoRestController {

    @Autowired
    private ProductoService productoService;

    private static final Logger log = LoggerFactory.getLogger(ProductoRestController.class);

    // Listado de productos
    @GetMapping("/productos")
    public Flux<Producto> listaProductos() {
        Flux<Producto> productos = productoService
                .findAll()
                .doOnNext(prod -> log.info(prod.getNombre()));
        return productos;
    }

    // Listado de productos modificado
    @GetMapping("/productos-custom")
    public Mono<ResponseEntity<Flux<Producto>>> listaProductosCustom() {
        return Mono.just(
                ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(productoService.findAllCustom()));
    }

    // Detalle de producto
    @GetMapping("/productos/{id}")
    public Mono<ResponseEntity<Producto>> detalleProducto(@PathVariable String id) {
        return productoService.findById(id)
                .map(p -> ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(p))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/productos")
    public Mono<ResponseEntity<Producto>> crearProducto(@RequestBody Producto producto) {
        if (producto.getCreateAt() == null) {
            producto.setCreateAt(new Date());
        }

        return productoService.save(producto).map(p -> ResponseEntity
                .created(URI.create("/api/productos/".concat(p.getId())))
                .contentType(MediaType.APPLICATION_JSON)
                .body(p));
    }

}
