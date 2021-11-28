package com.controlemidias.Midias.domain;

import java.sql.Date;

import lombok.Data;

@Data
public class Usuario {
	private Long id;
	private String nome;
	private String genero;
	private String usuario;
	private String senha;
	private Date datacadastro;
	

}
