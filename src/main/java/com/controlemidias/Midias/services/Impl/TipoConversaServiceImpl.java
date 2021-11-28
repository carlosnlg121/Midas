package com.controlemidias.Midias.services.Impl;

import com.controlemidias.Midias.domain.TipoConversa;
import com.controlemidias.Midias.domain.TipoConversaRepository;
import com.controlemidias.Midias.services.TipoConversaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoConversaServiceImpl implements TipoConversaService {

    @Autowired
    TipoConversaRepository tipoConversaRepository;

    @Override
    public TipoConversa salvarTipo(TipoConversa tipo){
        return null;
    }

    @Override
    public List<TipoConversa> ListarTipo(){
        return null;
    }
}
