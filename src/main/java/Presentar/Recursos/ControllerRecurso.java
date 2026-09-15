package Presentar.Recursos;

import Recursos.CategoriaRecurso;
import Recursos.Recurso;
import Service.GestorCategorias;
import Service.GestorRecursos;

public class ControllerRecurso {
    private final GestorRecursos gestor = new GestorRecursos();
    private final GestorCategorias gestorCategorias = new GestorCategorias();
    private final ModelRecurso model = new ModelRecurso();

    public ControllerRecurso() {
        cargarLista("", "");
    }

    public ModelRecurso getModel() {
        return model;
    }

    public GestorCategorias getGestorCategorias() {
        return gestorCategorias;
    }

    public void cargarLista(String categoriaId, String descripcion) {
        model.setList(gestor.buscar(categoriaId, descripcion));
    }

    public boolean guardar(String id, CategoriaRecurso categoria, String descripcion) {
        Recurso recurso = new Recurso(id.trim(), categoria, descripcion.trim());
        boolean guardado = gestor.guardar(recurso);
        if (guardado) {
            model.setCurrent(recurso);
            cargarLista("", "");
        }
        return guardado;
    }

    public boolean actualizar(String id, CategoriaRecurso categoria, String descripcion) {
        Recurso recurso = new Recurso(id.trim(), categoria, descripcion.trim());
        boolean actualizado = gestor.actualizar(recurso);
        if (actualizado) {
            model.setCurrent(recurso);
            cargarLista("", "");
        }
        return actualizado;
    }

    public boolean eliminar(String id) {
        boolean eliminado = gestor.eliminar(id);
        if (eliminado) {
            limpiar();
            cargarLista("", "");
        }
        return eliminado;
    }

    public void seleccionar(String id) {
        Recurso recurso = gestor.buscarPorId(id);
        if (recurso != null) {
            model.setCurrent(recurso);
        }
    }

    public void limpiar() {
        model.setCurrent(new Recurso());
    }
}
