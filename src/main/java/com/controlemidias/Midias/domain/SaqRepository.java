package com.controlemidias.Midias.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SaqRepository {

    @Autowired
    private JdbcTemplate db;

    public Boolean Salvar(Saq r){

        try {
            db.update("insert into saq (numero,numeroenviado,menssage) values(?,?,?)",
                    new Object[] { r.getNumero(), r.getNumeroenviado(), r.getMenssage()});
            return true;
        } catch (IncorrectResultSizeDataAccessException e) {
            return false;
        }
    }
}
