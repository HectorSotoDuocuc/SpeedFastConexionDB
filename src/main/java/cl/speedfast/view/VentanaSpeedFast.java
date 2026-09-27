package cl.speedfast.view;

import cl.speedfast.Controller.SpeedfastController;
import cl.speedfast.model.Pedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaSpeedFast extends JFrame {

    private SpeedfastController controller = new SpeedfastController();

    private JTextField txtDireccion =
            new JTextField(15);

    private JTextField txtNombreRepartidor =
            new JTextField(15);

    private JTextField txtIDPedido =
            new JTextField(15);

    private JTextField txtIDRepartidor =
            new JTextField(15);

    private JTextField txtIDPedidoEntregado =
            new JTextField(15);

    private JComboBox<String> comboTipoPedido;

    private DefaultTableModel modeloTabla =
            new DefaultTableModel(
                    new Object[]{"ID", "Direccion", "TipoDePedido", "EstadoPedido"},
                    0
            );
    private JTable tablaPedidos =
            new JTable(modeloTabla);

    public VentanaSpeedFast(){


        setTitle("SpeedFast Pedidos");
        setSize(900,900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        crearIntafaz();
        cargarPedidos();
    }

    private void crearIntafaz() {
        setLayout(new BorderLayout());


        JPanel panelSuperior =
                new JPanel();

        panelSuperior.setLayout(
                new BoxLayout(
                        panelSuperior,
                        BoxLayout.Y_AXIS
                )
        );


        JPanel panelDireccion =
                new JPanel();

        panelDireccion.add(
                new JLabel("Pedido")
        );

        panelDireccion.add(
                new JLabel("Dirección: ")
        );
        panelDireccion.add(txtDireccion);

        comboTipoPedido = new JComboBox<>();

        comboTipoPedido.addItem("Comida");
        comboTipoPedido.addItem("Encomienda");
        comboTipoPedido.addItem("Express");

        panelDireccion.add(
                new JLabel("Tipo: ")
        );

        panelDireccion.add(comboTipoPedido);

        JButton btnGuardarPedido =
                new JButton("Guardar Pedido");

        panelDireccion.add(btnGuardarPedido);

        JPanel panelRepartidor =
                new JPanel();

        panelRepartidor.add(
                new JLabel("Nombre Repartidor: ")
        );

        panelRepartidor.add(txtNombreRepartidor);

        JButton btnGuardarRepartidor =
                new JButton("Guardar Repartidor");

        panelRepartidor.add(btnGuardarRepartidor);

        JPanel panelEntrega =
                new JPanel();

        panelEntrega.add(
                new JLabel("Entregar Pedido")
        );

        panelEntrega.add(
                new JLabel("ID Pedido: ")
        );

        panelEntrega.add(txtIDPedido);

        panelEntrega.add(
                new JLabel("ID Repartidor: ")
        );

        panelEntrega.add(txtIDRepartidor);

        JButton btnRealizarEntrega =
                new JButton("Realizar Entrega Pedido");
        panelEntrega.add(btnRealizarEntrega);

        JPanel panelEstadoEntregado =
                new JPanel();

        panelEstadoEntregado.add(
                new JLabel("Pedido Estado")
        );
        panelEstadoEntregado.add(
                new JLabel("ID Pedido: ")
        );

        panelEstadoEntregado.add(txtIDPedidoEntregado);
        JButton btnPedidoEntregado =
                new JButton("Pedido Entregado");
        panelEstadoEntregado.add(btnPedidoEntregado);

        panelSuperior.add(panelDireccion);
        panelSuperior.add(panelRepartidor);
        panelSuperior.add(panelEntrega);
        panelSuperior.add(panelEstadoEntregado);

        add(panelSuperior, BorderLayout.NORTH);


        add(
                new JScrollPane(tablaPedidos),
                BorderLayout.CENTER
        );

        JButton btnActualizar =
                new JButton("Actualizar Lista Pedidos");

        add(btnActualizar, BorderLayout.SOUTH);

        btnGuardarPedido.addActionListener(e -> {

            boolean resultado =
                    controller.registrarPedido(
                            txtDireccion.getText(),
                            (String) comboTipoPedido.getSelectedItem()
                    );

            if (resultado) {
                JOptionPane.showMessageDialog(
                        this,"Pedido Agregado Con Exito"
                );
                txtDireccion.setText("");
                cargarPedidos();
            }else {
                JOptionPane.showMessageDialog(this, "Error al Registrar El Pedido.");
            }
        });

        btnGuardarRepartidor.addActionListener(e -> {

            boolean resultado =
                    controller.registrarRepartidor(
                            txtNombreRepartidor.getText()
                    );

            if (resultado) {
                JOptionPane.showMessageDialog(this,"Repartidor Guardado COn Exito"
                );
                txtNombreRepartidor.setText("");
            }else {
                JOptionPane.showMessageDialog(
                        this,
                        "No se Pudo Guardar El Repartidor"
                );
            }
        });

        btnRealizarEntrega.addActionListener(e -> {

            try {

                int IDPedido =
                        Integer.parseInt(
                                txtIDPedido.getText()
                        );

                int IDRepartidor =
                        Integer.parseInt(
                                txtIDRepartidor.getText()
                        );

                boolean resultado =
                        controller.registrarEntrega(
                                IDPedido,
                                IDRepartidor
                        );

                if (resultado) {
                    JOptionPane.showMessageDialog(this, "El Pedido Sera Entregado"
                    );
                    txtIDPedido.setText("");
                    txtIDRepartidor.setText("");
                    cargarPedidos();
                }else {
                    JOptionPane.showMessageDialog(this,"No Se Pudo Empezar La Entrega del Pedido");
                }
            }catch (NumberFormatException ex) {

                JOptionPane.showMessageDialog(this, "Los ID deben Ser números");
            }
        });

        btnPedidoEntregado.addActionListener(E ->{
            try {
                int idPedido =
                        Integer.parseInt(
                                txtIDPedidoEntregado.getText()
                        );

                boolean resultado =
                        controller.marcarPedidoComoEntregado(
                                idPedido
                        );

                if (resultado) {
                    JOptionPane.showMessageDialog(
                            this,"EL Pedido: " + idPedido + " Fue Entregado Con Exito"
                    );
                    txtIDPedidoEntregado.setText("");
                    cargarPedidos();
                }else {
                    JOptionPane.showMessageDialog(this, "No Se Pudo Actualizar El Pedido. Verifique el ID");
                }
            }catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Los ID deben ser números");
            }
        });

        btnActualizar.addActionListener(e -> {
            cargarPedidos();
        });
    }

    private void cargarPedidos() {
        modeloTabla.setRowCount(0);

        for(Pedido pedido:
                controller.obtenerPedidos()) {

            modeloTabla.addRow(
            new Object[]{
            pedido.getId(),
            pedido.getDireccion(),
            pedido.getTipoDePedido(),
            pedido.getEstadoPedido()
            }
            );
        }
    }
}
