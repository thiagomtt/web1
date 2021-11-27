package br.ufscar.dc.dsw.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ClienteDTO {

    @JsonProperty("email")
    private String email;

    @JsonProperty("senha")
    private String senha;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("papel")
    private String papel;

    @JsonProperty("cpf")
    private String cpf;

    @JsonProperty("sexo")
    private String sexo;

    @JsonProperty("telefone")
    private String telefone;

    @JsonProperty("dataNascimento")
    private LocalDate dataNascimento;

}
