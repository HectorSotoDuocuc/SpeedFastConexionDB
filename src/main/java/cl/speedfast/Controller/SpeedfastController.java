package cl.speedfast.Controller;

import cl.speedfast.dao.EntregaDAO;
import cl.speedfast.dao.PedidoDAO;
import cl.speedfast.dao.RepartidorDAO;
import cl.speedfast.model.Entrega;
import cl.speedfast.model.Pedido;
import cl.speedfast.model.Repartidor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class SpeedfastController {

    private PedidoDAO pedidoDAO = new PedidoDAO();
    private RepartidorDAO repartidorDAO = new RepartidorDAO();
    private EntregaDAO entregaDAO = new EntregaDAO();

    public boolean registrarRepartidor(String nombre) {
        if (nombre.isBlank()) {
            return false;
        }
        Repartidor repartidor = new Repartidor(nombre);
        return repartidorDAO.guardar(repartidor);
    }

    public boolean registrarPedido(String direccion, String tipoDePedido) {
        if (direccion.isBlank() || tipoDePedido.isBlank()) {
            return false;
        }
        Pedido pedido = new Pedido(direccion, tipoDePedido);
        return pedidoDAO.guardar(pedido);
    }
    public List<Pedido> obtenerPedidos() {
        return pedidoDAO.obtenerPedidos();
    }

    public boolean registrarEntrega(int idPedido, int idRepartidor) {

        Entrega entrega =
                new Entrega(idPedido,idRepartidor, LocalDate.now(), LocalTime.now());
        boolean entregaGuardada = entregaDAO.guardar(entrega);

        if (entregaGuardada) {
            pedidoDAO.actualizarEstado(idPedido, "EN_REPARTO");
        }
        return entregaGuardada;
    }

    public boolean marcarPedidoComoEntregado(int idPedido) {
        if (idPedido <= 0) {
            return false;
        }
        return pedidoDAO.actualizarEstado(idPedido, "ENTREGADO");
    }
}
