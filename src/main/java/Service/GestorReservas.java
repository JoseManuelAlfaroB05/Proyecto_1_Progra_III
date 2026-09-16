package Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import Recursos.CategoriaRecurso;
import Recursos.Recurso;
import Recursos.Reserva;
import Recursos.SolicitudRecurso;
import Recursos.User;

public class GestorReservas {
    private ArrayList<Reserva> reservas;
    private GestorRecursos gestorRecursos;
    private GestorCategorias gestorCategorias;
    private ReservaXMLDao reservaXMLDao;
    private String mensajeError;

    public GestorReservas() {
        reservaXMLDao = new ReservaXMLDao();
        gestorCategorias = new GestorCategorias();
        gestorRecursos = new GestorRecursos();
        reservas = reservaXMLDao.cargar();
        mensajeError = "";
    }

    public ArrayList<Reserva> getReservas() {
        return reservas;
    }

    public GestorRecursos getGestorRecursos() {
        return gestorRecursos;
    }

    public GestorCategorias getGestorCategorias() {
        return gestorCategorias;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void recargarReservas() {
        reservas = reservaXMLDao.cargar();
    }

    public void agregarReserva(Reserva reserva) {
        reservas.add(reserva);
        reservaXMLDao.guardar(reserva);
    }

    public boolean eliminarReserva(String idReserva) {
        for (int i = 0; i < reservas.size(); i++) {
            if (reservas.get(i)
                    .getId()
                    .equals(idReserva)) {
                reservas.remove(i);
                reservaXMLDao.eliminar(idReserva);
                return true;
            }
        }
        return false;
    }

    public Reserva buscarPorId(String idReserva) {
        for (Reserva reserva : reservas) {
            if (reserva.getId().equals(idReserva)) {
                return reserva;
            }
        }
        return null;
    }

    public boolean crearReserva(
            String id,
            User usuario,
            String actividad,
            LocalDate fecha,
            LocalTime horaInicio,
            LocalTime horaFin,
            ArrayList<SolicitudRecurso> solicitudes) {

        mensajeError = "";

        if (!validarReserva(
                actividad,
                fecha,
                horaInicio,
                horaFin,
                solicitudes)) {
            return false;
        }

        ArrayList<Recurso> recursosAsignados = new ArrayList<>();

        for (SolicitudRecurso solicitud : solicitudes) {
            CategoriaRecurso categoria = solicitud.getCategoria();
            int cantidad = solicitud.getCantidad();

            ArrayList<Recurso> disponibles =
                    obtenerRecursosDisponibles(
                            categoria,
                            fecha,
                            horaInicio,
                            horaFin
                    );

            if (cantidad > disponibles.size()) {
                mensajeError =
                        "No hay suficientes recursos disponibles de la categoría: "
                                + categoria.getDescripcion()
                                + ".\n"
                                + "Solicitados: " + cantidad
                                + "\n"
                                + "Disponibles: " + disponibles.size();
                return false;
            }

            for (int i = 0; i < cantidad; i++) {
                recursosAsignados.add(disponibles.get(i));
            }
        }

        Reserva nuevaReserva =
                new Reserva(
                        id,
                        usuario,
                        actividad,
                        fecha,
                        horaInicio,
                        horaFin,
                        recursosAsignados
                );

        reservas.add(nuevaReserva);
        reservaXMLDao.guardar(nuevaReserva);

        return true;
    }

    private ArrayList<Recurso> obtenerRecursosDisponibles(
            CategoriaRecurso categoria,
            LocalDate fecha,
            LocalTime horaInicio,
            LocalTime horaFin) {

        ArrayList<Recurso> recursosDisponibles =
                gestorRecursos.obtenerPorCategoria(categoria);

        for (Reserva reserva : reservas) {
            if (!reserva.getFecha().equals(fecha)) {
                continue;
            }

            if (!hayCruceDeHorario(
                    horaInicio,
                    horaFin,
                    reserva.getHoraInicio(),
                    reserva.getHoraFin())) {
                continue;
            }

            for (Recurso recursoReservado : reserva.getRecursos()) {
                for (int i = 0; i < recursosDisponibles.size(); i++) {
                    if (recursosDisponibles.get(i)
                            .getId()
                            .equals(recursoReservado.getId())) {
                        recursosDisponibles.remove(i);
                        break;
                    }
                }
            }
        }

        return recursosDisponibles;
    }

    private boolean hayCruceDeHorario(
            LocalTime horaInicioNueva,
            LocalTime horaFinNueva,
            LocalTime horaInicioExistente,
            LocalTime horaFinExistente) {

        return horaInicioNueva.isBefore(horaFinExistente)
                && horaFinNueva.isAfter(horaInicioExistente);
    }

    private boolean validarReserva(
            String actividad,
            LocalDate fecha,
            LocalTime horaInicio,
            LocalTime horaFin,
            ArrayList<SolicitudRecurso> solicitudes) {

        if (actividad == null ||
                actividad.trim().isEmpty()) {
            mensajeError = "La actividad es obligatoria.";
            return false;
        }

        if (fecha == null) {
            mensajeError = "La fecha es obligatoria.";
            return false;
        }

        if (horaInicio == null ||
                horaFin == null) {
            mensajeError = "Las horas son obligatorias.";
            return false;
        }

        if (!horaInicio.isBefore(horaFin)) {
            mensajeError =
                    "La hora final debe ser posterior a la hora inicial.";
            return false;
        }

        if (solicitudes == null ||
                solicitudes.isEmpty()) {
            mensajeError =
                    "Debe seleccionar al menos un recurso.";
            return false;
        }

        for (SolicitudRecurso solicitud : solicitudes) {
            if (solicitud.getCategoria() == null) {
                mensajeError =
                        "La categoría del recurso es obligatoria.";
                return false;
            }

            if (solicitud.getCantidad() <= 0) {
                mensajeError =
                        "La cantidad debe ser mayor que 0.";
                return false;
            }
        }

        return true;
    }

    public ArrayList<Reserva> buscarReservasPorFechaYCategoria(
            LocalDate fecha,
            CategoriaRecurso categoria) {

        ArrayList<Reserva> resultado =
                new ArrayList<>();

        for (Reserva reserva : reservas) {
            if (!reserva.getFecha().equals(fecha)) {
                continue;
            }

            for (Recurso recurso :
                    reserva.getRecursos()) {

                if (recurso.getRecurso()
                        .getVarId()
                        .equals(categoria.getVarId())) {

                    resultado.add(reserva);
                    break;
                }
            }
        }

        return resultado;
    }
}