package cl.speedfast.dao.impl;

import cl.speedfast.dao.EntregaDAO;
import cl.speedfast.model.Entrega;
import cl.speedfast.util.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntregaDAOImpl implements EntregaDAO {
    @Override
    public boolean guardar(Entrega entrega) {
        String sql = "INSERT INTO entrega (id_pedido, id_repartidor, fecha, hora) VALUES (?, ?, ?, ?)";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, entrega.getIdPedido());
            ps.setInt(2, entrega.getIdRepartidor());
            ps.setDate(3, java.sql.Date.valueOf(entrega.getFecha()));
            ps.setTime(4, java.sql.Time.valueOf(entrega.getHora()));
            return ps.executeUpdate() > 0;
        }catch (SQLException e){
            System.out.println("Error Al Guardar la Entrega: " + e.getMessage());
            return false;
        }
    }

    @Override
    public List<Entrega> listarEntregas() {
        List<Entrega>  entregas = new ArrayList<>();
        String sql = "SELECT id, id_pedido, id_repartidor, fecha, hora FROM entrega ORDER BY id";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                entregas.add(new Entrega(rs.getInt("id"), rs.getInt("id_pedido"), rs.getInt("id_repartidor"), rs.getDate("fecha").toLocalDate(), rs.getTime("hora").toLocalTime()));
            }
    }catch (SQLException e){
            System.out.println("Error al ver la lista de Entregas:" + e.getMessage());
        }
        return entregas;
    }

    @Override
    public boolean actualizar(Entrega entrega) {
        String sql = "UPDATE entrega SET id_pedido = ?, id_repartidor = ?, fecha = ?, hora = ? WHERE id = ?";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1,entrega.getIdPedido());
            ps.setInt(2, entrega.getIdRepartidor());
            ps.setDate(3, Date.valueOf(entrega.getFecha()));
            ps.setTime(4, Time.valueOf(entrega.getHora()));
            ps.setInt(5, entrega.getId());

            return ps.executeUpdate() >0;
        }catch (SQLException e) {
            System.out.println("Error al Actualizar la entrega " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql ="DELETE FROM entrega WHERE id = ?";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() >0;
        }catch (SQLException e) {
            System.out.println("Error al eliminar la Entrega " + e.getMessage());
            return false;
        }
    }

}
