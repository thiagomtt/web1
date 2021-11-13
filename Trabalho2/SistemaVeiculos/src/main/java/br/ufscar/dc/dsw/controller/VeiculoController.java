package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.dao.FotoDAO;
import br.ufscar.dc.dsw.dao.UsuarioDAO;
import br.ufscar.dc.dsw.dao.VeiculoDAO;
import br.ufscar.dc.dsw.domain.Foto;
import br.ufscar.dc.dsw.domain.Usuario;
import br.ufscar.dc.dsw.domain.Veiculo;
import br.ufscar.dc.dsw.security.UsuarioDetails;
import br.ufscar.dc.dsw.util.Erro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/veiculos")
public class VeiculoController {

    @Autowired
    ServletContext context;

    @Autowired
    private VeiculoDAO veiculoDAO;

    @Autowired
    private FotoDAO fotoDAO;

    @Autowired
    private UsuarioDAO usuarioDAO;

    @GetMapping("/lista")
    public String lista(ModelMap model,
                        @RequestParam(required = false) String modelo) {
        List<Veiculo> listaVeiculos = new ArrayList<>();
        if (modelo != null) {
            listaVeiculos = veiculoDAO.getAllByModelo(modelo);
        } else {
            listaVeiculos = veiculoDAO.findAll();
        }
        List<String> listaModelos = veiculoDAO.getModelos();
        model.addAttribute("listaModelos", listaModelos);
        model.addAttribute("listaVeiculos", listaVeiculos);
        try {
            model.addAttribute("user", getUsuarioLogado());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "veiculo/lista";
    }

    @GetMapping("/cadastro")
    public String cadastro(ModelMap model,
                           @RequestParam(required = false) Veiculo veiculo) {
        model.addAttribute("user", getUsuarioLogado());
        model.addAttribute("veiculo", veiculo);
        return "veiculo/cadastro";
    }

    @GetMapping("/edita")
    public String edita(ModelMap model,
                        @RequestParam String chassi) {
        model.addAttribute("veiculo", veiculoDAO.getByChassi(chassi));
        return "veiculo/cadastro";
    }

    @PostMapping("/insere")
    public String insere(@Valid Veiculo veiculo,
                         @RequestParam List<MultipartFile> fotosUp,
                         RedirectAttributes attr)
            throws IOException {

        //veiculoDAO.save(veiculo);
        String uploadPath = context.getRealPath("") + "upload";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        try {
            Veiculo veiculoReal = new Veiculo(veiculo.getLoja(), veiculo.getPlaca(), veiculo.getModelo(), veiculo.getChassi(), veiculo.getAno(), veiculo.getKm(), veiculo.getDescricao(), veiculo.getValor(), null, null);
            veiculoDAO.save(veiculoReal);

            int i = 1;
            String fileName = null;
            for (MultipartFile foto : fotosUp) {
                fileName = veiculo.getChassi() + "_" + i + ".jpg";
                foto.transferTo(new File(uploadPath, fileName));
                Foto fotodb = new Foto(veiculoReal, uploadPath + File.separator + fileName);
                fotoDAO.save(fotodb);
                i += 1;
            }
            return "redirect:/veiculo/lista";
        } catch (Exception e) {
            Erro erro = new Erro();
            erro.add("Erro erro ao cadastrar veiculo");
            attr.addFlashAttribute("mensagens", erro);
            return "redirect:/veiculo/lista";
        }
    }

    @GetMapping("/remove")
    public String remove(@RequestParam String chassi) {
        Veiculo veiculo = veiculoDAO.getByChassi(chassi);
        veiculoDAO.deleteById(veiculo.getId());
        return "redirect:/veiculos/lista";
    }

    private Usuario getUsuarioLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UsuarioDetails user = (UsuarioDetails) authentication.getPrincipal();
        return usuarioDAO.getById(user.getId());
    }


}
