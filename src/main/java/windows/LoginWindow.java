package windows;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;
import java.sql.*;

public class LoginWindow extends JFrame implements ActionListener {

    private JLabel userLabel;
    private JLabel passwordLabel;
    private JTextField userTextField;
    private JPasswordField passwordUserField;
    private JLabel logoUll;
    private final String ROUTE_ICON = "resources/images/icon-ull-original.png";
    private final String ROUTE_LOGO = "resources/images/logo-ull.png";
    private final String ROUTE_DB = "jdbc:sqlite:db_teaching_guides.db";
    private JButton aceptButton;


    public LoginWindow() {
        super("Acceso");
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(92, 6, 140));
        setIconImage(new ImageIcon(ROUTE_ICON).getImage());

        logoUll = new JLabel(new ImageIcon(ROUTE_LOGO));
        logoUll.setBounds(25, 5, 450, 200);
        add(logoUll);

        userLabel = new JLabel("Usuario: ");
        userLabel.setBounds(90, 220, 100, 30);
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(new Font("sans-serif", Font.BOLD, 15));
        add(userLabel);

        userTextField = new JTextField();
        userTextField.setBounds(90, 260, 310, 30);
        userTextField.setFont(new Font("sans-serif", Font.BOLD, 14));
        add(userTextField);

        passwordLabel = new JLabel("Contraseña: ");
        passwordLabel.setBounds(90, 300, 100, 30);
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(new Font("sans-serif", Font.BOLD, 15));
        add(passwordLabel);

        passwordUserField = new JPasswordField();
        passwordUserField.setBounds(90, 340, 310, 30);
        passwordUserField.setFont(new Font("sans-serif", Font.BOLD, 15));
        add(passwordUserField);

        aceptButton = new JButton("Acceder");
        aceptButton.setBounds(200, 390, 100, 30);
        aceptButton.setBackground(Color.WHITE);
        aceptButton.setFont(new Font("sans-serif", Font.BOLD, 15));
        aceptButton.addActionListener(this);
        add(aceptButton);

    }

    private boolean authentication(final String user, final String password) {
        try {
            Connection connection = DriverManager.getConnection(ROUTE_DB);
            PreparedStatement sqlQuery = connection.prepareStatement("SELECT * FROM user WHERE username = ? AND password = ?");
            sqlQuery.setString(1, user);
            sqlQuery.setString(2, password);

            ResultSet resultSet = sqlQuery.executeQuery();
            boolean authenticated = resultSet.next();
            resultSet.close();
            sqlQuery.close();
            connection.close();
            return authenticated;
        } catch (SQLException ex) {
            System.out.println("Invalid SQL consult");
            ex.printStackTrace();
            return false;
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == aceptButton) {
            final String user = userTextField.getText();
            final String password = new String(passwordUserField.getPassword());

            if (user.equals("") || password.equals(""))
                JOptionPane.showMessageDialog(null, "Complete los campos");
            else {
                if (authentication(user, password))
                    System.out.println("Valid User");
                else
                    System.out.println("Invalid User");
            }
        }

    }


}
