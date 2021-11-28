package com.controlemidias.Midias.services;

import com.controlemidias.Midias.domain.TipoConversa;
import org.springframework.stereotype.Service;

import java.util.List;


public interface TipoConversaService {

    TipoConversa salvarTipo(TipoConversa tipo);
    List<TipoConversa> ListarTipo();
}
