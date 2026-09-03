package Recurso;

public class CategoriaRecurso {
    private String varId;
    private String varDescripcion;

    public CategoriaRecurso(String varId, String varDescripcion) {
        this.varId = varId;
        this.varDescripcion = varDescripcion;
    }

    //getters
    public String getVarId() {
        return this.varId;
    }
    public String getDescripcion() {
        return varDescripcion;
    }

    //setter
    public void setDescripcion(String varDescripcion) {
        this.varDescripcion = varDescripcion;
    }
}
