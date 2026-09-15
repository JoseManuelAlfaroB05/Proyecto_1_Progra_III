package Presentar.Funcionarios;

import Recursos.User;
import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class FuncionariosView extends JPanel implements PropertyChangeListener {
    private final ControllerFuncionario controller = new ControllerFuncionario();
    private final ModelFuncionario model;
    private JTextField filtroId = new JTextField(12);
    private JTextField filtroNombre = new JTextField(18);
    private JTextField campoId = new JTextField(12);
    private JTextField campoNombre = new JTextField(24);
    private JTextField campoTelefono = new JTextField(24);
    private JTable tabla = new JTable();

    public FuncionariosView() {
        model = controller.getModel();
        model.addPropertyChangeListener(this);

        setLayout(new BorderLayout(10, 12));
        setBorder(BorderFactory.createEmptyBorder(18, 24, 18, 24));

        add(crearBusqueda(), BorderLayout.NORTH);
        JPanel formulario = crearFormulario();
        formulario.setPreferredSize(new Dimension(0, 235));
        add(formulario, BorderLayout.CENTER);
        add(crearListado(), BorderLayout.SOUTH);

        campoId.setEnabled(true);
        configurarTelefono();
    }

    private void configurarTelefono() {
        campoTelefono.setToolTipText("Ingrese solo números");
        ((AbstractDocument) campoTelefono.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, javax.swing.text.AttributeSet attr) throws javax.swing.text.BadLocationException {
                if (string != null && string.matches("[0-9]*")) {
                    fb.insertString(offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, javax.swing.text.AttributeSet attrs) throws javax.swing.text.BadLocationException {
                if (text != null && text.matches("[0-9]*")) {
                    fb.replace(offset, length, text, attrs);
                }
            }
        });
    }

    private JPanel crearBusqueda() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
        panel.setBorder(BorderFactory.createTitledBorder("Búsqueda"));
        panel.add(new JLabel("ID"));
        panel.add(filtroId);
        panel.add(new JLabel("Nombre"));
        panel.add(filtroNombre);

        JButton buscar = new JButton("Buscar");
        buscar.addActionListener(e -> controller.buscar(filtroId.getText(), filtroNombre.getText()));
        panel.add(buscar);

        JButton imprimir = new JButton("Imprimir");
        imprimir.addActionListener(e -> imprimirTabla());
        panel.add(imprimir);

        return panel;
    }

    private JPanel crearFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Funcionario"));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 8, 6, 8);
        c.anchor = GridBagConstraints.WEST;

        agregarCampo(panel, c, 0, "ID", campoId);
        agregarCampo(panel, c, 1, "Nombre", campoNombre);
        agregarCampo(panel, c, 2, "Teléfono", campoTelefono);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));

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

    private void agregarCampo(JPanel panel, GridBagConstraints c, int fila, String etiqueta, JComponent campo) {
        c.gridy = fila;
        c.gridx = 0;
        c.gridwidth = 1;
        panel.add(new JLabel(etiqueta), c);

        c.gridx = 1;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(campo, c);
        c.fill = GridBagConstraints.NONE;
    }

    private JPanel crearListado() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Listado"));

        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabla.getSelectedRow() >= 0) {
                int fila = tabla.getSelectedRow();
                String id = String.valueOf(tabla.getValueAt(fila, 0));
                controller.buscar(id, "");
            }
        });

        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        panel.setPreferredSize(new Dimension(0, 250));

        return panel;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals(ModelFuncionario.LIST)) {
            int[] cols = {
                    TableModelFuncionario.ID,
                    TableModelFuncionario.NOMBRE,
                    TableModelFuncionario.TELEFONO
            };
            tabla.setModel(new TableModelFuncionario(cols, model.getList()));
        }

        if (evt.getPropertyName().equals(ModelFuncionario.CURRENT)) {
            User funcionario = model.getCurrent();
            campoId.setText(funcionario.getVarId());
            campoNombre.setText(funcionario.getVarNombre());
            campoTelefono.setText(funcionario.getVarTelefono());
            campoId.setEnabled(funcionario.getVarId() == null || funcionario.getVarId().isEmpty());
        }
    }

    private void guardar() {
        String id = campoId.getText().trim();
        String nombre = campoNombre.getText().trim();
        String telefono = campoTelefono.getText().trim();

        if (id.isEmpty() || nombre.isEmpty() || telefono.isEmpty()) {
            mostrarError("Complete todos los campos del funcionario.");
            return;
        }

        if (!telefono.matches("[0-9]+")) {
            mostrarError("El teléfono solo puede contener números.");
            return;
        }

        boolean existe = controller.buscar(id, "").stream()
                .anyMatch(funcionario -> funcionario.getVarId().equalsIgnoreCase(id));

        boolean resultado = existe
                ? controller.actualizar(id, nombre, telefono)
                : controller.guardar(id, nombre, telefono);

        if (!resultado) {
            mostrarError("No fue posible guardar el funcionario. Verifique el ID.");
            return;
        }

        limpiar();
    }

    private void borrar() {
        String id = campoId.getText().trim();

        if (id.isEmpty() || !controller.eliminar(id)) {
            mostrarError("Seleccione un funcionario válido para borrar.");
            return;
        }

        limpiar();
    }

    private void limpiar() {
        filtroId.setText("");
        filtroNombre.setText("");
        controller.limpiar();
        tabla.clearSelection();
    }

    private void imprimirTabla() {
        try {
            tabla.print();
        } catch (Exception e) {
            mostrarError("No fue posible imprimir el listado.");
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