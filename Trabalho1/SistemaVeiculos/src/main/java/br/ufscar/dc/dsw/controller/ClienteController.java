package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.dao.ClienteDAO;
import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.util.Erro;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet(urlPatterns = "/clientes/*")
public class ClienteController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private ClienteDAO clienteDAO;

    @Override
    public void init() {
        clienteDAO = new ClienteDAO();
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
        List<Cliente> listaClientes = clienteDAO.getAll();
        request.setAttribute("listaClientes", listaClientes);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/cliente/lista.jsp");
        dispatcher.forward(request, response);
    }

    private void formCadastro(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/cliente/formulario.jsp");
        dispatcher.forward(request, response);
    }

    private void formEdicao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String cpf = request.getParameter("cpf");
        Cliente cliente = clienteDAO.getByCpf(cpf);
        request.setAttribute("cliente", cliente);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/cliente/formulario.jsp");
        dispatcher.forward(request, response);
    }

    private void insere(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String cpf = request.getParameter("cpf");
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String sexo = request.getParameter("sexo");
        String telefone = request.getParameter("telefone");
        LocalDate dataNascimento = LocalDate.parse(request.getParameter("dataNascimento"));
        String papel = request.getParameter("papel");
        Cliente cliente = new Cliente(cpf, nome, email, senha, sexo, telefone, dataNascimento, papel);
        try {
            clienteDAO.insert(cliente);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/clientes");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            Erro erro = new Erro();
            erro.add("Erro no cadastro de cliente");
            request.setAttribute("mensagens", erro);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/cliente/formulario.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void atualiza(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String cpf = request.getParameter("cpf");
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String sexo = request.getParameter("sexo");
        String telefone = request.getParameter("telefone");
        LocalDate dataNascimento = LocalDate.parse(request.getParameter("dataNascimento"));
        String papel = request.getParameter("papel");
        Cliente cliente = new Cliente(cpf, nome, email, senha, sexo, telefone, dataNascimento, papel);
        try {
            clienteDAO.update(cliente);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/clientes");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            Erro erro = new Erro();
            erro.add("Erro na atualização de cliente");
            request.setAttribute("mensagens", erro);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/cliente/formulario.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void remove(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String cpf = request.getParameter("cpf");
        try {
            Cliente cliente = new Cliente(cpf);
            clienteDAO.delete(cliente);
        } catch (Exception e) {
            Erro erro = new Erro();
            erro.add("Erro na exclusão de cliente");
            request.setAttribute("mensagens", erro);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/clientes");
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
