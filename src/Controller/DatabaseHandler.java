package Controller;

//import org.apache.poi.EncryptedDocumentException;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.ss.usermodel.WorkbookFactory;
import Model.User;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.*;

/** <code>DatabaseHandler</code> writes and reads data
 * from database file <i>Database.xlsx</i> */
public class DatabaseHandler {

    // final String filepath = "Database.xlsx";
    final String filepath = "data-DB.xlsx";
    File workbookFile;

    /** Default constructor
     * Sets class attribute <code>File</code> to database file <i>Database.xlsx</i>
     * @throws IOException if database file can not be found or read
     */
    public DatabaseHandler() throws IOException {
        this.workbookFile = new File(filepath);

    }

    /** Adds user to database file <i>Database.xlsx</i>
     * @param newuser the <code>User</code> to be added
     * @throws IOException if database file can not be found or read */
    public void addUser(User newuser) throws IOException {

        try {

            FileInputStream in = openFileInputStream();
            Workbook database = getWorkbook(in);
            Sheet users = getSheet(database,"Users");

            int lastrow = users.getLastRowNum();
            Row row = users.createRow(lastrow + 1);
            String[] data = newuser.userDataToArray(newuser);

            int i = 0;
            for (String string : data) {
                row.createCell(i).setCellValue(string);
                i++;
            }
            in.close();
            FileOutputStream out = new FileOutputStream(workbookFile);
            database.write(out);
            out.close();
            database.close();


        } catch (IOException | EncryptedDocumentException ex) {
            ex.printStackTrace();
        }


    }

    /**
     * Adds new course to excelfile.
     * @param user user
     * @param course course that is added
     * @author Fredrik Pettersson
     * @throws IOException if database file can not be found or read
     */
    public void addCourse(User user, String course) throws IOException {
        try{
            FileInputStream in = openFileInputStream();
            Workbook database = getWorkbook(in);
            Sheet sheet = getSheet(database,"Courses");

            int lastRow = sheet.getLastRowNum();
            Row row = sheet.createRow(lastRow + 1);

            row.createCell(0).setCellValue(user.getUsername());
            row.createCell(1).setCellValue(course);

            in.close();
            FileOutputStream out = new FileOutputStream(workbookFile);
            database.write(out);
            out.close();
            database.close();


        } catch (IOException | EncryptedDocumentException ex) {
            ex.printStackTrace();
        }
    }
    public boolean courseAlreadyAdded(User user, String course){

        try {
            FileInputStream in = openFileInputStream();
            Workbook database = getWorkbook(in);
            Sheet users = getSheet(database, "Courses");

            int lastrow = users.getLastRowNum();
            String username;
            String courseName;
            for (int i = 0; i <= lastrow; i++) {
                username = users.getRow(i).getCell(0).getStringCellValue();
                courseName = users.getRow(i).getCell(1).getStringCellValue();
                if (username.equals(user.getUsername()) && courseName.equals(course)) {
                    in.close();
                    database.close();
                    return true;
                }
                in.close();
                database.close();
            }
        }
        catch (EncryptedDocumentException | IOException ex) {
            ex.printStackTrace();
        }
        return false;

    }

    public FileInputStream openFileInputStream(){
        FileInputStream in = null;
        try{
            in = new FileInputStream(workbookFile);
            return in;
        }
        catch (IOException e){

        }
        return in;

    }

    /**
     * Creates a Workbook instance from FileInputStream passed to it
     * @author Eira Birkhammar
     * @param in the database file
     * @return a workbork generated from database file
     */
    public Workbook getWorkbook(FileInputStream in){
        Workbook database = null;
        try{
            database = WorkbookFactory.create(in);
            return database;
        }
        catch (IOException e){

        }
        return database;

    }

    public Sheet getSheet(Workbook workbook, String sheetname) throws IOException{
        Sheet users = null;
        try{
            users = workbook.getSheet(sheetname);
            return users;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;

    }

    /** Checks if a certain username is already in use
     * @param username the username as a String
     * @return returns true if username is already in use
     * @author Fredrik Pettersson
     * @author Dennis Tanaka
     * @throws FileNotFoundException if database file can not be found or read
     */
    private boolean userCheck(String username) throws IOException {
        if(getRow(username) > -1){
            return true;
        }
        return false;
    }

    /**
     * Returns the row number of the user
     * @param username as a String
     * @return the row number, if row number doesn't exists return -1
     * @throws IOException DB exception
     */
    private int getRow(String username) throws IOException {

        try {
            FileInputStream in = openFileInputStream();
            Workbook database = getWorkbook(in);
            Sheet users = getSheet(database, "Users");

            int lastrow = users.getLastRowNum();
            String curUsername;
            for (int i = 0; i <= lastrow; i++) {
                curUsername = users.getRow(i).getCell(0).getStringCellValue();
                if (curUsername.equals(username)) {
                    in.close();
                    database.close();
                    return i;
                }
                in.close();
                database.close();
            }
        }
        catch (EncryptedDocumentException | IOException ex) {
            ex.printStackTrace();
        }
        return -1;
    }

    /**
     * Gets the user from the DB as a User object
     * @param username as a String
     * @return User as a object User
     * @author Fredrik Pettersson
     * @author Dennis Tanaka
     * @throws IOException Exception
     */
    public User getUser(String username) throws IOException {
        FileInputStream in = openFileInputStream();
        Workbook database = getWorkbook(in);
        Sheet users = getSheet(database, "Users");

        User user = new User();
        user.setUsername(username);

        user.setPassword(users.getRow(getRow(username)).getCell(1).getStringCellValue());
        user.setEmail(users.getRow(getRow(username)).getCell(2).getStringCellValue());

        return user;
    }

    /**
     * Checks if user already exists when register new user
     * @param username username as a String
     * @return true if username exists else false
     * @author Fredrik Pettersson
     * @author Dennis Tanaka
     * @throws FileNotFoundException if database file can not be found or read
     */
    public boolean userExists(String username) throws IOException {
        if(userCheck(username)){
            //  System.out.println("The username " + username + " already exists!");
            return true;
        }
        return false;
    }

    /**
     * Checks if user is in DB when logging in
     * @param username username as a String
     * @return true if user exists else false
     * @author Fredrik Pettersson
     * @author Dennis Tanaka
     * @throws FileNotFoundException if database file can not be found or read
     */
    public boolean userExistsLogIn(String username) throws IOException {
        return userCheck(username);
    }
}