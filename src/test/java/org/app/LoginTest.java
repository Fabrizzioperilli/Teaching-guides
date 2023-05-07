package org.app;
import junit.framework.TestCase;
import windows.Login;

/**
 * The type Login test.
 */
public class LoginTest extends TestCase {
    private Login login;

    public void setUp() {
        login = new Login();
    }

    /**
     * Test authentication valid.
     */
    public void testAuthenticationValid() {
        assertTrue(login.authentication("user1", "user1"));
        assertTrue(login.authentication("admin", "admin"));
    }

    /**
     * Test authentication invalid.
     */
    public void testAuthenticationInvalid() {
        assertFalse(login.authentication("1234", "1234"));
        assertFalse(login.authentication("USER", "USER"));
    }
}
