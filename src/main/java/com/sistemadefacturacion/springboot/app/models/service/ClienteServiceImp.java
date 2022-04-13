package com.sistemadefacturacion.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sistemadefacturacion.springboot.app.models.dao.IClienteDao;
import com.sistemadefacturacion.springboot.app.models.entity.Cliente;

@Service(value = "clienteService")
public class ClienteServiceImp implements IClienteService{
	
	@Autowired
	@Qualifier("clienteDaoJPA")
	private IClienteDao clienteDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		return clienteDao.findAll();
	}

	@Override
	@Transactional
	public void save(Cliente cliente) {
		clienteDao.save(cliente);
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findOne(Long id) {
		return clienteDao.findOne(id);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		clienteDao.delete(id);
	}

}
