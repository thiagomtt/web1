package br.ufscar.dc.dsw.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Veiculo {
    private Loja loja;
    private String placa;
    private String modelo;
    private String chassi;
    private int ano;
    private float km;
    private String descricao;
    private float valor;

    public Veiculo(Loja loja, String placa, String chassi, float km, String descricao, float valor) {
        this.loja = loja;
        this.placa = placa;
        this.chassi = chassi;
        this.km = km;
        this.descricao = descricao;
        this.valor = valor;
    }

    public Veiculo(String chassi) {
        this.chassi = chassi;
    }

    public Veiculo(Loja loja, String placa, float km, String descricao, float valor) {
        this.loja = loja;
        this.placa = placa;
        this.km = km;
        this.descricao = descricao;
        this.valor = valor;
    }
}
