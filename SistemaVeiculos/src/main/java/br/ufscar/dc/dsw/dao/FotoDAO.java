package br.ufscar.dc.dsw.dao;

import br.ufscar.dc.dsw.domain.Foto;

import java.net.ConnectException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FotoDAO extends GenericDAO {

    public void insere(Foto foto) {
        String sql = "INSERT INTO Foto(chassi, pathFoto) VALUES (?, ?)";
        try {
            Connection connection = this.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, foto.getVeiculo().getChassi());
            statement.setString(2, foto.getPathFoto());
            statement.executeUpdate();

            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getNumFotos(String chassi) {
        int numFotos = 0;
        String sql = "SELECT COUNT(*) AS numFotos FROM Foto WHERE chassi=?";
        try {
            Connection connection = this.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, chassi);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                numFotos = resultSet.getInt("numFotos");
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return numFotos;
    }
}
