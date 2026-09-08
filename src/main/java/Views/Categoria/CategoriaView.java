package Views.Categoria;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Controllers.ControllerCategoria;

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

        this.controller = new ControllerCategoria();

        setLayout(new BorderLayout());
        add(PrincipalPanel, BorderLayout.CENTER);

        buttonLimpiar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                limpiarCampos();
            }
        });

        buttonAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = textFieldId.getText().trim();
                String descripcion = textFieldDescripcion.getText();

                Boolean resultado = controller.agregarCategoria(id, descripcion);
                if (resultado) {
                    JOptionPane.showMessageDialog(
                    CategoriaView.this, "Categoría creada correctamente.","Categoría", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos();
                }else{
                    JOptionPane.showMessageDialog(CategoriaView.this, "Error al agregar categoria.","Error", JOptionPane.ERROR_MESSAGE);
                };
            }
        });
    }

    private void limpiarCampos() {
        textFieldBusqueda.setText("");
        textFieldId.setText("");
        textFieldDescripcion.setText("");
        tableCategorias.clearSelection();
    }
}