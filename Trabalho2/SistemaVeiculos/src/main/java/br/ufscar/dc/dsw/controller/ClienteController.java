package br.ufscar.dc.dsw.controller;


import br.ufscar.dc.dsw.dao.ClienteDAO;
import br.ufscar.dc.dsw.domain.Cliente;
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
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteDAO clienteDAO;

    @GetMapping("/lista")
    public String lista(ModelMap model) {
        List<Cliente> listaClientes = clienteDAO.findAll();
        model.addAttribute("listaClientes", listaClientes);

        return "cliente/lista";
    }

    @GetMapping("/cadastro")
    public String cadastro(ModelMap model) {
        return "cliente/cadastro";
    }

    @GetMapping("/edita")
    public String edita(ModelMap model,
                        @RequestParam String cpf) {
        model.addAttribute("cliente", clienteDAO.getByCpf(cpf));
        return "cliente/cadastro";
    }

    @PostMapping("/insere")
    public String insere(@Valid Cliente cliente,
                         RedirectAttributes attr,
                         BCryptPasswordEncoder encoder) {
        if (cliente.getPapel() == null) {
            cliente.setPapel("CLIENTE");
        }
        cliente.setSenha(encoder.encode(cliente.getSenha()));
        clienteDAO.save(cliente);
        attr.addFlashAttribute("sucess", "Cliente Cadastrado");
        return "redirect:/clientes/lista";
    }

    @PostMapping("/atualiza")
    public String atualiza(@Valid Cliente cliente,
                           RedirectAttributes attr,
                           BCryptPasswordEncoder encoder) {
        if (cliente.getPapel() == null) {
            cliente.setPapel("CLIENTE");
        }
        if (cliente.getId() == null) {
            cliente.setId(clienteDAO.getByCpf(cliente.getCpf()).getId());
        }
        clienteDAO.save(cliente);
        attr.addFlashAttribute("sucess", "Cliente Cadastrado");
        return "redirect:/clientes/lista";
    }

    @GetMapping("/remove")
    public String remove(@RequestParam String cpf) {
        Cliente cliente = clienteDAO.getByCpf(cpf);
        clienteDAO.deleteById(cliente.getId());
        return "redirect:/clientes/lista";
    }

}
