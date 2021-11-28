package com.controlemidias.Midias.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WebhookRepository {
    @Autowired
    private JdbcTemplate db;

    public Boolean Salvar(Webhook utima){

        try {
            db.update("insert into webhook (body,de,para) values(?,?,?)",
                    new Object[] { utima.getBody(), utima.getDe(), utima.getPara() });
            return true;
        } catch (IncorrectResultSizeDataAccessException e) {
            return false;
        }
    }

    public List<Webhook> findAll(){

        try {
            List<Webhook> resposta = db.query("select * from webhook ",
                    BeanPropertyRowMapper.newInstance(Webhook.class));
            return resposta;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }


}
