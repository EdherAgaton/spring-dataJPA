package com.example.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.models.dao.IClienteDao;
import com.example.app.models.entity.Cliente;

import jakarta.transaction.Transactional;

@Service()
public class ClienteServiceImplements  implements IClienteService{
	
	@Autowired
	private IClienteDao clienteDao;

	@Transactional
	@Override
	public List<Cliente> findAll() {
		
		return (List<Cliente>) clienteDao.findAll();
	}

	@Transactional
	@Override
	public void save(Cliente cliente) {
		clienteDao.save(cliente);
		
	}

	@Transactional
	@Override
	public Cliente findOne(long id) {
		
		return clienteDao.findById(id).orElse(null);
	}

	@Transactional
	@Override
	public void delete(long id) {
		clienteDao.deleteById(id);
		
	}

}
