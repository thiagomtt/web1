package br.ufscar.dc.dsw.dao;

import br.ufscar.dc.dsw.domain.Cliente;
import br.ufscar.dc.dsw.domain.Loja;
import br.ufscar.dc.dsw.domain.Proposta;
import br.ufscar.dc.dsw.domain.Veiculo;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PropostaDAO extends GenericDAO {

    public void insert(Proposta proposta) {
        String sql = "INSERT INTO Proposta(cpf, cnpj, chassi, valor, pagamento, data, status)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection connection = this.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, proposta.getCliente().getCpf());
            statement.setString(2, proposta.getLoja().getCnpj());
            statement.setString(3, proposta.getVeiculo().getChassi());
            statement.setFloat(4, proposta.getValor());
            statement.setString(5, proposta.getPagamento());
            statement.setDate(6, Date.valueOf(LocalDate.now().toString()));
            statement.setInt(7, proposta.getStatus());
            statement.executeUpdate();

            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Proposta> getAll() {
        List<Proposta> listaPropostas = new ArrayList<>();
        String sql = "SELECT * FROM Proposta p, Cliente c, Loja l, Veiculo v " +
                "WHERE p.cpf = c.cpf AND p.cnpj = l.cnpj AND p.chassi = v.chassi ORDER BY p.chassi";
        try {
            Connection connection = this.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                // Cliente
                String cpfCliente = resultSet.getString("c.cpf");
                String nomeCliente = resultSet.getString("c.nome");
                String emailCliente = resultSet.getString("c.email");
                String senhaCliente = resultSet.getString("c.senha");
                String sexoCliente = resultSet.getString("c.sexo");
                String telefoneCliente = resultSet.getString("c.telefone");
                LocalDate dataNascimentoCliente = LocalDate.parse(resultSet.getDate("c.dataNascimento").toString());
                String papelCliente = resultSet.getString("c.papel");
                Cliente cliente = new Cliente(cpfCliente, nomeCliente, emailCliente, senhaCliente, sexoCliente, telefoneCliente, dataNascimentoCliente, papelCliente);
                // Loja
                String cnpjLoja = resultSet.getString("l.cnpj");
                String nomeLoja = resultSet.getString("l.nome");
                String emailLoja = resultSet.getString("l.email");
                String senhaLoja = resultSet.getString("l.senha");
                String descricaoLoja = resultSet.getString("l.descricao");
                Loja loja = new Loja(cnpjLoja, nomeLoja, emailLoja, senhaLoja, descricaoLoja);
                // Veiculo
                String placaVeiculo = resultSet.getString("v.placa");
                String modeloVeiculo = resultSet.getString("v.modelo");
                String chassiVeiculo = resultSet.getString("v.chassi");
                int anoVeiculo = resultSet.getInt("v.ano");
                float kmVeiculo = resultSet.getFloat("v.km");
                String descricaoVeiculo = resultSet.getString("v.descricao");
                float valorVeiculo = resultSet.getFloat("v.valor");
                Veiculo veiculo = new Veiculo(loja, placaVeiculo, modeloVeiculo, chassiVeiculo, anoVeiculo, kmVeiculo, descricaoVeiculo, valorVeiculo);
                // Proposta
                float valorProposta = resultSet.getFloat("p.valor");
                String pagamentoProposta = resultSet.getString("p.pagamento");
                LocalDate dataProposta = LocalDate.parse(resultSet.getDate("p.data").toString());
                int statusProposta = resultSet.getInt("status");
                Proposta proposta = new Proposta(cliente, loja, veiculo, valorProposta, pagamentoProposta, dataProposta, statusProposta);
                listaPropostas.add(proposta);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaPropostas;
    }

    public void delete(Proposta proposta) {
        String sql = "DELETE FROM Proposta WHERE cpf=? AND chassi=?";
        try {
            Connection connection = this.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, proposta.getCliente().getCpf());
            statement.setString(2, proposta.getVeiculo().getChassi());
            statement.executeUpdate();

            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Proposta proposta) {
        String sql = "UPDATE Proposta SET valor=?, pagamento=?, data=?, status=? " +
                "WHERE cpf=? AND chassi=?";
        try {
            Connection connection = this.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setFloat(1, proposta.getValor());
            statement.setString(2, proposta.getPagamento());
            statement.setDate(3, Date.valueOf(proposta.getData()));
            statement.setInt(4, proposta.getStatus());
            statement.setString(5, proposta.getCliente().getCpf());
            statement.setString(6, proposta.getVeiculo().getChassi());
            statement.executeUpdate();

            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Proposta getByCpfAndChassi(String cpf, String chassi) {
        Proposta proposta = null;
        String sql = "SELECT * FROM Proposta p, Cliente c, Loja l, Veiculo v " +
                "WHERE p.cpf=? AND p.chassi=? AND p.cpf = c.cpf AND p.chassi = v.chassi AND p.cnpj = l.cnpj  ORDER BY p.cnpj";
        try {
            Connection connection = this.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, cpf);
            statement.setString(2, chassi);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // Cliente
                String nomeCliente = resultSet.getString("c.nome");
                String emailCliente = resultSet.getString("c.email");
                String senhaCliente = resultSet.getString("c.senha");
                String sexoCliente = resultSet.getString("c.sexo");
                String telefoneCliente = resultSet.getString("c.telefone");
                LocalDate dataNascimentoCliente = LocalDate.parse(resultSet.getDate("c.dataNascimento").toString());
                String papelCliente = resultSet.getString("c.papel");
                Cliente cliente = new Cliente(cpf, nomeCliente, emailCliente, senhaCliente, sexoCliente, telefoneCliente, dataNascimentoCliente, papelCliente);
                // Loja
                String cnpjLoja = resultSet.getString("l.cnpj");
                String nomeLoja = resultSet.getString("l.nome");
                String emailLoja = resultSet.getString("l.email");
                String senhaLoja = resultSet.getString("l.senha");
                String descricaoLoja = resultSet.getString("l.descricao");
                Loja loja = new Loja(cnpjLoja, nomeLoja, emailLoja, senhaLoja, descricaoLoja);
                // Veiculo
                String placaVeiculo = resultSet.getString("v.placa");
                String modeloVeiculo = resultSet.getString("v.modelo");
                int anoVeiculo = resultSet.getInt("v.ano");
                float kmVeiculo = resultSet.getFloat("v.km");
                String descricaoVeiculo = resultSet.getString("v.descricao");
                float valorVeiculo = resultSet.getFloat("v.valor");
                Veiculo veiculo = new Veiculo(loja, placaVeiculo, modeloVeiculo, chassi, anoVeiculo, kmVeiculo, descricaoVeiculo, valorVeiculo);
                // Proposta
                float valorProposta = resultSet.getFloat("p.valor");
                String pagamentoProposta = resultSet.getString("p.pagamento");
                LocalDate dataProposta = LocalDate.parse(resultSet.getDate("p.data").toString());
                int statusProposta = resultSet.getInt("p.status");
                proposta = new Proposta(cliente, loja, veiculo, valorProposta, pagamentoProposta, dataProposta, statusProposta);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return proposta;
    }

    public Proposta getByCpf(String cpf) {
        Proposta proposta = null;
        String sql = "SELECT * FROM Proposta p, Cliente c, Loja l, Veiculo v " +
                "WHERE p.cpf=? AND p.cpf = c.cpf AND p.cnpj = l.cnpj AND p.chassi = v.chassi";
        try {
            Connection connection = this.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, cpf);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // Cliente
                String nomeCliente = resultSet.getString("c.nome");
                String emailCliente = resultSet.getString("c.email");
                String senhaCliente = resultSet.getString("c.senha");
                String sexoCliente = resultSet.getString("c.sexo");
                String telefoneCliente = resultSet.getString("c.telefone");
                LocalDate dataNascimentoCliente = LocalDate.parse(resultSet.getDate("c.dataNascimento").toString());
                String papelCliente = resultSet.getString("c.papel");
                Cliente cliente = new Cliente(cpf, nomeCliente, emailCliente, senhaCliente, sexoCliente, telefoneCliente, dataNascimentoCliente, papelCliente);
                // Loja
                String cnpjLoja = resultSet.getString("l.cnpj");
                String nomeLoja = resultSet.getString("l.nome");
                String emailLoja = resultSet.getString("l.email");
                String senhaLoja = resultSet.getString("l.senha");
                String descricaoLoja = resultSet.getString("l.descricao");
                Loja loja = new Loja(cnpjLoja, nomeLoja, emailLoja, senhaLoja, descricaoLoja);
                // Veiculo
                String placaVeiculo = resultSet.getString("v.placa");
                String modeloVeiculo = resultSet.getString("v.modelo");
                String chassiVeiculo = resultSet.getString("v.chassi");
                int anoVeiculo = resultSet.getInt("v.ano");
                float kmVeiculo = resultSet.getFloat("v.km");
                String descricaoVeiculo = resultSet.getString("v.descricao");
                float valorVeiculo = resultSet.getFloat("v.valor");
                Veiculo veiculo = new Veiculo(loja, placaVeiculo, modeloVeiculo, chassiVeiculo, anoVeiculo, kmVeiculo, descricaoVeiculo, valorVeiculo);
                // Proposta
                float valorProposta = resultSet.getFloat("p.valor");
                String pagamentoProposta = resultSet.getString("p.pagamento");
                LocalDate dataProposta = LocalDate.parse(resultSet.getDate("p.data").toString());
                int statusProposta = resultSet.getInt("p.status");
                proposta = new Proposta(cliente, loja, veiculo, valorProposta, pagamentoProposta, dataProposta, statusProposta);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return proposta;
    }

    public Proposta getByCnpj(String cnpj) {
        Proposta proposta = null;
        String sql = "SELECT * FROM Proposta p, Cliente c, Loja l, Veiculo v " +
                "WHERE p.cnpj=? AND p.cpf = c.cpf AND p.cnpj = l.cnpj AND p.chassi = v.chassi";
        try {
            Connection connection = this.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, cnpj);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // Cliente
                String cpfCliente = resultSet.getString("c.cpf");
                String nomeCliente = resultSet.getString("c.nome");
                String emailCliente = resultSet.getString("c.email");
                String senhaCliente = resultSet.getString("c.senha");
                String sexoCliente = resultSet.getString("c.sexo");
                String telefoneCliente = resultSet.getString("c.telefone");
                LocalDate dataNascimentoCliente = LocalDate.parse(resultSet.getDate("c.dataNascimento").toString());
                String papelCliente = resultSet.getString("c.papel");
                Cliente cliente = new Cliente(cpfCliente, nomeCliente, emailCliente, senhaCliente, sexoCliente, telefoneCliente, dataNascimentoCliente, papelCliente);
                // Loja
                String nomeLoja = resultSet.getString("l.nome");
                String emailLoja = resultSet.getString("l.email");
                String senhaLoja = resultSet.getString("l.senha");
                String descricaoLoja = resultSet.getString("l.descricao");
                Loja loja = new Loja(cnpj, nomeLoja, emailLoja, senhaLoja, descricaoLoja);
                // Veiculo
                String placaVeiculo = resultSet.getString("v.placa");
                String modeloVeiculo = resultSet.getString("v.modelo");
                String chassiVeiculo = resultSet.getString("v.chassi");
                int anoVeiculo = resultSet.getInt("v.ano");
                float kmVeiculo = resultSet.getFloat("v.km");
                String descricaoVeiculo = resultSet.getString("v.descricao");
                float valorVeiculo = resultSet.getFloat("v.valor");
                Veiculo veiculo = new Veiculo(loja, placaVeiculo, modeloVeiculo, chassiVeiculo, anoVeiculo, kmVeiculo, descricaoVeiculo, valorVeiculo);
                // Proposta
                float valorProposta = resultSet.getFloat("p.valor");
                String pagamentoProposta = resultSet.getString("p.pagamento");
                LocalDate dataProposta = LocalDate.parse(resultSet.getDate("p.data").toString());
                int statusProposta = resultSet.getInt("p.status");
                proposta = new Proposta(cliente, loja, veiculo, valorProposta, pagamentoProposta, dataProposta, statusProposta);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return proposta;
    }

}
