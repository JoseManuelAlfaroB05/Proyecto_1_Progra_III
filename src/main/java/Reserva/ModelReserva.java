package Reserva;
import Recursos.SolicitudRecurso;
import Recursos.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class ModelReserva {
    private User usuario;
    private String actividad;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private ArrayList<SolicitudRecurso> solicitudes;

    public ModelReserva() {
        solicitudes = new ArrayList<>();
    }

    public User getUsuario() {
        return usuario;
    }

    public void setUsuario(User usuario) {
        this.usuario = usuario;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public ArrayList<SolicitudRecurso> getSolicitudes() {
        return solicitudes;
    }

    public void setSolicitudes(ArrayList<SolicitudRecurso> solicitudes) {
        this.solicitudes = solicitudes;
    }

    public void agregarSolicitud(SolicitudRecurso solicitud) {
        solicitudes.add(solicitud);
    }

    public void eliminarSolicitud(int indice) {
        if (indice >= 0 && indice < solicitudes.size()) {
            solicitudes.remove(indice);
        }
    }

    public void limpiar() {
        actividad = "";
        fecha = null;
        horaInicio = null;
        horaFin = null;
        solicitudes.clear();
    }
}