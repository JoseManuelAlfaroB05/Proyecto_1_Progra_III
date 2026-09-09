package Reserva;


import Recursos.SolicitudRecurso;
import Recursos.User;
import Service.GestorCategorias;
import Service.GestorRecursos;
import Service.GestorReservas;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class ControllerReserva {
    private GestorReservas gestorReservas;
    private ModelReserva model;

    public ControllerReserva() {
        gestorReservas = new GestorReservas();
        model = new ModelReserva();
    }

    public GestorReservas getGestorReservas() {
        return gestorReservas;
    }

    public GestorRecursos getGestorRecursos() {
        return gestorReservas.getGestorRecursos();
    }

    public GestorCategorias getGestorCategorias() {
        return gestorReservas.getGestorCategorias();
    }

    public ModelReserva getModel() {
        return model;
    }

    public void agregarSolicitud(SolicitudRecurso solicitud) {
        model.agregarSolicitud(solicitud);
    }

    public void eliminarSolicitud(int indice) {
        model.eliminarSolicitud(indice);
    }

    public boolean crearReserva(
            User usuario,
            String actividad,
            LocalDate fecha,
            LocalTime horaInicio,
            LocalTime horaFin,
            ArrayList<SolicitudRecurso> solicitudes) {

        model.setUsuario(usuario);
        model.setActividad(actividad);
        model.setFecha(fecha);
        model.setHoraInicio(horaInicio);
        model.setHoraFin(horaFin);
        model.setSolicitudes(new ArrayList<>(solicitudes));

        String id =
                "RES-" +
                        String.format(
                                "%03d",
                                gestorReservas.getReservas().size() + 1
                        );

        return gestorReservas.crearReserva(
                id,
                model.getUsuario(),
                model.getActividad(),
                model.getFecha(),
                model.getHoraInicio(),
                model.getHoraFin(),
                model.getSolicitudes()
        );
    }

    public void limpiarModel() {
        model.limpiar();
    }
}