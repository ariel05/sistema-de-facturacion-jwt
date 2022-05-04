package com.sistemadefacturacion.springboot.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sistemadefacturacion.springboot.app.models.service.IUploadFileService;

@SpringBootApplication
public class SistemaDeFacturacionApplication implements CommandLineRunner{

	@Autowired
	private IUploadFileService uploadFileService;
	
	public static void main(String[] args) {
		SpringApplication.run(SistemaDeFacturacionApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		this.uploadFileService.deleteAll();
		this.uploadFileService.init();
		
	}

}
