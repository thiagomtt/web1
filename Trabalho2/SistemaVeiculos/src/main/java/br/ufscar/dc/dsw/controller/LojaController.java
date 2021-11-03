package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.dao.LojaDAO;
import br.ufscar.dc.dsw.domain.Loja;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/lojas")
public class LojaController {

    @Autowired
    private LojaDAO lojaDAO;

    @GetMapping("/lista")
    public String lista(ModelMap model) {
        List<Loja> listaLojas = lojaDAO.findAll();
        model.addAttribute("listaLojas", listaLojas);
        return "loja/lista";
    }

    @GetMapping("/cadastro")
    public String cadastro(ModelMap model) {
        return "loja/cadastro";
    }

    @GetMapping("/edita")
    public String edita(ModelMap model,
                        @RequestParam String cnpj) {
        model.addAttribute("loja", lojaDAO.getByCnpj(cnpj));
        return "loja/cadastro";
    }

    @PostMapping("/insere")
    public String insere(@Valid Loja loja,
                         RedirectAttributes attr,
                         BCryptPasswordEncoder encoder) {
        if (loja.getPapel() == null) {
            loja.setPapel("LOJA");
        }
        loja.setSenha(encoder.encode(loja.getSenha()));
        lojaDAO.save(loja);
        attr.addFlashAttribute("sucess", "Loja Cadastrada");
        return "redirect:/lojas/lista";
    }

    @PostMapping("/atualiza")
    public String atualiza(@Valid Loja loja,
                           RedirectAttributes attr,
                           BCryptPasswordEncoder encoder) {
        if (loja.getPapel() == null) {
            loja.setPapel("LOJA");
        }
        if (loja.getId() == null) {
            loja.setId(lojaDAO.getByCnpj(loja.getCnpj()).getId());
        }
        lojaDAO.save(loja);
        attr.addFlashAttribute("sucess", "Loja Atualizada");
        return "redirect:/lojas/lista";
    }

    @GetMapping("/remove")
    public String remove(@RequestParam String cnpj) {
        lojaDAO.deleteById(lojaDAO.getByCnpj(cnpj).getId());
        return "redirect:/lojas/lista";
    }

}
