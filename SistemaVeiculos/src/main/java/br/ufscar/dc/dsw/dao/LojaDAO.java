package br.ufscar.dc.dsw.dao;

import br.ufscar.dc.dsw.domain.Loja;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LojaDAO extends GenericDAO {

    public void insert(Loja loja) {
        String sql = "INSERT INTO Loja(cnpj, nome, email, senha, descricao) " +
                "VALUES (?, ?, ?, ?, ?)";
        try {
            Connection connection = this.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1, loja.getCnpj());
            statement.setString(2, loja.getNome());
            statement.setString(3, loja.getEmail());
            statement.setString(4, loja.getSenha());
            statement.setString(5, loja.getDescricao());
            statement.executeUpdate();

            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Loja> getAll() {
        List<Loja> listaLojas = new ArrayList<>();
        String sql = "SELECT * FROM Loja ORDER BY cnpj";
        try {
            Connection connection = this.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String cnpj = resultSet.getString("cnpj");
                String nome = resultSet.getString("nome");
                String email = resultSet.getString("email");
                String senha = resultSet.getString("senha");
                String descricao = resultSet.getString("descricao");
                Loja loja = new Loja(cnpj, nome, email, senha, descricao);
                listaLojas.add(loja);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaLojas;
    }

    public void delete(Loja loja) {
        String sql = "DELETE FROM Loja WHERE cnpj=?";
        try {
            Connection connection = this.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, loja.getCnpj());
            statement.executeUpdate();

            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Loja loja) {
        String sql = "UPDATE Loja SET nome=?, email=?, senha=?, descricao=? WHERE cnpj=?";
        try {
            Connection connection = this.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, loja.getNome());
            statement.setString(2, loja.getEmail());
            statement.setString(3, loja.getSenha());
            statement.setString(4, loja.getDescricao());
            statement.setString(5, loja.getCnpj());
            statement.executeUpdate();

            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Loja getByEmail(String email) {
        Loja loja = null;
        String sql = "SELECT * FROM Loja WHERE email=?";
        try {
            Connection connection = this.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String cnpj = resultSet.getString("cnpj");
                String nome = resultSet.getString("nome");
                String senha = resultSet.getString("senha");
                String descricao = resultSet.getString("descricao");
                loja = new Loja(cnpj, nome, email, senha, descricao);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return loja;
    }

    public Loja getByCnpj(String cnpj) {
        Loja loja = null;
        String sql = "SELECT * FROM Loja WHERE cnpj=?";
        try {
            Connection connection = this.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, cnpj);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String email = resultSet.getString("email");
                String nome = resultSet.getString("nome");
                String senha = resultSet.getString("senha");
                String descricao = resultSet.getString("descricao");
                loja = new Loja(cnpj, nome, email, senha, descricao);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return loja;
    }

}
