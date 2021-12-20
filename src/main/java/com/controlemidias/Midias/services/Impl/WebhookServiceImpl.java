package com.controlemidias.Midias.services.Impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.controlemidias.Midias.domain.*;
import com.controlemidias.Midias.domain.Dto.EList.Lista;
import com.controlemidias.Midias.domain.Dto.btn.Button;
import com.controlemidias.Midias.services.ZapService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.controlemidias.Midias.services.WebhookService;

@Service
public class WebhookServiceImpl implements WebhookService {

    @Autowired
    private WebhookRepository webrepository;
    @Autowired
    private CadastroRepository cadastroRepository;
    @Autowired
    private UltimaPerguntaRepository ultimaPerguntaRepository;
    @Autowired
    private RespostaRepository respostaRepository;
    @Autowired
    ZapService zapServicel;
    @Autowired
    ResultadoRepository resultadoRepository;
    @Autowired
    SaqRepository saqRepository;
    @Autowired
    AgendaRepository agendaRepository;
    @Autowired
    HoraRepository horaRepository;
    @Autowired
    AtendimentoRepository atendimentoRepository;
    @Autowired
    MedicoRepository medicoRepository;


    @Override
    public Webhook salvar(String user) {
        Webhook web = new Webhook();
        JSONObject root = new JSONObject(user);
        web.setBody(root.getString("body"));
        web.setPara(root.getString("to"));
        web.setRepondido(retirar(root.getString("from")));
        if (!web.getRepondido().equals("status")){
            if (!web.getBody().isEmpty()){
                bot(web);
            }
        }
        webrepository.Salvar(web);
        return web;
    }


    public List<com.controlemidias.Midias.domain.Dto.EList.List> opcao() {
        List<com.controlemidias.Midias.domain.Dto.EList.List> l = new ArrayList<>();

        com.controlemidias.Midias.domain.Dto.EList.List Agendamento = new com.controlemidias.Midias.domain.Dto.EList.List();
        Agendamento.setTitle("Agendamento");
        l.add(Agendamento);

        com.controlemidias.Midias.domain.Dto.EList.List Resultados = new com.controlemidias.Midias.domain.Dto.EList.List();
        Resultados.setTitle("Resultados");
        l.add(Resultados);

        com.controlemidias.Midias.domain.Dto.EList.List reclamacao = new com.controlemidias.Midias.domain.Dto.EList.List();
        reclamacao.setTitle("Registrar reclamacao");
        l.add(reclamacao);

        com.controlemidias.Midias.domain.Dto.EList.List Atendente = new com.controlemidias.Midias.domain.Dto.EList.List();
        Atendente.setTitle("Falar com um Atendente");
        l.add(Atendente);

        return l;

    }

    public List<com.controlemidias.Midias.domain.Dto.EList.List> ListaMedico() {
        List<com.controlemidias.Midias.domain.Dto.EList.List> l = new ArrayList<>();

        List<Medico> medico = medicoRepository.Listar();
        for (Medico m : medico) {
            com.controlemidias.Midias.domain.Dto.EList.List md = new com.controlemidias.Midias.domain.Dto.EList.List();
            md.setTitle(m.getMedico());
            l.add(md);
        }
        return l;

    }

    public List<com.controlemidias.Midias.domain.Dto.EList.List> ListaAgenda(String id) {
        List<com.controlemidias.Midias.domain.Dto.EList.List> l = new ArrayList<>();
        List<Agenda> Agenda = agendaRepository.ListarDataDispoinivel(id);
        SimpleDateFormat sdff = new SimpleDateFormat("dd/MM/yyyy");
        for (Agenda m : Agenda) {
            com.controlemidias.Midias.domain.Dto.EList.List md = new com.controlemidias.Midias.domain.Dto.EList.List();
            md.setTitle(sdff.format(m.getAgenda()));
            l.add(md);
        }
        return l;

    }

