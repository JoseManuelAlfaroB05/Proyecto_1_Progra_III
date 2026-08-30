package Views;

import Controllers.ControllerLogin;
import Models.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    private JPanel mainPanel;
    private JTextField UserTextField;
    private JPasswordField passwordField;
    private JButton btnIngresar;
    private JButton btnRechazar;
    private JButton restaurarPass;

    private ControllerLogin controller = new ControllerLogin();

    public LoginView() {
        setContentPane(mainPanel);
        setTitle("Login");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        btnIngresar.addActionListener(e -> {
            String id = UserTextField.getText();
            String clave = new String(passwordField.getPassword());

            User usuario = controller.autenticar(id, clave);

            if (usuario != null) {
                JOptionPane.showMessageDialog(this, "Bienvenido! Rol: " + usuario.getVarRol());
                this.dispose();
                controller.invocarPrincipal(usuario);
            } else {
                JOptionPane.showMessageDialog(this, "Usuario o clave incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
                UserTextField.setText("");
                passwordField.setText("");

                UserTextField.requestFocus();
            }



        });
        btnRechazar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserTextField.setText("");
                passwordField.setText("");
            }
        });

        restaurarPass.addActionListener(e -> {
            String id = UserTextField.getText();
            controller.changePass(id);
        });
    }
}