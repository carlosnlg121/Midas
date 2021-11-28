package com.controlemidias.Midias.services;

import java.util.List;

import com.controlemidias.Midias.domain.Webhook;

public interface WebhookService {

	Webhook salvar(String user);

	List<Webhook> listar();

}
