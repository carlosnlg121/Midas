package com.controlemidias.Midias.api;

import com.controlemidias.Midias.domain.AtendimentoRepository;
import com.controlemidias.Midias.domain.ResultadoRepository;
import com.controlemidias.Midias.domain.SaqRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value = "Gerir")
@RequestMapping("/gestao")
public class SaqController {

    @Autowired
    SaqRepository saqRepository;

    @Autowired
    ResultadoRepository resultadoRepository;

    @Autowired
    AtendimentoRepository atendimentoRepository;

    @ApiOperation(value = "Reclamacao")
    @GetMapping("/saq")
    public ResponseEntity<?> saq( ) {
        return ResponseEntity.ok(saqRepository.atendimentos());
    }

    @ApiOperation(value = "Resultados")
    @GetMapping("/resultado")
    public ResponseEntity<?> result( ) {
        return ResponseEntity.ok(resultadoRepository.atendimentos());
    }

    @ApiOperation(value = "Agendamento")
    @GetMapping("/agenda")
    public ResponseEntity<?> agenda( ) {
        return ResponseEntity.ok(atendimentoRepository.atendimentos());
    }

}
