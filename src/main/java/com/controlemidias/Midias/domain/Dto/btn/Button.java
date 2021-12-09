package com.controlemidias.Midias.domain.Dto.btn;

import lombok.Data;

import java.util.List;

@Data
public class Button {

    private String number;
    private String buttonTitle;
    private String buttonBody;
    private String buttonFooter;
    private List<Bts> bts;

}
