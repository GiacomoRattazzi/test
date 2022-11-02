package bookaroom.v1.controllers;

import bookaroom.v1.exceptions.AlreadyExistsException;
import bookaroom.v1.exceptions.DoesNotExistException;
import bookaroom.v1.models.User;
import bookaroom.v1.database.MockDatabase;
//To change to InvalidCreditCard: import bookaroom.v1.exceptions.InsufficientBalanceException;

/**
 * Software Architectures | DOPLab | UniL
 *
 * @author Team BookARoom
 */

//Everything from BookingCart: 
public class UserController {

    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private int password;
    private int CCnumber;
    private int CCcode;
    private String CCexpirationdate;
    //TODO: set up booking class:
    //private booking booking;

    public static void createAUser() {
        try {
            if (!emailExists() && !usernameExists()) {
                MockDatabase.addAUser(new User(username, firstName, lastName, email, password));
            }
        } catch (AlreadyExistsException | DoesNotExistException ex) {
            System.out.println(ex.getMessage());
        }
    }
    /* BCDelete:
    public static void depositMoney() {
        LoginController.getUserLoggedIn().increaseBalance(amount);
    */
    
    /*To add:
    public static void completeShopping() {
        try {
            LoginController.getUserLoggedIn().completeShopping();
        /* Change to InvalidCreditCard:
        } catch (InsufficientBalanceException ex) {
            System.out.println(ex.getMessage());
        }
    }
        */

    protected static User findByUsername(String username) throws DoesNotExistException {
        for (User user : MockDatabase.getUsers()) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        throw new DoesNotExistException("The user " + username + " does not exist.");
    }

    protected static boolean emailExists() throws AlreadyExistsException {
        for (User user : MockDatabase.getUsers()) {
            if (user.getEmail().equals(email)) {
                throw new AlreadyExistsException("The email " + email + " already in use.");
            }
        }
        return false;
    }

    protected static boolean usernameExists() throws DoesNotExistException {
        for (User user : MockDatabase.getUsers()) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }
    
    //BCDelete:
    public static double getAmount() {
        return amount;
    }

    public static String getEmail() {
        return email;
    }

    public static String getFirstName() {
        return firstName;
    }

    public static String getLastName() {
        return lastName;
    }

    public static String getPassword() {
        return password;
    }

    public static String getUsername() {
        return username;
    }
    
    //BCDelete: 
    public static void setAmount(double amount) {
        UserController.amount = amount;
    }

    public static void setEmail(String email) {
        UserController.email = email;
    }

    public static void setFirstName(String firstName) {
        UserController.firstName = firstName;
    }

    public static void setLastName(String lastName) {
        UserController.lastName = lastName;
    }

    public static void setPassword(String password) {
        UserController.password = password;
    }

    public static void setUsername(String username) {
        UserController.username = username;
    }

}
