package Presentar.Categoria;

public class ModelCategoria {
    private String id;
    private String descripcion;

    public ModelCategoria() {
        id = "";
        descripcion = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}