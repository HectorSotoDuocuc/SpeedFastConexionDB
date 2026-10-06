package cl.speedfast.dao.impl;

import cl.speedfast.dao.PedidoDAO;
import cl.speedfast.model.EstadoPedido;
import cl.speedfast.model.Pedido;
import cl.speedfast.util.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAOImpl implements PedidoDAO {
    @Override
    public boolean guardar(Pedido pedido) {

        String sql =
                "INSERT INTO pedido (direccion, tipo, estado) VALUES (?, ?, ?)";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, pedido.getDireccion());
            ps.setString(2, pedido.getTipoDePedido());
            ps.setString(3, pedido.getEstadoPedido().name());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al guardar un Pedido: " + e.getMessage());
            return false;
        }
    }
    @Override
    public List<Pedido> listarPedidos(){

        List<Pedido> pedidos = new ArrayList<>();

        String sql =
                "SELECT id, direccion, tipo, estado FROM pedido ORDER  BY id";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String estadoString = rs.getString("estado");

                EstadoPedido estadoenum = EstadoPedido.PENDIENTE;
                if (estadoString != null && !estadoString.isBlank()) {
                    try {
                        estadoenum = EstadoPedido.valueOf(estadoString.trim().toUpperCase());
                    }catch (IllegalArgumentException e) {
                        System.out.println("Estado desconocido en BD");
                    }
                }
                Pedido pedido = new Pedido(
                        rs.getInt("id"),
                        rs.getString("direccion"),
                        rs.getString("tipo"),
                        estadoenum);


                pedidos.add(pedido);
            }

        }catch (SQLException e){
            System.out.println("Error al listar los Pedidos " + e.getMessage());
        }
        return pedidos;
    }
    @Override
    public boolean actualizar(Pedido pedido) {
        String sql =
                "UPDATE pedido SET direccion = ?, tipo = ? WHERE id = ?";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, pedido.getDireccion());
            ps.setString(2, pedido.getTipoDePedido());
            ps.setInt(3, pedido.getId());

            return ps.executeUpdate() > 0;
        }catch (SQLException e) {
            System.out.println("Error Al Actualizar: " + e.getMessage());
            return false;
        }
    }
    public boolean actualizarEstado(int idPedido, String nuevoEstado) {
        String sql = "UPDATE pedido SET estado = ? WHERE id = ?";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, nuevoEstado);
            ps.setInt(2, idPedido);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar Estado: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql ="DELETE FROM pedido WHERE id = ?";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() >0;
        }catch (SQLException e) {
            System.out.println("Error al eliminar Pedido: " + e.getMessage());
            return false;
        }

    }

}
