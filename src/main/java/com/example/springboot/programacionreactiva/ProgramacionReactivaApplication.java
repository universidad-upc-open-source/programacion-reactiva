package com.example.springboot.programacionreactiva;

import com.example.springboot.programacionreactiva.models.entities.Producto;
import com.example.springboot.programacionreactiva.models.repositories.ProductoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;

import java.util.Date;

@SpringBootApplication
public class ProgramacionReactivaApplication implements CommandLineRunner {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ReactiveMongoTemplate mongoTemplate;
    private static final Logger log = LoggerFactory.getLogger(ProgramacionReactivaApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(ProgramacionReactivaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        // Para desarrollo, eliminamos la colección
        mongoTemplate.dropCollection("productos").subscribe();
        Flux.just(
            new Producto("Mesa Altura Regulable", 1369.89),
            new Producto("Audífono Pro X Wireless", 800.00),
            new Producto("Iphone 13 64GB", 3000.00),
            new Producto("Monitor Xiaomi 4K", 2800.00)
        ).flatMap(producto -> {
            producto.setCreateAt(new Date());
            return productoRepository.save(producto);
        })
        .subscribe(producto -> log.info("Crea: " + producto.getId() + " nombre: " + producto.getNombre()));
    }
}
