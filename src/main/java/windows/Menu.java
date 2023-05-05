package windows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;



public class Menu extends JFrame implements ActionListener {

    private int permissionUser;
    private String userName;
    private JButton modifyButton;
    private JButton usersButton;
    private JButton searchButton;
    private JMenuItem itemMenuBack;
    private JMenuItem itemMenuExit;
    private JComboBox subjetsComboBox;



    public Menu(String userName, int permissionUser) {
        super("Menu");
        setLayout(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(92, 6, 140));
        final String  ROUTE_ICON = "resources/images/icon-ull-original.png";
        setIconImage(new ImageIcon(ROUTE_ICON).getImage());

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menuOptions = new JMenu("Opciones");
        menuOptions.setFont(new Font("Andale Mono", 1, 14));
        menuBar.add(menuOptions);

        itemMenuBack = new JMenuItem("Volver");
        itemMenuBack.setFont(new Font("Andale Mono", 1, 14));
        menuOptions.add(itemMenuBack);
        itemMenuBack.addActionListener(this);

        itemMenuExit = new JMenuItem("Salir");
        itemMenuExit.setFont(new Font("Andale Mono", 1, 14));
        menuOptions.add(itemMenuExit);
        itemMenuExit.addActionListener(this);

        this.userName = userName;
        this.permissionUser = permissionUser;

        JLabel welcomeUserLabel = new JLabel("Bienvenid@ " + userName);
        welcomeUserLabel.setBounds(130, 50, 350, 30);
        welcomeUserLabel.setForeground(Color.WHITE);
        welcomeUserLabel.setFont(new Font("Arial Black", Font.BOLD, 25));
        add(welcomeUserLabel);

        JLabel teachingGuideLabel = new JLabel("Guías Docentes de la Universidad de la Laguna");
        teachingGuideLabel.setBounds(40, 100, 420, 30);
        teachingGuideLabel.setForeground(Color.WHITE);
        teachingGuideLabel.setFont(new Font("Arial Black", Font.PLAIN, 16));
        add(teachingGuideLabel);

        usersButton = new JButton();
        usersButton.setBounds(100, 180, 70, 70);
        usersButton.setBackground(new Color(92, 6, 140));
        final String ROUTE_ICO_USERS = "resources/images/users.png";
        usersButton.setIcon(new ImageIcon(ROUTE_ICO_USERS));
        usersButton.addActionListener(this);
        usersButton.setVisible(false);
        add(usersButton);

        searchButton = new JButton();
        searchButton.setBounds(210, 180, 70, 70);
        searchButton.setBackground(new Color(92, 6, 140));
        final String ROUTE_ICO_SEARCH = "resources/images/search.png";
        searchButton.setIcon(new ImageIcon(ROUTE_ICO_SEARCH));
        searchButton.addActionListener(this);
        searchButton.setVisible(false);
        add(searchButton);

        modifyButton = new JButton();
        modifyButton.setBounds(320, 180, 70, 70);
        modifyButton.setBackground(new Color(92, 6, 140));
        final String ROUTE_ICO_MODIFY = "resources/images/pencil.png";
        ImageIcon imageIcon = new ImageIcon(ROUTE_ICO_MODIFY);
        modifyButton.setIcon(imageIcon);
        modifyButton.addActionListener(this);
        modifyButton.setVisible(false);
        add(modifyButton);

        if (permissionUser == 0) {
            usersButton.setVisible(true);
            searchButton.setVisible(true);
            modifyButton.setVisible(true);
        } else if (permissionUser == 1) {
            searchButton.setVisible(true);
            searchButton.setBounds(150, 180, 70, 70);
            modifyButton.setVisible(true);
            modifyButton.setBounds(280, 180, 70, 70);
        } else {
            searchButton.setVisible(true);
        }
        List<String> subjects  = getSubjectList();
        subjetsComboBox = new JComboBox(subjects.toArray());
        subjetsComboBox.setBounds(150, 300, 180, 30);
        subjetsComboBox.insertItemAt("", 0);
        subjetsComboBox.setSelectedIndex(0);
        add(subjetsComboBox);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == itemMenuBack) {
            this.setVisible(false);
            Login login = new Login();
            login.setSize(500, 600);
            login.setResizable(false);
            login.setLocationRelativeTo(null);
        }
        if (e.getSource() == itemMenuExit) {
            System.exit(0);
        }
        if (e.getSource() == usersButton) {
            TableUsers tableUsers = new TableUsers();

        }
        if (e.getSource() == searchButton) {
            subjetsComboBox.setVisible(true);
            String selectedSubject = (String) subjetsComboBox.getSelectedItem();
            if (selectedSubject != "") {
                showResultToSearch(selectedSubject);
            }
        }
    }

    private List<String> getSubjectList() {
       List<String> subjects = new ArrayList<String>();
        try {
            final String ROUTE_DB = "jdbc:sqlite:db_teaching_guides.db";
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


    private void showResultToSearch(String selectedSubject) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            final String ROUTE_DB = "jdbc:sqlite:db_teaching_guides.db";
            conn = DriverManager.getConnection(ROUTE_DB);

            String sql = "SELECT * FROM teaching_guide NATURAL JOIN subject WHERE name = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, selectedSubject);

            rs = stmt.executeQuery();

            JFrame resultsFrame = new JFrame("Guia docente (" + selectedSubject + ")");
            resultsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            resultsFrame.setSize(1000, 700);
            resultsFrame.setLocationRelativeTo(null);
            resultsFrame.setResizable(false);
            final String ROUTE_ICON = "resources/images/icon-ull-original.png";
            resultsFrame.setIconImage(new ImageIcon(ROUTE_ICON).getImage());

            JTextArea resultsTextArea = new JTextArea();
            resultsTextArea.setEditable(false);
            JScrollPane resultsScrollPane = new JScrollPane(resultsTextArea);
            resultsFrame.add(resultsScrollPane);

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            String[] namesColunms = {"Requisitos", "Competencias", "Evaluación", "Código asignatura", "Nombre", "Curso","Departamento", "Creditos"};
            while (rs.next()) {
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    String columnName = metaData.getColumnName(columnIndex);
                    int indexInNamesColumns = columnIndex - 1;
                    if (indexInNamesColumns < namesColunms.length) {
                        columnName = namesColunms[indexInNamesColumns];
                    }
                    resultsTextArea.append("---- "+ columnName + " ----\n");
                    resultsTextArea.append(rs.getObject(columnIndex) + "\n\n\n");
                }
            }
            resultsTextArea.setFont((new Font("sans-serif", Font.PLAIN, 16)));

            resultsFrame.setVisible(true);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

}
