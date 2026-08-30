package Views;

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

    public ChangePassView(String id) {
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




}