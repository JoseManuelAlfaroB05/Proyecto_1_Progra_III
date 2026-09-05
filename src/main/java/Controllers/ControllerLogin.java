package Controllers;

import Models.User;
import Persistencia.UserXMLDao;
import Views.MainView;
import Views.Login.ChangePassView;

public class ControllerLogin {

    private UserXMLDao dao = new UserXMLDao();

    public User autenticar(String id, String claveIngresada) {
        User usuario = dao.buscarPorId(id);

        if (usuario != null && usuario.getVarClave().equals(claveIngresada)) {
            return usuario;
        }
        return null;
    }

    public void invocarPrincipal(User usuarioLogueado) {
        MainView main = new MainView(usuarioLogueado);
        main.setVisible(true);
    }


    public void changePass(String id) {
        ChangePassView cpv = new ChangePassView(id);
        cpv.setVisible(true);
    }


    public boolean cambiarClave(String id, String claveActual, String claveNueva) {
        User usuario = dao.buscarPorId(id);

        if (usuario == null) {
            return false;
        }
        if (!usuario.getVarClave().equals(claveActual)) {
            return false;
        }

        return dao.actualizarClave(id, claveNueva);
    }


}