package Controllers;

import Models.GestorRecursos;
import Models.GestorReservas;
import Models.User;

import java.time.LocalDate;
import java.time.LocalTime;

public class ControllerReserva {

    private GestorRecursos gestorRecursos;
    private GestorReservas gestorReservas;

    public ControllerReserva() {
        gestorReservas = new GestorReservas();
        gestorRecursos = new GestorRecursos();
    }

    public GestorReservas getGestorReservas() {
        return gestorReservas;
    }

    public GestorRecursos getGestorRecursos() {
        return gestorRecursos;
    }

    public boolean crearReserva(
            User usuario,
            String actividad,
            LocalDate fecha,
            LocalTime horaInicio,
            LocalTime horaFin,
            boolean necesitaLab,
            int cantidadLab,
            boolean necesitaPC,
            int cantidadPC,
            boolean necesitaProy,
            int cantidadProy
    ) {

        String id = "RES-" + String.format(
                "%03d",
                gestorReservas.getReservas().size() + 1
        );

        return gestorReservas.crearReserva(
                id,
                usuario,
                actividad,
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
    }
}