package com.controlemidias.Midias.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class RespostaRepository  {
    @Autowired
    private JdbcTemplate db;

    public List<Resposta> findByIddashIs(Long id){

        try {
            List<Resposta> resposta = db.query("select * from resposta where id_dash = ?",
                    BeanPropertyRowMapper.newInstance(Resposta.class), id);
            return resposta;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    public Resposta PrimeiraResposta(Long id){

        try {
            Resposta resposta = db.queryForObject("select * from resposta where id_pergunta = 0 and id_dash = ?",
                    BeanPropertyRowMapper.newInstance(Resposta.class), id);
            return resposta;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }
    public Resposta buscarResposta(Long idPegunta,Long id){

        try {
            Resposta resposta = db.queryForObject("select * from resposta where id_pergunta = ?  and id_dash = ?",
                    BeanPropertyRowMapper.newInstance(Resposta.class), idPegunta,id);
            return resposta;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }


    public Resposta buscarResposta(String texto){

        try {
            Resposta resposta = db.queryForObject("select * from resposta where id_pergunta = ?  and id_dash = ?",
                    BeanPropertyRowMapper.newInstance(Resposta.class), texto);
            return resposta;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

}
