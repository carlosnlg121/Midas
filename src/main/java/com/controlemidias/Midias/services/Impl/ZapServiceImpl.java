package com.controlemidias.Midias.services.Impl;

import com.controlemidias.Midias.services.ZapService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service
public class ZapServiceImpl implements ZapService {


    //@Value("${sqconnect.url.dependenciapropria}")
    private String url_dependencia_proprias = "http://18.222.1.102:3333/sendText";

    @Override
    public String EnviarSMS(String menssagem, String numero, String numeroEnvio) {

        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> parametros = new HashMap<>();

        parametros.put("sessionName", numero);
        parametros.put("number", numeroEnvio);
        parametros.put("text", menssagem);

        ResponseEntity<String> response = restTemplate.postForEntity(url_dependencia_proprias, parametros, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            System.out.println("Request Failed");
            return null;
        }
    }

}
