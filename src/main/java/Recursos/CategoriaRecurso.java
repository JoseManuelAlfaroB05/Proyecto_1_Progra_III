package Recursos;

public class CategoriaRecurso {

    private String varId;
    private String varDescripcion;

    public CategoriaRecurso(String varId, String varDescripcion) {
        this.varId = varId;
        this.varDescripcion = varDescripcion;
    }

    public String getVarId() {
        return this.varId;
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