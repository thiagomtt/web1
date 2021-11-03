package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.dao.ClienteDAO;
import br.ufscar.dc.dsw.dao.LojaDAO;
import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.domain.Loja;
import br.ufscar.dc.dsw.util.Erro;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/login/*", "/logout"})
public class LoginController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Erro erros = new Erro();
        if (request.getParameter("loginCliente") != null) {
            String email = request.getParameter("email");
            String senha = request.getParameter("senha");
            if (!erros.isExisteErros()) {
                ClienteDAO clienteDAO = new ClienteDAO();
                Cliente cliente = clienteDAO.getByEmail(email);

                if (cliente != null) {
                    if (cliente.getSenha().equals(senha)) {
                        request.getSession().setAttribute("clienteLogado", cliente);
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
                        dispatcher.forward(request, response);
                        return;
                    } else {
                        erros.add("Senha do usuário inválida");
                    }
                } else {
                    erros.add("Cliente não encontrado");
                }
            }
        } else if (request.getParameter("loginLoja") != null) {
            String email = request.getParameter("email");
            String senha = request.getParameter("senha");
            if (!erros.isExisteErros()) {
                LojaDAO lojaDAO = new LojaDAO();
                Loja loja = lojaDAO.getByEmail(email);

                if (loja != null) {
                    if (loja.getSenha().equals(senha)) {
                        request.getSession().setAttribute("lojaLogado", loja);
                        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
                        dispatcher.forward(request, response);
                        return;
                    } else {
                        erros.add("Senha da loja inválida");
                    }
                } else {
                    erros.add("Loja não encontrado");
                }
            }
        }

        request.getSession().invalidate();
        request.setAttribute("mensagens", erros);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
        dispatcher.forward(request, response);

    }

}
