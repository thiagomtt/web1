package br.ufscar.dc.dsw.dao;

import br.ufscar.dc.dsw.domain.Cliente;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClienteDAO extends CrudRepository<Cliente, Long> {

    List<Cliente> findAll();

    Cliente getByCpf(String cpf);

    Cliente getById(Long id);

    Cliente save(Cliente cliente);

    void deleteById(Long id);
}
