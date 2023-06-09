/**
 * @file TeachingGuideForm.java
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

/**
 * The type Teaching guide form.
 */
public class TeachingGuideForm extends JFrame implements ActionListener {
    private final JButton aceptButton;
    private final JTextArea requiremetTextArea;
    private final JTextArea skillsTextArea;
    private final JTextArea evaluationTextArea;
    private final String subject;

    /**
     * Instantiates a new Teaching guide form.
     *
     * @param subject the subject
     */
    public TeachingGuideForm(String subject) {
        super("Edición Guía Docente");
        setLayout(null);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        getContentPane().setBackground(new Color(92, 6, 140));
        final URL ROUTE_ICON = getClass().getResource("/images/icon-ull-original.png");
        setIconImage(new ImageIcon(ROUTE_ICON).getImage());

        this.subject = subject;

        JLabel title = new JLabel("Edición guía docente:  " + subject);
        title.setBounds(50, 20, 700, 30);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Andale Mono", Font.BOLD, 20));
        add(title);

        JLabel requirementLabel = new JLabel("Requisitos");
        requirementLabel.setBounds(50, 60, 100, 30);
        requirementLabel.setForeground(Color.WHITE);
        requirementLabel.setFont(new Font("Arial Black", Font.BOLD, 15));
        add(requirementLabel);

        requiremetTextArea = new JTextArea();
        requiremetTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane resultsScrollPane = new JScrollPane(requiremetTextArea);
        resultsScrollPane.setBounds(50, 95, 750, 150);
        add(resultsScrollPane);

        JLabel skillsLabel = new JLabel("Competencias");
        skillsLabel.setBounds(50, 260, 120, 30);
        skillsLabel.setForeground(Color.WHITE);
        skillsLabel.setFont(new Font("Arial Black", Font.BOLD, 15));
        add(skillsLabel);

        skillsTextArea = new JTextArea();
        skillsTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane skillsScrollPane = new JScrollPane(skillsTextArea);
        skillsScrollPane.setBounds(50, 290, 750, 150);
        add(skillsScrollPane);

        JLabel evaluationLabel = new JLabel("Evaluación");
        evaluationLabel.setBounds(50, 450, 120, 30);
        evaluationLabel.setForeground(Color.WHITE);
        evaluationLabel.setFont(new Font("Arial Black", Font.BOLD, 15));
        add(evaluationLabel);

        evaluationTextArea = new JTextArea();
        evaluationTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane evaluationScrollPane = new JScrollPane(evaluationTextArea);
        evaluationScrollPane.setBounds(50, 480, 750, 150);
        add(evaluationScrollPane);

        aceptButton = new JButton("Aceptar");
        aceptButton.setBounds(330, 650, 200, 30);
        aceptButton.addActionListener(this);
        add(aceptButton);

        queryTeachingGuide();

    }

    /**
     * Content the actions of events
     * @param e the event to be processed
     */
    public void actionPerformed(ActionEvent e) {
        String requirement = requiremetTextArea.getText();
        String skills = skillsTextArea.getText();
        String evaluation = evaluationTextArea.getText();

        if (e.getSource() == aceptButton) {
            queryModifyTeachingGuide(requirement, skills, evaluation);
        }
    }

    /**
     * Query modify teaching guide.
     *
     * @param requirement the requirement
     * @param skills      the skills
     * @param evaluation  the evaluation
     */
    public void queryModifyTeachingGuide(String requirement, String skills, String evaluation) {
        try {
            final String ROUTE_DB = "jdbc:sqlite:src/main/resources/database/db_teaching_guides.db";
            Connection connection = DriverManager.getConnection(ROUTE_DB);

            // Consulta para obtener el ID de la asignatura por nombre
            String selectSubjectId = "SELECT id_subject FROM subject WHERE name = ?";
            PreparedStatement selectStatement = connection.prepareStatement(selectSubjectId);
            selectStatement.setString(1, subject);
            ResultSet selectResult = selectStatement.executeQuery();

            int id_subject = selectResult.getInt("id_subject");

            String updateTeachingGuide = "UPDATE teaching_guide SET requirement=?, skills=?, evualuation=? WHERE id_subject=?";
            PreparedStatement updateStatement = connection.prepareStatement(updateTeachingGuide);
            updateStatement.setString(1, requirement);
            updateStatement.setString(2, skills);
            updateStatement.setString(3, evaluation);
            updateStatement.setInt(4, id_subject);

            int rowsAffected = updateStatement.executeUpdate();

            selectResult.close();
            selectStatement.close();
            updateStatement.close();
            connection.close();
            JOptionPane.showMessageDialog(null, "Se ha modificado correctamente");
            setVisible(false);
        } catch (SQLException ex) {
            System.out.println("Error en la consulta SQL");
            ex.printStackTrace();
        }
    }

    /**
     * Query teaching guide.
     */
    public void queryTeachingGuide() {
        final String ROUTE_DB = "jdbc:sqlite:src/main/resources/database/db_teaching_guides.db";
        try {
            Connection connection = DriverManager.getConnection(ROUTE_DB);

            // Consulta para obtener el ID de la asignatura por nombre
            String selectSubjectId = "SELECT id_subject FROM subject WHERE name = ?";
            PreparedStatement selectStatement = connection.prepareStatement(selectSubjectId);
            selectStatement.setString(1, subject);
            ResultSet selectResult = selectStatement.executeQuery();

            int id_subject = selectResult.getInt("id_subject");

            String updateTeachingGuide = "SELECT requirement, skills, evualuation FROM teaching_guide WHERE id_subject=?";
            PreparedStatement updateStatement = connection.prepareStatement(updateTeachingGuide);

            updateStatement.setInt(1, id_subject);
            ResultSet updateResult = updateStatement.executeQuery();

            if (updateResult.next()) {
                String requirement = updateResult.getString("requirement");
                String skills = updateResult.getString("skills");
                String evaluation = updateResult.getString("evualuation");

                requiremetTextArea.setText(requirement);
                skillsTextArea.setText(skills);
                evaluationTextArea.setText(evaluation);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró una guía docente para esta asignatura.");
            }


            updateResult.close();
            selectResult.close();
            selectStatement.close();
            updateStatement.close();
            connection.close();
        } catch (SQLException ex) {
            System.out.println("Error en la consulta SQL");
            ex.printStackTrace();
        }
    }


}
