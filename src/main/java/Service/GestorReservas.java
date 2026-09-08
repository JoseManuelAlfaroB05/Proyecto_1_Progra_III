package Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;


import Models.CategoriaRecurso;
import Models.Recurso;
import Models.Reserva;
import Models.User;

public class GestorReservas {

    private ArrayList<Reserva> reservas;
    private GestorRecursos gestorRecursos;
    private ReservaXMLDao reservaXMLDao;

    public GestorReservas() {
        reservaXMLDao = new ReservaXMLDao();
        reservas = reservaXMLDao.cargar();
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

        if (necesitaLab) {
            CategoriaRecurso categoriaLab =
                    new CategoriaRecurso("LAB", "Laboratorio");

            ArrayList<Recurso> laboratorios =
                    gestorRecursos.obtenerPorCategoria(categoriaLab);

            for (int i = 0; i < cantidadLab && i < laboratorios.size(); i++) {
                recursosAsignados.add(laboratorios.get(i));
            }
        }

        if (necesitaPC) {
            CategoriaRecurso categoriaPC =
                    new CategoriaRecurso("PC", "Computadora");

            ArrayList<Recurso> computadoras =
                    gestorRecursos.obtenerPorCategoria(categoriaPC);

            for (int i = 0; i < cantidadPC && i < computadoras.size(); i++) {
                recursosAsignados.add(computadoras.get(i));
            }
        }

        if (necesitaProy) {
            CategoriaRecurso categoriaProy =
                    new CategoriaRecurso("PRO", "Proyector");

            ArrayList<Recurso> proyectores =
                    gestorRecursos.obtenerPorCategoria(categoriaProy);

            for (int i = 0; i < cantidadProy && i < proyectores.size(); i++) {
                recursosAsignados.add(proyectores.get(i));
            }
        }

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
        reservaXMLDao.guardar(nuevaReserva);

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

    public ArrayList<Reserva> buscarReservasPorFechaYCategoria(
            LocalDate fecha,
            CategoriaRecurso categoria) {

        ArrayList<Reserva> resultado = new ArrayList<>();

        for (Reserva reserva : reservas) {

            if (!reserva.getFecha().equals(fecha)) {
                continue;
            }

            for (Recurso recurso : reserva.getRecursos()) {

                if (recurso.getRecurso().getVarId().equals(categoria.getVarId())) {
                    resultado.add(reserva);
                    break;
                }
            }
        }

        return resultado;
    }

}