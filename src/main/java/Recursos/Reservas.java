package Recursos;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;

@XmlRootElement(name = "reservas")
@XmlAccessorType(XmlAccessType.FIELD)
public class Reservas {

    @XmlElement(name = "reserva")
    private ArrayList<Reserva> reservas;

    public Reservas() {
        reservas = new ArrayList<>();
    }

    public ArrayList<Reserva> getReservas() {
        return reservas;
    }

    public void agregar(Reserva reserva) {
        reservas.add(reserva);
    }
}