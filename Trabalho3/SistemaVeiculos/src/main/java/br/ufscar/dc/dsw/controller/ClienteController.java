package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.dao.ClienteDAO;
import br.ufscar.dc.dsw.dao.UsuarioDAO;
import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.dto.ClienteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteDAO clienteDAO;

    @Autowired
    BCryptPasswordEncoder encoder;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Cliente> cadastra(@RequestBody ClienteDTO clienteDTO) {
        try {
            Cliente cliente = castToCliente(clienteDTO);
            clienteDAO.save(cliente);
            return ResponseEntity.ok(cliente);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> lista() {
        List<Cliente> lista = clienteDAO.findAll();
        if (lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Cliente> lista(@PathVariable("id") long id) {
        Cliente cliente = clienteDAO.getById(id);
        if (cliente == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cliente);
    }

    @PutMapping(path = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Cliente> atualiza(@PathVariable("id") long id,
                                            @RequestBody ClienteDTO clienteDTO) {
        try {
            Cliente cliente = clienteDAO.getById(id);
            if (cliente == null) {
                return ResponseEntity.notFound().build();
            } else {
                castToCliente(clienteDTO, cliente);
                clienteDAO.save(cliente);
                return ResponseEntity.ok(cliente);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Boolean> remove(@PathVariable("id") long id) {
        Cliente cliente = clienteDAO.getById(id);
        if (cliente == null) {
            return ResponseEntity.notFound().build();
        } else {
            clienteDAO.deleteById(id);
            return ResponseEntity.noContent().build();
        }
    }

    private Cliente castToCliente(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setSenha(encoder.encode(clienteDTO.getSenha()));
        cliente.setNome(clienteDTO.getNome());
        cliente.setPapel(clienteDTO.getPapel());
        cliente.setCpf(clienteDTO.getCpf());
        cliente.setSexo(clienteDTO.getSexo());
        cliente.setTelefone(clienteDTO.getTelefone());
        cliente.setDataNascimento(LocalDate.parse(clienteDTO.getDataNascimento().toString()));
        return cliente;
    }

    private void castToCliente(ClienteDTO clienteDTO, Cliente cliente) {
        if (clienteDTO.getEmail() != null)
            cliente.setEmail(clienteDTO.getEmail());
        if (clienteDTO.getSenha() != null)
            cliente.setSenha(encoder.encode(clienteDTO.getSenha()));
        if (clienteDTO.getNome() != null)
            cliente.setNome(clienteDTO.getNome());
        if (clienteDTO.getPapel() != null)
            cliente.setPapel(clienteDTO.getPapel());
        if (clienteDTO.getCpf() != null)
            cliente.setCpf(clienteDTO.getCpf());
        if (clienteDTO.getSexo() != null)
            cliente.setSexo(clienteDTO.getSexo());
        if (clienteDTO.getTelefone() != null)
            cliente.setTelefone(clienteDTO.getTelefone());
        if (clienteDTO.getDataNascimento() != null)
            cliente.setDataNascimento(LocalDate.parse(clienteDTO.getDataNascimento().toString()));
    }
}
