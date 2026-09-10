package Presentar.Login.LoginView;

import Presentar.Login.ControllerLogin;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ChangePassView extends JDialog {

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField actualContraseña;
    private JPasswordField contraseñaNueva1;
    private JPasswordField contraseñaNueva2;
    private JButton exitButton;

    private ControllerLogin controller = new ControllerLogin();  // <- ESTE atributo, aqui arriba
    private String idUsuario;

    public ChangePassView(String id) {
        this.idUsuario = id;
        setContentPane(contentPane);
        setModal(true);

        pack();
        setLocationRelativeTo(null);
        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualContraseña.setText("");
                contraseñaNueva1.setText("");
                contraseñaNueva2.setText("");
                actualContraseña.requestFocus();
            }
        });
        //Volver al loggin
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmarCambio();
            }
        });
    }

    public JButton getButtonOK() {
        return buttonOK;
    }

    public JButton getButtonCancel() {
        return buttonCancel;
    }

    public JTextField getTextField1() {
        return actualContraseña;
    }

    public JPasswordField getPasswordField1() {
        return contraseñaNueva1;
    }

    public JPasswordField getPasswordField2() {
        return contraseñaNueva2;
    }

    private void confirmarCambio() {
        String claveActual = actualContraseña.getText();
        String claveNueva = new String(contraseñaNueva1.getPassword());
        String confirmacion = new String(contraseñaNueva2.getPassword());

        // 1. verificar que nada este vacio
        if (claveActual.isEmpty() || claveNueva.isEmpty() || confirmacion.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 2. verificar que la clave nueva coincida con la confirmacion
        if (!claveNueva.equals(confirmacion)) {
            JOptionPane.showMessageDialog(this, "La clave nueva no coincide con la confirmación", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 3. verificar clave actual + guardar cambio (esto lo resuelve el Controller)
        boolean exito = controller.cambiarClave(idUsuario, claveActual, claveNueva);

        if (exito) {
            JOptionPane.showMessageDialog(this, "Clave actualizada correctamente");
            dispose(); // cierra el dialog, regresa al login
        } else {
            JOptionPane.showMessageDialog(this, "La clave actual es incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
            actualContraseña.setText("");
            contraseñaNueva1.setText("");
            contraseñaNueva2.setText("");
            actualContraseña.requestFocus();
        }
    }


}