package Service;

import Recursos.CategoriaRecurso;
import Recursos.Recurso;

import java.util.ArrayList;

public class GestorRecursos {

    private ArrayList<Recurso> recursos;
    private final RecursoXMLDao dao;

    public GestorRecursos() {
        dao = new RecursoXMLDao();

        recursos =
                new ArrayList<>(
                        dao.listarTodos()
                );
    }

    public ArrayList<Recurso> getRecursos() {
        return recursos;
    }

    public Recurso buscarPorId(String id) {

        for (Recurso recurso : recursos) {

            if (recurso.getId().equals(id)) {
                return recurso;
            }
        }

        return null;
    }

    public ArrayList<Recurso> obtenerPorCategoria(
            CategoriaRecurso categoria) {

        ArrayList<Recurso> resultado =
                new ArrayList<>();

        for (Recurso recurso : recursos) {

            if (recurso.getRecurso() != null &&
                    recurso.getRecurso()
                            .getVarId()
                            .equals(categoria.getVarId())) {

                resultado.add(recurso);
            }
        }

        return resultado;
    }

    public int cantidadPorCategoria(
            CategoriaRecurso categoria) {

        return obtenerPorCategoria(categoria).size();
    }

    public ArrayList<Recurso> buscar(String categoriaId, String descripcion) {
        ArrayList<Recurso> resultado = new ArrayList<>();
        String filtroDescripcion = descripcion == null ? "" : descripcion.trim().toLowerCase();
        for (Recurso recurso : recursos) {
            boolean coincideCategoria = categoriaId == null || categoriaId.isEmpty()
                    || categoriaId.equals(recurso.getCategoriaId());
            boolean coincideDescripcion = filtroDescripcion.isEmpty()
                    || recurso.getDescripcion().toLowerCase().contains(filtroDescripcion);
            if (coincideCategoria && coincideDescripcion) {
                resultado.add(recurso);
            }
        }
        return resultado;
    }

    public boolean existeId(String id) {
        return buscarPorId(id) != null;
    }

    public boolean guardar(Recurso recurso) {
        if (recurso == null || recurso.getId() == null || recurso.getId().trim().isEmpty()
                || recurso.getRecurso() == null || recurso.getDescripcion() == null
                || recurso.getDescripcion().trim().isEmpty() || existeId(recurso.getId())) {
            return false;
        }
        if (!dao.guardar(recurso)) {
            return false;
        }
        recursos = new ArrayList<>(dao.listarTodos());
        return true;
    }

    public boolean actualizar(Recurso recurso) {
        if (recurso == null || recurso.getRecurso() == null
                || recurso.getDescripcion() == null || recurso.getDescripcion().trim().isEmpty()) {
            return false;
        }
        if (!dao.actualizar(recurso)) {
            return false;
        }
        recursos = new ArrayList<>(dao.listarTodos());
        return true;
    }

    public boolean eliminar(String id) {
        if (!dao.eliminar(id)) {
            return false;
        }
        recursos = new ArrayList<>(dao.listarTodos());
        return true;
    }
}