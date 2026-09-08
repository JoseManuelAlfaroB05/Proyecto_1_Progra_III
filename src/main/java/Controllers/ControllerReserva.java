package Controllers;

import Models.SolicitudRecurso;
import Models.User;
import Service.GestorCategorias;
import Service.GestorRecursos;
import Service.GestorReservas;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class ControllerReserva {

    private GestorReservas gestorReservas;

    public ControllerReserva() {

        gestorReservas =
                new GestorReservas();
    }

    public GestorReservas getGestorReservas() {
        return gestorReservas;
    }

    public GestorRecursos getGestorRecursos() {

        return gestorReservas
                .getGestorRecursos();
    }

    public GestorCategorias getGestorCategorias() {

        return gestorReservas
                .getGestorCategorias();
    }

    public boolean crearReserva(
            User usuario,
            String actividad,
            LocalDate fecha,
            LocalTime horaInicio,
            LocalTime horaFin,
            ArrayList<SolicitudRecurso> solicitudes) {

        String id =
                "RES-" +
                        String.format(
                                "%03d",
                                gestorReservas
                                        .getReservas()
                                        .size() + 1
                        );

        return gestorReservas.crearReserva(
                id,
                usuario,
                actividad,
                fecha,
                horaInicio,
                horaFin,
                solicitudes
        );
    }
}