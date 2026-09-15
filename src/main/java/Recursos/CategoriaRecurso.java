package Recursos;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class CategoriaRecurso {

    @XmlElement(name = "id")
    private String varId;

    @XmlElement(name = "descripcion")
    private String varDescripcion;

    public CategoriaRecurso() {
    }

    public CategoriaRecurso(String varId, String varDescripcion) {
        this.varId = varId;
        this.varDescripcion = varDescripcion;
    }

    public String getVarId() {
        return varId;
    }

    public String getDescripcion() {
        return varDescripcion;
    }

    public void setDescripcion(String varDescripcion) {
        this.varDescripcion = varDescripcion;
    }

    @Override
    public String toString() {
        return varDescripcion;
    }
}