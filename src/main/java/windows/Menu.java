/**
 * @file Menu.java
 * @author Fabrizzio Daniell Perilli Martin alu0101138589@ull.edu.es
 * @version 1.0
 * @date 2023-05-07
 */
package windows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;


/**
 * The type Menu.
 */
public class Menu extends JFrame implements ActionListener {

    private final String userName;

    private final JButton modifyButton;
    private final JButton usersButton;
    private final JButton searchButton;
    private final JMenuItem itemMenuBack;
    private final JMenuItem itemMenuExit;
    private final JComboBox subjetsComboBox;
    private final JComboBox subjetsEditableComboBox;


    /**
     * Instantiates a new Menu.
     *
     * @param userName       the user name
     * @param name           the name
     * @param permissionUser the permission user
     */
    public Menu(String userName, String name, int permissionUser) {
        super("Menu");
        setLayout(null);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setBackground(new Color(92, 6, 140));
        final URL ROUTE_ICON = getClass().getResource("/images/icon-ull-original.png");
        setIconImage(new ImageIcon(ROUTE_ICON).getImage());

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menuOptions = new JMenu("Opciones");
        menuOptions.setFont(new Font("Andale Mono", Font.BOLD, 14));
        menuBar.add(menuOptions);

        itemMenuBack = new JMenuItem("Volver");
        itemMenuBack.setFont(new Font("Andale Mono", Font.BOLD, 14));
        menuOptions.add(itemMenuBack);
        itemMenuBack.addActionListener(this);

        itemMenuExit = new JMenuItem("Salir");
        itemMenuExit.setFont(new Font("Andale Mono", Font.BOLD, 14));
        menuOptions.add(itemMenuExit);
        itemMenuExit.addActionListener(this);

        this.userName = userName;

        JLabel welcomeUserLabel = new JLabel("Bienvenid@ " + name);
        welcomeUserLabel.setBounds(130, 50, 350, 30);
        welcomeUserLabel.setForeground(Color.WHITE);
        welcomeUserLabel.setFont(new Font("Arial Black", Font.BOLD, 25));
        add(welcomeUserLabel);

        JLabel teachingGuideLabel = new JLabel("Guías Docentes de la Universidad de la Laguna");
        teachingGuideLabel.setBounds(40, 110, 420, 30);
        teachingGuideLabel.setForeground(Color.WHITE);
        teachingGuideLabel.setFont(new Font("Arial Black", Font.PLAIN, 16));
        add(teachingGuideLabel);

        usersButton = new JButton();
        usersButton.setBounds(100, 180, 70, 70);
        usersButton.setBackground(new Color(92, 6, 140));
        final URL ROUTE_ICO_USERS = getClass().getResource("/images/users.png");
        usersButton.setIcon(new ImageIcon(ROUTE_ICO_USERS));
        usersButton.addActionListener(this);
        usersButton.setVisible(false);
        add(usersButton);

        searchButton = new JButton();
        searchButton.setBounds(210, 180, 70, 70);
        searchButton.setBackground(new Color(92, 6, 140));
        final URL ROUTE_ICO_SEARCH = getClass().getResource("/images/search.png");
        searchButton.setIcon(new ImageIcon(ROUTE_ICO_SEARCH));
        searchButton.addActionListener(this);
        searchButton.setVisible(false);
        add(searchButton);

        modifyButton = new JButton();
        modifyButton.setBounds(320, 180, 70, 70);
        modifyButton.setBackground(new Color(92, 6, 140));
        final URL ROUTE_ICO_MODIFY = getClass().getResource("/images/pencil.png");
        ImageIcon imageIcon = new ImageIcon(ROUTE_ICO_MODIFY);
        modifyButton.setIcon(imageIcon);
        modifyButton.addActionListener(this);
        modifyButton.setVisible(false);
        add(modifyButton);

        List<String> subjects = getSubjectList();
        subjetsComboBox = new JComboBox(subjects.toArray());
        subjetsComboBox.setBounds(150, 280, 180, 30);
        subjetsComboBox.insertItemAt("Buscar...", 0);
        subjetsComboBox.setSelectedIndex(0);
        subjetsComboBox.setVisible(false);
        add(subjetsComboBox);

        List<String> subjectsEditable = getSubjectListEditable();
        subjetsEditableComboBox = new JComboBox(subjectsEditable.toArray());
        subjetsEditableComboBox.setBounds(150, 330, 180, 30);
        subjetsEditableComboBox.insertItemAt("Editar...", 0);
        subjetsEditableComboBox.setSelectedIndex(0);
        subjetsEditableComboBox.setVisible(false);
        add(subjetsEditableComboBox);

        if (permissionUser == 0) {
            usersButton.setVisible(true);
            searchButton.setVisible(true);
            subjetsComboBox.setVisible(true);
            modifyButton.setVisible(true);
            subjetsEditableComboBox.setVisible(true);
        } else if (permissionUser == 1) {
            searchButton.setVisible(true);
            searchButton.setBounds(150, 180, 70, 70);
            subjetsComboBox.setVisible(true);
            modifyButton.setVisible(true);
            modifyButton.setBounds(255, 180, 70, 70);
            subjetsEditableComboBox.setVisible(true);
        } else {
            searchButton.setVisible(true);
            subjetsComboBox.setVisible(true);
        }
    }

