package cl.speedfast.dao;

import cl.speedfast.model.Entrega;

import java.util.List;

public interface EntregaDAO {
    boolean guardar(Entrega entrega);
    List<Entrega> listarEntregas();
    boolean actualizar(Entrega entrega);
    boolean eliminar(int id);
}
