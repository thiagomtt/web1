package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.dao.UsuarioDAO;
import br.ufscar.dc.dsw.dao.VeiculoDAO;
import br.ufscar.dc.dsw.domain.Usuario;
import br.ufscar.dc.dsw.domain.Veiculo;
import br.ufscar.dc.dsw.security.UsuarioDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/veiculos")
public class VeiculoController {

    @Autowired
    private VeiculoDAO veiculoDAO;

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

    @PostMapping("/insere")
    public String insere(@Valid Veiculo veiculo)
            throws IOException {

        System.out.println(veiculo);

        veiculoDAO.save(veiculo);

        return "/veiculo/lista";
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
