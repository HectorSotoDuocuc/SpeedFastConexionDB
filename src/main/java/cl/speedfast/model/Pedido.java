package cl.speedfast.model;

public class Pedido {
    private int id;
    private String direccion;
    private String tipoDePedido;
    private EstadoPedido estadoPedido;

    public Pedido( String direccion, String tipoDePedido) {
        this.direccion = direccion;
        this.tipoDePedido = tipoDePedido;
        this.estadoPedido = EstadoPedido.PENDIENTE;
    }

    public Pedido(int id, String direccion, String tipoDePedido) {
        this.id = id;
        this.direccion = direccion;
        this.tipoDePedido = tipoDePedido;
    }

    public Pedido(int id, String direccion, String tipoDePedido, EstadoPedido estadoPedido) {
        this.id = id;
        this.direccion = direccion;
        this.tipoDePedido = tipoDePedido;
        this.estadoPedido = estadoPedido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTipoDePedido() {
        return tipoDePedido;
    }

    public void setTipoDePedido(String tipoDePedido) {
        this.tipoDePedido = tipoDePedido;
    }

    public EstadoPedido getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(EstadoPedido estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    @Override
    public String toString() {
        return "id=" + id + " - " + direccion +
                "(" + tipoDePedido + ")";
    }
}
