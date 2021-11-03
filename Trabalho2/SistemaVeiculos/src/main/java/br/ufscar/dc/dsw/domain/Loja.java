package br.ufscar.dc.dsw.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "Loja")
public class Loja extends Usuario {

    @Column(nullable = false, unique = true, length = 14)
    private String cnpj;

    @Column(nullable = false)
    private String descricao;

    @OneToMany(mappedBy = "loja", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<Proposta> propostas;

    @OneToMany(mappedBy = "loja", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<Veiculo> veiculos;

}
