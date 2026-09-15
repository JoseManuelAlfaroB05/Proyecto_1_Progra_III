package Presentar.Calendarizacion;

import Recursos.CategoriaRecurso;
import Recursos.Reserva;
import Service.GestorReservas;

import java.time.LocalDate;
import java.util.ArrayList;

public class ControllerCalendarizacion {
    private GestorReservas gestorReservas;
    private ModelCalendarizacion model;

    public ControllerCalendarizacion() {
        gestorReservas = new GestorReservas();
        model = new ModelCalendarizacion();
    }

    public GestorReservas getGestorReservas() {
        return gestorReservas;
    }

    public ModelCalendarizacion getModel() {
        return model;
    }

    public void buscarFechaYCategoria(
            LocalDate fecha,
            CategoriaRecurso categoriaRecurso) {

        model.setFecha(fecha);
        model.setCategoria(categoriaRecurso);

        gestorReservas.recargarReservas();

        ArrayList<Reserva> reservas =
                gestorReservas.buscarReservasPorFechaYCategoria(
                        model.getFecha(),
                        model.getCategoria()
                );

        model.setReservas(reservas);
    }
}