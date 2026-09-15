package Presentar.Actividades;

import Recursos.Reserva;
import javax.swing.table.AbstractTableModel;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TableModelActividades extends AbstractTableModel {
    private final ModelActividades model;
    private final DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:00");

    public TableModelActividades(ModelActividades model) {
        this.model = model;
    }

    @Override
    public int getRowCount() {
        return 24;
    }

    @Override
    public int getColumnCount() {
        return 8;
    }

    @Override
    public String getColumnName(int column) {
        if (column == 0) {
            return "Hora";
        }
        return model.getLunes() == null ? "" : model.getLunes().plusDays(column - 1).toString();
    }

    @Override
    public Object getValueAt(int row, int column) {
        if (column == 0) {
            return formatoHora.format(LocalTime.of(row, 0));
        }
        return actividadEn(model.getReservas(), model.getLunes().plusDays(column - 1), row);
    }

    private String actividadEn(List<Reserva> reservas, java.time.LocalDate fecha, int hora) {
        for (Reserva reserva : reservas) {
            if (!fecha.equals(reserva.getFecha())
                    || !reserva.getHoraInicio().isBefore(hora == 23
                    ? LocalTime.of(23, 59, 59)
                    : LocalTime.of(hora + 1, 0))
                    || !reserva.getHoraFin().isAfter(LocalTime.of(hora, 0))) {
                continue;
            }
            String usuario = reserva.getUsuario() != null
                    && reserva.getUsuario().getVarNombre() != null
                    && !reserva.getUsuario().getVarNombre().isBlank()
                    ? reserva.getUsuario().getVarNombre()
                    : reserva.getUsuarioId();
            return reserva.getActividad() + " (" + usuario + ")";
        }
        return "";
    }
}
