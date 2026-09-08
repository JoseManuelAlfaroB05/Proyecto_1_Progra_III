package Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import Models.CategoriaRecurso;
import Models.Recurso;
import Models.Reserva;
import Models.SolicitudRecurso;
import Models.User;

public class GestorReservas {

    private ArrayList<Reserva> reservas;
    private GestorRecursos gestorRecursos;
    private GestorCategorias gestorCategorias;
    private ReservaXMLDao reservaXMLDao;

    public GestorReservas() {

        reservaXMLDao = new ReservaXMLDao();

        gestorCategorias =
                new GestorCategorias();

        gestorRecursos =
                new GestorRecursos();

        reservas =
                reservaXMLDao.cargar();
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

        if (!validarReserva(
                actividad,
                fecha,
                horaInicio,
                horaFin,
                solicitudes)) {

            return false;
        }

        ArrayList<Recurso> recursosAsignados =
                new ArrayList<>();

        for (SolicitudRecurso solicitud : solicitudes) {

            CategoriaRecurso categoria =
                    solicitud.getCategoria();

            int cantidad =
                    solicitud.getCantidad();

            ArrayList<Recurso> disponibles =
                    gestorRecursos.obtenerPorCategoria(
                            categoria
                    );

            if (cantidad > disponibles.size()) {

                System.out.println(
                        "No hay suficientes recursos de la categoría: "
                                + categoria.getDescripcion()
                );

                return false;
            }

            for (int i = 0; i < cantidad; i++) {

                recursosAsignados.add(
                        disponibles.get(i)
                );
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

    private boolean validarReserva(
            String actividad,
            LocalDate fecha,
            LocalTime horaInicio,
            LocalTime horaFin,
            ArrayList<SolicitudRecurso> solicitudes) {

        if (actividad == null ||
                actividad.trim().isEmpty()) {

            System.out.println(
                    "La actividad es obligatoria."
            );

            return false;
        }

        if (fecha == null) {

            System.out.println(
                    "La fecha es obligatoria."
            );

            return false;
        }

        if (horaInicio == null ||
                horaFin == null) {

            System.out.println(
                    "Las horas son obligatorias."
            );

            return false;
        }

        if (!horaInicio.isBefore(horaFin)) {

            System.out.println(
                    "La hora final debe ser posterior a la hora inicial."
            );

            return false;
        }

        if (solicitudes == null ||
                solicitudes.isEmpty()) {

            System.out.println(
                    "Debe seleccionar al menos un recurso."
            );

            return false;
        }

        for (SolicitudRecurso solicitud : solicitudes) {

            if (solicitud.getCategoria() == null) {
                return false;
            }

            if (solicitud.getCantidad() <= 0) {

                System.out.println(
                        "La cantidad debe ser mayor que 0."
                );

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