package windows;

import javax.swing.*;
import java.awt.*;
import java.sql.*;


public class TeachingGuideViewer extends JFrame {
    JTextArea resultsTextArea;
    JScrollPane resultsScrollPane;

    public TeachingGuideViewer(String selectedSubject) {
        super("Guía Docente " + selectedSubject);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setResizable(true);
        final String ROUTE_ICON = "resources/images/icon-ull-original.png";
        setIconImage(new ImageIcon(ROUTE_ICON).getImage());

        resultsTextArea = new JTextArea();
        resultsTextArea.setEditable(false);
        resultsScrollPane = new JScrollPane(resultsTextArea);
        add(resultsScrollPane);

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

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            String[] namesColunms = {"Requisitos", "Competencias", "Evaluación", "Código asignatura", "Nombre", "Curso", "Departamento", "Creditos"};
            while (rs.next()) {
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    String columnName = metaData.getColumnName(columnIndex);
                    int indexInNamesColumns = columnIndex - 1;
                    if (indexInNamesColumns < namesColunms.length) {
                        columnName = namesColunms[indexInNamesColumns];
                    }
                    resultsTextArea.append("---- " + columnName + " ----\n");
                    resultsTextArea.append(rs.getObject(columnIndex) + "\n\n\n");
                }
            }
            resultsTextArea.setFont((new Font("sans-serif", Font.PLAIN, 16)));

            setVisible(true);
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