    public List<com.controlemidias.Midias.domain.Dto.EList.List> ListaHora(Long id) {
        List<com.controlemidias.Midias.domain.Dto.EList.List> l = new ArrayList<>();
        List<Hora> Agenda = horaRepository.ListarDataDispoinivel(id);
        for (Hora m : Agenda) {
            com.controlemidias.Midias.domain.Dto.EList.List md = new com.controlemidias.Midias.domain.Dto.EList.List();
            md.setTitle(m.getHora());
            l.add(md);
        }
        return l;

    }

    public Lista Medico() {
        Lista lista = new Lista();
        lista.setTitle("Medico");
        lista.setBtnText("Escolher uma opcao");
        lista.setList_body("Selecione o medico \n");
        lista.setFooter("Obridado!");
        lista.setList(ListaMedico());
        return lista;
    }

    public Lista Agenda(Long Id) {
        Lista lista = new Lista();
        lista.setTitle("Agenda");
        lista.setBtnText("Escolher uma opcao");
        lista.setList_body("Selecione o seu dia! \n");
        lista.setFooter("Obridado!");
        lista.setList(ListaAgenda(String.valueOf(Id)));
        return lista;
    }

    public Lista Hora(Long Id) {
        Lista lista = new Lista();
        lista.setTitle("Hora");
        lista.setBtnText("Escolher uma opcao");
        lista.setList_body("Selecione o seu horario! \n");
        lista.setFooter("Obridado!");
        lista.setList(ListaHora(Id));
        return lista;
    }

    public Lista Default() {
        Lista lista = new Lista();

        lista.setTitle("Seja bem vindo!");
        lista.setBtnText("Escolher uma opcao");
        lista.setList_body("Ola tudo bem, Esse e nosso bot Teste, \n" +
                "nosso horario de funcionamento e \n" +
                "de *segunda-feira a sexta-feira*,\n" +
                "das *8:00 as 18:00*,\n" +
                "Exames nao tem a necessidade de agendar,\n" +
                "estamos te aguardando.");
        lista.setFooter("Obridado!");
        lista.setList(opcao());


        return lista;
    }

    @Override
    public List<Webhook> listar() {
        return webrepository.findAll();
    }

    public String retirar(String dado) {
        int pos = dado.indexOf("@");
        return dado.substring(0, pos);
    }


