package Controllers;

import Models.CategoriaRecurso;
import Models.Reserva;
import Service.GestorReservas;
import Views.Calendarizacion.CalendarizacionView;

import java.time.LocalDate;
import java.util.ArrayList;

public class ControllerCalendarizacion {

    private GestorReservas gestorReservas;
    private CalendarizacionView view;

    public ControllerCalendarizacion(CalendarizacionView view) {
        this.view = view;
        gestorReservas = new GestorReservas();
    }

    public void buscarFechaYCategoria(
            LocalDate fecha,
            CategoriaRecurso categoriaRecurso) {

        ArrayList<Reserva> reservas =
                gestorReservas.buscarReservasPorFechaYCategoria(
                        fecha,
                        categoriaRecurso
                );

        view.mostrarReservas(reservas);
    }
}