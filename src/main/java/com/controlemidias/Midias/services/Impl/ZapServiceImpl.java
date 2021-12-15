package com.controlemidias.Midias.services.Impl;

import com.controlemidias.Midias.domain.Dto.EList.List;
import com.controlemidias.Midias.domain.Dto.EList.Lista;
import com.controlemidias.Midias.domain.Dto.btn.Button;
import com.controlemidias.Midias.services.ZapService;
import com.google.gson.Gson;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service
public class ZapServiceImpl implements ZapService {

    @Override
    public String EnviarSMS(String menssagem, String numero, String numeroEnvio) {

        String url_dependencia_proprias = "http://3.145.80.78:8000/send-message";
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> parametros = new HashMap<>();

        parametros.put("number", numeroEnvio);
        parametros.put("message", menssagem);

        ResponseEntity<String> response = restTemplate.postForEntity(url_dependencia_proprias, parametros, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            System.out.println("Request Failed");
            return null;
        }
    }

    @Override
    public String Enviarbuton(Button button) {

        String url_dependencia_proprias = "http://3.145.80.78:8000/send-buttons";
        RestTemplate restTemplate = new RestTemplate();
        Gson gson = new Gson();
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<Object> request = new HttpEntity<Object>(gson.toJson(button), headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url_dependencia_proprias, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            System.out.println("Request Failed");
            return null;
        }
    }

    @Override
    public String EnviarList(Lista list) {

        String url_dependencia_proprias = "http://3.145.80.78:8000/send-lists";
        RestTemplate restTemplate = new RestTemplate();
        Gson gson = new Gson();
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<Object> request = new HttpEntity<Object>(gson.toJson(list), headers);

        ResponseEntity<String> response = restTemplate.postForEntity(url_dependencia_proprias,request, String.class);


        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            System.out.println("Request Failed");
            return null;
        }
    }

}
