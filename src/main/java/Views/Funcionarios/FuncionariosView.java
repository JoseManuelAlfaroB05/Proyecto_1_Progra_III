package Views.Funcionarios;

import Controllers.ControllerFuncionario;
import Models.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import java.awt.*;

public class FuncionariosView extends JPanel {

    private final ControllerFuncionario controller = new ControllerFuncionario();

    private JTextField filtroId = new JTextField(12);
    private JTextField filtroNombre = new JTextField(18);

    private JTextField campoId = new JTextField(12);
    private JTextField campoNombre = new JTextField(24);
    private JTextField campoTelefono = new JTextField(24);

    private JTable tabla = new JTable();

    public FuncionariosView() {

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(12, 18, 12, 18));

        add(crearBusqueda(), BorderLayout.NORTH);
        add(crearFormulario(), BorderLayout.CENTER);
        add(crearListado(), BorderLayout.SOUTH);

        campoId.setEnabled(true);

        configurarTelefono();
        cargarTabla();
    }

    private void configurarTelefono() {

        campoTelefono.setToolTipText("Ingrese solo números");

        ((AbstractDocument) campoTelefono.getDocument())
                .setDocumentFilter(new DocumentFilter() {

                    @Override
                    public void insertString(
                            FilterBypass fb,
                            int offset,
                            String string,
                            javax.swing.text.AttributeSet attr)
                            throws javax.swing.text.BadLocationException {

                        if (string != null && string.matches("[0-9]*")) {
                            fb.insertString(offset, string, attr);
                        }
                    }

                    @Override
                    public void replace(
                            FilterBypass fb,
                            int offset,
                            int length,
                            String text,
                            javax.swing.text.AttributeSet attrs)
                            throws javax.swing.text.BadLocationException {

                        if (text != null && text.matches("[0-9]*")) {
                            fb.replace(offset, length, text, attrs);
                        }
                    }
                });
    }

    private JPanel crearBusqueda() {

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        panel.setBorder(
                BorderFactory.createTitledBorder("Búsqueda")
        );

        panel.add(new JLabel("ID"));
        panel.add(filtroId);

        panel.add(new JLabel("Nombre"));
        panel.add(filtroNombre);

        JButton buscar = new JButton("Buscar");

        buscar.addActionListener(e -> cargarTabla());

        panel.add(buscar);

        JButton imprimir = new JButton("Imprimir");

        imprimir.addActionListener(e -> imprimirTabla());

        panel.add(imprimir);

        return panel;
    }

    private JPanel crearFormulario() {

        JPanel panel = new JPanel(new GridBagLayout());

        panel.setBorder(
                BorderFactory.createTitledBorder("Funcionario")
        );

        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(6, 8, 6, 8);
        c.anchor = GridBagConstraints.WEST;

        agregarCampo(
                panel,
                c,
                0,
                "ID",
                campoId
        );

        agregarCampo(
                panel,
                c,
                1,
                "Nombre",
                campoNombre
        );

        agregarCampo(
                panel,
                c,
                2,
                "Teléfono",
                campoTelefono
        );

        JPanel botones = new JPanel(
                new FlowLayout(
                        FlowLayout.CENTER,
                        12,
                        0
                )
        );

        JButton guardar = new JButton("Guardar");

        guardar.addActionListener(e -> guardar());

        JButton borrar = new JButton("Borrar");

        borrar.addActionListener(e -> borrar());

        JButton limpiar = new JButton("Limpiar");

        limpiar.addActionListener(e -> limpiar());

        botones.add(guardar);
        botones.add(borrar);
        botones.add(limpiar);

        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 2;

        panel.add(botones, c);

        return panel;
    }

    private void agregarCampo(
            JPanel panel,
            GridBagConstraints c,
            int fila,
            String etiqueta,
            JComponent campo) {

        c.gridy = fila;

        c.gridx = 0;
        c.gridwidth = 1;

        panel.add(
                new JLabel(etiqueta),
                c
        );

        c.gridx = 1;
        c.gridwidth = 2;

        c.fill = GridBagConstraints.HORIZONTAL;

        panel.add(
                campo,
                c
        );

        c.fill = GridBagConstraints.NONE;
    }

    private JPanel crearListado() {

        JPanel panel = new JPanel(
                new BorderLayout()
        );

        panel.setBorder(
                BorderFactory.createTitledBorder("Listado")
        );

        tabla.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION
        );

        tabla.getSelectionModel()
                .addListSelectionListener(e -> {

                    if (!e.getValueIsAdjusting()
                            && tabla.getSelectedRow() >= 0) {

                        campoId.setText(
                                String.valueOf(
                                        tabla.getValueAt(
                                                tabla.getSelectedRow(),
                                                0
                                        )
                                )
                        );

                        campoNombre.setText(
                                String.valueOf(
                                        tabla.getValueAt(
                                                tabla.getSelectedRow(),
                                                1
                                        )
                                )
                        );

                        campoTelefono.setText(
                                String.valueOf(
                                        tabla.getValueAt(
                                                tabla.getSelectedRow(),
                                                2
                                        )
                                )
                        );
                    }
                });

        panel.add(
                new JScrollPane(tabla),
                BorderLayout.CENTER
        );

        panel.setPreferredSize(
                new Dimension(0, 220)
        );

        return panel;
    }

    private void cargarTabla() {

        DefaultTableModel modelo =
                new DefaultTableModel(
                        new Object[]{
                                "Id",
                                "Nombre",
                                "Teléfono"
                        },
                        0) {

                    @Override
                    public boolean isCellEditable(
                            int row,
                            int column) {

                        return false;
                    }
                };

        for (User funcionario :
                controller.buscar(
                        filtroId.getText(),
                        filtroNombre.getText())) {

            modelo.addRow(
                    new Object[]{
                            funcionario.getVarId(),
                            funcionario.getVarNombre(),
                            funcionario.getVarTelefono()
                    }
            );
        }

        tabla.setModel(modelo);
    }

    private void guardar() {

        String id = campoId.getText().trim();
        String nombre = campoNombre.getText().trim();
        String telefono = campoTelefono.getText().trim();

        if (id.isEmpty()
                || nombre.isEmpty()
                || telefono.isEmpty()) {

            mostrarError(
                    "Complete todos los campos del funcionario."
            );

            return;
        }

        if (!telefono.matches("[0-9]+")) {

            mostrarError(
                    "El teléfono solo puede contener números."
            );

            return;
        }

        boolean existe =
                controller.buscar(id, "")
                        .stream()
                        .anyMatch(
                                funcionario ->
                                        funcionario
                                                .getVarId()
                                                .equalsIgnoreCase(id)
                        );

        boolean resultado =
                existe
                        ? controller.actualizar(
                        id,
                        nombre,
                        telefono
                )
                        : controller.guardar(
                        id,
                        nombre,
                        telefono
                );

        if (!resultado) {

            mostrarError(
                    "No fue posible guardar el funcionario. "
                            + "Verifique el ID."
            );

            return;
        }

        limpiar();
    }

    private void borrar() {

        String id = campoId.getText().trim();

        if (id.isEmpty()
                || !controller.eliminar(id)) {

            mostrarError(
                    "Seleccione un funcionario válido para borrar."
            );

            return;
        }

        limpiar();
    }

    private void limpiar() {

        filtroId.setText("");
        filtroNombre.setText("");

        campoId.setText("");
        campoNombre.setText("");
        campoTelefono.setText("");

        tabla.clearSelection();

        cargarTabla();
    }

    private void imprimirTabla() {

        try {

            tabla.print();

        } catch (Exception e) {

            mostrarError(
                    "No fue posible imprimir el listado."
            );
        }
    }

    private void mostrarError(String mensaje) {

        JOptionPane.showMessageDialog(
                this,
                mensaje,
                "Funcionarios",
                JOptionPane.ERROR_MESSAGE
        );
    }
}