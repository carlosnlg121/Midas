package com.controlemidias.Midias.services.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.controlemidias.Midias.domain.Usuario;
import com.controlemidias.Midias.domain.UsuarioRepository;
import com.controlemidias.Midias.services.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	UsuarioRepository repository;
	
	@Override
	public List<Usuario> getList(){
		return null;
	}
	
	@Override
	public Optional<Usuario > getPorId(Long id){
		return null;
	}
	
	@Override
	public Usuario Post(Usuario user){
		return null;
	}
	
}
