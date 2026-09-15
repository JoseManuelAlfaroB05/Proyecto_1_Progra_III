package Presentar.Actividades;

import Recursos.Reserva;
import Service.GestorReservas;
import com.github.lgooddatepicker.components.DatePicker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public class ActividadesView extends JPanel {
    private final DatePicker fechaReferencia = new DatePicker();
    private final JTable tabla = new JTable();
    private final GestorReservas gestor = new GestorReservas();
    private final DateTimeFormatter horaFormato = DateTimeFormatter.ofPattern("HH:00");

    public ActividadesView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(16, 24, 16, 24));
        fechaReferencia.setDate(LocalDate.now());
        add(crearFiltro(), BorderLayout.NORTH);
        add(crearTabla(), BorderLayout.CENTER);
        cargarSemana();
    }

    private JPanel crearFiltro() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));
        panel.setBorder(BorderFactory.createTitledBorder("Semana"));
        panel.add(new JLabel("Fecha de referencia"));
        panel.add(fechaReferencia);
        JButton cargar = new JButton("Cargar");
        cargar.addActionListener(e -> cargarSemana());
        panel.add(cargar);
        JButton imprimir = new JButton("Imprimir");
        imprimir.addActionListener(e -> imprimir());
        panel.add(imprimir);
        return panel;
    }

    private JScrollPane crearTabla() {
        tabla.setRowHeight(54);
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tabla.setGridColor(new Color(180, 180, 180));
        return new JScrollPane(tabla);
    }

    private void cargarSemana() {
        LocalDate referencia = fechaReferencia.getDate();
        if (referencia == null) {
            return;
        }
        LocalDate lunes = referencia.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        String[] columnas = new String[8];
        columnas[0] = "Hora";
        for (int i = 0; i < 7; i++) {
            columnas[i + 1] = lunes.plusDays(i).toString();
        }

        Object[][] datos = new Object[24][8];
        gestor.recargarReservas();
        List<Reserva> reservas = gestor.getReservas();
        for (int hora = 0; hora < 24; hora++) {
            datos[hora][0] = horaFormato.format(LocalTime.of(hora, 0));
            for (int dia = 0; dia < 7; dia++) {
                datos[hora][dia + 1] = actividadEn(reservas, lunes.plusDays(dia), hora);
            }
        }

        tabla.setModel(new DefaultTableModel(datos, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        tabla.getColumnModel().getColumn(0).setPreferredWidth(70);
        for (int i = 1; i < 8; i++) {
            tabla.getColumnModel().getColumn(i).setPreferredWidth(145);
        }
    }

    private String actividadEn(List<Reserva> reservas, LocalDate fecha, int hora) {
        for (Reserva reserva : reservas) {
            if (!fecha.equals(reserva.getFecha())
                    || !reserva.getHoraInicio().isBefore(hora == 23
                    ? LocalTime.of(23, 59, 59)
                    : LocalTime.of(hora + 1, 0))
                    || !reserva.getHoraFin().isAfter(LocalTime.of(hora, 0))) {
                continue;
            }
            String usuario = reserva.getUsuario() != null && reserva.getUsuario().getVarNombre() != null
                    && !reserva.getUsuario().getVarNombre().isBlank()
                    ? reserva.getUsuario().getVarNombre()
                    : reserva.getUsuarioId();
            return reserva.getActividad() + " (" + usuario + ")";
        }
        return "";
    }

    private void imprimir() {
        try {
            tabla.print();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "No fue posible imprimir las actividades.",
                    "Actividades", JOptionPane.ERROR_MESSAGE);
        }
    }
}
