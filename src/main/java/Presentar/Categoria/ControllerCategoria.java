package Presentar.Categoria;

import Service.GestorCategorias;
import Recursos.CategoriaRecurso;
import java.util.List;

public class ControllerCategoria {
    private GestorCategorias gestorCategorias;
    private ModelCategoria model;

    public ControllerCategoria() {
        gestorCategorias = new GestorCategorias();
        model = new ModelCategoria();
        cargarCategorias();
    }

    public GestorCategorias getGestorCategorias() {
        return gestorCategorias;
    }

    public ModelCategoria getModel() {
        return model;
    }

    private void cargarCategorias() {
        List<CategoriaRecurso> categorias = gestorCategorias.getCategorias();
        model.setList(categorias);
    }

    public boolean agregarCategoria(String id, String descripcion) {
        boolean resultado = gestorCategorias.agregarCategoria(id, descripcion);
        if (resultado) {
            model.setCurrent(new CategoriaRecurso(id, descripcion));
            cargarCategorias();
        }
        return resultado;
    }

    public CategoriaRecurso buscarCategoria(String descripcion) {
        CategoriaRecurso categoria = gestorCategorias.buscarPorDescripcion(descripcion);
        if (categoria != null) {
            model.setCurrent(categoria);
        }
        return categoria;
    }

    public boolean eliminarCategoria(String id) {
        boolean resultado = gestorCategorias.eliminarCategoria(id);
        if (resultado) {
            model.setCurrent(new CategoriaRecurso("", ""));
            cargarCategorias();
        }
        return resultado;
    }

    public void limpiar() {
        model.setCurrent(new CategoriaRecurso("", ""));
    }
}