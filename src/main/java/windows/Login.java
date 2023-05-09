/**
 * @file LoginTest.java
 * @author Fabrizzio Daniell Perilli Martin alu0101138589@ull.edu.es
 * @version 1.0
 * @date 2023-05-07
 *
 */

package windows;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Font;
import java.net.URL;
import java.sql.*;

/**
 * The type Login.
 */
public class Login extends JFrame implements ActionListener {

    private final JTextField userTextField;
    private final JPasswordField passwordUserField;
    private final JButton aceptButton;
    private int permissionUser;
    private String name;

    /**
     * Instantiates a new Login.
     */
    public Login() {
        super("Acceso");
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(92, 6, 140));
        final URL ROUTE_ICON = getClass().getResource("/images/icon-ull-original.png");
        setIconImage(new ImageIcon(ROUTE_ICON).getImage());

        final URL ROUTE_LOGO = getClass().getResource("/images/logo-ull.png");
        JLabel logoUll = new JLabel(new ImageIcon(ROUTE_LOGO));
        logoUll.setBounds(25, 5, 450, 200);
        add(logoUll);

        JLabel userLabel = new JLabel("Usuario: ");
        userLabel.setBounds(90, 220, 100, 30);
        userLabel.setForeground(Color.WHITE);
        userLabel.setFont(new Font("sans-serif", Font.BOLD, 15));
        add(userLabel);

        userTextField = new JTextField();
        userTextField.setBounds(90, 260, 310, 30);
        userTextField.setFont(new Font("sans-serif", Font.BOLD, 14));
        add(userTextField);

        JLabel passwordLabel = new JLabel("Contraseña: ");
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

    /**
     * Authentication boolean.
     *
     * @param user     the user
     * @param password the password
     * @return the boolean
     */
    public boolean authentication(final String user, final String password) {
        try {
            final String ROUTE_DB = "jdbc:sqlite:src/main/resources/database/db_teaching_guides.db";
            Connection connection = DriverManager.getConnection(ROUTE_DB);
            PreparedStatement sqlQuery = connection.prepareStatement("SELECT * FROM teacher WHERE username = ? AND password = ?");
            sqlQuery.setString(1, user);
            sqlQuery.setString(2, password);

            ResultSet resultSet = sqlQuery.executeQuery();

            boolean authenticated = resultSet.next();
            this.permissionUser = resultSet.getInt("permission");
            this.name = resultSet.getString("name");

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

    /**
     * Actions of events
     * @param e the event to be processed
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == aceptButton) {
            final String user = userTextField.getText();
            final String password = new String(passwordUserField.getPassword());

            if (user.equals("") || password.equals(""))
                JOptionPane.showMessageDialog(null, "Complete los campos");
            else {
                if (authentication(user, password)) {
                    this.setVisible(false);
                    Menu menu = new Menu(user ,name, permissionUser);
                    menu.setSize(500, 475);
                    menu.setResizable(false);
                    menu.setLocationRelativeTo(null);
                } else {
                    JOptionPane.showMessageDialog(null, "Usuario incorrecto");
                }
            }
        }
    }
}
