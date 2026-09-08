package Controllers;

import Service.GestorCategorias;

public class ControllerCategoria {

    private GestorCategorias gestorCategorias;

    public ControllerCategoria() {
        gestorCategorias = new GestorCategorias();
    }

    public GestorCategorias getGestorCategorias() {
        return gestorCategorias;
    }

    public boolean agregarCategoria(String id, String descripcion) {
        return gestorCategorias.agregarCategoria(id, descripcion);
    }
}