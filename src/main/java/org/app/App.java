package org.app;
import windows.LoginWindow;
import javax.swing.SwingUtilities;


public class App 
{
    public static void main( String[] args )
    {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoginWindow frame = new LoginWindow();
                frame.setSize(200, 200);
                frame.setResizable(false);
                frame.setLocationRelativeTo(null);
            }
        });
    }
}
