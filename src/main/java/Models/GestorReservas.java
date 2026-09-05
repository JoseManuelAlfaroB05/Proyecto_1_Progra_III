package Models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class GestorReservas {

    private ArrayList<Reserva> reservas;
    private GestorRecursos gestorRecursos;

    public GestorReservas() {
        reservas = new ArrayList<>();
        gestorRecursos = new GestorRecursos();
    }

    public ArrayList<Reserva> getReservas() {
        return reservas;
    }

    public GestorRecursos getGestorRecursos() {
        return gestorRecursos;
    }

    public void agregarReserva(Reserva reserva) {
        reservas.add(reserva);
    }

    public boolean eliminarReserva(String idReserva) {
        for (int i = 0; i < reservas.size(); i++) {
            if (reservas.get(i).getId().equals(idReserva)) {
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
            boolean necesitaLab,
            int cantidadLab,
            boolean necesitaPC,
            int cantidadPC,
            boolean necesitaProy,
            int cantidadProy
    ) {

        ArrayList<Recurso> recursosAsignados = new ArrayList<>();

        Reserva nuevaReserva = new Reserva(
                id,
                usuario,
                actividad,
                fecha,
                horaInicio,
                horaFin,
                recursosAsignados
        );

        reservas.add(nuevaReserva);

        // AQUÍ
        System.out.println("Reserva creada correctamente");
        System.out.println("ID: " + nuevaReserva.getId());
        System.out.println("Usuario: " + nuevaReserva.getUsuario().getVarId());
        System.out.println("Actividad: " + nuevaReserva.getActividad());
        System.out.println("Fecha: " + nuevaReserva.getFecha());
        System.out.println("Hora inicio: " + nuevaReserva.getHoraInicio());
        System.out.println("Hora fin: " + nuevaReserva.getHoraFin());
        System.out.println("Cantidad de recursos: " + nuevaReserva.getRecursos().size());

        return true;
    }
}