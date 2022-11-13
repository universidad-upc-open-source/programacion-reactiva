package com.example.springboot.programacionreactiva;

import com.example.springboot.programacionreactiva.models.entities.Producto;
import com.example.springboot.programacionreactiva.models.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.time.Duration;

@Configuration
public class RouterFunctionConfig {

    @Autowired
    private ProductoService productoService;

    @Bean
    public RouterFunction<ServerResponse> routes(){
        return RouterFunctions.route(RequestPredicates.GET("/api/v2/productos"), request -> {
            return ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(productoService.findAll().delayElements(Duration.ofSeconds(1)), Producto.class);
        });
    }
}
