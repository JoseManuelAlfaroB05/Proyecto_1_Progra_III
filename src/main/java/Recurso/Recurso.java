package Recurso;

import Recurso.CategoriaRecurso;

public class Recurso {

    private String varId;
    private CategoriaRecurso varRecurso;
    private String varDescripciom;

    public Recurso(String pId, CategoriaRecurso pRecurso, String pDescripcion) {
        this.varId = pId;
        this.varRecurso = pRecurso;
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

    public void setRecurso(CategoriaRecurso pRecurso) {
        this.varRecurso = pRecurso;
    }

    public void setDescripcion(String pDescripcion) {
        this.varDescripciom = pDescripcion;
    }

}
