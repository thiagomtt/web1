package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.dao.FotoDAO;
import br.ufscar.dc.dsw.dao.PropostaDAO;
import br.ufscar.dc.dsw.dao.VeiculoDAO;
import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.domain.Loja;
import br.ufscar.dc.dsw.domain.Proposta;
import br.ufscar.dc.dsw.domain.Veiculo;
import br.ufscar.dc.dsw.service.EmailService;
import br.ufscar.dc.dsw.util.Erro;

import javax.mail.internet.InternetAddress;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.IntStream;

@WebServlet(urlPatterns = "/propostas/*")
public class PropostaController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private PropostaDAO propostaDAO;
    private VeiculoDAO veiculoDAO;
    private FotoDAO fotoDAO;
    private EmailService email;

    @Override
    public void init() {
        propostaDAO = new PropostaDAO();
        veiculoDAO = new VeiculoDAO();
        fotoDAO = new FotoDAO();
        email = new EmailService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        String action = request.getPathInfo();
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "/cadastro":
                    formCadastro(request, response);
                    break;
                case "/insere":
                    insere(request, response);
                    break;
                case "/remove":
                    remove(request, response);
                    break;
                case "/edita":
                    formEdicao(request, response);
                    break;
                case "/atualiza":
                    atualiza(request, response);
                    break;
                case "/noAuth":
                    noAuth(request, response);
                    break;
                default:
                    lista(request, response);
                    break;
            }
        } catch (RuntimeException | IOException | ServletException e) {
            throw new ServletException(e);
        }
    }

    private void lista(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Proposta> listaPropostas = propostaDAO.getAll();
        request.setAttribute("listaPropostas", listaPropostas);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/proposta/lista.jsp");
        dispatcher.forward(request, response);
    }

    private void formCadastro(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String chassi = request.getParameter("chassi");
        Veiculo veiculo = veiculoDAO.getByChassi(chassi);
        request.setAttribute("veiculo", veiculo);
        int numFotosVeiculo = fotoDAO.getNumFotos(chassi);
        request.setAttribute("numFotosVeiculo", numFotosVeiculo);
        int[] arrayNumFotos = IntStream.range(1, numFotosVeiculo+1).toArray();
        request.setAttribute("arrayNumFotos", arrayNumFotos);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/proposta/formulario.jsp");
        dispatcher.forward(request, response);
    }

    private void formEdicao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String chassi = request.getParameter("chassi");
        Veiculo veiculo = veiculoDAO.getByChassi(chassi);
        request.setAttribute("veiculo", veiculo);
        String cpf = request.getParameter("cpf");
        Proposta proposta = propostaDAO.getByCpfAndChassi(cpf, chassi);
        request.setAttribute("proposta", proposta);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/proposta/formulario.jsp");
        dispatcher.forward(request, response);
    }

    private void insere(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        // Cliente
        String cpf = request.getParameter("cpf");
        Cliente cliente = new Cliente(cpf);
        // Loja
        String cnpj = request.getParameter("cnpj");
        Loja loja = new Loja(cnpj);
        // Veiculo
        String chassi = request.getParameter("chassi");
        Veiculo veiculo = new Veiculo(chassi);
        // Proposta
        float valor = Float.parseFloat(request.getParameter("valor"));
        String pagamento = request.getParameter("pagamento");
        LocalDate data = LocalDate.now();
        Proposta proposta = new Proposta(cliente, loja, veiculo, valor, pagamento, data, 2);
        try {
            propostaDAO.insert(proposta);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/veiculos");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            Erro erro = new Erro();
            erro.add("É permitido somente uma proposta por carro");
            request.setAttribute("mensagens", erro);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/proposta/formulario.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void atualiza(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        // Cliente
        String cpf = request.getParameter("cpf");
        Cliente cliente = new Cliente(cpf);
        // Loja
        String cnpj = request.getParameter("cnpj");
        Loja loja = new Loja(cnpj);
        // Veiculo
        String chassi = request.getParameter("chassi");
        Veiculo veiculo = new Veiculo(chassi);
        // Proposta
        float valor = Float.parseFloat(request.getParameter("valor"));
        String pagamento = request.getParameter("pagamento");
        LocalDate data = LocalDate.now();
        int status = Integer.parseInt(request.getParameter("status"));
        Proposta proposta = new Proposta(cliente, loja, veiculo, valor, pagamento, data, status);
        try {
            String body;
            String modeloCapitalized = capitalize(request.getParameter("modelo"));
            InternetAddress from = new InternetAddress("nathijorveiculos@gmail.com", "Nathijor Veiculos");
            InternetAddress to = new InternetAddress(request.getParameter("emailCliente"), request.getParameter("nomeCliente"));
            switch (status) {
                case 1:
                    String meet = "https://meet.google.com/zwd-awmj-fmr";
                    body = "<h2>PARABÉNS!! Sua proposta em " + modeloCapitalized + " foi aceita</h2>" +
                            "<br>Para podermos prosseguir com a negociação pedimos que compareça a reunião no dia: " + myLocalDate(LocalDate.now().plusDays(2)) + " às " + myLocalTime(LocalTime.now().plusHours(1)) + "h00" +
                            "<br>Link para reunião: <a href=\"" + meet + "\">Clique aqui</a> " +
                            "<br><br>RESUMO PROPOSTA<br>Carro: " + modeloCapitalized + " - " + capitalize(request.getParameter("descricao")) + "<br>Valor: " + valor + "<br>Forma de Pagamento: " + pagamento + "<br><br>";
                    email.send(from, to, "Proposta Aceita " + modeloCapitalized, body);
                    break;
                case 0:
                    body = "<h2>INFELIZMENTE NÃO É O DIA DA SUA PROPOSTA</h2>" +
                            "<br>Hoje sua proposta foi recusada. Tente outro dia." +
                            "<br><br>RESUMO PROPOSTA<br>Carro: " + modeloCapitalized + " - " + capitalize(request.getParameter("descricao")) + "<br>Valor: " + valor + "<br>Forma de Pagamento: " + pagamento + "<br><br>";
                    email.send(from, to, "Proposta Recusada " + modeloCapitalized, body);
                    break;
                case 3:
                    body = "<h2>GOSTAMOS DA SUA PROPOSTA MAS PERAÍ.. TEMOS UMA CONTRAPROPOSTA</h2>" +
                            "<br>RESUMO CONTRAPROPOSTA<br>Carro: " + modeloCapitalized + " - " + capitalize(request.getParameter("descricao")) + "<br>Valor: " + valor + "<br>Forma de Pagamento: " + pagamento +
                            "<br><br>Observações do Vendedor:<br><cite>" + request.getParameter("observacao") + "</cite><br><br>";
                    email.send(from, to, "Contraproposta " + modeloCapitalized, body);
                    break;
            }
            propostaDAO.update(proposta);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/propostas");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            Erro erro = new Erro();
            erro.add("Erro na atualização da proposta");
            request.setAttribute("mensagens", erro);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/proposta/formulario.jsp");
            dispatcher.forward(request, response);
        }
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

    private void remove(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Cliente
        String cpf = request.getParameter("cpf");
        Cliente cliente = new Cliente(cpf);
        // Veiculo
        String chassi = request.getParameter("chassi");
        Veiculo veiculo = new Veiculo(chassi);
        try {
            Proposta proposta = new Proposta(cliente, veiculo);
            propostaDAO.delete(proposta);
        } catch (Exception e) {
            Erro erro = new Erro();
            erro.add("Erro na exclusão de proposta");
            request.setAttribute("mensagens", erro);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/propostas");
        dispatcher.forward(request, response);
    }

    private void noAuth(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        Erro erro = new Erro();
        erro.add("Você precisa estar logado para ter acesso a essa página.");
        request.setAttribute("mensagens", erro);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/noAuth.jsp");
        dispatcher.forward(request, response);
    }

}
