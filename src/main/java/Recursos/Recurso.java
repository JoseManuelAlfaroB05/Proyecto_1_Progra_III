package Recursos;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlTransient;

@XmlAccessorType(XmlAccessType.FIELD)
public class Recurso {

    @XmlElement(name = "id")
    private String varId;

    @XmlElement(name = "categoria")
    private String categoriaId;

    @XmlTransient
    private CategoriaRecurso varRecurso;

    @XmlElement(name = "descripcion")
    private String varDescripciom;

    public Recurso() {
    }

    public Recurso(String pId, CategoriaRecurso pRecurso, String pDescripcion) {
        this.varId = pId;
        this.varRecurso = pRecurso;
        this.categoriaId = pRecurso != null ? pRecurso.getVarId() : null;
        this.varDescripciom = pDescripcion;
    }

    public String getId() {
        return varId;
    }

    public CategoriaRecurso getRecurso() {
        return varRecurso;
    }

    public String getDescripcion() {
        return varDescripciom;
    }

    public String getCategoriaId() {
        return categoriaId;
    }

    public void setRecurso(CategoriaRecurso pRecurso) {
        this.varRecurso = pRecurso;
        this.categoriaId = pRecurso != null ? pRecurso.getVarId() : null;
    }

    public void setDescripcion(String pDescripcion) {
        this.varDescripciom = pDescripcion;
    }

    public void setCategoriaId(String pCategoriaId) {
        this.categoriaId = pCategoriaId;
    }
}