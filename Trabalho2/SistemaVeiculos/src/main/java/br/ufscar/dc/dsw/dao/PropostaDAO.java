package br.ufscar.dc.dsw.dao;

import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.domain.Proposta;
import br.ufscar.dc.dsw.domain.Veiculo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface PropostaDAO extends CrudRepository<Proposta, Long> {

    List<Proposta> findAll();

    Proposta findById(long id);

    Proposta save(Proposta proposta);

    void deleteById(Long id);

    Proposta getPropostaByClienteAndVeiculo(Cliente cliente, Veiculo veiculo);

    void deletePropostaByClienteAndVeiculo(Cliente cliente, Veiculo veiculo);
}
