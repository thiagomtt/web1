package br.ufscar.dc.dsw.dao;

import br.ufscar.dc.dsw.domain.Cliente;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO extends GenericDAO {

    public void insert(Cliente cliente) {
        String sql = "INSERT INTO Cliente(cpf, nome, email, senha, sexo, telefone, dataNascimento, papel) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection connection = this.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, cliente.getCpf());
            statement.setString(2, cliente.getNome());
            statement.setString(3, cliente.getEmail());
            statement.setString(4, cliente.getSenha());
            statement.setString(5, cliente.getSexo());
            statement.setString(6, cliente.getTelefone());
            statement.setDate(7, Date.valueOf(cliente.getDataNascimento()));
            statement.setString(8, cliente.getPapel());
            statement.executeUpdate();

            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Cliente> getAll() {
        List<Cliente> listaClientes = new ArrayList<>();
        String sql = "SELECT * FROM Cliente ORDER BY cpf";
        try {
            Connection connection = this.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                String cpf = resultSet.getString("cpf");
                String nome = resultSet.getString("nome");
                String email = resultSet.getString("email");
                String senha = resultSet.getString("senha");
                String sexo = resultSet.getString("sexo");
                String telefone = resultSet.getString("telefone");
                LocalDate dataNascimento = LocalDate.parse(resultSet.getDate("dataNascimento").toString());
                String papel = resultSet.getString("papel");
                Cliente cliente = new Cliente(cpf, nome, email, senha, sexo, telefone, dataNascimento, papel);
                listaClientes.add(cliente);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaClientes;
    }

    public void delete(Cliente cliente) {
        String sql = "DELETE FROM Cliente WHERE cpf=?";
        try {
            Connection connection = this.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, cliente.getCpf());
            statement.executeUpdate();

            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Cliente cliente) {
        String sql = "UPDATE Cliente SET nome=?, email=?, senha=?, sexo=?, telefone=?, dataNascimento=?, papel=? " +
                "WHERE cpf=?";
        try {
            Connection connection = this.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, cliente.getNome());
            statement.setString(2, cliente.getEmail());
            statement.setString(3, cliente.getSenha());
            statement.setString(4, cliente.getSexo());
            statement.setString(5, cliente.getTelefone());
            statement.setDate(6, Date.valueOf(cliente.getDataNascimento()));
            statement.setString(7, cliente.getPapel());
            statement.setString(8, cliente.getCpf());
            statement.executeUpdate();

            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Cliente getByEmail(String email) {
        Cliente cliente = null;
        String sql = "SELECT * FROM Cliente WHERE email=?";
        try {
            Connection connection = this.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String cpf = resultSet.getString("cpf");
                String nome = resultSet.getString("nome");
                String senha = resultSet.getString("senha");
                String sexo = resultSet.getString("sexo");
                String telefone = resultSet.getString("telefone");
                LocalDate dataNascimento = LocalDate.parse(resultSet.getDate("dataNascimento").toString());
                String papel = resultSet.getString("papel");
                cliente = new Cliente(cpf, nome, email, senha, sexo, telefone, dataNascimento, papel);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cliente;
    }

    public Cliente getByCpf(String cpf) {
        Cliente cliente = null;
        String sql = "SELECT * FROM Cliente WHERE cpf=?";
        try {
            Connection connection = this.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, cpf);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String nome = resultSet.getString("nome");
                String email = resultSet.getString("email");
                String senha = resultSet.getString("senha");
                String sexo = resultSet.getString("sexo");
                String telefone = resultSet.getString("telefone");
                LocalDate dataNascimento = LocalDate.parse(resultSet.getDate("dataNascimento").toString());
                String papel = resultSet.getString("papel");
                cliente = new Cliente(cpf, nome, email, senha, sexo, telefone, dataNascimento, papel);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cliente;
    }


}
