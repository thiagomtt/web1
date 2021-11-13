package br.ufscar.dc.dsw.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "Foto")
public class Foto extends AbstractEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "veiculo_id")
    private Veiculo veiculo;

    @Column(nullable = false, unique = true)
    private String pathFoto;

}
