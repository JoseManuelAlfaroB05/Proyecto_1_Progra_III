package Recurso;

import Persistencia.RecursoXMLDao;

import java.util.ArrayList;
import java.util.List;

public class GestorRecursos {

    private ArrayList<Recurso> recursos;

    public GestorRecursos() {
        RecursoXMLDao dao = new RecursoXMLDao();
        recursos = new ArrayList<>(dao.listarTodos());
    }

    public ArrayList<Recurso> getRecursos() {
        return recursos;
    }
}