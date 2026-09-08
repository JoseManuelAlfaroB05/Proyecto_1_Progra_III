package Views.Reservas;

import Controllers.ControllerReserva;
import Models.Reserva;
import Models.User;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class ReservaView extends JPanel {

    private JPanel PrincipalPanel;
    private JPanel contetPanel;
    private JPanel ReservaPanel;
    private JPanel JPanelReservaAutomatica;
    private JLabel tabbReservaAutomatica;
    private JTextField textFieldReservaAutomatica;
    private JButton buttonReservaAuntomatica;
    private JPanel JPanelReservaManual;
    private JPanel panelActividad;
    private JLabel labelActividad;
    private JTextField textFieldActividad;
    private JPanel jpanelTiempo;
    private JPanel jpanelFecha;
    private JLabel labelFecha;
    private DatePicker datePicker;
    private JPanel jpanelHoraI;
    private JLabel labelHoraI;
    private TimePicker timePickerInicio;
    private JPanel jpanelHoraF;
    private JLabel labelhoraf;
    private TimePicker timePickerFin;
    private JPanel panelRecursos;
    private JPanel panelLabs;
    private JTextField textFieldLabCant;
    private JCheckBox laboratorioCheckBox;
    private JPanel PanelPC;
    private JTextField textFieldCompCant;
    private JCheckBox computadorasCheckBox;
    private JPanel panelProyector;
    private JTextField textFieldProyCant;
    private JCheckBox proyectoresCheckBox;
    private JPanel panelTable;
    private JTable tableReseravas;
    private JScrollBar scrollBar1;
    private JButton buttonAceptar;
    private JButton buttonRechazar;

    private User usuarioLogueado;
    private ControllerReserva controller;

    public ReservaView(User usuarioLogueado) {
        this.usuarioLogueado = usuarioLogueado;
        this.controller = new ControllerReserva();

        setLayout(new BorderLayout());
        add(PrincipalPanel, BorderLayout.CENTER);

        cargarTabla();

        buttonRechazar.addActionListener(e -> {
            limpiarCampos();
        });

        buttonAceptar.addActionListener(e -> {
            String varActividad = textFieldActividad.getText();

            LocalDate fecha = datePicker.getDate();
            LocalTime horaInicio = timePickerInicio.getTime();
            LocalTime horaFin = timePickerFin.getTime();

            boolean necesitaLab = laboratorioCheckBox.isSelected();
            boolean necesitaPC = computadorasCheckBox.isSelected();
            boolean necesitaProy = proyectoresCheckBox.isSelected();

            int cantidadLab = 0;
            int cantidadPC = 0;
            int cantidadProy = 0;

            if (necesitaLab) {
                cantidadLab = obtenerCantidad(textFieldLabCant, "laboratorios");

                if (cantidadLab == -1) {
                    return;
                }
            }

            if (necesitaPC) {
                cantidadPC = obtenerCantidad(textFieldCompCant, "computadoras");

                if (cantidadPC == -1) {
                    return;
                }
            }

            if (necesitaProy) {
                cantidadProy = obtenerCantidad(textFieldProyCant, "proyectores");

                if (cantidadProy == -1) {
                    return;
                }
            }

            boolean resultado = controller.crearReserva(
                    usuarioLogueado,
                    varActividad,
                    fecha,
                    horaInicio,
                    horaFin,
                    necesitaLab,
                    cantidadLab,
                    necesitaPC,
                    cantidadPC,
                    necesitaProy,
                    cantidadProy
            );

            if (resultado) {
                cargarTabla();
                limpiarCampos();
            }
        });
    }

    private int obtenerCantidad(JTextField campo, String nombreRecurso) {
        String texto = campo.getText().trim();

        if (texto.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Ingrese la cantidad de " + nombreRecurso + ".",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return -1;
        }

        try {
            int cantidad = Integer.parseInt(texto);

            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(
                        this,
                        "La cantidad de " + nombreRecurso + " debe ser mayor que 0.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return -1;
            }
            return cantidad;

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "La cantidad de " + nombreRecurso + " debe ser un número entero.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            return -1;
        }
    }
    private void limpiarCampos() {
        textFieldReservaAutomatica.setText("");
        textFieldActividad.setText("");
        textFieldLabCant.setText("");
        textFieldProyCant.setText("");
        textFieldCompCant.setText("");

        datePicker.setDate(null);
        timePickerInicio.setTime(null);
        timePickerFin.setTime(null);

        laboratorioCheckBox.setSelected(false);
        computadorasCheckBox.setSelected(false);
        proyectoresCheckBox.setSelected(false);
    }

    private void cargarTabla() {
        String[] columnas = {
                "ID",
                "Actividad",
                "Fecha",
                "Hora Inicio",
                "Hora Fin"
        };

        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        for (Reserva reserva : controller.getGestorReservas().getReservas()) {
            Object[] fila = {
                    reserva.getId(),
                    reserva.getActividad(),
                    reserva.getFecha(),
                    reserva.getHoraInicio(),
                    reserva.getHoraFin()
            };
            modelo.addRow(fila);
        }
        tableReseravas.setModel(modelo);
    }
}