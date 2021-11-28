package com.controlemidias.Midias.domain;

import lombok.Data;


@Data
public class LogEnvio {

    private Long id;
    private Long tipo;
    private String numeroenviado;
    private String numeros;
    private String idmensage;
}
