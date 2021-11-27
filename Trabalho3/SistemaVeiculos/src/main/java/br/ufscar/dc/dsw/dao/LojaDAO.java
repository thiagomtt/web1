package br.ufscar.dc.dsw.dao;

import br.ufscar.dc.dsw.domain.Loja;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LojaDAO extends CrudRepository<Loja, Long> {

    List<Loja> findAll();

    Loja getById(Long id);

    Loja getByCnpj(String cnpj);

    Loja save(Loja loja);

    void deleteById(Long id);
}
