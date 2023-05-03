package org.app;
import junit.framework.TestCase;
import windows.LoginWindow;

public class LoginWindowTest extends TestCase {
    private LoginWindow loginWindow;

    public void setUp() {
        loginWindow = new LoginWindow();
    }

    public void testAuthenticationValid() {
        assertTrue(loginWindow.authentication("user1", "user1"));
        assertTrue(loginWindow.authentication("admin", "admin"));
    }

    public void testAuthenticationInvalid() {
        assertFalse(loginWindow.authentication("1234", "1234"));
        assertFalse(loginWindow.authentication("USER", "USER"));
    }
}
