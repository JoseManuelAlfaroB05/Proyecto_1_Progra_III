package Views.Reservas;

import Controllers.ControllerReserva;
import Models.CategoriaRecurso;
import Models.Reserva;
import Models.SolicitudRecurso;
import Models.User;
import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.TimePicker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

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

    private JPanel panelSeleccionCategoria;
    private JLabel labelCategoria;
    private JComboBox<CategoriaRecurso> comboBoxCategoria;
    private JLabel labelCantidad;
    private JSpinner spinnerCantidad;
    private JButton buttonAgregarRecurso;

    private JPanel panelRecursosSeleccionados;
    private JLabel labelRecursosSeleccionados;
    private JList<String> listRecursos;
    private JScrollPane scrollRecursos;
    private JButton buttonEliminarRecurso;

    private JPanel panelTable;
    private JTable tableReseravas;
    private JScrollPane scrollTabla;

    private JLabel labelHistorial;

    private JButton buttonAceptar;
    private JButton buttonRechazar;

    private User usuarioLogueado;
    private ControllerReserva controller;

    private ArrayList<SolicitudRecurso> solicitudes;
    private DefaultListModel<String> modeloRecursos;

    public ReservaView(User usuarioLogueado) {

        this.usuarioLogueado = usuarioLogueado;
        this.controller = new ControllerReserva();

        this.solicitudes = new ArrayList<>();
        this.modeloRecursos = new DefaultListModel<>();

        setLayout(new BorderLayout());

        add(
                PrincipalPanel,
                BorderLayout.CENTER
        );

        contetPanel.setBackground(
                new Color(217, 217, 227)
        );

        inicializarCategorias();

        configurarCantidad();

        cargarTabla();

        buttonAgregarRecurso.addActionListener(
                e -> agregarRecurso()
        );

        buttonEliminarRecurso.addActionListener(
                e -> eliminarRecurso()
        );

        buttonRechazar.addActionListener(
                e -> limpiarCampos()
        );

        buttonAceptar.addActionListener(e -> {

            String actividad =
                    textFieldActividad
                            .getText()
                            .trim();

            LocalDate fecha =
                    datePicker.getDate();

            LocalTime horaInicio =
                    timePickerInicio.getTime();

            LocalTime horaFin =
                    timePickerFin.getTime();

            if (actividad.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Ingrese la actividad.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            if (fecha == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Seleccione una fecha.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            if (horaInicio == null ||
                    horaFin == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Seleccione la hora de inicio y la hora final.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            if (!horaInicio.isBefore(horaFin)) {

                JOptionPane.showMessageDialog(
                        this,
                        "La hora final debe ser posterior a la hora inicial.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            if (solicitudes.isEmpty()) {

                JOptionPane.showMessageDialog(
                        this,
                        "Seleccione al menos una categoría de recurso.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            boolean resultado =
                    controller.crearReserva(
                            usuarioLogueado,
                            actividad,
                            fecha,
                            horaInicio,
                            horaFin,
                            new ArrayList<>(solicitudes)
                    );

            if (resultado) {

                cargarTabla();

                limpiarCampos();

                JOptionPane.showMessageDialog(
                        this,
                        "Reserva creada correctamente.",
                        "Reserva",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });
    }

    private void inicializarCategorias() {

        comboBoxCategoria.removeAllItems();

        for (CategoriaRecurso categoria :
                controller
                        .getGestorCategorias()
                        .getCategorias()) {

            comboBoxCategoria.addItem(
                    categoria
            );
        }
    }

    private void configurarCantidad() {

        SpinnerNumberModel modelo =
                new SpinnerNumberModel(
                        1,
                        1,
                        100,
                        1
                );

        spinnerCantidad.setModel(
                modelo
        );
    }

    private void agregarRecurso() {

        CategoriaRecurso categoria =
                (CategoriaRecurso)
                        comboBoxCategoria
                                .getSelectedItem();

        if (categoria == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione una categoría.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        int cantidad =
                (Integer)
                        spinnerCantidad
                                .getValue();

        for (SolicitudRecurso solicitud :
                solicitudes) {

            if (solicitud
                    .getCategoria()
                    .getVarId()
                    .equals(
                            categoria.getVarId()
                    )) {

                JOptionPane.showMessageDialog(
                        this,
                        "La categoría ya fue agregada.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
            }
        }

        SolicitudRecurso solicitud =
                new SolicitudRecurso(
                        categoria,
                        cantidad
                );

        solicitudes.add(
                solicitud
        );

        modeloRecursos.addElement(
                categoria.getDescripcion()
                        + " x"
                        + cantidad
        );

        listRecursos.setModel(
                modeloRecursos
        );

        spinnerCantidad.setValue(1);
    }

    private void eliminarRecurso() {

        int indice =
                listRecursos
                        .getSelectedIndex();

        if (indice == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un recurso de la lista.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        solicitudes.remove(
                indice
        );

        modeloRecursos.remove(
                indice
        );
    }

    private void limpiarCampos() {

        textFieldReservaAutomatica.setText("");

        textFieldActividad.setText("");

        datePicker.setDate(null);

        timePickerInicio.setTime(null);

        timePickerFin.setTime(null);

        solicitudes.clear();

        modeloRecursos.clear();

        listRecursos.setModel(
                modeloRecursos
        );

        spinnerCantidad.setValue(1);

        if (comboBoxCategoria.getItemCount() > 0) {

            comboBoxCategoria.setSelectedIndex(0);
        }
    }

    private void cargarTabla() {

        String[] columnas = {
                "ID",
                "Actividad",
                "Fecha",
                "Hora Inicio",
                "Hora Fin"
        };

        DefaultTableModel modelo =
                new DefaultTableModel(
                        columnas,
                        0
                );

        for (Reserva reserva :
                controller
                        .getGestorReservas()
                        .getReservas()) {

            Object[] fila = {
                    reserva.getId(),
                    reserva.getActividad(),
                    reserva.getFecha(),
                    reserva.getHoraInicio(),
                    reserva.getHoraFin()
            };

            modelo.addRow(
                    fila
            );
        }

        tableReseravas.setModel(
                modelo
        );
    }
}