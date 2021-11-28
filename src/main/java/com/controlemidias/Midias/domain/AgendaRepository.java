package com.controlemidias.Midias.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AgendaRepository {

    @Autowired
    private JdbcTemplate db;

    public List<Agenda> ListarDataDispoinivel(String id){

        try {
            List<Agenda> agenda = db.query("select a.id ,a.agenda from agendas a , hora h " +
                                               "where a.id = h.id_agenda " +
                                               "and a.agenda >= current_date and h.diponivel = true and id_medico = ? " +
                                               "group by a.id ,a.agenda  ",
                    BeanPropertyRowMapper.newInstance(Agenda.class),id);
            return agenda;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    public Agenda agendado(Long id){
        try {
            Agenda atendimento = db.queryForObject("select * from  agendas where id = ?",
                    BeanPropertyRowMapper.newInstance(Agenda.class),id);
            return atendimento;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }
}
