package Recursos;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Reserva {

    private String id;
    private User usuario;
    private String actividad;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private ArrayList<Recurso> recursos;

    public Reserva(String id,
                   User usuario,
                   String actividad,
                   LocalDate fecha,
                   LocalTime horaInicio,
                   LocalTime horaFin,
                   ArrayList<Recurso> recursos) {

        this.id = id;
        this.usuario = usuario;
        this.actividad = actividad;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.recursos = recursos;
    }

    // Getters

    public String getId() {
        return id;
    }

    public User getUsuario() {
        return usuario;
    }

    public String getActividad() {
        return actividad;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public ArrayList<Recurso> getRecursos() {
        return recursos;
    }

    // Setters

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public void setRecursos(ArrayList<Recurso> recursos) {
        this.recursos = recursos;
    }
}