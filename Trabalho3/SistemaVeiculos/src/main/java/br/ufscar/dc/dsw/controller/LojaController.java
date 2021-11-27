package br.ufscar.dc.dsw.controller;

import br.ufscar.dc.dsw.dao.LojaDAO;
import br.ufscar.dc.dsw.domain.Loja;
import br.ufscar.dc.dsw.dto.LojaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/lojas")
public class LojaController {

    @Autowired
    private LojaDAO lojaDAO;

    @Autowired
    BCryptPasswordEncoder encoder;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Loja> cadastra(@RequestBody LojaDTO lojaDTO) {
        try {
            Loja loja = castToLoja(lojaDTO);
            lojaDAO.save(loja);
            return ResponseEntity.ok(loja);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Loja>> lista() {
        List<Loja> lista = lojaDAO.findAll();
        if (lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Loja> lista(@PathVariable("id") long id) {
        Loja loja = lojaDAO.getById(id);
        if (loja == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(loja);
    }

    @PutMapping(path = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Loja> atualiza(@PathVariable("id") long id,
                                         @RequestBody LojaDTO lojaDTO) {
        try {
            Loja loja = lojaDAO.getById(id);
            if (loja == null) {
                return ResponseEntity.notFound().build();
            } else {
                castToLoja(lojaDTO, loja);
                lojaDAO.save(loja);
                return ResponseEntity.ok(loja);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Boolean> remove(@PathVariable("id") long id) {
        Loja loja = lojaDAO.getById(id);
        if (loja == null) {
            return ResponseEntity.notFound().build();
        } else {
            lojaDAO.deleteById(id);
            return ResponseEntity.noContent().build();
        }
    }

    private Loja castToLoja(LojaDTO lojaDTO) {
        Loja loja = new Loja();
        loja.setEmail(lojaDTO.getEmail());
        loja.setSenha(encoder.encode(lojaDTO.getSenha()));
        loja.setNome(lojaDTO.getNome());
        loja.setCnpj(lojaDTO.getCnpj());
        loja.setDescricao(lojaDTO.getDescricao());
        loja.setPapel("LOJA");
        return loja;
    }

    private void castToLoja(LojaDTO lojaDTO, Loja loja) {
        if (lojaDTO.getEmail() != null)
            loja.setEmail(lojaDTO.getEmail());
        if (lojaDTO.getSenha() != null)
            loja.setSenha(lojaDTO.getSenha());
        if (lojaDTO.getNome() != null)
            loja.setNome(lojaDTO.getNome());
        if (lojaDTO.getCnpj() != null)
            loja.setCnpj(lojaDTO.getCnpj());
        if (lojaDTO.getDescricao() != null)
            loja.setDescricao(lojaDTO.getDescricao());
    }

}
