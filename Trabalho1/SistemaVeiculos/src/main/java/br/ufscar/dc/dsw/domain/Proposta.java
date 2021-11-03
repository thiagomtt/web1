package br.ufscar.dc.dsw.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Proposta {
    private Cliente cliente;
    private Loja loja;
    private Veiculo veiculo;
    private float valor;
    private String pagamento;
    private LocalDate data;
    private int status;

    public Proposta(Cliente cliente, Veiculo veiculo) {
        this.cliente = cliente;
        this.veiculo = veiculo;
    }
}
