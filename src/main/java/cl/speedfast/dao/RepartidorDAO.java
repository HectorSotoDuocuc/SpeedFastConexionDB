package cl.speedfast.dao;

import cl.speedfast.model.Repartidor;

import java.util.List;

public interface RepartidorDAO {
    boolean guardar(Repartidor repartidor);
    List<Repartidor> listarRepartidores();
    boolean actualizar(Repartidor repartidor);
    boolean eliminar(int id);
}
