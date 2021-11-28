package com.controlemidias.Midias.services;

import java.util.List;
import java.util.Optional;

import com.controlemidias.Midias.domain.Usuario;

public interface UsuarioService {

	List<Usuario> getList();

	Optional<Usuario> getPorId(Long id);

	Usuario Post(Usuario user);




}
