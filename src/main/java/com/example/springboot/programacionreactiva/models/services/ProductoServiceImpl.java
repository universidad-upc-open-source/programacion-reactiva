package com.example.springboot.programacionreactiva.models.services;

import com.example.springboot.programacionreactiva.controllers.ProductoRestController;
import com.example.springboot.programacionreactiva.models.entities.Producto;
import com.example.springboot.programacionreactiva.models.repositories.ProductoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    private static final Logger log = LoggerFactory.getLogger(ProductoRestController.class);

    @Override
    public Flux<Producto> findAll() {
        Flux<Producto> productos = productoRepository.findAll();
        return productos;
    }

    @Override
    public Flux<Producto> findAllCustom() {
        Flux<Producto> productos = productoRepository
                .findAll()
                .map(prod -> {
                    prod.setNombre(prod.getNombre().toUpperCase());
                    return prod;
                });
        return productos;
    }

    @Override
    public Mono<Producto> findById(String id) {
        Flux<Producto> productos = productoRepository.findAll();

        // Filtramos el producto y retornamos un Mono
        Mono<Producto> producto = productos.filter(p -> p.getId().equals(id)).next();
        return producto;
    }

    public Mono<Producto> save(Producto producto) {
        /*
         * if (StringUtils.isEmpty(producto.getCreateAt())) {
         * producto.setCreateAt(new Date());
         * }
         */
        return productoRepository.save(producto);
    }
}