    public Boolean bot(Webhook web) {

        try {
            UtimaPergunta p = buscarUltimaP(web.getPara());
            Lista lista = new Lista();
            if (Objects.isNull(p)) {
                lista = Default();
                lista.setNumber(web.getPara());
                gravarUtimaPergunta(web, "Default", lista);

            } else if (web.getBody().equals("Agendamento")) {
                lista = Medico();
                lista.setNumber(web.getPara());
                gravarUtimaPergunta(web, "Medico", lista);

            } else if (web.getBody().equals("Resultados")) {
                zapServicel.EnviarSMS("Descreva o ocorrido para tomar a devida providência\n" +
                        "desculpe o transtorno. \n", web.getPara(), web.getPara());
                gravarUtimaPerguntaSemEnvio(web, "RESULTADO");

            } else if (web.getBody().equals("Registrar reclamacao")) {
                zapServicel.EnviarSMS("Descreva o ocorrido para tomar a devida providência\n" +
                        "desculpe o transtorno. \n", web.getPara(), web.getPara());
                gravarUtimaPerguntaSemEnvio(web, "SAQ");

            } else if (web.getBody().equals("Falar com um Atendente")) {
                zapServicel.EnviarSMS("Em desenvolvimento\n" +
                        "desculpe o transtorno. \n", web.getPara(), web.getPara());
                LimparPerguntas(web.getPara());
            } else if (p.getPergunta().equals("Hora")) {

                Atendimento at = atendimentoRepository.agendadoHora(web.getPara());
                Hora m = horaRepository.dia(String.valueOf(at.getId_agenda()), web.getBody());
                LimparPerguntas(web.getPara());
                horaRepository.Update(m.getId());
                atendimentoRepository.UpdateHora(m.getId(),at.getId());

                Enviaragendamento(web);


            } else if (p.getPergunta().equals("Agenda")) {
                Atendimento at = atendimentoRepository.agendadoagenda(web.getPara());
                //Agenda m = agendaRepository.dia(String.valueOf(at.getId_medico()), sdff.format(web.getBody()));
                Agenda m = AcharAgenda(at.getId_medico(), web.getBody());
                lista = Hora(m.getId());
                lista.setNumber(web.getPara());
                gravarUtimaPergunta(web, "Hora", lista);
                atendimentoRepository.UpdateAgenda(m.getId(),at.getId());

            } else if (p.getPergunta().equals("Medico")) {
                Medico m = medicoRepository.Medico(web.getBody());

                List<Agenda> a = AgendaLivre(String.valueOf(m.getId()));
                if (a.size() >0){
                    lista = Agenda(m.getId());
                    lista.setNumber(web.getPara());
                    gravarUtimaPergunta(web, "Agenda", lista);
                    Atendimento atend = new Atendimento();

                    atend.setId_medico(m.getId());
                    atend.setNumero(web.getPara());
                    atend.setNumerocliente(web.getPara());
                    atendimentoRepository.Salvar(atend);
                }else{
                    zapServicel.EnviarSMS("Medico Selecionado : *"+web.getBody()+"*\n" +
                            "*Sem agenda disponivel*", web.getPara(), web.getPara());
                    LimparPerguntas(web.getPara());
                }

            } else if (p.getPergunta().equals("SAQ")) {
                Saq saq = new Saq();
                saq.setMenssage(web.getBody());
                saq.setNumero(web.getPara());
                saq.setNumeroenviado(web.getPara());
                saqRepository.Salvar(saq);
                LimparPerguntas(web.getPara());
                zapServicel.EnviarSMS("Obrigado e disponha.", web.getPara(), web.getPara());

            } else if (p.getPergunta().equals("RESULTADO")) {
                Resultado re = new Resultado();
                re.setMenssage(web.getBody());
                re.setNumero(web.getPara());
                re.setNumeroenviado(web.getPara());
                resultadoRepository.Salvar(re);
                LimparPerguntas(web.getPara());
                zapServicel.EnviarSMS("Obrigado e disponha.", web.getPara(), web.getPara());
            } else {
                zapServicel.EnviarSMS("Ops! *opção invalida.* \n" +
                        "Vamos recomecar!", web.getPara(), web.getPara());
                lista = Default();
                lista.setNumber(web.getPara());
                gravarUtimaPergunta(web, "Default", lista);
            }

        } catch (Exception e) {

        }

        return true;

    }

    public Agenda AcharAgenda(Long id, String data){
        Agenda ag = new Agenda();
        List<Agenda> a = AgendaLivre(String.valueOf(id));
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        for (Agenda t:a){
            if (data.equals(f.format(t.getAgenda()))){
                return t;
            }
        }
        return null;
    }

    public void Enviaragendamento(Webhook web){
        List<Atendimento> at = atendimentoRepository.ListaAtendimentos(web.getPara());
        String sms = new String();
        sms = "Consultas Agendadas ";
        SimpleDateFormat sdff = new SimpleDateFormat("dd/MM/yyyy");
        for (Atendimento a :at){
            Medico m = medicoRepository.MedicoId(a.getId_medico());
            Agenda ag = agendaRepository.agendado(a.getId_agenda());
            Hora h = horaRepository.agendado(a.getId_hora());

            sms = sms + "\n Medico: "+m.getMedico() +"\n Dia: "+ sdff.format(ag.getAgenda()) +" Hora: "+h.getHora()+"\n";

        }
        zapServicel.EnviarSMS(sms, web.getPara(), web.getPara());
    }

    public void gravarUtimaPergunta(Webhook web, String pergunta, Lista lista) {
        ultimaPerguntaRepository.deleteById(web.getPara());
        UtimaPergunta ult = new UtimaPergunta();
        ult.setNumero(web.getPara());
        ult.setNumeroenviado(web.getPara());
        ult.setPergunta(pergunta);
        zapServicel.EnviarList(lista);
        ultimaPerguntaRepository.Salvar(ult);

    }

