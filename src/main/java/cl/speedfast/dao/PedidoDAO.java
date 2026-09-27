package cl.speedfast.dao;

import cl.speedfast.model.EstadoPedido;
import cl.speedfast.model.Pedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

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
    public List<Pedido> obtenerPedidos(){

        List<Pedido> pedidos = new ArrayList<>();

        String sql =
                "SELECT id, direccion, tipo, estado FROM pedido ORDER  BY id";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Pedido pedido = new Pedido(
                        rs.getInt("id"),
                        rs.getString("direccion"),
                        rs.getString("tipo"),
                        EstadoPedido.valueOf(rs.getString("estado"))
                );

                pedidos.add(pedido);
            }

        }catch (SQLException e){
            System.out.println("Error al listar los Pedidos " + e.getMessage());
        }
        return pedidos;
    }
    public boolean actualizarEstado(int idPedido, String nuevoEstado) {
        String sql =
                "UPDATE pedido SET estado = ? WHERE id = ?";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, nuevoEstado);
            ps.setInt(2, idPedido);

            return ps.executeUpdate() > 0;
        }catch (SQLException e) {
            System.out.println("Error Al Actualizar: " + e.getMessage());
            return false;
        }
    }
}
