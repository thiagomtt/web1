package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.dao.LojaDAO;
import br.ufscar.dc.dsw.domain.Loja;
import br.ufscar.dc.dsw.util.Erro;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = "/lojas/*")
public class LojaController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private LojaDAO lojaDAO;

    @Override
    public void init() {
        lojaDAO = new LojaDAO();
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
        List<Loja> listaLojas = lojaDAO.getAll();
        request.setAttribute("listaLojas", listaLojas);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/loja/lista.jsp");
        dispatcher.forward(request, response);
    }

    private void formCadastro(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/loja/formulario.jsp");
        dispatcher.forward(request, response);
    }

    private void formEdicao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String cnpj = request.getParameter("cnpj");
        Loja loja = lojaDAO.getByCnpj(cnpj);
        request.setAttribute("loja", loja);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/loja/formulario.jsp");
        dispatcher.forward(request, response);
    }

    private void insere(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String cnpj = request.getParameter("cnpj");
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String descricao = request.getParameter("descricao");
        Loja loja = new Loja(cnpj, nome, email, senha, descricao);
        try {
            lojaDAO.insert(loja);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/lojas");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            Erro erro = new Erro();
            erro.add("Erro ao cadastrar loja");
            request.setAttribute("mensagens", erro);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/loja/formulario.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void atualiza(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String cnpj = request.getParameter("cnpj");
        String nome = request.getParameter("nome");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        String descricao = request.getParameter("descricao");
        Loja loja = new Loja(cnpj, nome, email, senha, descricao);
        try {
            lojaDAO.update(loja);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/lojas");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            Erro erro = new Erro();
            erro.add("Erro na atualização da loja");
            request.setAttribute("mensagens", erro);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/loja/formulario.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void remove(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String cnpj = request.getParameter("cnpj");
        try {
            Loja loja = new Loja(cnpj);
            lojaDAO.delete(loja);
        } catch (Exception e) {
            Erro erro = new Erro();
            erro.add("Erro na exclusão da loja");
            request.setAttribute("mensagens", erro);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/lojas");
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
