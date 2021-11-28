package com.controlemidias.Midias.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AtendimentoRepository {

    @Autowired
    private JdbcTemplate db;

    public List<Atendimento> atendimentos(){

        try {
            List<Atendimento> atendimento = db.query("select * from  atendimento",
                    BeanPropertyRowMapper.newInstance(Atendimento.class));
            return atendimento;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    public Atendimento agendado(String numero){
        try {
            Atendimento atendimento = db.queryForObject("select * from  atendimento where numerocliente = ?",
                    BeanPropertyRowMapper.newInstance(Atendimento.class),numero);
            return atendimento;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    public void UpdateHora (long id ,String numero) {
        db.update("UPDATE atendimento SET id_hora = ?  WHERE numerocliente=?",id, numero);
    }

    public void UpdateAgenda (long id ,String numero) {
        db.update("UPDATE atendimento SET id_agenda = ?  WHERE numerocliente=?",id, numero);
    }

    public void deleteById(String id) {
        db.update("DELETE FROM utimapergunta WHERE numerocliente=?", id);
    }

    public Boolean Salvar(Atendimento at){

        try {
            db.update("insert into atendimento (numero,numerocliente,id_medico) values(?,?,?)",
                    new Object[] { at.getNumero(), at.getNumerocliente(), at.getId_medico()});
            return true;
        } catch (IncorrectResultSizeDataAccessException e) {
            return false;
        }
    }
}
