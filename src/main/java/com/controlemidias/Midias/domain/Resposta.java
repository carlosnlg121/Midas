package com.controlemidias.Midias.domain;

import lombok.Data;


@Data
public class Resposta {

    private Long id;
    private String resposta;
    private Long id_pergunta;
    private Long id_sequencia;
    private Long id_dash;
    private boolean buscamedico;
    private boolean buscaagenda;
    private boolean finaliza;
    private boolean saq;
    private boolean resultado;

}
