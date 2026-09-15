package Presentar.Calendarizacion;

import Recursos.Recurso;
import Recursos.Reserva;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class TableModelCalendarizacion extends AbstractTableModel {
    private ArrayList<Reserva> reservas;

    private final String[] columnas = {
            "Hora inicio",
            "Hora fin",
            "Actividad",
            "Usuario",
            "Recurso"
    };

    public TableModelCalendarizacion(ArrayList<Reserva> reservas) {
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
                return reserva.getHoraInicio();

            case 1:
                return reserva.getHoraFin();

            case 2:
                return reserva.getActividad();

            case 3:
                return reserva.getUsuario() != null
                        ? reserva.getUsuario().getVarId()
                        : "";

            case 4:
                return obtenerRecursos(reserva);

            default:
                return null;
        }
    }

    private String obtenerRecursos(Reserva reserva) {
        if (reserva.getRecursos() == null) {
            return "";
        }

        String recursos = "";

        for (Recurso recurso : reserva.getRecursos()) {
            recursos += recurso.getDescripcion() + " ";
        }

        return recursos.trim();
    }

    public void setReservas(ArrayList<Reserva> reservas) {
        this.reservas = reservas;
        fireTableDataChanged();
    }

    public Reserva getReserva(int rowIndex) {
        return reservas.get(rowIndex);
    }
}