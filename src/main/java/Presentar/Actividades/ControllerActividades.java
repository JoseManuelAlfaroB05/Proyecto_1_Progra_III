package Presentar.Actividades;

import Recursos.Reserva;
import Service.GestorReservas;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

public class ControllerActividades {
    private final GestorReservas gestor = new GestorReservas();
    private final ModelActividades model = new ModelActividades();

    public ModelActividades getModel() {
        return model;
    }

    public void cargarSemana(LocalDate referencia) {
        if (referencia == null) {
            return;
        }
        LocalDate lunes = referencia.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        gestor.recargarReservas();
        List<Reserva> reservas = gestor.getReservas().stream()
                .filter(reserva -> !reserva.getFecha().isBefore(lunes)
                        && reserva.getFecha().isBefore(lunes.plusDays(7)))
                .toList();
        model.setSemana(lunes, reservas);
    }
}
