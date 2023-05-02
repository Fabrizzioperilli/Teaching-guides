package windows;

import javax.swing.*;

public class LoginWindow extends JFrame{
    private JPanel mainPanel;

    public LoginWindow() {
        super("Login");
        setContentPane(mainPanel);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}
