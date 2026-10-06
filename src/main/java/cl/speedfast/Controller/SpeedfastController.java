package cl.speedfast.Controller;

import cl.speedfast.dao.PedidoDAO;
import cl.speedfast.dao.RepartidorDAO;
import cl.speedfast.dao.impl.EntregaDAOImpl;
import cl.speedfast.dao.impl.PedidoDAOImpl;
import cl.speedfast.dao.impl.RepartidorDAOImpl;
import cl.speedfast.model.Entrega;
import cl.speedfast.model.EstadoPedido;
import cl.speedfast.model.Pedido;
import cl.speedfast.model.Repartidor;

import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class SpeedfastController {

    private PedidoDAOImpl pedidoDAO = new PedidoDAOImpl();
    private RepartidorDAOImpl repartidorDAO = new RepartidorDAOImpl();
    private EntregaDAOImpl entregaDAO = new EntregaDAOImpl();

    public boolean registrarRepartidor(String nombre) {
        if (nombre.isBlank()) {
            return false;
        }
        Repartidor repartidor = new Repartidor(nombre);
        return repartidorDAO.guardar(repartidor);
    }

    public boolean actualizarRepartidor(int id, String nombre) {
        if (id <=0 || nombre == null || nombre.isBlank()) {
            return false;
        }
        Repartidor repartidor = new Repartidor(id, nombre);
        return repartidorDAO.actualizar(repartidor);
    }

    public List<Repartidor> obtenerRepartidores() {
        return repartidorDAO.listarRepartidores();
    }

    public boolean eliminarRepartidor(int id) {
        if (id <=0) {
            return false;
        }
        return repartidorDAO.eliminar(id);
    }

    public boolean registrarPedido(String direccion, String tipoDePedido) {
        if (direccion.isBlank() || tipoDePedido.isBlank()) {
            return false;
        }
        Pedido pedido = new Pedido(direccion, tipoDePedido);
        return pedidoDAO.guardar(pedido);
    }
    public List<Pedido> obtenerPedidos() {
        return pedidoDAO.listarPedidos();
    }

    public boolean actualizarPedido(int id, String direccion, String tipoDePedido) {
        if ( id<= 0 ||direccion.isBlank() || tipoDePedido.isBlank()) {
            return false;
        }
        Pedido pedido = new Pedido(id ,direccion, tipoDePedido);
        return pedidoDAO.actualizar(pedido);
    }
    public boolean eliminarPedido(int id) {
        if (id  <= 0) {
            return false;
        }
        return pedidoDAO.eliminar(id);
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

    public List<Entrega> obtenerEntregas() {
        return entregaDAO.listarEntregas();
    }

    public boolean actualizarEntrega(int id, int idPedido, int idRepartidor) {
        if (id<= 0 || idPedido <= 0 || idRepartidor <= 0) {
            return false;
        }
        Entrega entrega = new Entrega(id, idPedido , idRepartidor);
        return entregaDAO.actualizar(entrega);
    }

    public boolean eliminarEntrega(int id) {
        if (id <= 0) {
            return false;
        }
        return entregaDAO.eliminar(id);
    }

    public boolean marcarPedidoComoEntregado(int idPedido) {
        if (idPedido <= 0) {
            return false;
        }
        return pedidoDAO.actualizarEstado(idPedido, "ENTREGADO");
    }

}
