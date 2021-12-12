package com.controlemidias.Midias.domain;

import lombok.Data;


@Data
public class UtimaPergunta {
    private Long id;
    private String numeroenviado;
    private Long id_pergunta;
    private String numero;
    private String reg;
    private boolean medico;
    private boolean agenda;
    private String pergunta;
}