    public void gravarUtimaPerguntaSemEnvio(Webhook web, String pergunta) {
        ultimaPerguntaRepository.deleteById(web.getPara());
        UtimaPergunta ult = new UtimaPergunta();
        ult.setNumero(web.getPara());
        ult.setNumeroenviado(web.getPara());
        ult.setPergunta(pergunta);
        ultimaPerguntaRepository.Salvar(ult);
    }


    public Boolean Buscarmensagem(Webhook web) {
        Cadastro cadastro = buscarCadastro(web.getPara());
        UtimaPergunta pegunta = buscarUltimaP(web.getDe());
        if (Objects.nonNull(pegunta)) {
            if (pegunta.isAgenda()) {
                if (Objects.isNull(pegunta.getReg())) {
                    Agendar(web, pegunta);
                } else if (pegunta.getReg().equals("Agenda")) {


                } else {
                    Fechar(web, pegunta);
                }
            } else {
                Resposta r = buscarResp(web.getBody(), cadastro.getId_tipodash());
                if (Objects.isNull(r)) {
                    Resposta exit = buscarRespFinal(pegunta.getId_pergunta(), cadastro.getId_tipodash());
                    if (exit.isSaq()) {
                        Saq saq = new Saq();
                        saq.setMenssage(web.getBody());
                        saq.setNumero(web.getPara());
                        saq.setNumeroenviado(web.getDe());
                        saqRepository.Salvar(saq);
                        LimparPerguntas(web.getDe());
                        zapServicel.EnviarSMS("Obrigado e disponha.", web.getPara(), web.getDe());
                    } else if (exit.isResultado()) {
                        Resultado re = new Resultado();
                        re.setMenssage(web.getBody());
                        re.setNumero(web.getPara());
                        re.setNumeroenviado(web.getDe());
                        resultadoRepository.Salvar(re);
                        LimparPerguntas(web.getDe());
                        zapServicel.EnviarSMS("Obrigado e disponha.", web.getPara(), web.getDe());
                    }

                } else {
                    if (r.isFinaliza()) {
                        if (r.isSaq()) {
                            LimparPerguntas(web.getDe());
                            gravarUtimaPergunta(r, web);
                        } else if (r.isResultado()) {
                            LimparPerguntas(web.getDe());
                            gravarUtimaPergunta(r, web);
                        } else {
                            gravarUtimaPergunta(r, web);
                            LimparPerguntas(web.getDe());
                        }
                    } else {
                        LimparPerguntas(web.getDe());
                        gravarUtimaPergunta(r, web);
                    }
                }

            }


        } else {
            Resposta r = buscarPrimeiraResp(cadastro.getId_tipodash());
            gravarUtimaPergunta(r, web);
        }
        return true;

    }

    @Override
    public String enviobuton(Button button) {
        return zapServicel.Enviarbuton(button);
    }

    @Override
    public String envioList(com.controlemidias.Midias.domain.Dto.EList.Lista l) {
        return zapServicel.EnviarList(l);
    }

    public void Agendar(Webhook w, UtimaPergunta u) {
        List<Agenda> ag = AgendaLivre(w.getBody());
        LimparPerguntas(w.getDe());
        UtimaPergunta ult = new UtimaPergunta();
        ult.setId_pergunta(u.getId_pergunta());
        ult.setNumero(w.getPara());
        ult.setNumeroenviado(w.getDe());
        ult.setReg("Agenda");
        ult.setAgenda(true);
        ultimaPerguntaRepository.Salvar(ult);

        String texto = "";
        if (!ag.isEmpty()) {
            texto = "Informe apenas o *numero* desejado\n";
            SimpleDateFormat sdff = new SimpleDateFormat("dd/MM/yyyy");
            for (Agenda a : ag) {
                texto = texto + "*" + a.getId() + "* - " + sdff.format(a.getAgenda()) + "\n";
            }
        } else {
            zapServicel.EnviarSMS("*Medico com agenda fechada*, \n selecione outro medico", w.getPara(), w.getDe());
            LimparPerguntas(w.getDe());
            return;
        }

        zapServicel.EnviarSMS(texto, w.getPara(), w.getDe());
        Atendimento atend = new Atendimento();
        atend.setId_medico(Long.valueOf(w.getBody()));
        atend.setNumero(w.getPara());
        atend.setNumerocliente(w.getDe());
        try {
            atendimentoRepository.deleteById(w.getDe());
            Atendimento at = atendimentoRepository.agendado(w.getDe());
            horaRepository.UpdateAtiva(at.getId_hora());
            atendimentoRepository.deleteById(w.getDe());
        } catch (Exception e) {
        }
        atendimentoRepository.Salvar(atend);
    }



