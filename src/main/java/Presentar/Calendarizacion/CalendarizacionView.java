package Presentar.Calendarizacion;

import Recursos.CategoriaRecurso;
import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;

public class CalendarizacionView extends JPanel implements PropertyChangeListener {

    private JPanel panelPrincipal;
    private JPanel ContentedPanel;
    private JPanel CalendarizacionPanel;
    private JPanel panelFecha;
    private DatePicker datePicker;
    private JComboBox comboBox1;
    private JPanel panelCategoria;
    private JButton buttonOK;
    private JButton buttonPDF;
    private JTable table1;
    private JScrollBar scrollBar1;
    private JPanel panelTaba;

    private ControllerCalendarizacion controller;
    private TableModelCalendarizacion tableModelCalendarizacion;

    public CalendarizacionView() {
        setLayout(new BorderLayout());
        add(panelPrincipal, BorderLayout.CENTER);

        controller = new ControllerCalendarizacion();

        controller.getModel().addPropertyChangeListener(this);

        tableModelCalendarizacion =
                new TableModelCalendarizacion(
                        controller.getModel().getReservas()
                );

        table1.setModel(tableModelCalendarizacion);

        cargarCategorias();

        buttonOK.addActionListener(e -> {
            controller.buscarFechaYCategoria(
                    getFecha(),
                    getCategoria()
            );
        });
    }

    private void cargarCategorias() {
        for (CategoriaRecurso categoria :
                controller.getGestorReservas().getGestorCategorias().getCategorias()) {

            comboBox1.addItem(categoria);
        }
    }

    public void recargarDatos() {
        controller.recargarDatos();
        comboBox1.removeAllItems();
        cargarCategorias();
    }

    public LocalDate getFecha() {
        return datePicker.getDate();
    }

    public CategoriaRecurso getCategoria() {
        return (CategoriaRecurso) comboBox1.getSelectedItem();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        if (evt.getPropertyName().equals("reservas")) {

            tableModelCalendarizacion.setReservas(
                    controller.getModel().getReservas()
            );
        }
    }
}