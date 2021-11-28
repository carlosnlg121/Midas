package com.controlemidias.Midias.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Hora {

    private Long id;
    private Long id_agenda;
    private Boolean diponivel;
    private String hora;


}
