package com.controlemidias.Midias.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Atendimento {

    private Long id;
    private Long id_agenda;
    private Long id_hora;
    private Long id_medico;
    private String numero;
    private String numerocliente;
}
