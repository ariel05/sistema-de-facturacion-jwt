package com.sistemadefacturacion.springboot.app.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.sistemadefacturacion.springboot.app.models.entity.Factura;

public interface IFacturaDao extends CrudRepository<Factura, Long>{

	//Necesario para que toda la consulta se haga en una y hibernate no tengo que volver a hacer la consulta en caso de acceder a un objeto dentro de la factura
	@Query("select f from Factura f join fetch f.cliente c join fetch f.item l join fetch l.producto where f.id=?1")
	public Factura fetchByIdWhitClienteWhitItemFacturaWhitProducto(Long id);
}
