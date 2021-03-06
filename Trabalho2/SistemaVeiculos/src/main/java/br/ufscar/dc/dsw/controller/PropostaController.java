package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.dao.*;
import br.ufscar.dc.dsw.domain.*;
import br.ufscar.dc.dsw.security.UsuarioDetails;
import br.ufscar.dc.dsw.service.EmailService;
import br.ufscar.dc.dsw.util.Erro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.internet.InternetAddress;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/propostas")
public class PropostaController {

    @Autowired
    private PropostaDAO propostaDAO;

    @Autowired
    private VeiculoDAO veiculoDAO;

    @Autowired
    private ClienteDAO clienteDAO;

    @Autowired
    private LojaDAO lojaDAO;

    @GetMapping("/lista")
    public String lista(ModelMap model) {
        List<Proposta> listaPropostas = propostaDAO.findAll();
        model.addAttribute("listaPropostas", listaPropostas);
        model.addAttribute("cliente", getClienteLogado());
        model.addAttribute("loja", getLojaLogado());
        return "proposta/lista";
    }

    @GetMapping("/cadastro")
    public String cadastro(ModelMap model,
                           @RequestParam String chassi) {
        Veiculo veiculo = veiculoDAO.getByChassi(chassi);
        model.addAttribute("veiculo", veiculo);
        int numFotosVeiculo = veiculo.getFotos().size();
        model.addAttribute("numFotosVeiculo", numFotosVeiculo);
        int[] arrayNumFotos = IntStream.range(1, numFotosVeiculo+1).toArray();
        model.addAttribute("arrayNumFotos", arrayNumFotos);
        model.addAttribute("cliente", getClienteLogado());
        return "proposta/cadastro";
    }

    @GetMapping("/edita")
    public String edita(ModelMap model,
                        @RequestParam String cpf,
                        @RequestParam String chassi) {
        Cliente cliente = clienteDAO.getByCpf(cpf);
        Veiculo veiculo = veiculoDAO.getByChassi(chassi);
        Proposta proposta = propostaDAO.getPropostaByClienteAndVeiculo(cliente, veiculo);
        int numFotosVeiculo = veiculo.getFotos().size();
        model.addAttribute("numFotosVeiculo", numFotosVeiculo);
        int[] arrayNumFotos = IntStream.range(1, numFotosVeiculo+1).toArray();
        model.addAttribute("arrayNumFotos", arrayNumFotos);
        model.addAttribute("veiculo", veiculo);
        model.addAttribute("proposta", proposta);
        model.addAttribute("cliente", cliente);
        model.addAttribute("loja", getLojaLogado());
        return "proposta/cadastro";
    }

    @PostMapping("/insere")
    public String insere(@Valid Proposta proposta,
                         RedirectAttributes attr) {
        proposta.setData(LocalDate.now());
        proposta.setStatus(2);
        try {
            propostaDAO.save(proposta);
            attr.addFlashAttribute("sucess", "Proposta Enviada");
        } catch (Exception e) {
            Erro erro = new Erro();
            erro.add("Erro no cadastro de cliente");
            attr.addFlashAttribute("mensagens", erro);
        }
        return "redirect:/propostas/lista";
    }

