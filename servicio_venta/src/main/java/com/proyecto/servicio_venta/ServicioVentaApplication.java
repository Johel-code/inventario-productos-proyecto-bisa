package com.proyecto.servicio_venta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ServicioVentaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicioVentaApplication.class, args);
	}

}
