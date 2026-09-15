package Recursos;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;

@XmlRootElement(name = "categorias")
@XmlAccessorType(XmlAccessType.FIELD)
public class Categorias {

    @XmlElement(name = "categoria")
    private ArrayList<CategoriaRecurso> categorias;

    public Categorias() {
        categorias = new ArrayList<>();
    }

    public ArrayList<CategoriaRecurso> getCategorias() {
        return categorias;
    }

    public void agregar(CategoriaRecurso categoria) {
        categorias.add(categoria);
    }
}