package org.example;

import Recurso.CategoriaRecurso;
import Recurso.GestorRecursos;
import Recurso.Recurso;
import Login.User;
import Reserva.ControllerReserva;
import Login.Rol;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        System.out.println("\n===== PRUEBA DE RESERVA =====");

        ControllerReserva controllerReserva = new ControllerReserva();

        User usuario = new User("admin","1234",Rol.ADMINISTRADOR);

        boolean resultado = controllerReserva.crearReserva(
                usuario,
                "Reunion de proyecto",
                LocalDate.of(2026, 9, 10),
                LocalTime.of(10, 0),
                LocalTime.of(12, 0),
                true,   // necesita laboratorio
                1,      // cantidad laboratorios
                true,   // necesita computadoras
                2,      // cantidad computadoras
                false,  // necesita proyector
                0       // cantidad proyectores
        );
        System.out.println("Reserva creada? " + resultado);
    }
}