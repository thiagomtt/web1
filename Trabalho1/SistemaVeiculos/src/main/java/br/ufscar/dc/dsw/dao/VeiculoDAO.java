package br.ufscar.dc.dsw.dao;

import br.ufscar.dc.dsw.domain.Loja;
import br.ufscar.dc.dsw.domain.Veiculo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VeiculoDAO extends GenericDAO {

    public void insert(Veiculo veiculo) {
        String sql = "INSERT INTO Veiculo(cnpj, placa, modelo, chassi, ano, km, descricao, valor) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection connection = this.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, veiculo.getLoja().getCnpj());
            statement.setString(2, veiculo.getPlaca());
            statement.setString(3, veiculo.getModelo());
            statement.setString(4, veiculo.getChassi());
            statement.setInt(5, veiculo.getAno());
            statement.setFloat(6, veiculo.getKm());
            statement.setString(7, veiculo.getDescricao());
            statement.setFloat(8, veiculo.getValor());
            statement.executeUpdate();

            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Veiculo> getAll() {
        List<Veiculo> listaVeiculos = new ArrayList<>();
        String sql = "SELECT * FROM Veiculo v, Loja l WHERE v.cnpj = l.cnpj ORDER BY v.chassi";
        try {
            Connection connection = this.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String cnpj = resultSet.getString("cnpj");
                String nome = resultSet.getString("nome");
                String email = resultSet.getString("email");
                String descricaoLoja = resultSet.getString("l.descricao");
                Loja loja = new Loja(cnpj, nome, email, descricaoLoja);
                String placa = resultSet.getString("placa");
                String modelo = resultSet.getString("modelo");
                String chassi = resultSet.getString("chassi");
                int ano = resultSet.getInt("ano");
                float km = resultSet.getFloat("km");
                String descricaoVeiculo = resultSet.getString("v.descricao");
                float valor = resultSet.getFloat("valor");
                Veiculo veiculo = new Veiculo(loja, placa, modelo, chassi, ano, km, descricaoVeiculo, valor);
                listaVeiculos.add(veiculo);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaVeiculos;
    }

    public void delete(Veiculo veiculo) {
        String sql = "DELETE FROM Veiculo WHERE chassi = ?";
        try {
            Connection connection = this.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, veiculo.getChassi());
            statement.executeUpdate();

            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Veiculo veiculo) {
        String sql = "UPDATE Veiculo SET cnpj=?, placa=?, km=?, descricao=?, valor=? WHERE chassi=?";
        try {
            Connection connection = this.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, veiculo.getLoja().getCnpj());
            statement.setString(2, veiculo.getPlaca());
            statement.setFloat(3, veiculo.getKm());
            statement.setString(4, veiculo.getDescricao());
            statement.setFloat(5, veiculo.getValor());
            statement.setString(6, veiculo.getChassi());
            statement.executeUpdate();

            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Veiculo getByChassi(String chassi) {
        Veiculo veiculo = null;
        String sql = "SELECT * FROM Veiculo v, Loja l WHERE v.chassi=? AND v.cnpj = l.cnpj";
        try {
            Connection connection = this.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, chassi);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String cnpj = resultSet.getString("l.cnpj");
                String nome = resultSet.getString("l.nome");
                String email = resultSet.getString("l.email");
                String descricaoLoja = resultSet.getString("l.descricao");
                Loja loja = new Loja(cnpj, nome, email, descricaoLoja);
                String placa = resultSet.getString("placa");
                String modelo = resultSet.getString("modelo");
                int ano = resultSet.getInt("ano");
                float km = resultSet.getFloat("km");
                String descricaoVeiculo = resultSet.getString("v.descricao");
                float valor = resultSet.getFloat("valor");
                veiculo = new Veiculo(loja, placa, modelo, chassi, ano, km, descricaoVeiculo, valor);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return veiculo;
    }

    public List<Veiculo> getByModelo(String modelo) {
        List<Veiculo> listaVeiculos = new ArrayList<>();
        String sql = "SELECT * FROM Veiculo v, Loja l WHERE v.modelo=? AND v.cnpj = l.cnpj ORDER BY v.chassi";
        try {
            Connection connection = this.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, modelo);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String cnpj = resultSet.getString("cnpj");
                String nome = resultSet.getString("nome");
                String email = resultSet.getString("email");
                String descricaoLoja = resultSet.getString("l.descricao");
                Loja loja = new Loja(cnpj, nome, email, descricaoLoja);
                String placa = resultSet.getString("placa");
                String chassi = resultSet.getString("chassi");
                int ano = resultSet.getInt("ano");
                float km = resultSet.getFloat("km");
                String descricaoVeiculo = resultSet.getString("v.descricao");
                float valor = resultSet.getFloat("valor");
                Veiculo veiculo = new Veiculo(loja, placa, modelo, chassi, ano, km, descricaoVeiculo, valor);
                listaVeiculos.add(veiculo);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaVeiculos;
    }

    public List<String> getModelos() {
        List<String> listaModelos = new ArrayList<>();
        String sql = "SELECT DISTINCT Modelo FROM Veiculo";
        try {
            Connection connection = this.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String modelo = resultSet.getString("modelo");
                listaModelos.add(modelo);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaModelos;
    }

}
