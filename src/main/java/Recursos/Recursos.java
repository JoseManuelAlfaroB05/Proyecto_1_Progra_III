package Recursos;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;

@XmlRootElement(name = "recursos")
@XmlAccessorType(XmlAccessType.FIELD)
public class Recursos {

    @XmlElement(name = "recurso")
    private ArrayList<Recurso> recursos;

    public Recursos() {
        recursos = new ArrayList<>();
    }

    public ArrayList<Recurso> getRecursos() {
        return recursos;
    }

    public void agregar(Recurso recurso) {
        recursos.add(recurso);
    }
}