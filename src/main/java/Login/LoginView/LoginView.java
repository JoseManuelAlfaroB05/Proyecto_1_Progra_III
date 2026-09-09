package Login.LoginView;

import Login.ControllerLogin;
import Recursos.User;

import javax.swing.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {

    private JPanel mainPanel;
    private JPanel centralPanel;
    private JTextField UserTextField;
    private JPasswordField passwordField;
    private JButton btnIngresar;
    private JButton btnRechazar;
    private JButton restaurarPass;

    private ControllerLogin controller = new ControllerLogin();

    public LoginView() {

        setContentPane(mainPanel);
        setTitle("Login");
        setSize(900, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel.setBackground(new Color(255, 255, 255));

        centralPanel.setBackground(new Color(120, 120, 124, 107));
        centralPanel.setBorder(
                BorderFactory.createLineBorder(
                        new Color(107, 109, 112),
                        2
                )
        );

        btnIngresar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String id = UserTextField.getText();
                String clave = new String(passwordField.getPassword());

                User usuario = controller.autenticar(id, clave);

                if (usuario != null) {

                    JOptionPane.showMessageDialog(
                            LoginView.this,
                            "Bienvenido! Rol: " + usuario.getVarRol()
                    );

                    dispose();
                    controller.invocarPrincipal(usuario);

                } else {

                    JOptionPane.showMessageDialog(
                            LoginView.this,
                            "Usuario o clave incorrectos",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );

                    UserTextField.setText("");
                    passwordField.setText("");

                    UserTextField.requestFocus();
                }
            }
        });

        btnRechazar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                UserTextField.setText("");
                passwordField.setText("");

                UserTextField.requestFocus();
            }
        });

        restaurarPass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String id = UserTextField.getText();

                controller.changePass(id);
            }
        });
    }
}