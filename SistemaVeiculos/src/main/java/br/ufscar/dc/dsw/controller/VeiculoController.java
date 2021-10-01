package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.dao.FotoDAO;
import br.ufscar.dc.dsw.dao.LojaDAO;
import br.ufscar.dc.dsw.dao.VeiculoDAO;
import br.ufscar.dc.dsw.domain.Foto;
import br.ufscar.dc.dsw.domain.Loja;
import br.ufscar.dc.dsw.domain.Veiculo;
import br.ufscar.dc.dsw.util.Erro;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static br.ufscar.dc.dsw.util.Constants.*;

@WebServlet(urlPatterns = {"/veiculos/*", "/index"})
@MultipartConfig
public class VeiculoController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private VeiculoDAO veiculoDAO;
    private FotoDAO fotoDAO;

    @Override
    public void init() {
        veiculoDAO = new VeiculoDAO();
        fotoDAO = new FotoDAO();
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
        List<String> listaModelos = veiculoDAO.getModelos();
        String filtroModelo = request.getParameter("modelo");
        List<Veiculo> listaVeiculos = new ArrayList<>();
        if (filtroModelo == null || filtroModelo.isEmpty()) {
            listaVeiculos = veiculoDAO.getAll();
        } else {
            listaVeiculos = veiculoDAO.getByModelo(filtroModelo);
        }
        request.setAttribute("listaModelos", listaModelos);
        request.setAttribute("listaVeiculos", listaVeiculos);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/veiculo/lista.jsp");
        dispatcher.forward(request, response);
    }

    private void formCadastro(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/veiculo/formulario.jsp");
        dispatcher.forward(request, response);
    }

    private Map<String, String> getLojas() {
        Map<String, String> lojas = new HashMap<>();
        for (Loja loja : new LojaDAO().getAll()) {
            lojas.put(loja.getEmail(), loja.getNome());
        }
        return lojas;
    }

    private void formEdicao(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String chassi = request.getParameter("chassi");
        Veiculo veiculo = veiculoDAO.getByChassi(chassi);
        request.setAttribute("veiculo", veiculo);
        request.setAttribute("lojas", getLojas());
        RequestDispatcher dispatcher = request.getRequestDispatcher("/veiculo/formulario.jsp");
        dispatcher.forward(request, response);
    }

    private void insere(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Loja loja = (Loja) request.getSession().getAttribute("lojaLogado");
        String placa = request.getParameter("placa");
        String modelo = request.getParameter("modelo");
        String chassi = request.getParameter("chassi");
        int ano = Integer.parseInt(request.getParameter("ano"));
        float km = Float.parseFloat(request.getParameter("km"));
        String descricao = request.getParameter("descricao");
        float valor = Float.parseFloat(request.getParameter("valor"));
        List<Part> fotos = request.getParts().stream().filter(part -> "imagens".equals(part.getName()) && part.getSize() > 0).collect(Collectors.toList());
        Veiculo veiculo = new Veiculo(loja, placa, modelo, chassi, ano, km, descricao, valor);
        try {
            veiculoDAO.insert(veiculo);
            // Salva fotos
            if (ServletFileUpload.isMultipartContent(request)) {
                String uploadPath = getServletContext().getRealPath("") + UPLOAD_DIRECTORY;
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
                int i = 1;
                for (Part foto : fotos) {
                    String fotoPath = uploadPath + File.separator + chassi + "_" + i + ".jpg";
                    foto.write(fotoPath);
                    Foto fotoBd = new Foto(veiculo, fotoPath);
                    fotoDAO.insere(fotoBd);
                    i += 1;
                }
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("/veiculos");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            Erro erro = new Erro();
            erro.add("Erro ao cadastrar veiculo");
            request.setAttribute("mensagens", erro);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/veiculos/cadastro");
            dispatcher.forward(request, response);
        }
    }

    private void atualiza(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String emailLoja = request.getParameter("loja");
        Loja loja = new LojaDAO().getByEmail(emailLoja);
        String placa = request.getParameter("placa");
        String chassi = request.getParameter("chassi");
        float km = Float.parseFloat(request.getParameter("km"));
        String descricao = request.getParameter("descricao");
        float valor = Float.parseFloat(request.getParameter("valor"));
        Veiculo veiculo = new Veiculo(loja, placa, chassi, km, descricao, valor);
        try {
            veiculoDAO.update(veiculo);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/veiculos");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            Erro erro = new Erro();
            erro.add("Erro na atualização de veiculo");
            request.setAttribute("mensagens", erro);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/veiculos/edita");
            dispatcher.forward(request, response);
        }

    }

    private void remove(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String chassi = request.getParameter("chassi");
        Veiculo veiculo = new Veiculo(chassi);
        try {
            veiculoDAO.delete(veiculo);
        } catch (Exception e) {
            Erro erro = new Erro();
            erro.add("Erro na exclusão do veiculo");
            request.setAttribute("mensagens", erro);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/veiculos");
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
