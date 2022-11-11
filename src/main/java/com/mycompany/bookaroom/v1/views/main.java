/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.bookaroom.v1.views;

import bookaroom.v1.controllers.UserController;
import bookaroom.v1.controllers.LoginController;
import bookaroom.v1.controllers.RoomController;
import bookaroom.v1.controllers.CommentController;
import bookaroom.v1.database.MockDatabase;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;

/**
 *
 * @author Team BookARoom
 */
public class main {

    private static final Scanner sc = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("Welcome to the BookARoom hotel website!");
        homePage();
    }
        private static void homePage() {
        String choice, username, password, firstName, lastName, email, ccexpirationdate, cccode, ccnumber;
        //String ver;
        
        do {
            System.out.println("Enter:"
                    + "\n[q] to quit the application"
                    + "\n[1] to login"
                    + "\n[2] to create a user account"
                    + "\n[3] to see rooms in the hotel");
            choice = sc.nextLine();
            switch (choice) {
                case "1":
                    System.out.println("Enter your username:");
                    username = sc.nextLine();
                    System.out.println("Enter your password:");
                    password = sc.nextLine();
                    LoginController.setUsername(username);
                    LoginController.setPassword(password);
                    LoginController.userLogsIn();
                    if (LoginController.getUserLoggedIn() != null) {
                        userHomePage();
                    }
                    break;
                case "2":
                    System.out.println("Enter a username:");
                    username = sc.nextLine();
                    System.out.println("Enter a first name:");
                    firstName = sc.nextLine();
                    System.out.println("Enter a last name:");
                    lastName = sc.nextLine();
                    System.out.println("Enter an email:");
                    email = sc.nextLine();
                    System.out.println("Enter a password:");
                    password = sc.nextLine();
                    System.out.println("PAYMENT INFORMATION");
                    //CCnumber:
                    ccnumber = "";
                    String code1 = "";
                    boolean numCorrect = false;
                    while(!numCorrect){

                            System.out.println("Enter a credit card number (16-digit number):"); 
                            code1 = sc.nextLine();
                            if (code1.length()!=16) {
                                System.out.println("The credit card number should a 16-digit number, yours is a "+code1.length()+"-digit number. Please enter it again");
                                
                            }
                            else if (code1.length()==16)
                                numCorrect =true;
                                ccnumber = code1;
                      }    
                    //CCcode:
                    cccode = "";
                    String code2 = "";
                    boolean codeCorrect = false;
                    while(!codeCorrect){
                        
                        System.out.println("Enter a verification code (3-digit number):");
                        code2 = sc.nextLine();
                        if (code2.length()!=3) {
                            System.out.println("The credit card number should a 3-digit number, yours is a "+code2.length()+"-digit number. Please enter it again");

                        }
                        else if (code2.length()==3)
                            codeCorrect =true;
                            cccode = code2;
                      }
                    
                    // Maybe if it fails here the user needs to put a new credit card so restart from ccnumber
                    ccexpirationdate = "";
                    boolean CCDateValid = false;
                    while(!CCDateValid){
                        System.out.println("Enter an expiration date (month/year => MM/yy):");
                        ccexpirationdate = sc.nextLine();
                        
                        try
                        {
                            YearMonth ccexpdateFormat = YearMonth.parse(ccexpirationdate, UserController.getFormatter()); 
                            System.out.println(ccexpirationdate+" is valid date format.");
                            boolean valid = UserController.getCurrentTime().isBefore(ccexpdateFormat);
                            
                            if (valid==true) {
                                System.out.println("Credit Card is still valid.");
                                CCDateValid = true;
                        } else {
                            System.out.println("Credit Card has expired.");
                        }
                        }
                        catch (DateTimeParseException e)
                        {
                            System.out.println(ccexpirationdate+" is not a valid Date format.");
                        }
                    }
                    UserController.setUsername(username);
                    UserController.setFirstName(firstName);
                    UserController.setLastName(lastName);
                    UserController.setEmail(email);
                    UserController.setPassword(password);
                    UserController.setCCnumber(ccnumber);
                    UserController.setCCcode(cccode);
                    UserController.setCCexpirationDate(ccexpirationdate);
                    UserController.createAUser();
                    break;
                case "3":
                    System.out.println(RoomController.getRooms());
                    break;
                case "q":
                    System.out.println("Quitting...");
                    break;
                default:
                    System.out.println("Choice = " + choice + " does not exist.");
                    break;
            }
        } while (!choice.equals("q"));
        }
    public static void userHomePage() {
        String choice, subChoice, roomName = null, comment, dayArrival = null, dayDeparture = null, r, roomNameTemp = null;
        LocalDate dayArrivalD, dayDepartureD;
        long difDays = 0;
        double totalprice;

        do {
            System.out.println("Enter:"
                    + "\n[q] to log out"
                    + "\n[1] to see rooms in the Hotel and add one to Booking"
                    + "\n[2] to remove a room from Booking"
                    + "\n[3] to see Room in Booking and confirm it"
                    + "\n[4] to see website comments and add one"
                    + "\n[5] to show/+change user information");
                   
                    //TODO if we finished the rest, do restaurants preferences
            choice = sc.nextLine();
            switch (choice) {
                case "1":
                    System.out.println(RoomController.getRooms());
                    do {
                        System.out.println("Enter: "
                                + "\n[q] to go back"
                                + "\n[1] to add room from the hotel to Booking");
                        subChoice = sc.nextLine();
                        
                        switch (subChoice) {
                            case "1":
                                boolean roomnameValid = false;
                                while(roomnameValid == false) {
                                    System.out.println("Enter the name of the room:");
                                    roomName = sc.nextLine();
                                    RoomController.setRoomName(roomName);
                                    if (RoomController.checkRoomExists() == false){
                                        System.out.println("The room does not exist. Please Try again");
                                    } else { roomnameValid = true;
                                                }
                                }
                                dayArrival = "";
                                dayDeparture = "";
                                                           
                                boolean DADateValid = false;
                                while(!DADateValid){
                                    System.out.println("Enter the name date of arrival: (day,month,year = dd/mm/yyyy)");
                                    dayArrival = sc.nextLine();

                                    try
                                    {
                                        LocalDate dadateFormat = LocalDate.parse(dayArrival, UserController.getDateFormatter()); 
                                        boolean valid = UserController.getCurrentTimeLong().isBefore(dadateFormat);

                                        if (valid==true) {
                                            System.out.println("The arrival day will be "+dayArrival+".");
                                            
                                            DADateValid = true;
                                    } else {
                                        System.out.println(dayArrival+" has already passed. Today's date is " +UserController.getCurrentTimeLong()+".");
                                    }
                                    }
                                    catch (DateTimeParseException e)
                                    {
                                        System.out.println(dayArrival+" is not a valid Date format.");
                                    }
                                }
                                
                                boolean DDDateValid = false;
                                while(!DDDateValid){
                                    System.out.println("Enter the name date of departure: (day,month,year = dd/mm/yyyy)");
                                    dayDeparture = sc.nextLine();

                                    try
                                    {
                                        LocalDate dayArrivalFormat = LocalDate.parse(dayArrival, UserController.getDateFormatter());
                                        LocalDate dayDepartureFormat = LocalDate.parse(dayDeparture, UserController.getDateFormatter()); 
                                        boolean valid = dayArrivalFormat.isBefore(dayDepartureFormat);

                                        if (valid==true) {
                                            System.out.println("The departure day will be "+dayDeparture+".");
                                            
                                            DDDateValid = true;
                                    } else {
                                        System.out.println("The departure day " +dayDeparture+ 
                                                " should later then the arrival day which is "+dayArrival+ ".");
                                    }
                                    }
                                    catch (DateTimeParseException e)
                                    {
                                        System.out.println(dayDeparture+" is not a valid Date format.");
                                    }
                                }
                               /*
                                try
                                    {
                                        LocalDate dayArrivalFormat = LocalDate.parse(dayArrival, UserController.getDateFormatter());
                                        LocalDate dayDepartureFormat = LocalDate.parse(dayDeparture, UserController.getDateFormatter()); 
                                        List<LocalDate> bookedDateTest = dayArrivalFormat.datesUntil(dayDepartureFormat).collect(Collectors.toList());
                                        HashMap<String, List<LocalDate>> HmapTest;
                                        HmapTest.
                                        boolean valid = dmap.containsKey("Unknown Friend");

                                        if (valid==true) {
                                            System.out.println("The departure day will be "+dayDeparture+".");
                                    } else {
                                        System.out.println("The departure day " +dayDeparture+ 
                                                " should later then the arrival day which is "+dayArrival+ ".");
                                    }
                                    }
                                    catch (DateTimeParseException e)
                                    {
                                        System.out.println(dayDeparture+" is not a valid Date format.");
                                    }
                               */
                                //set up dates other way
                                dayArrivalD = LocalDate.parse(dayArrival, UserController.getDateFormatter());  
                                dayDepartureD = LocalDate.parse(dayDeparture, UserController.getDateFormatter());
                                List<LocalDate> bookedDate = dayArrivalD.datesUntil(dayDepartureD).collect(Collectors.toList());
                                difDays = ChronoUnit.DAYS.between(dayArrivalD , dayDepartureD);
                                //end
                                
                                // Dico with all the dates for a specific room
                                RoomController.getBookRoomAndDates(roomName, bookedDate);
                                
                                HashMap<String, List<LocalDate>> Hmap;
                                Hmap = RoomController.getBookRoomAndDates(roomName, bookedDate);
                                
                                // the list with only the dates that the room are booked
                                HashMap<String, List<LocalDate>> Map2;
                                Map2 = new HashMap<>(); // get the list of dates and make a putAll and then compare the 2 (LoginController.getUserLoggedIn().getBooking().toString())
                                Map2.putAll(Hmap); // issu is that it doesn't give any expection message
                                
                                System.out.println("map1 == map2 : " + Hmap.equals(Map2)); // to compare the two, if they are not the same it returns false
                                
                                RoomController.setBookRoomAndDates(Hmap);
                                System.out.println(RoomController.getBookRoomAndDates(roomName, bookedDate));
                                
                                //RoomController.getDatesBetween(dadateFormat,dddateFormat);
                                RoomController.setRoomName(roomName);
                                RoomController.setRoomDayArrival(dayArrival);
                                RoomController.setRoomDayDeparture(dayDeparture);
                                RoomController.setRoomDayDates(bookedDate);
                                RoomController.addRoomToBooking();
                                totalprice = RoomController.getRoomPriceTest();
                                System.out.println(" You have booked "+ roomName +"."
                                        +"\n The arrival day is "+ dayArrival +"."
                                        +"\n The departure is " + dayDeparture +"."
                                        +"\n Your stay will last "+difDays+" nights."
                                        +"\n The total price of your stay will be "+ (difDays * totalprice) +".-"
                                        + "test booked Dates: " +bookedDate);
                                totalprice = 0;
                                
                                break;  
  
                            case "q":
                                break;
                            default:
                                System.out.println("Choice = " + subChoice + " does not exist.");
                                break;
                        }
                    } while (!subChoice.equals("q"));
                    break;
                case "2":
                    System.out.println("Here are the products in Booking, write the name of the room you want to remove.");
                    System.out.println(LoginController.getUserLoggedIn().getBooking().toString());
                    System.out.println("Room name:");
                    roomName = sc.nextLine();
                    RoomController.setRoomName(roomName);
                    RoomController.removeRoomFromBooking();
                    break;
                case "3":
                    System.out.println("Here are the room(s) that you have booked.");
                    System.out.println(LoginController.getUserLoggedIn().getBooking().toString());
                    //System.out.println(LoginController.getUserLoggedIn().getBooking().getDatesBooked());
                    
                    //System.out.println(LoginController.getUserLoggedIn().getBooking().getArrDay());
                    //System.out.println(LoginController.getUserLoggedIn().getBooking().getDepartDay());
                    
                    System.out.println("Here is the total amount for all the room(s) that you have booked.");
                    // TODO Total amount for the room(s) that the user has booked
                    //System.out.println(LoginController.getUserLoggedIn().getTotalPrice().toString());
                    System.out.println("Current time :"+UserController.getCurrentTime());
                    System.out.println(LoginController.getUserLoggedIn().getBooking().getArrivalDateBooking());
                    System.out.println(LoginController.getUserLoggedIn().getBooking().getDepartureDateBooking());
                    System.out.println("Credit Card expiration date:"+LoginController.getUserLoggedIn().getCCExpirationDate());
                    YearMonth ccexpdateInputFormat = YearMonth.parse(LoginController.getUserLoggedIn().getCCExpirationDate(), UserController.getFormatter());
                    var CCexpdate = ccexpdateInputFormat.plusMonths(1);
                    // TODO: put this in exception invalidcreditcard and if the credit Card has expired, ask to update with a new credit card 
                    boolean expired = UserController.getCurrentTime().isBefore(CCexpdate);
                    if (expired==true) {
                        System.out.println("Credit Card is still valid.");
                    } else {
                        System.out.println("Credit Card has expired.");
                    } 
                    //UserController.completeBooking();
                    //RoomController.setRoomName(roomName);
                    //RoomController.removeRoomFromBooking();
                    break;
                    		
                case "4":
                    System.out.println(CommentController.getComments());
                    do {
                        System.out.println("Enter: "
                                + "\n[q] to go back"
                                + "\n[1] to add a comment");
                        subChoice = sc.nextLine();
                        switch (subChoice) {
                            case "1":
                                System.out.println("Enter your comment:");
                                comment = sc.nextLine();
                                CommentController.setComment(comment);
                                CommentController.addCommentFromUser();
                                System.out.println("Comment added!");
                                System.out.println(CommentController.getComments());
                                break;
                            case "q":
                                break;
                            default:
                                System.out.println("Choice = " + subChoice + " does not exist.");
                                break;
                        }
                    } while (!subChoice.equals("q"));
                    break;
                case "5":
                    System.out.println(LoginController.getUserLoggedIn().toString());
                        do {
                        System.out.println("Enter: "
                                + "\n[q] to go back"
                                + "\n[1] to update user information");
                        subChoice = sc.nextLine();
                        switch (subChoice) {
                            case "1":
                                String nusername, npassword, nfirstName, nlastName, nemail, nccexpirationdate, ncccode, nccnumber;
                                System.out.println("Update username:");
                                nusername = sc.nextLine();
                                System.out.println("Update first name:");
                                nfirstName = sc.nextLine();
                                System.out.println("Update last name:");
                                nlastName = sc.nextLine();
                                System.out.println("Update email:");
                                nemail = sc.nextLine();
                                System.out.println("Update password:");
                                npassword = sc.nextLine();
                                
                                //new CCnumber:
                                nccnumber = "";
                                String code1 = "";
                                boolean numCorrect = false;
                                while(!numCorrect){

                                    System.out.println("Update credit card number (16-digit number):"); 
                                    code1 = sc.nextLine();
                                    if (code1.length()!=16) {
                                        System.out.println("The credit card number should a 16-digit number, yours is a "+code1.length()+"-digit number. Please enter it again");
                                
                                        }
                                    else if (code1.length()==16)
                                        numCorrect =true;
                                    nccnumber = code1;
                                }   
                                
                                
                                 //new CCcode:
                                ncccode = "";
                                String code2 = "";
                                boolean codeCorrect = false;
                                while(!codeCorrect){
                        
                                    System.out.println("Enter a verification code (3-digit number):");
                                    code2 = sc.nextLine();
                                    if (code2.length()!=3) {
                                        System.out.println("The credit card number should a 3-digit number, yours is a "+code2.length()+"-digit number. Please enter it again");

                                        }
                                    else if (code2.length()==3)
                                    codeCorrect =true;
                                ncccode = code2;
                                }
                                
                                //new CCexpirationdate
                                nccexpirationdate = "";
                                boolean CCDateValid = false;
                                while(!CCDateValid){
                                System.out.println("Update expiration date (month/year => MM/yy):");
                                nccexpirationdate = sc.nextLine();
                        
                                try
                                {
                                    YearMonth ccexpdateFormat = YearMonth.parse(nccexpirationdate, UserController.getFormatter()); 
                                    System.out.println(nccexpirationdate+" is valid date format.");
                                    boolean valid = UserController.getCurrentTime().isBefore(ccexpdateFormat);
                            
                                    if (valid==true) {
                                        System.out.println("Credit Card is still valid.");
                                        CCDateValid = true;
                                    } 
                                    else {
                                        System.out.println("Credit Card has expired.");
                                    }
                                }
                                catch (DateTimeParseException e)
                                {
                                    System.out.println(nccexpirationdate+" is not a valid Date format.");
                                }
                                }
                  
                                
                                UserController.setUsername(nusername);
                                UserController.setFirstName(nfirstName);
                                UserController.setLastName(nlastName);
                                UserController.setEmail(nemail);
                                UserController.setPassword(npassword);
                                UserController.setCCnumber(nccnumber);
                                UserController.setCCcode(ncccode);
                                UserController.setCCexpirationDate(nccexpirationdate);
                                
                                MockDatabase.getInstance().removeAUser(LoginController.getUserLoggedIn());
                                LoginController.userLogsout();
                                UserController.createAUser();
                                LoginController.setUsername(nusername);
                                LoginController.setPassword(npassword);
                                LoginController.userLogsIn();
                                
                                break;
                           
                            case "q":
                                break;
                            default:
                                System.out.println("Choice = " + subChoice + " does not exist.");
                                break;
                        }
                    } while (!subChoice.equals("q"));
                    
                    break;
                    
                  
                case "q":
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Choice = " + choice + " does not exist.");
                    break;
            }
        } while (!choice.equals("q"));
        LoginController.userLogsout();
    }
}





