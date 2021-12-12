package com.controlemidias.Midias.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public class UltimaPerguntaRepository {

    @Autowired
    private JdbcTemplate db;

    public UtimaPergunta findByNumeroenviadoIs(String id){

        try {
            UtimaPergunta resposta = db.queryForObject("select * from utimapergunta where numeroenviado = ?",
                    BeanPropertyRowMapper.newInstance(UtimaPergunta.class), id);
            return resposta;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    public Boolean Salvar(UtimaPergunta utima){

        try {
            db.update("insert into utimapergunta (id_pergunta,numero,numeroenviado,medico,agenda,reg,pergunta) values(?,?,?,?,?,?,?)",
                    new Object[] { utima.getId_pergunta(), utima.getNumero(), utima.getNumeroenviado(),utima.isMedico(),utima.isAgenda(), utima.getReg(),utima.getPergunta()});
            return true;
        } catch (IncorrectResultSizeDataAccessException e) {
            return false;
        }
    }

    public void deleteById(String id) {
        db.update("DELETE FROM utimapergunta WHERE numeroenviado=?", id);
    }
}
