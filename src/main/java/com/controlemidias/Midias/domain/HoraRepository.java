package com.controlemidias.Midias.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HoraRepository {

    @Autowired
    private JdbcTemplate db;

    public List<Hora> ListarDataDispoinivel(Long id){

        try {
            List<Hora> hora = db.query("select * from  hora h where h.diponivel = true and id_agenda  = ? ",
                    BeanPropertyRowMapper.newInstance(Hora.class),id);
            return hora;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    public Hora agendado(Long id){
        try {
            Hora atendimento = db.queryForObject("select * from hora where id = ?",
                    BeanPropertyRowMapper.newInstance(Hora.class),id);
            return atendimento;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    public void Update (Long id) {
        db.update("UPDATE hora SET diponivel = false WHERE id=?", id);
    }

    public void UpdateAtiva (Long id) {
        db.update("UPDATE hora SET diponivel = true WHERE id=?", id);
    }
}
