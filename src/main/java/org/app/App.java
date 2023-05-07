/**
 * @file App.java
 * @author Fabrizzio Daniell Perilli Martin alu0101138589@ull.edu.es
 * @version 1.0
 * @date 2023-05-07
 *
 */

package org.app;

import windows.Login;

import javax.swing.SwingUtilities;

/**
 * The type App.
 */
public class App {

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Login frame = new Login();
                frame.setSize(500, 550);
                frame.setResizable(false);
                frame.setLocationRelativeTo(null);
            }
        });
    }
}
