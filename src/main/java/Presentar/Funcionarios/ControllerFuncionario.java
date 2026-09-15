package Presentar.Funcionarios;

import Recursos.Rol;
import Recursos.User;
import Service.UserXMLDao;
import java.util.List;

public class ControllerFuncionario {
    private final UserXMLDao dao = new UserXMLDao();
    private final ModelFuncionario model = new ModelFuncionario();

    public ControllerFuncionario() {
        cargarLista();
    }

    public ModelFuncionario getModel() {
        return model;
    }

    private void cargarLista() {
        model.setList(dao.buscarFuncionarios("", ""));
    }

    public List<User> buscar(String id, String nombre) {
        List<User> resultado = dao.buscarFuncionarios(id, nombre);
        model.setList(resultado);
        return resultado;
    }

    public boolean guardar(String id, String nombre, String telefono) {
        if (!telefonoValido(telefono)) {
            return false;
        }

        User funcionario = new User(
                id,
                id,
                Rol.FUNCIONARIO,
                nombre,
                telefono
        );

        boolean resultado = dao.guardarFuncionario(funcionario);

        if (resultado) {
            model.setCurrent(funcionario);
            cargarLista();
        }

        return resultado;
    }

    public boolean actualizar(String id, String nombre, String telefono) {
        if (!telefonoValido(telefono)) {
            return false;
        }

        User funcionario = new User(
                id,
                "",
                Rol.FUNCIONARIO,
                nombre,
                telefono
        );

        boolean resultado = dao.actualizarFuncionario(funcionario);

        if (resultado) {
            model.setCurrent(funcionario);
            cargarLista();
        }

        return resultado;
    }

    public boolean eliminar(String id) {
        boolean resultado = dao.eliminarFuncionario(id);

        if (resultado) {
            model.setCurrent(
                    new User("", "", Rol.FUNCIONARIO, "", "")
            );
            cargarLista();
        }

        return resultado;
    }

    public void limpiar() {
        model.setCurrent(
                new User("", "", Rol.FUNCIONARIO, "", "")
        );
    }

    private boolean telefonoValido(String telefono) {
        return telefono != null && telefono.matches("[0-9]+");
    }
}