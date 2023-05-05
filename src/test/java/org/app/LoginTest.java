package org.app;
import junit.framework.TestCase;
import windows.Login;

public class LoginTest extends TestCase {
    private Login login;

    public void setUp() {
        login = new Login();
    }

    public void testAuthenticationValid() {
        assertTrue(login.authentication("user1", "user1"));
        assertTrue(login.authentication("admin", "admin"));
    }

    public void testAuthenticationInvalid() {
        assertFalse(login.authentication("1234", "1234"));
        assertFalse(login.authentication("USER", "USER"));
    }
}