    @PostMapping("/atualiza")
    public String atualiza(@Valid Proposta proposta,
                           RedirectAttributes attr) {
        EmailService email = new EmailService();
        Cliente cliente = proposta.getCliente();
        Veiculo veiculo = proposta.getVeiculo();
        Proposta propostaAtt = propostaDAO.getPropostaByClienteAndVeiculo(cliente, veiculo);
        propostaAtt.setStatus(proposta.getStatus());
        if (proposta.getObservacao() != null) {
            propostaAtt.setObservacao(proposta.getObservacao());
        }
        try {
            String body;
            String modeloCapitalized = capitalize(veiculo.getModelo());
            InternetAddress from = new InternetAddress("nathijorveiculos@gmail.com", "Nathijor Veiculos");
            InternetAddress to = new InternetAddress(cliente.getEmail(), cliente.getNome());
            switch (propostaAtt.getStatus()) {
                case 1:
                    String meet = "https://meet.google.com/zwd-awmj-fmr";
                    body = "<h2>PARAB??NS!! Sua proposta em " + modeloCapitalized + " foi aceita</h2>" +
                            "<br>Para podermos prosseguir com a negocia????o pedimos que compare??a a reuni??o no dia: " + myLocalDate(LocalDate.now().plusDays(2)) + " ??s " + myLocalTime(LocalTime.now().plusHours(1)) + "h00" +
                            "<br>Link para reuni??o: <a href=\"" + meet + "\">Clique aqui</a> " +
                            "<br><br>RESUMO PROPOSTA<br>Carro: " + modeloCapitalized + " - " + capitalize(veiculo.getDescricao()) + "<br>Valor: " + propostaAtt.getValor() + "<br>Forma de Pagamento: " + propostaAtt.getPagamento() + "<br><br>";
                    email.send(from, to, "Proposta Aceita " + modeloCapitalized, body);
                    break;
                case 0:
                    body = "<h2>INFELIZMENTE N??O ?? O DIA DA SUA PROPOSTA</h2>" +
                            "<br>Hoje sua proposta foi recusada. Tente outro dia." +
                            "<br><br>RESUMO PROPOSTA<br>Carro: " + modeloCapitalized + " - " + capitalize(veiculo.getDescricao()) + "<br>Valor: " + propostaAtt.getValor() + "<br>Forma de Pagamento: " + propostaAtt.getPagamento() + "<br><br>";
                    email.send(from, to, "Proposta Recusada " + modeloCapitalized, body);
                    break;
                case 3:
                    body = "<h2>GOSTAMOS DA SUA PROPOSTA MAS PERA??.. TEMOS UMA CONTRAPROPOSTA</h2>" +
                            "<br>RESUMO CONTRAPROPOSTA<br>Carro: " + modeloCapitalized + " - " + capitalize(veiculo.getDescricao()) + "<br>Valor: " + propostaAtt.getValor() + "<br>Forma de Pagamento: " + propostaAtt.getPagamento() +
                            "<br><br>Observa????es do Vendedor:<br><cite>" + proposta.getObservacao() + "</cite><br><br>";
                    email.send(from, to, "Contraproposta " + modeloCapitalized, body);
                    break;
            }
            propostaDAO.save(propostaAtt);
        } catch (Exception e) {
            Erro erro = new Erro();
            erro.add("Erro na atualizacao de proposta");
            attr.addFlashAttribute("mensagens", erro);
        }
        return "redirect:/propostas/lista";
    }

    @GetMapping("/remove")
    public String remove(@RequestParam String cpf,
                         @RequestParam String chassi,
                         RedirectAttributes attr) {
        Cliente cliente = clienteDAO.getByCpf(cpf);
        Veiculo veiculo = veiculoDAO.getByChassi(chassi);
        try {
            propostaDAO.deletePropostaByClienteAndVeiculo(cliente, veiculo);
        } catch (Exception e) {
            Erro erro = new Erro();
            erro.add("Erro na exclusao de propostas");
            attr.addFlashAttribute("mensagens", erro);
        }
        return "redirect:/propostas/lista";
    }

    private Cliente getClienteLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UsuarioDetails user = (UsuarioDetails) authentication.getPrincipal();
        return clienteDAO.getById(user.getId());
    }

    private Loja getLojaLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UsuarioDetails user = (UsuarioDetails) authentication.getPrincipal();
        return lojaDAO.getById(user.getId());
    }

    private String capitalize(String word) {
        return word.charAt(0) + word.substring(1).toLowerCase();
    }

    private String myLocalTime(LocalTime time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH");
        return time.format(formatter);
    }

    private String myLocalDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return date.format(formatter);
    }
}
