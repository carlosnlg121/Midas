package com.controlemidias.Midias.domain;

import lombok.Data;

import java.util.Date;
import java.util.Timer;

@Data
public class Agenda {
    private Long id;
    private Long id_medico;
    private Date agenda;

}
