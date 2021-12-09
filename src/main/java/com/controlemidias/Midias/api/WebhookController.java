package com.controlemidias.Midias.api;

import com.controlemidias.Midias.domain.Dto.EList.List;
import com.controlemidias.Midias.domain.Dto.EList.Lista;
import com.controlemidias.Midias.domain.Dto.btn.Button;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.controlemidias.Midias.domain.Usuario;
import com.controlemidias.Midias.domain.Webhook;
import com.controlemidias.Midias.services.WebhookService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "Webhook")
@RequestMapping("/Webhook")
public class WebhookController {
	
	@Autowired
	WebhookService service;
	
	
	@ApiOperation(value = "SalvarSms")
	@PostMapping("/")
	public ResponseEntity<?>  PostSms(  @RequestBody String payload ) {		
		return ResponseEntity.ok(service.salvar(payload));
	}
	
	@ApiOperation(value = "ListarSms")
	@GetMapping("/")
	public ResponseEntity<?> ListarSms( ) {
		return ResponseEntity.ok(service.listar());
	}

	@ApiOperation(value = "list")
	@PostMapping("/list")
	public ResponseEntity<?>  ls(  @RequestBody Lista payload ) {
		return ResponseEntity.ok(service.envioList(payload));
	}

	@ApiOperation(value = "button")
	@PostMapping("/button")
	public ResponseEntity<?>  btn(  @RequestBody Button payload ) {
		return ResponseEntity.ok(service.enviobuton(payload));
	}

}
