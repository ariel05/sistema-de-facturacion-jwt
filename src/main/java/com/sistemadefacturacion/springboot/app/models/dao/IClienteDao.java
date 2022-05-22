package com.sistemadefacturacion.springboot.app.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.sistemadefacturacion.springboot.app.models.entity.Cliente;

public interface IClienteDao extends PagingAndSortingRepository<Cliente, Long>{
	
	//Necesario para que toda la consulta se haga en una y hibernate no tengo que volver a hacer la consulta en caso de acceder a una factura desde el cliente
	//Es requerido el left join para poder accerder a los clientes aunque no tengan facturas relacionadas
	@Query("select c from Cliente c left join fetch c.facturas f where c.id=?1")
	public Cliente fetchByIdWhitFacturas(Long id);
}
