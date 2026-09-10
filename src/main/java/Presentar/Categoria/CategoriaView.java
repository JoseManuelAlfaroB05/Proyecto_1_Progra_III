package Presentar.Categoria;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import Recursos.CategoriaRecurso;

public class CategoriaView extends JPanel {

    private JPanel PrincipalPanel;
    private JPanel panelBusqueda;
    private JLabel labelBusqueda;
    private JTextField textFieldBusqueda;
    private JButton buttonBuscar;
    private JButton buttonPDF;

    private JPanel panelDatosCategoria;
    private JPanel panelBotonesCategoria;
    private JPanel panelCategoria;
    private JLabel labelTituloCategoria;
    private JLabel labelId;
    private JTextField textFieldId;
    private JLabel labelDescripcion;
    private JTextField textFieldDescripcion;
    private JButton buttonAceptar;
    private JButton buttonBorrar;
    private JButton buttonLimpiar;

    private JPanel panelTabla;
    private JLabel labelTabla;
    private JTable tableCategorias;
    private JScrollPane scrollTabla;

    private ControllerCategoria controller;

    public CategoriaView() {

        controller = new ControllerCategoria();

        setLayout(new BorderLayout());
        add(PrincipalPanel, BorderLayout.CENTER);

        cargarTabla();

        // Botón limpiar
        buttonLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });

        // Botón buscar
        buttonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String descripcion = textFieldBusqueda.getText().trim();

                // Si el campo está vacío, mostrar todas las categorías
                if (descripcion.isEmpty()) {

                    cargarTabla();

                    textFieldId.setText("");
                    textFieldDescripcion.setText("");

                } else {

                    CategoriaRecurso categoria =
                            controller.buscarCategoria(descripcion);

                    // Si encontró la categoría
                    if (categoria != null) {

                        textFieldId.setText(categoria.getVarId());
                        textFieldDescripcion.setText(categoria.getDescripcion());

                        mostrarCategoria(categoria);

                    } else {

                        mostrarTablaVacia();

                        textFieldId.setText("");
                        textFieldDescripcion.setText("");

                        JOptionPane.showMessageDialog(
                                CategoriaView.this,
                                "No se encontró ninguna categoría con esa descripción.",
                                "Búsqueda",
                                JOptionPane.INFORMATION_MESSAGE
                        );
                    }
                }
            }
        });
        buttonBorrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String id = textFieldId.getText().trim();

                if (id.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            CategoriaView.this,
                            "Debe seleccionar una categoría para eliminar.",
                            "Eliminar categoría",
                            JOptionPane.WARNING_MESSAGE
                    );

                    return;
                }

                int respuesta = JOptionPane.showConfirmDialog(
                        CategoriaView.this,
                        "¿Está seguro de que desea eliminar esta categoría?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION
                );

                if (respuesta == JOptionPane.YES_OPTION) {

                    boolean eliminado = controller.eliminarCategoria(id);

                    if (eliminado) {

                        JOptionPane.showMessageDialog(
                                CategoriaView.this,
                                "Categoría eliminada correctamente.",
                                "Eliminar categoría",
                                JOptionPane.INFORMATION_MESSAGE
                        );

                        limpiarCampos();

                    } else {

                        JOptionPane.showMessageDialog(
                                CategoriaView.this,
                                "No se pudo eliminar la categoría.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
            }
        });
        buttonAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = textFieldId.getText().trim();
                String descripcion = textFieldDescripcion.getText().trim();

                if (id.isEmpty() || descripcion.isEmpty()) {

                    JOptionPane.showMessageDialog(
                            CategoriaView.this,
                            "Debe completar todos los campos.",
                            "Agregar categoría",
                            JOptionPane.WARNING_MESSAGE
                    );

                    return;
                }

                boolean agregada =
                        controller.agregarCategoria(id, descripcion);

                if (agregada) {

                    JOptionPane.showMessageDialog(
                            CategoriaView.this,
                            "Categoría agregada correctamente.",
                            "Agregar categoría",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    limpiarCampos();

                } else {

                    JOptionPane.showMessageDialog(
                            CategoriaView.this,
                            "No se pudo agregar la categoría.\n" +
                                    "Verifique que el ID no esté repetido.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }

            }
        });
    }

    private void limpiarCampos() {

        textFieldBusqueda.setText("");
        textFieldId.setText("");
        textFieldDescripcion.setText("");

        tableCategorias.clearSelection();

        // Mostrar nuevamente todas las categorías
        cargarTabla();
    }

    private void cargarTabla() {

        String[] columnas = {"ID", "Descripción"};

        DefaultTableModel modelo =
                new DefaultTableModel(columnas, 0);

        for (CategoriaRecurso categoria :
                controller.getGestorCategorias().getCategorias()) {

            Object[] fila = {
                    categoria.getVarId(),
                    categoria.getDescripcion()
            };

            modelo.addRow(fila);
        }

        tableCategorias.setModel(modelo);
    }

    private void mostrarCategoria(CategoriaRecurso categoria) {

        String[] columnas = {"ID", "Descripción"};

        DefaultTableModel modelo =
                new DefaultTableModel(columnas, 0);

        Object[] fila = {
                categoria.getVarId(),
                categoria.getDescripcion()
        };

        modelo.addRow(fila);

        tableCategorias.setModel(modelo);
    }

    private void mostrarTablaVacia() {

        String[] columnas = {"ID", "Descripción"};

        DefaultTableModel modelo =
                new DefaultTableModel(columnas, 0);

        tableCategorias.setModel(modelo);
    }
}