package org.app;
import windows.Login;
import javax.swing.SwingUtilities;


public class App 
{
    public static void main( String[] args )
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Login frame = new Login();
                frame.setSize(500, 600);
                frame.setResizable(false);
                frame.setLocationRelativeTo(null);
            }
        });
    }
}
