package Service;

import Recursos.CategoriaRecurso;

import java.util.ArrayList;

public class GestorCategorias {

    private ArrayList<CategoriaRecurso> categorias;
    private CategoriaXMLDao categoriaXMLDao;

    public GestorCategorias() {

        categoriaXMLDao = new CategoriaXMLDao();

        categorias = categoriaXMLDao.listarTodas();
    }

    public ArrayList<CategoriaRecurso> getCategorias() {
        return categorias;
    }

    public CategoriaRecurso buscarPorId(String id) {

        for (CategoriaRecurso categoria : categorias) {

            if (categoria.getVarId().equals(id)) {
                return categoria;
            }
        }

        return null;
    }

    public CategoriaRecurso buscarPorDescripcion(String descripcion) {

        for (CategoriaRecurso categoria : categorias) {

            if (categoria.getDescripcion().equalsIgnoreCase(descripcion)) {
                return categoria;
            }
        }

        return null;
    }

    public boolean existeId(String id) {

        return buscarPorId(id) != null;
    }

    public boolean agregarCategoria(
            String id,
            String descripcion) {

        if (id == null || id.trim().isEmpty()) {
            return false;
        }

        if (descripcion == null || descripcion.trim().isEmpty()) {
            return false;
        }

        id = id.trim().toUpperCase();
        descripcion = descripcion.trim();

        if (existeId(id)) {
            return false;
        }

        CategoriaRecurso categoria =
                new CategoriaRecurso(
                        id,
                        descripcion
                );

        if (!categoriaXMLDao.guardar(categoria)) {
            return false;
        }

        categorias.add(categoria);

        return true;
    }

    public boolean eliminarCategoria(String id) {

        boolean eliminado =
                categoriaXMLDao.eliminarCategoria(id);

        if (eliminado) {

            categorias =
                    categoriaXMLDao.listarTodas();

            return true;
        }

        return false;
    }
}