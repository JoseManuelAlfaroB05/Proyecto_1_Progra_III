package Presentar.Reserva;

import Recursos.Reserva;

import javax.swing.table.AbstractTableModel;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class TableModelReserva extends AbstractTableModel {
    private ArrayList<Reserva> reservas;

    private final String[] columnas = {
            "ID",
            "Actividad",
            "Fecha",
            "Hora inicio",
            "Hora fin",
            "Recursos"
    };

    public TableModelReserva(ArrayList<Reserva> reservas) {
        this.reservas = reservas;
    }

    @Override
    public int getRowCount() {
        return reservas.size();
    }

    @Override
    public int getColumnCount() {
        return columnas.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnas[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Reserva reserva = reservas.get(rowIndex);

        switch (columnIndex) {
            case 0:
                return reserva.getId();
            case 1:
                return reserva.getActividad();
            case 2:
                return reserva.getFecha() != null
                        ? reserva.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                        : "";
            case 3:
                return reserva.getHoraInicio() != null
                        ? reserva.getHoraInicio().format(DateTimeFormatter.ofPattern("HH:mm"))
                        : "";
            case 4:
                return reserva.getHoraFin() != null
                        ? reserva.getHoraFin().format(DateTimeFormatter.ofPattern("HH:mm"))
                        : "";
            case 5:
                return reserva.getRecursos() != null
                        ? reserva.getRecursos().size()
                        : 0;
            default:
                return null;
        }
    }

    public void setReservas(ArrayList<Reserva> reservas) {
        this.reservas = reservas;
        fireTableDataChanged();
    }

    public Reserva getReserva(int rowIndex) {
        return reservas.get(rowIndex);
    }
}