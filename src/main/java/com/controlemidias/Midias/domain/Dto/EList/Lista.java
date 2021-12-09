package com.controlemidias.Midias.domain.Dto.EList;

import lombok.Data;

import java.util.List;

@Data
public class Lista {

    private String number;
    private String List_body;
    private String Title;
    private String btnText;
    private String footer;
    private List<com.controlemidias.Midias.domain.Dto.EList.List> List;
}
