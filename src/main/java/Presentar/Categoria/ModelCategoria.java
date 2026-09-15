package Presentar.Categoria;

import Recursos.CategoriaRecurso;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class ModelCategoria extends Presentar.AbstractModel {
    private CategoriaRecurso current;
    private List<CategoriaRecurso> list;
    public static final String CURRENT = "current";
    public static final String LIST = "list";

    public ModelCategoria() {
        current = new CategoriaRecurso("", "");
        list = new ArrayList<>();
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
        firePropertyChange(CURRENT);
        firePropertyChange(LIST);
    }

    public CategoriaRecurso getCurrent() {
        return current;
    }

    public void setCurrent(CategoriaRecurso current) {
        this.current = current;
        firePropertyChange(CURRENT);
    }

    public List<CategoriaRecurso> getList() {
        return list;
    }

    public void setList(List<CategoriaRecurso> list) {
        this.list = list;
        firePropertyChange(LIST);
    }

    public String getId() {
        return current.getVarId();
    }

    public String getDescripcion() {
        return current.getDescripcion();
    }

    public void setDescripcion(String descripcion) {
        current.setDescripcion(descripcion);
        firePropertyChange(CURRENT);
    }
}