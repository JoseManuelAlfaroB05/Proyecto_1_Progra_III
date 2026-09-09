package Categoria;

import Service.GestorCategorias;

public class ControllerCategoria {
    private GestorCategorias gestorCategorias;
    private ModelCategoria model;

    public ControllerCategoria() {
        gestorCategorias = new GestorCategorias();
        model = new ModelCategoria();
    }

    public GestorCategorias getGestorCategorias() {
        return gestorCategorias;
    }

    public ModelCategoria getModel() {
        return model;
    }

    public boolean agregarCategoria(String id, String descripcion) {
        model.setId(id);
        model.setDescripcion(descripcion);

        return gestorCategorias.agregarCategoria(
                model.getId(),
                model.getDescripcion()
        );
    }
}