package com.example.springboot.programacionreactiva.models.repositories;

import com.example.springboot.programacionreactiva.models.entities.Producto;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProductoRepository extends ReactiveMongoRepository<Producto, String> {

}
