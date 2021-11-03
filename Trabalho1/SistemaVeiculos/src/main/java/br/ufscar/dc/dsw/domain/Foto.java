package br.ufscar.dc.dsw.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Foto {
    private Veiculo veiculo;
    private String pathFoto;

}
