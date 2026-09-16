package Presentar.Recursos;

import Recursos.CategoriaRecurso;
import Recursos.Recurso;
import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class RecursosView extends JPanel implements PropertyChangeListener {
    private final ControllerRecurso controller = new ControllerRecurso();
    private final ModelRecurso model = controller.getModel();
    private JComboBox<CategoriaRecurso> filtroCategoria = new JComboBox<>();
    private JTextField filtroDescripcion = new JTextField(20);
    private JTextField campoId = new JTextField(24);
    private JComboBox<CategoriaRecurso> campoCategoria = new JComboBox<>();
    private JTextField campoDescripcion = new JTextField(24);
    private JTable tabla = new JTable();

    public RecursosView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(16, 24, 16, 24));
        model.addPropertyChangeListener(this);
        cargarCategorias();
        add(crearFiltro(), BorderLayout.NORTH);
        add(crearFormulario(), BorderLayout.CENTER);
        add(crearListado(), BorderLayout.SOUTH);
    }

    private void cargarCategorias() {
        filtroCategoria.addItem(null);

        for (CategoriaRecurso categoria : controller.getGestorCategorias().getCategorias()) {
            filtroCategoria.addItem(categoria);
            campoCategoria.addItem(categoria);
        }

        filtroCategoria.setRenderer(new CategoriaRenderer("Todas las categorías"));
        campoCategoria.setRenderer(new CategoriaRenderer("Seleccione una categoría"));
    }

    public void recargarDatos() {
        controller.recargarCategorias();

        filtroCategoria.removeAllItems();
        campoCategoria.removeAllItems();

        cargarCategorias();
    }

    private JPanel crearFiltro() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        panel.setBorder(BorderFactory.createTitledBorder("Filtro"));
        panel.add(new JLabel("Categoría"));
        panel.add(filtroCategoria);
        panel.add(new JLabel("Descripción"));
        panel.add(filtroDescripcion);

        JButton buscar = new JButton("Buscar");
        buscar.addActionListener(e ->
                controller.cargarLista(
                        categoriaId(filtroCategoria),
                        filtroDescripcion.getText()
                )
        );
        panel.add(buscar);

        JButton imprimir = new JButton("Imprimir");
        imprimir.addActionListener(e -> imprimirTabla());
        panel.add(imprimir);

        return panel;
    }

    private JPanel crearFormulario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Recurso"));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(7, 10, 7, 10);
        c.anchor = GridBagConstraints.WEST;

        agregarCampo(panel, c, 0, "ID", campoId);
        agregarCampo(panel, c, 1, "Categoría", campoCategoria);
        agregarCampo(panel, c, 2, "Descripción", campoDescripcion);

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));

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
                controller.seleccionar(
                        String.valueOf(
                                tabla.getValueAt(
                                        tabla.getSelectedRow(),
                                        0
                                )
                        )
                );
            }
        });

        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);
        panel.setPreferredSize(new Dimension(0, 235));

        return panel;
    }

    private void guardar() {
        String id = campoId.getText().trim();
        String descripcion = campoDescripcion.getText().trim();

        CategoriaRecurso categoria =
                (CategoriaRecurso) campoCategoria.getSelectedItem();

        if (id.isEmpty() || descripcion.isEmpty() || categoria == null) {
            mostrarError("Complete el ID, la categoría y la descripción.");
            return;
        }

        boolean existe = model.getList()
                .stream()
                .anyMatch(r -> r.getId().equalsIgnoreCase(id));

        boolean resultado = existe
                ? controller.actualizar(id, categoria, descripcion)
                : controller.guardar(id, categoria, descripcion);

        if (!resultado) {
            mostrarError(
                    "No fue posible guardar el recurso. Verifique que el ID sea único."
            );
            return;
        }

        limpiar();
    }

    private void borrar() {
        String id = campoId.getText().trim();

        if (id.isEmpty() || !controller.eliminar(id)) {
            mostrarError("Seleccione un recurso válido para borrar.");
            return;
        }

        limpiar();
    }

    private void limpiar() {
        filtroCategoria.setSelectedIndex(0);
        filtroDescripcion.setText("");
        controller.limpiar();
        tabla.clearSelection();
    }

    private String categoriaId(JComboBox<CategoriaRecurso> combo) {
        CategoriaRecurso categoria =
                (CategoriaRecurso) combo.getSelectedItem();

        return categoria == null
                ? ""
                : categoria.getVarId();
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
                "Recursos",
                JOptionPane.ERROR_MESSAGE
        );
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (ModelRecurso.LIST.equals(evt.getPropertyName())) {

            tabla.setModel(
                    new TableModelRecurso(
                            new int[]{
                                    TableModelRecurso.ID,
                                    TableModelRecurso.CATEGORIA,
                                    TableModelRecurso.DESCRIPCION
                            },
                            model.getList()
                    )
            );

        } else if (ModelRecurso.CURRENT.equals(evt.getPropertyName())) {

            Recurso recurso = model.getCurrent();

            campoId.setText(
                    recurso.getId() == null
                            ? ""
                            : recurso.getId()
            );

            campoDescripcion.setText(
                    recurso.getDescripcion() == null
                            ? ""
                            : recurso.getDescripcion()
            );

            if (recurso.getRecurso() != null) {
                campoCategoria.setSelectedItem(
                        recurso.getRecurso()
                );
            } else {
                campoCategoria.setSelectedItem(null);
            }
        }
    }

    private static class CategoriaRenderer
            extends DefaultListCellRenderer {

        private final String emptyText;

        private CategoriaRenderer(String emptyText) {
            this.emptyText = emptyText;
        }

        @Override
        public Component getListCellRendererComponent(
                JList<?> list,
                Object value,
                int index,
                boolean selected,
                boolean focused) {

            super.getListCellRendererComponent(
                    list,
                    value,
                    index,
                    selected,
                    focused
            );

            setText(
                    value == null
                            ? emptyText
                            : value.toString()
            );

            return this;
        }
    }
}