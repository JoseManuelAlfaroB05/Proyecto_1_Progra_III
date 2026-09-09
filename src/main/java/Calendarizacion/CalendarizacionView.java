package Calendarizacion;

import Recursos.CategoriaRecurso;
import Recursos.Reserva;
import Recursos.Recurso;
import Service.GestorRecursos;
import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class CalendarizacionView extends JPanel {

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

    public CalendarizacionView() {
        setLayout(new BorderLayout());
        add(panelPrincipal, BorderLayout.CENTER);

        ControllerCalendarizacion controller =
                new ControllerCalendarizacion(this);

        GestorRecursos gestorRecursos = new GestorRecursos();

        for (Recurso recurso : gestorRecursos.getRecursos()) {

            CategoriaRecurso categoria = recurso.getRecurso();

            boolean existe = false;

            for (int i = 0; i < comboBox1.getItemCount(); i++) {

                CategoriaRecurso categoriaExistente =
                        (CategoriaRecurso) comboBox1.getItemAt(i);

                if (categoriaExistente.getVarId().equals(categoria.getVarId())) {
                    existe = true;
                    break;
                }
            }

            if (!existe) {
                comboBox1.addItem(categoria);
            }
        }

        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.buscarFechaYCategoria(
                        getFecha(),
                        getCategoria()
                );
            }
        });
    }

    public LocalDate getFecha() {
        return datePicker.getDate();
    }

    public CategoriaRecurso getCategoria() {
        return (CategoriaRecurso) comboBox1.getSelectedItem();
    }

    public void mostrarReservas(ArrayList<Reserva> reservas) {

        System.out.println("Reservas encontradas: " + reservas.size());

        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("Hora inicio");
        modelo.addColumn("Hora fin");
        modelo.addColumn("Actividad");
        modelo.addColumn("Usuario");
        modelo.addColumn("Recurso");

        for (Reserva reserva : reservas) {

            String recursos = "";

            for (Recurso recurso : reserva.getRecursos()) {
                recursos += recurso.getDescripcion() + " ";
            }

            modelo.addRow(new Object[]{
                    reserva.getHoraInicio(),
                    reserva.getHoraFin(),
                    reserva.getActividad(),
                    reserva.getUsuario().getVarId(),
                    recursos
            });
        }

        table1.setModel(modelo);
    }
}