    public void Fechar(Webhook w, UtimaPergunta u) {
        LimparPerguntas(w.getDe());
        SimpleDateFormat sdff = new SimpleDateFormat("dd/MM/yyyy");

        Atendimento at = atendimentoRepository.agendado(w.getDe());
        Hora h = horaRepository.agendado(Long.valueOf(w.getBody()));
        Agenda a = agendaRepository.agendado(at.getId_agenda());
        zapServicel.EnviarSMS("Estamos te aguardando \n No dia " + sdff.format(a.getAgenda()) + " horario " + h.getHora(), w.getPara(), w.getDe());
    }

    public void gravarUtimaPergunta(Resposta r, Webhook web) {
        if (Objects.nonNull(r)) {
            UtimaPergunta ult = new UtimaPergunta();
            ult.setId_pergunta(r.getId_pergunta());
            ult.setNumero(web.getPara());
            ult.setNumeroenviado(web.getDe());
            ult.setMedico(r.isBuscamedico());
            ult.setAgenda(r.isBuscaagenda());
            zapServicel.EnviarSMS(r.getResposta(), web.getPara(), web.getDe());
            ultimaPerguntaRepository.Salvar(ult);
        }

    }

    public Cadastro buscarCadastro(String id) {
        Cadastro cadastro = null;
        try {
            cadastro = cadastroRepository.findByNumeroIs(id);
        } catch (Exception e) {
        }
        return cadastro;
    }

    public UtimaPergunta buscarUltimaP(String id) {
        UtimaPergunta pegunta = null;
        try {
            pegunta = ultimaPerguntaRepository.findByNumeroenviadoIs(id);
        } catch (Exception e) {
        }
        return pegunta;
    }

    public List<Resposta> buscarListaResp(Long id) {
        List<Resposta> resp = null;
        try {
            resp = respostaRepository.findByIddashIs(id);
        } catch (Exception e) {
        }
        return resp;
    }

    public Resposta buscarPrimeiraResp(Long id) {
        Resposta resp = null;
        try {
            resp = respostaRepository.PrimeiraResposta(id);
        } catch (Exception e) {
        }
        return resp;
    }

    public void LimparPerguntas(String id) {
        try {
            ultimaPerguntaRepository.deleteById(id);
        } catch (Exception e) {
        }
    }

    public Resposta buscarResp(String idperg, Long id) {
        Resposta resp = null;
        try {
            resp = respostaRepository.buscarResposta(Long.valueOf(idperg), id);
        } catch (Exception e) {
        }
        return resp;
    }

    public Resposta buscarRespFinal(Long idperg, Long id) {
        Resposta resp = null;
        try {
            resp = respostaRepository.buscarResposta(idperg, id);
        } catch (Exception e) {
        }
        return resp;
    }

    public List<Agenda> AgendaLivre(String id) {
        List<Agenda> resp = null;
        try {
            resp = agendaRepository.ListarDataDispoinivel(id);
        } catch (Exception e) {
        }
        return resp;
    }

    public List<Hora> HoraDisp(Long id) {
        List<Hora> resp = null;
        try {
            resp = horaRepository.ListarDataDispoinivel(id);
        } catch (Exception e) {
        }
        return resp;
    }

}
