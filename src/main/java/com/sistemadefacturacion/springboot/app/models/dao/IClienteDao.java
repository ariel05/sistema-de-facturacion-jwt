package com.sistemadefacturacion.springboot.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.sistemadefacturacion.springboot.app.models.entity.Cliente;

public interface IClienteDao extends CrudRepository<Cliente, Long>{

}
