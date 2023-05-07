package windows;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.*;

public class UsersTable extends JFrame {

    public UsersTable() {
        super("Usuarios del sistema");
        setLayout(null);
        setVisible(true);
        setSize(620, 360);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setBackground(new Color(92, 6, 140));
        final String ROUTE_ICON = "resources/images/icon-ull-original.png";
        setIconImage(new ImageIcon(ROUTE_ICON).getImage());

        final String ROUTE_DB = "jdbc:sqlite:db_teaching_guides.db";
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            con = DriverManager.getConnection(ROUTE_DB);
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM teacher");

            JTable table = new JTable(buildTableModel(rs));
            table.setEnabled(false);
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setBounds(17, 10, 570, 300);
            add(scrollPane);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar la conexión a la base de datos
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
        DefaultTableModel tableModel = new DefaultTableModel();
        int columnCount = rs.getMetaData().getColumnCount();


        for (int i = 1; i <= columnCount; i++) {
            tableModel.addColumn(rs.getMetaData().getColumnName(i));
        }

        while (rs.next()) {
            Object[] row = new Object[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                row[i-1] = rs.getObject(i);
            }
            tableModel.addRow(row);
        }

        return tableModel;
    }
}
