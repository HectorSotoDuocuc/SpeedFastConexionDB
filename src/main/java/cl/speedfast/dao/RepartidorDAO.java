package cl.speedfast.dao;

import cl.speedfast.model.Repartidor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepartidorDAO {

    public boolean guardar(Repartidor repartidor) {

        String sql =
                "INSERT INTO repartidor (nombre) VALUES (?)";

        try(Connection conexion = ConexionBD.conectar();
            PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, repartidor.getNombre());

            return ps.executeUpdate() > 0;

        }catch (SQLException e){
            System.out.println("Error al guardar un Repartidor: " + e.getMessage());
            return false;
        }
    }

    public List<Repartidor> listaRepartidores() {

        List<Repartidor> repartidores = new ArrayList<>();

        String sql =
                "SELECT id, nombre FROM repartidor ORDER BY id";

        try (Connection conexion = ConexionBD.conectar();
        PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Repartidor repartidor = new Repartidor(
                        rs.getInt("id"),
                        rs.getString("nombre")
                );

                repartidores.add(repartidor);
            }

        }catch (SQLException e) {
            System.out.println("Error al listar Repartidores: " + e.getMessage());
        }
        return repartidores;
}
}
