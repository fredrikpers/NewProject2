package Model;

import Controller.DatabaseHandler;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class UserTest {

    @Test
    public void validateEmail() {
        assertTrue(User.validateEmail("kalle@gmail.com"));
        assertTrue(User.validateEmail("kalle@kth.se"));
        assertTrue(User.validateEmail("gina_andersson123@gmail.com"));
    }

    @Test
    public void validateUsername(){
        assertTrue(User.validateUsername("bushra"));
        assertFalse(User.validateUsername("bush_"));
        assertFalse(User.validateUsername("Bush_m_"));
    }

    @Test
    public void validatePasswordTest() {
        User user = new User();
        String password = "Abcdefg5#";
        boolean actual = user.validatePassword(password);
        assertFalse(actual);
    }

    @Test
    public void validatePassword_IsNull() {
        User user = new User();
        String password = null;
        boolean actual = user.validatePassword(password);
        assertFalse(actual);
    }

    @Test
    public void validatePassword_IsEmptyString() {
        User user = new User();
        String password = "";
        boolean actual = user.validatePassword(password);
        assertFalse(actual);
    }

    @Test
    public void validatePassword_LengthIsTooLong() {
        User user = new User();
        String password = "Abcdefg5#abcdefgabcd";
        boolean actual = user.validatePassword(password);
        assertFalse(actual);
    }

    @Test
    public void checkPassword() throws IOException {
        DatabaseHandler database = new DatabaseHandler();
        String falsePW = "4321";
        String correctPW = "1234";
        User user = database.getUser("fredrik");
        assertTrue(user.checkPassword(user, correctPW));
        assertFalse(user.checkPassword(user, falsePW));

    }


}