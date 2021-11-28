package com.controlemidias.Midias.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    public List<Saq> atendimentos(){
        try {
            List<Saq> atendimento = db.query("select * from  saq",
                    BeanPropertyRowMapper.newInstance(Saq.class));
            return atendimento;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }
}
