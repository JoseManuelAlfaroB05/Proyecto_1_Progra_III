package Login;

import Recursos.User;
import Service.UserXMLDao;
import Login.LoginView.ChangePassView;
import MainViews.MainView;

public class ControllerLogin {

    private UserXMLDao dao = new UserXMLDao();
    private ModelLogin model;

    public ControllerLogin() {
        model = new ModelLogin();
    }

    public User autenticar(String id, String claveIngresada) {

        model.setVarId(id);
        model.setVarClave(claveIngresada);

        User usuario = dao.buscarPorId(model.getVarId());

        if (usuario != null &&
                usuario.getVarClave().equals(model.getVarClave())) {

            model.setUsuarioAutenticado(usuario);

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