package Presentar.Calendarizacion;

import Recursos.CategoriaRecurso;
import Recursos.Reserva;
import java.time.LocalDate;
import java.util.ArrayList;

public class ModelCalendarizacion {
    private LocalDate fecha;
    private CategoriaRecurso categoria;
    private ArrayList<Reserva> reservas;

    public ModelCalendarizacion() {
        reservas = new ArrayList<>();
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public CategoriaRecurso getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaRecurso categoria) {
        this.categoria = categoria;
    }

    public ArrayList<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(ArrayList<Reserva> reservas) {
        this.reservas = reservas;
    }
}