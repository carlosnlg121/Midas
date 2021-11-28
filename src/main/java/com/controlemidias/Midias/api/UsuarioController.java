package com.controlemidias.Midias.api;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.controlemidias.Midias.domain.Usuario;
import com.controlemidias.Midias.services.UsuarioService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Usuario")
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	UsuarioService service;

	@ApiOperation(value = "Listar Usuarios")
	@GetMapping("/")
	public ResponseEntity<?>  getUsuarios() {
		return ResponseEntity.ok(service.getList());
	}
	
	@ApiOperation(value = "Listar Usuario por id")
	@GetMapping("/{id}")
	public ResponseEntity<?>  getUsuario(
			 @PathVariable Long id ) {
		return ResponseEntity.ok(service.getPorId(id));
	}
	
	
	@ApiOperation(value = "Listar Usuario por id")
	@PostMapping("/")
	public ResponseEntity<?>  PostUsuario( Usuario user ) {
		return ResponseEntity.ok(service.Post(user));
	}

}
