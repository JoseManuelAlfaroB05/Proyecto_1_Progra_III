package Models;

import Persistencia.RecursoXMLDao;

import java.util.ArrayList;

public class GestorRecursos {

    private ArrayList<Recurso> recursos;

    public GestorRecursos() {
        RecursoXMLDao dao = new RecursoXMLDao();
        recursos = new ArrayList<>(dao.listarTodos());
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

    public ArrayList<Recurso> obtenerPorCategoria(CategoriaRecurso categoria) {
        ArrayList<Recurso> resultado = new ArrayList<>();
        for (Recurso recurso : recursos) {
            if (recurso.getRecurso().getVarId().equals(categoria.getVarId())) {
                resultado.add(recurso);
            }
        }
        return resultado;
    }
}