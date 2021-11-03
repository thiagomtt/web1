package br.ufscar.dc.dsw.dao;

import br.ufscar.dc.dsw.domain.Veiculo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface VeiculoDAO extends CrudRepository<Veiculo, Long> {

    List<Veiculo> findAll();

    Veiculo findById(long id);

    Veiculo save(Veiculo veiculo);

    void deleteById(Long id);

    List<Veiculo> getAllByModelo(String modelo);

    Veiculo getByChassi(String chassi);

    @Query(value = "SELECT DISTINCT modelo FROM Veiculo",
            nativeQuery = true)
    List<String> getModelos();

}
