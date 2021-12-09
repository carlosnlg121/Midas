package com.controlemidias.Midias.services;

import java.util.List;

import com.controlemidias.Midias.domain.Dto.btn.Button;
import com.controlemidias.Midias.domain.Webhook;

public interface WebhookService {

	Webhook salvar(String user);

	List<Webhook> listar();

	String envioList(com.controlemidias.Midias.domain.Dto.EList.Lista l);
	String enviobuton(Button button);

}
