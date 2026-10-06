package cl.speedfast.dao.impl;

import cl.speedfast.dao.RepartidorDAO;
import cl.speedfast.model.Repartidor;
import cl.speedfast.util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RepartidorDAOImpl implements RepartidorDAO {

    @Override
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
    @Override
    public List<Repartidor> listarRepartidores() {
        List<Repartidor> repartidores = new ArrayList<>();
        String sql = "SELECT id, nombre FROM repartidor ORDER BY id";
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

    @Override
    public boolean actualizar(Repartidor repartidor) {
        String sql = "UPDATE repartidor SET nombre = ? WHERE id = ?";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, repartidor.getNombre());
            ps.setInt(2, repartidor.getId());
            return ps.executeUpdate() >0;
        }catch (SQLException e) {
            System.out.println("Error al actualizar Repartidor: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM repartidor WHERE id = ?";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar Repartidor: " + e.getMessage());
            return false;
        }
    }
}
