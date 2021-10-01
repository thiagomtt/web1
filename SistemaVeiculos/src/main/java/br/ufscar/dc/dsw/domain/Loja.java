package br.ufscar.dc.dsw.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Loja {
    private String cnpj;
    private String nome;
    private String email;
    private String senha;
    private String descricao;

    public Loja(String cnpj, String nome, String email, String descricao) {
        this.cnpj = cnpj;
        this.nome = nome;
        this.email = email;
        this.descricao = descricao;
    }

    public Loja(String cnpj) {
        this.cnpj = cnpj;
    }
}
