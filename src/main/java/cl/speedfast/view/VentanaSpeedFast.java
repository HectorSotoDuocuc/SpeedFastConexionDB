package cl.speedfast.view;

import cl.speedfast.Controller.SpeedfastController;
import cl.speedfast.model.Entrega;
import cl.speedfast.model.EstadoPedido;
import cl.speedfast.model.Pedido;
import cl.speedfast.model.Repartidor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaSpeedFast extends JFrame {

    private final SpeedfastController controller = new SpeedfastController();

    private final JTextField txtDireccion =
            new JTextField(15);
    private final JTextField txtNombreRepartidor =
            new JTextField(15);
    private JComboBox comboPedidos;
    private JComboBox comboPedidosEntregados;
    private JComboBox comboRepartidores;
    private final JTextField txtIDPedidoEntregado =
            new JTextField(15);
    private JComboBox<String> comboTipoPedido;
    private int idPedidoSeleccionado = -1;
    private int idRepartidorSeleccionado = -1;
    private int idEntregaSeleccionado = -1;

    private final DefaultTableModel modeloTablaPedidos =
            new DefaultTableModel(
                    new Object[]{"ID", "Direccion", "TipoDePedido", "EstadoPedido"},
                    0
            );
    private final DefaultTableModel modeloTablaEntregas =
            new DefaultTableModel(
                    new Object[]{"ID", "IDPEDIDO", "IDREPARTIDOR", "FECHA" , "HORA"},
                    0
            );
    private final DefaultTableModel modelotablaRepartidores =
            new DefaultTableModel(
                    new Object[]{"ID", "Nombre"},
                    0);
    private final JTable tablaPedidos =
            new JTable(modeloTablaPedidos) {
                @Override
                public boolean isCellEditable ( int row, int column){return false;}
            };


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
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));

        JPanel panelPedido = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelPedido.setBorder(BorderFactory.createTitledBorder("Pedido"));
        panelPedido.add(new JLabel("Dirección: "));
        panelPedido.add(txtDireccion);
        comboTipoPedido = new JComboBox<>();
        comboTipoPedido.addItem("Comida");
        comboTipoPedido.addItem("Encomienda");
        comboTipoPedido.addItem("Express");
        panelPedido.add(new JLabel("Tipo: "));
        panelPedido.add(comboTipoPedido);
        JButton btnGuardarPedido = new JButton("Guardar Pedido");
        panelPedido.add(btnGuardarPedido);
        JButton btnActualizarPedido = new JButton("Actualizar Pedido");
        panelPedido.add(btnActualizarPedido);
        JButton btnEliminarPedido = new JButton("Eliminar Pedido");
        panelPedido.add(btnEliminarPedido);

        JPanel panelRepartidor = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelRepartidor.setBorder(BorderFactory.createTitledBorder("Repartidor"));
        panelRepartidor.add(new JLabel("Nombre Repartidor:"));
        panelRepartidor.add(txtNombreRepartidor);
        JButton btnGuardarRepartidor = new JButton("Guardar Repartidor");
        panelRepartidor.add(btnGuardarRepartidor);
        JButton btnActualizarRepartidor = new JButton("Actualizar Repartidor");
        panelRepartidor.add(btnActualizarRepartidor);
        JButton btnEliminarRepartidor = new JButton("Eliminar Repartidor");
        panelRepartidor.add(btnEliminarRepartidor);
        JButton btnListarRepartidores = new JButton("Listar Repartidores");
        panelRepartidor.add(btnListarRepartidores);

        JPanel panelEntrega = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelEntrega.setBorder(BorderFactory.createTitledBorder("Entregar Pedido"));
        panelEntrega.add(new JLabel("ID Pedido: "));
        comboPedidos =  new JComboBox<>();
        comboRepartidores = new JComboBox<>();
        panelEntrega.add(comboPedidos);
        panelEntrega.add(new JLabel("ID Repartidor: "));
        panelEntrega.add(comboRepartidores);
        JButton btnRealizarEntrega = new JButton("Realizar Entrega Pedido");
        panelEntrega.add(btnRealizarEntrega);
        JButton btnActualizarEntrega = new JButton("Actualizar Entrega");
        panelEntrega.add(btnActualizarEntrega);
        JButton btnEliminarEntrega = new JButton("Eliminar Entrega");
        panelEntrega.add(btnEliminarEntrega);
        JButton btnListarEntregas = new JButton("Listar Entregas");
        panelEntrega.add(btnListarEntregas);


        JPanel panelEstadoEntregado = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelEstadoEntregado.setBorder(BorderFactory.createTitledBorder("Pedido Entregado"));
        panelEstadoEntregado.add(new JLabel("Pedido Estado"));
        panelEstadoEntregado.add(new JLabel("ID Pedido: "));
        comboPedidosEntregados = new JComboBox<>();
        panelEstadoEntregado.add(comboPedidosEntregados);
        JButton btnPedidoEntregado = new JButton("Pedido Entregado");
        panelEstadoEntregado.add(btnPedidoEntregado);

        panelSuperior.add(panelPedido);
        panelSuperior.add(panelRepartidor);
        panelSuperior.add(panelEntrega);
        panelSuperior.add(panelEstadoEntregado);

        add(panelSuperior, BorderLayout.NORTH);

        cargarComboPedidos();
        cargarComboRepartidores();
        tablaPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(tablaPedidos), BorderLayout.CENTER);


        JButton btnActualizar =
                new JButton("Actualizar Lista Pedidos");

        add(btnActualizar, BorderLayout.SOUTH);

        tablaPedidos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaPedidos.getSelectedRow() >=0) {
                int fila = tablaPedidos.getSelectedRow();
                idPedidoSeleccionado = (int) modeloTablaPedidos.getValueAt(fila, 0);
                String direccion = modeloTablaPedidos.getValueAt(fila, 1).toString();
                String tipoPedido = modeloTablaPedidos.getValueAt(fila, 2).toString();

                txtDireccion.setText(direccion);
                comboTipoPedido.setSelectedItem(tipoPedido);
            }
        });


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
        btnActualizarPedido.addActionListener(e -> {
            if (idPedidoSeleccionado == -1) {
                JOptionPane.showMessageDialog(this, "Priemro Selecione un pedido para actualizar");
                return;
            }
            String nuevaDireccion = txtDireccion.getText().trim();
            String nuevoTipoDePedido = (String) comboTipoPedido.getSelectedItem();
            if (nuevaDireccion.isEmpty()) {
                JOptionPane.showMessageDialog(this, "La direccion no puede quedar vacia");
                return;
            }
            boolean exito = controller.actualizarPedido(idPedidoSeleccionado, nuevaDireccion, nuevoTipoDePedido);
            if (exito) {
                JOptionPane.showMessageDialog(this,"Pedido Actualizado correctamente");
                txtDireccion.setText("");
                idPedidoSeleccionado = -1;
                cargarPedidos();
                cargarComboPedidos();
            }else {
                JOptionPane.showMessageDialog(this, "Error al actualizar el Pedido");
            }
        });
        btnEliminarPedido.addActionListener(e -> {
            if (idPedidoSeleccionado == -1) {
                JOptionPane.showMessageDialog(this, "Priemro Selecione un pedido para Eliminar");
                return;
            }
            int opcion = JOptionPane.showConfirmDialog(this, "Deseas eliminar el Pedido selecionado", "confirmar eliminacion",
                    JOptionPane.YES_NO_OPTION);
            if (opcion == JOptionPane.YES_OPTION) {
                boolean ok = controller.eliminarPedido(idPedidoSeleccionado);
                JOptionPane.showMessageDialog(this, ok ? "Pedido eliminado correctamente" :
                        "No fue posible eliminarlo");
                if (ok) { txtDireccion.setText(""); cargarComboPedidos(); cargarPedidos(); }
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
        btnActualizarRepartidor.addActionListener(e -> {
            if (idRepartidorSeleccionado == -1) {
                JOptionPane.showMessageDialog(this, "Primero selecione un repartidor para actualizar de la tabla");
                return;
            }
            String nuevoNombre = txtNombreRepartidor.getText().trim();
            if(nuevoNombre.isEmpty()) {
                JOptionPane.showMessageDialog(this,"El nombre no puede estar vacio");
                return;
            }
            boolean exito = controller.actualizarRepartidor(idRepartidorSeleccionado, nuevoNombre);
            if (exito) {
                JOptionPane.showMessageDialog(this,"Repartidor Actualizado correctamente");
                txtNombreRepartidor.setText("");
                idRepartidorSeleccionado = -1;
                cargarComboRepartidores();
            }else {
                JOptionPane.showMessageDialog(this, "Error al actualizar el repartidor");
            }
        });

        btnEliminarRepartidor.addActionListener( e -> {
            if (idRepartidorSeleccionado == -1) {
                JOptionPane.showMessageDialog(this,"Selecione un Repartidor de la lista para poder eliminarlo");
                return;
            }
            int opcion = JOptionPane.showConfirmDialog(this, "Deseas eliminar el repartidor selecionado", "confirmar eliminacion",
                    JOptionPane.YES_NO_OPTION);
            if (opcion == JOptionPane.YES_OPTION) {
                boolean ok = controller.eliminarRepartidor(idRepartidorSeleccionado);
                JOptionPane.showMessageDialog(this, ok ? "Repartidor eliminado correctamente" :
                        "No fue posible eliminarlo. Puede estar asociado a un pedido");
                if (ok) { txtNombreRepartidor.setText(""); cargarComboRepartidores(); }
            }
        });
        btnListarRepartidores.addActionListener(e -> listarRepartidores());

        btnRealizarEntrega.addActionListener(e -> {
            Pedido pedidosel = (Pedido) comboPedidos.getSelectedItem();
            Repartidor repartidorsel = (Repartidor) comboRepartidores.getSelectedItem();

            if (pedidosel == null || repartidorsel == null) {
                JOptionPane.showMessageDialog(this, "Debe Selecionar un pedido y un repartidor valido");
                return;
            }

            boolean resultado = controller.registrarEntrega(pedidosel.getId(), repartidorsel.getId());

            if (resultado) {
                JOptionPane.showMessageDialog(this, "Pedido ID= " + pedidosel.getId() + " En Reparto y EN camino a direccion");
                cargarPedidos();
                cargarComboPedidos();
            }else {
                JOptionPane.showMessageDialog(this, "Error al registrar la entrega");
            }
        });
        btnActualizarEntrega.addActionListener(e -> {
            if (idEntregaSeleccionado == -1) {
                JOptionPane.showMessageDialog(this, "Primero selecione una Entrega para actualizar de la tabla");
                return;
            }
            int nuevoIDpedido = (int) comboPedidos.getSelectedItem();
            int nuevoIDRepartidor = (int) comboRepartidores.getSelectedItem();
            boolean exito = controller.actualizarEntrega(idEntregaSeleccionado, nuevoIDpedido, nuevoIDRepartidor);
            if (exito) {
                JOptionPane.showMessageDialog(this,"Pedido Actualizado correctamente");
                idPedidoSeleccionado = -1;
                cargarComboPedidos();
                cargarComboRepartidores();
            }else {
                JOptionPane.showMessageDialog(this, "Error al actualizar la entrega");
            }
        });

        btnEliminarEntrega.addActionListener(e -> {
            if (idEntregaSeleccionado == -1) {
                JOptionPane.showMessageDialog(this, "Selecione una entrega de la lista para poder eliminarlo");
                return;
            }
            int opcion = JOptionPane.showConfirmDialog(this, "Desea eliminar el repartidor selecionado" , "confirmar eliminacion",
                    JOptionPane.YES_NO_OPTION);
            if (opcion == JOptionPane.YES_OPTION) {
                boolean ok = controller.eliminarEntrega(idEntregaSeleccionado);
                JOptionPane.showMessageDialog(this, ok ? "Entrega eliminada correctamente" :
                        "No se Puede Eliminar");
                if (ok) {cargarComboPedidos(); cargarComboRepartidores(); }
            }
        });
        btnListarEntregas.addActionListener(e -> listarEntregas());
        btnPedidoEntregado.addActionListener(E ->{
            Pedido pedidosel = (Pedido) comboPedidos.getSelectedItem();
            if (pedidosel == null){
                JOptionPane.showMessageDialog(this, "Debe Seleccionar un pedido de la lista");
                return;
            }
            if (pedidosel.getEstadoPedido() ==  EstadoPedido.PENDIENTE) {
                JOptionPane.showMessageDialog(this, "El Pedido aun esta pendiente. Primero debe asignarle un repartidor para realizar la entrega");
                return;
            }
            boolean resultado = controller.marcarPedidoComoEntregado(pedidosel.getId());

            if (resultado) {
                JOptionPane.showMessageDialog(this, "El Pedido ID: " + pedidosel.getId() + " fue entregado con exito");
                cargarPedidos();
                cargarComboPedidos();
            }else {
                JOptionPane.showMessageDialog(this, "No se pudo Actualizar El estado del pedido");
            }
        });

        btnActualizar.addActionListener(e -> cargarPedidos());
    }

    private void listarEntregas() {
        modeloTablaEntregas.setRowCount(0);

        for (Entrega entrega : controller.obtenerEntregas()) {
            modeloTablaEntregas.addRow(new Object[]{entrega.getId(), entrega.getIdPedido(), entrega.getIdRepartidor(), entrega.getFecha(), entrega.getHora()});
        }
        JTable tablaEntregas = new JTable(modeloTablaEntregas) {
            @Override
            public boolean isCellEditable ( int row, int column){return false;}
        };
        tablaEntregas.setFillsViewportHeight(true);
        tablaEntregas.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaEntregas.getSelectedRow() >= 0) {
                int fila = tablaEntregas.getSelectedRow();
                idEntregaSeleccionado = (int) modeloTablaEntregas.getValueAt(fila, 0);
                int idPedido = (int) modeloTablaEntregas.getValueAt(fila, 1);
                int idRepartidor = (int) modeloTablaEntregas.getValueAt(fila, 2);

                comboPedidos.setSelectedItem(idPedido);
                comboRepartidores.setSelectedItem(idRepartidor);
            }
            JScrollPane scrollEntregas = new JScrollPane(tablaEntregas);
            scrollEntregas.setPreferredSize(new Dimension(500, 500));
            JOptionPane.showMessageDialog(this,scrollEntregas, "Lista Entregas", JOptionPane.PLAIN_MESSAGE);
        });
    }
    private void listarRepartidores() {
        modelotablaRepartidores.setRowCount(0);

        for (Repartidor repartidor : controller.obtenerRepartidores()) {
            modelotablaRepartidores.addRow(new Object[]{repartidor.getId(), repartidor.getNombre()});
        }
        JTable tablaRepartidores = new JTable(modelotablaRepartidores) {

        @Override
        public boolean isCellEditable ( int row, int column){return false;}
        };
        tablaRepartidores.setFillsViewportHeight(true);
        tablaRepartidores.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaRepartidores.getSelectedRow() >= 0) {
                int fila = tablaRepartidores.getSelectedRow();
                idRepartidorSeleccionado = (int) modelotablaRepartidores.getValueAt(fila, 0);
                String nombre = modelotablaRepartidores.getValueAt(fila, 1).toString();

                txtNombreRepartidor.setText(nombre);
            }
        });
        JScrollPane scrollRepartidores = new JScrollPane(tablaRepartidores);
        scrollRepartidores.setPreferredSize(new Dimension(450, 300));

        JOptionPane.showMessageDialog(this,scrollRepartidores, "Lista Repartidores", JOptionPane.PLAIN_MESSAGE);
    }

    private void cargarPedidos() {
        modeloTablaPedidos.setRowCount(0);

        for(Pedido pedido:
                controller.obtenerPedidos()) {

            modeloTablaPedidos.addRow(
            new Object[]{
            pedido.getId(),
            pedido.getDireccion(),
            pedido.getTipoDePedido(),
            pedido.getEstadoPedido()
            }
            );
        }
    }

    private void cargarComboPedidos() {
        comboPedidos.removeAllItems();
        if (comboPedidosEntregados != null) {
            comboPedidosEntregados.removeAllItems();
        }
        for (Pedido p : controller.obtenerPedidos()) {
            if (p.getEstadoPedido() == EstadoPedido.PENDIENTE) {
                comboPedidos.addItem(p);
            }
            if (p.getEstadoPedido() == EstadoPedido.EN_REPARTO) {
                comboPedidosEntregados.addItem(p);
            }
        }
    }

    private void cargarComboRepartidores() {
        comboRepartidores.removeAllItems();
        for (Repartidor r : controller.obtenerRepartidores()) {
            comboRepartidores.addItem(r);
        }
    }
}
