package com.controlemidias.Midias.domain;


import lombok.Data;


@Data
public class Mensagens {
	private Long id;
	private Long tipo;
	private String pergunta;
	private Boolean respostaFinal;
	private Boolean registraPergunta;


}
