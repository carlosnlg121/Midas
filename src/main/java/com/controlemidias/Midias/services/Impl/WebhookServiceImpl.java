package com.controlemidias.Midias.services.Impl;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.SimpleTimeZone;

import com.controlemidias.Midias.domain.*;
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


    @Override
    public Webhook salvar(String user) {
        Webhook web = new Webhook();
        JSONObject root = new JSONObject(user);
        web.setBody(root.getString("body"));
        web.setDe(retirar(root.getString("from")));
        web.setPara(retirar(root.getString("to")));
        web.setId(root.getString("id"));
        web.setRepondido("N");
        Buscarmensagem(web);
        webrepository.Salvar(web);
        return web;
    }

    @Override
    public List<Webhook> listar() {
        return webrepository.findAll();
    }

    public String retirar(String dado) {
        int pos = dado.indexOf("@");
        return dado.substring(0, pos);
    }

    public Boolean Buscarmensagem(Webhook web) {
        Cadastro cadastro = buscarCadastro(web.getPara());
        UtimaPergunta pegunta = buscarUltimaP(web.getDe());
        if (Objects.nonNull(pegunta)) {
            if (pegunta.isAgenda()) {
                if (Objects.isNull(pegunta.getReg())){
                   Agendar(web,pegunta);
                }else if (pegunta.getReg().equals("Agenda")){
                    Horario(web,pegunta);

                }else{
                    Fechar(web,pegunta);
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

    public void Agendar(Webhook w,UtimaPergunta u){
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
         if(!ag.isEmpty()) {
             texto = "Informe apenas o *numero* desejado\n";
             SimpleDateFormat sdff = new SimpleDateFormat("dd/MM/yyyy");
             for (Agenda a : ag) {
                 texto = texto + "*" + a.getId() + "* - " + sdff.format(a.getAgenda()) + "\n";
             }
         }else{
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
        }catch (Exception e){}
        atendimentoRepository.Salvar(atend);
    }

    public void Horario(Webhook w,UtimaPergunta u){
        List<Hora> hr = HoraDisp(Long.valueOf(w.getBody()));
        LimparPerguntas(w.getDe());
        UtimaPergunta ult = new UtimaPergunta();
        ult.setId_pergunta(u.getId_pergunta());
        ult.setNumero(w.getPara());
        ult.setNumeroenviado(w.getDe());
        ult.setReg("Hora");
        ult.setAgenda(true);
        ultimaPerguntaRepository.Salvar(ult);

        String texto = "";
        if(!hr.isEmpty()) {
            texto = "Informe apenas o *numero* desejado\n";
            for (Hora h : hr) {
                texto = texto + "*" + h.getId() + "* - " + h.getHora() + "\n";
            }
        }else{
            zapServicel.EnviarSMS("*Medico com agenda fechada*, \n selecione outro medico", w.getPara(), w.getDe());
            LimparPerguntas(w.getDe());
            return;
        }
        zapServicel.EnviarSMS(texto, w.getPara(), w.getDe());
        atendimentoRepository.UpdateAgenda(Long.valueOf(w.getBody()),w.getDe());
    }

    public void Fechar(Webhook w,UtimaPergunta u){
        LimparPerguntas(w.getDe());
        SimpleDateFormat sdff = new SimpleDateFormat("dd/MM/yyyy");
        atendimentoRepository.UpdateHora(Long.valueOf(w.getBody()),w.getDe());
        horaRepository.Update(Long.valueOf(w.getBody()));
        Atendimento at = atendimentoRepository.agendado(w.getDe());
        Hora h = horaRepository.agendado(Long.valueOf(w.getBody()));
        Agenda a = agendaRepository.agendado(at.getId_agenda());
        zapServicel.EnviarSMS("Estamos te aguardando \n No dia " +sdff.format(a.getAgenda())+" horario " +h.getHora() , w.getPara(), w.getDe());
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
        List<Agenda>  resp = null;
        try {
            resp = agendaRepository.ListarDataDispoinivel(id);
        } catch (Exception e) {
        }
        return resp;
    }

    public List<Hora> HoraDisp(Long id) {
        List<Hora>  resp = null;
        try {
            resp = horaRepository.ListarDataDispoinivel(id);
        } catch (Exception e) {
        }
        return resp;
    }

}