    /**
     * Content the actions of events
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == itemMenuBack) {
            this.setVisible(false);
            Login login = new Login();
            login.setSize(500, 550);
            login.setLocationRelativeTo(null);
        }
        if (e.getSource() == itemMenuExit) {
            System.exit(0);
        }
        if (e.getSource() == usersButton) {
            UsersTable usersTable = new UsersTable();
            usersTable.setSize(620, 360);
            usersTable.setLocationRelativeTo(null);
        }
        if (e.getSource() == searchButton) {
            String selectedSubject = (String) subjetsComboBox.getSelectedItem();
            if (selectedSubject != "Buscar...") {
                TeachingGuideViewer teachingGuideViewer = new TeachingGuideViewer(selectedSubject);
                teachingGuideViewer.setSize(1000, 700);
                teachingGuideViewer.setLocationRelativeTo(null);
            }
        }
        if (e.getSource() == modifyButton) {
            String selectedSubjectEditable = (String) subjetsEditableComboBox.getSelectedItem();
            if (selectedSubjectEditable != "Editar...") {
                TeachingGuideForm teachingGuideForm = new TeachingGuideForm(selectedSubjectEditable);
                teachingGuideForm.setSize(850, 750);
                teachingGuideForm.setLocationRelativeTo(null);
            }
        }
    }

    /**
     * This method return the list of subjects names
     * @return List<String>
     */
    private List<String> getSubjectList() {
        List<String> subjects = new ArrayList<String>();
        try {
            final String ROUTE_DB = "jdbc:sqlite:src/main/resources/database/db_teaching_guides.db";
            Connection connection = DriverManager.getConnection(ROUTE_DB);
            PreparedStatement sqlQuery = connection.prepareStatement("SELECT name FROM subject");

            ResultSet resultSet = sqlQuery.executeQuery();
            while (resultSet.next()) {
                subjects.add(resultSet.getString("name"));
            }
            resultSet.close();
            sqlQuery.close();
            connection.close();
        } catch (SQLException ex) {
            System.out.println("Invalid SQL consult");
            ex.printStackTrace();
        }
        return subjects;
    }

    /**
     * This method return the list of subjects names editable
     * @return List<String>
     */
    private List<String> getSubjectListEditable() {
        List<String> subjects = new ArrayList<String>();
        try {
            final String ROUTE_DB = "jdbc:sqlite:src/main/resources/database/db_teaching_guides.db";
            Connection connection = DriverManager.getConnection(ROUTE_DB);
            PreparedStatement sqlQuery = connection.prepareStatement("SELECT name FROM subject NATURAL JOIN teachingGuide_teacher WHERE username = ?");
            sqlQuery.setString(1, userName);

            ResultSet resultSet = sqlQuery.executeQuery();
            while (resultSet.next()) {
                subjects.add(resultSet.getString("name"));
            }

            resultSet.close();
            sqlQuery.close();
            connection.close();
        } catch (SQLException ex) {
            System.out.println("Invalid SQL consult");
            ex.printStackTrace();
        }
        return subjects;
    }
}
