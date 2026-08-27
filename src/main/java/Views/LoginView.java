package Views;

import Controllers.ControllerLogin;
import Models.User;

import javax.sound.sampled.Control;
import javax.swing.*;

public class LoginView extends JFrame {
    private JPanel mainPanel;
    private JTextField UserTextField;
    private JPasswordField passwordField1;
    private JButton btnIngresar;

    private ControllerLogin controller = new ControllerLogin();

    public LoginView() {
        setContentPane(mainPanel);
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        btnIngresar.addActionListener(e -> {
            String id = UserTextField.getText();
            String clave = new String(passwordField1.getPassword());

            User usuario = controller.autenticar(id, clave);

            if (usuario != null) {
                JOptionPane.showMessageDialog(this, "Bienvenido! Rol: " + usuario.getVarRol());
                this.dispose();
                controller.invocarPrincipal(usuario);
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o clave incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
                UserTextField.setText("");
                passwordField1.setText("");

                UserTextField.requestFocus();
            }



        });
    }
}