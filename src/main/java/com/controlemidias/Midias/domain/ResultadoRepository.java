package com.controlemidias.Midias.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ResultadoRepository {

    @Autowired
    private JdbcTemplate db;

    public Boolean Salvar(Resultado r){

        try {
            db.update("insert into resultado (numero,numeroenviado,menssage) values(?,?,?)",
                    new Object[] { r.getNumero(), r.getNumeroenviado(), r.getMenssage()});
            return true;
        } catch (IncorrectResultSizeDataAccessException e) {
            return false;
        }
    }
    public List<Resultado> atendimentos(){
        try {
            List<Resultado> atendimento = db.query("select * from  resultado",
                    BeanPropertyRowMapper.newInstance(Resultado.class));
            return atendimento;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }
}
