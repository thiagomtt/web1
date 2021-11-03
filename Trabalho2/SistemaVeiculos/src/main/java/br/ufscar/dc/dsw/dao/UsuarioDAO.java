package br.ufscar.dc.dsw.dao;

import br.ufscar.dc.dsw.domain.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UsuarioDAO extends CrudRepository<Usuario, Long> {

    Usuario findByEmail(String email);

    Usuario getById(Long id);

    List<Usuario> findAll();

    Usuario save(Usuario usuario);

    void deleteById(Long id);

}
