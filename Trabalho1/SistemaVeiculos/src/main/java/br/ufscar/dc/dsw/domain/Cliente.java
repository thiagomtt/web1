package br.ufscar.dc.dsw.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Cliente {
    private String cpf;
    private String nome;
    private String email;
    private String senha;
    private String sexo;
    private String telefone;
    private LocalDate dataNascimento;
    private String papel;

    public Cliente(String cpf) {
        this.cpf = cpf;
    }
}
