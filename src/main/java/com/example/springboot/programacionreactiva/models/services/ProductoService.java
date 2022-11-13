package com.example.springboot.programacionreactiva.models.services;

import com.example.springboot.programacionreactiva.models.entities.Producto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * El contrato para producto
 */
public interface ProductoService {

    public Flux<Producto> findAll();

    public Flux<Producto> findAllCustom();

    public Mono<Producto> findById(String id);

    public Mono<Producto> save(Producto producto);
}
