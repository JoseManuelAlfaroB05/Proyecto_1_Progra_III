package Views.Usuarios;

import javax.swing.*;
import java.awt.*;

public class UsuarioView extends JPanel {

    private JPanel panelPrincipal;

    public UsuarioView() {
        setLayout(new BorderLayout());
        add(panelPrincipal, BorderLayout.CENTER);
    }
}