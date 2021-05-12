package Model;

import Controller.DatabaseHandler;


import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Represents a <code>User</code>
 * @author fredrikpettersson
 */
public class User {


    private String username;
    private String password;
    private String email;




    /** Sole constructor for class, initialises an empty instance
     * of <code>User</code>
     */
    public User (){

    }

    /** Helper function which returns <code>User</code> class
     * attributes as a <code>String[]</code>
     * @param user a <code>User</code> object
     * @return String array with <code>User</code> attributes*/
    public String[] userDataToArray(User user){

        String[] data = {this.username, this.password, this.email};


        return data;

    }

    /** @return username of User */
    public String getUsername() {
        return username;
    }
    /** @return email of User */
    public String getEmail() {
        return email;
    }

    /** @return password of User */
    public String getPassword() {
        return password;
    }

    /**
     * @param username username to set as a <code>String</code>
     */
    /*
    public boolean setUsername(String username) {
        if (validateUsername(username)) {
            this.username = username;
            return true;
        }
        return false;
    }

     */

    public void setUsername(String username){
        this.username = username;
    }


    /**
     * @param password the password to set for <code>User</code>
     */
/*
    public boolean setPassword(String password) {

        if (validatePassword(password)) {
            this.password = password;
            return true;

        }
        return false;
    }


 */

    public void setPassword(String password){
        this.password = password;
    }



    /** @param email the email to set for <code>User</code>
     * @return true if email is valid */
    public boolean setEmail(String email) {

        if (validateEmail(email)) {
            this.email = email;
            return true;

        }
        return false;

    }

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static final Pattern VALID_USER_NAME_REGEX =
            Pattern.compile("^(?=^[^_\\n]+_?[^_\\n]+$)\\w{3,20}$", Pattern.CASE_INSENSITIVE);


    //private static final String VALID_PASSWORD_REGEX = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%=:\\?]).{6,15})";
    private static final String VALID_PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";

    /**
     * Validates the format of an email address passed to it
     * as a <code>String</code>
     *
     * @param emailStr the email address to validate
     * @return true if valid
     * @author Dennis Tanaka
     */

    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }


    /*
    public static boolean validateEmail(String emailStr) {
        return Pattern.matches(VALID_EMAIL_ADDRESS_REGEX, emailStr);
    }

     */

    /**
     * validates the username if it already exists in the database or if it
     * follows the pattern, 3 characters at least and 20 at most.
     * @param username the username to be validated
     * @return <code>true</code> if the username is valid
     * @return <code>false</code> if the username is not valid or already exists.
     * @author Bushra Alsabbagh
     */
    public static boolean validateUsername(String username){
        String userLowerCase = username.toLowerCase();
        boolean usernameExists;
        try {
            usernameExists = new DatabaseHandler().userExists(userLowerCase);
            if (usernameExists){
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Matcher matcher = VALID_USER_NAME_REGEX.matcher(userLowerCase);
        boolean isValid = matcher.find();
        if(isValid){
            System.out.println("The username " + username + " is valid!");
            return true;
        }else{
            System.out.println("The username " + username + " is not valid!");
            System.out.println("Must be 3 characters at least , 20 at most, can contain (_) at most");
            return false;
        }
    }

    public boolean validatePassword(String password) {
        return (password != null && Pattern.matches(VALID_PASSWORD_REGEX, password));
    }

    /**
     * Checks if password is valid
     * @param user User as a Object
     * @param password Password that program gets from terminal
     * @return true if password matches else false
     */
    public boolean checkPassword(User user, String password){
        return user.getPassword().equals(password);
    }

}
