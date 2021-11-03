package br.ufscar.dc.dsw.dao;

import br.ufscar.dc.dsw.domain.Foto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FotoDAO extends CrudRepository<Foto, Long> {

    List<Foto> findAll();

    Foto findById(long id);

    Foto save(Foto foto);

    void deleteById(Long id);

}
