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

    public List<Atendimento> ListaAtendimentos(String id){

        try {
            List<Atendimento> atendimento = db.query("select * from  atendimento where numerocliente = ?",
                    BeanPropertyRowMapper.newInstance(Atendimento.class),id);
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

    public Atendimento agendadoagenda(String numero){
        try {
            Atendimento atendimento = db.queryForObject("select * from  atendimento where id_agenda is null and id_hora is null and numerocliente = ?",
                    BeanPropertyRowMapper.newInstance(Atendimento.class),numero);
            return atendimento;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }
    public Atendimento agendadoHora(String numero){
        try {
            Atendimento atendimento = db.queryForObject("select * from  atendimento where id_hora is null and numerocliente = ?",
                    BeanPropertyRowMapper.newInstance(Atendimento.class),numero);
            return atendimento;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    public void UpdateAgenda (long id_agenda ,long id ) {
        db.update("UPDATE atendimento SET id_agenda = ?  WHERE  id = ?",id_agenda, id);
    }

    public void UpdateHora (long id_agenda ,long id ) {
        db.update("UPDATE atendimento SET id_hora = ?  WHERE  id = ?",id_agenda, id);
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
