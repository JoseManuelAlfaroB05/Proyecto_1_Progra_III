package Presentar.Estadisticas;

import Recursos.Recurso;
import Recursos.Reserva;
import Service.GestorReservas;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.LinkedHashMap;
import java.util.Map;

public class ControllerEstadisticas {
    private final GestorReservas gestor = new GestorReservas();
    private final ModelEstadisticas model = new ModelEstadisticas();

    public ModelEstadisticas getModel() {
        return model;
    }

    public void cargarRecursos(LocalDate desde, LocalDate hasta) {
        Map<String, Integer> conteo = new LinkedHashMap<>();
        for (Reserva reserva : reservasEnRango(desde, hasta)) {
            for (Recurso recurso : reserva.getRecursos()) {
                String categoria = recurso.getRecurso() == null
                        ? recurso.getCategoriaId()
                        : recurso.getRecurso().getDescripcion();
                conteo.put(categoria, conteo.getOrDefault(categoria, 0) + 1);
            }
        }
        model.setRecursos(conteo);
    }

    public void cargarActividades(LocalDate desde, LocalDate hasta) {
        Map<String, Integer> conteo = new LinkedHashMap<>();
        for (Reserva reserva : reservasEnRango(desde, hasta)) {
            LocalDate semana = reserva.getFecha()
                    .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            String clave = semana.toString();
            conteo.put(clave, conteo.getOrDefault(clave, 0) + 1);
        }
        model.setActividades(conteo);
    }

    private Iterable<Reserva> reservasEnRango(LocalDate desde, LocalDate hasta) {
        gestor.recargarReservas();
        return gestor.getReservas().stream()
                .filter(reserva -> !reserva.getFecha().isBefore(desde)
                        && !reserva.getFecha().isAfter(hasta))
                .toList();
    }
}
