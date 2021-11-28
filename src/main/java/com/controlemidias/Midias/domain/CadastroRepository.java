package com.controlemidias.Midias.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public class CadastroRepository {

    @Autowired
    private JdbcTemplate db;

    public Cadastro findByNumeroIs(String numero){

        try {
            Cadastro cad = db.queryForObject("SELECT * FROM cadastro WHERE NUMERO = ?",
                    BeanPropertyRowMapper.newInstance(Cadastro.class), numero);
            return cad;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }
}
