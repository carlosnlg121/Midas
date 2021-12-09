package com.controlemidias.Midias.services;

import com.controlemidias.Midias.domain.Dto.EList.List;
import com.controlemidias.Midias.domain.Dto.EList.Lista;
import com.controlemidias.Midias.domain.Dto.btn.Button;

public interface ZapService {
    String EnviarSMS(String menssagem, String numero, String numeroEnvio);
    String EnviarList(Lista list);
    String Enviarbuton(Button button);

}
