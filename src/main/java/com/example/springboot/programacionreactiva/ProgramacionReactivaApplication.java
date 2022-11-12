package com.example.springboot.programacionreactiva;

import com.example.springboot.programacionreactiva.models.entities.Producto;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class ProgramacionReactivaApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ProgramacionReactivaApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception{
        Flux.just(
            new Producto("Mesa Altura Regulable", 1369.89),
            new Producto("Audífono Pro X Wireless", 800.00),
            new Producto("Mesa Altura Regulable", 1369.89)



        );
    }
}
