package br.ufscar.dc.dsw.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "Veiculo")
public class Veiculo extends AbstractEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "loja_id")
    private Loja loja;

    @Column(nullable = false, unique = true, length = 7)
    private String placa;

    @Column(nullable = false)
    private String modelo;

    @Column(nullable = false, unique = true, length = 17)
    private String chassi;

    @Column(nullable = false, length = 4)
    private int ano;

    @Column(nullable = false)
    private float km;

    @Column(nullable = false)
    private String descricao;

    @NumberFormat(style = Style.CURRENCY)
    @Column(nullable = false)
    private float valor;

    @OneToMany(mappedBy = "veiculo", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<Foto> fotos;

    @OneToMany(mappedBy = "veiculo", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    private List<Proposta> propostas;
}
