package com.sistemadefacturacion.springboot.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemadefacturacion.springboot.app.models.service.IClienteService;
import com.sistemadefacturacion.springboot.app.view.xml.ClienteList;

//RestController combina @Controller y @ResponseBody, por lo tanto todos los métodos de este controller van a ser Rest, por lo cual no es necesario agregarle @ResponseBody a los métodos
@RestController
@RequestMapping("/api/clientes")
public class ClienteRestController {

	@Autowired
	@Qualifier("clienteService")
	private IClienteService clienteService;

	@GetMapping(value = "/listar")
	public ClienteList listar() {
		//Si quiero usar solo json, así está bien
		//return clienteService.findAll();
		
		//Pero si quiero usar json y XML, tengo que cambiar a ClienteList 
		return new ClienteList(clienteService.findAll());
	}
}
