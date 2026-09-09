package Calendarizacion;

import Recursos.CategoriaRecurso;
import Recursos.Reserva;
import Service.GestorReservas;

import java.time.LocalDate;
import java.util.ArrayList;

public class ControllerCalendarizacion {
    private GestorReservas gestorReservas;
    private CalendarizacionView view;
    private ModelCalendarizacion model;

    public ControllerCalendarizacion(CalendarizacionView view) {
        this.view = view;
        gestorReservas = new GestorReservas();
        model = new ModelCalendarizacion();
    }

    public void buscarFechaYCategoria(
            LocalDate fecha,
            CategoriaRecurso categoriaRecurso) {

        model.setFecha(fecha);
        model.setCategoria(categoriaRecurso);

        ArrayList<Reserva> reservas =
                gestorReservas.buscarReservasPorFechaYCategoria(
                        model.getFecha(),
                        model.getCategoria()
                );

        model.setReservas(reservas);

        view.mostrarReservas(model.getReservas());
    }
}