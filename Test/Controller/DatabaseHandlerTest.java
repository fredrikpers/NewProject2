package Controller;

import Model.User;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class DatabaseHandlerTest {

    private DatabaseHandler database;

    @Test
    public void userExists() throws IOException {
        database = new DatabaseHandler();
        boolean correct = database.userExists("fredrik");
        boolean fake = database.userExists("joakim");

        assertEquals(true, correct);
        assertEquals(false, fake);
    }

    @Test
    public void userExistsLogIn() throws IOException {
        database = new DatabaseHandler();
        boolean correct = database.userExistsLogIn("fredrik");
        boolean fake = database.userExistsLogIn("joakim");

        assertEquals(correct, true);
        assertEquals(fake, false);
    }

    @Test
    public void getUser() throws IOException {
        database = new DatabaseHandler();
        User user = new User();
        user.setUsername("fredrik");
        user.setPassword("1234");
        user.setEmail("fp@kth.se");

        String [] userArray = user.userDataToArray(user);
        String [] correctUser = user.userDataToArray(database.getUser("fredrik"));

        assertArrayEquals(userArray, correctUser);

    }

}