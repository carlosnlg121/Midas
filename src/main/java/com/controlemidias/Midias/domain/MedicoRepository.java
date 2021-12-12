package com.controlemidias.Midias.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MedicoRepository {
    @Autowired
    private JdbcTemplate db;

    public List<Medico> Listar(){

        try {
            List<Medico> Medico = db.query("select * from Medico ",
                    BeanPropertyRowMapper.newInstance(Medico.class));
            return Medico;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }
    public Medico Medico(String id){
        try {
            Medico resposta = db.queryForObject("select * from Medico where medico = ?",
                    BeanPropertyRowMapper.newInstance(Medico.class), id);
            return resposta;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }
    public Medico MedicoId(Long id){
        try {
            Medico resposta = db.queryForObject("select * from Medico where id = ?",
                    BeanPropertyRowMapper.newInstance(Medico.class), id);
            return resposta;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }
}
