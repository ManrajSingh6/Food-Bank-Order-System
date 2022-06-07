/*
@Authors: Manraj Singh, Kartik Sharma, Sajan Hayer, Kirtan Kakadiya
@Version: 2.5
@Since: 1.0
 */

//declaring package statement
package edu.ucalgary.ensf409;

//importing libraries
import javax.swing.*;
import java.sql.SQLException;

//main ApplicationForm class
public class ApplicationForm {
    //declaring required variables
    private Client client;
    private static Hampers mainHamper;
    private int ID;
    private boolean TRANSPORT_REQUIREMENT;
    private String ORDER_NAME;
    private String ORDER_DATE;
    private int countAdultMales;
    private int countAdultFemales;
    private int countChildUnder8;
    private int countChildOver8;

    //main constructor for the ApplicationForm class
    public ApplicationForm(int id, boolean transportChoice, String orderName, String orderDate, int numAdultMales,
                           int numAdultFemales, int numChildUnder8, int numChildOver8, Inventory inventory, JLabel success) throws SQLException, IllegalArgumentException {

        if (id > 10 || numAdultFemales > 10 || numAdultMales > 10 || numChildOver8 > 10 || numChildUnder8 > 10){
            throw new IllegalArgumentException();
        }

        this.ID = id;
        this.TRANSPORT_REQUIREMENT = transportChoice;
        this.ORDER_NAME = orderName;
        this.ORDER_DATE = orderDate;
        this.countAdultMales = numAdultMales;
        this.countAdultFemales = numAdultFemales;
        this.countChildUnder8 = numChildUnder8;
        this.countChildOver8 = numChildOver8;

        //creating a new Client object
        client = new Client(ID, countAdultMales, countAdultFemales, countChildOver8, countChildUnder8, success);
        //creating a new Hamper object, which uses the Client object to create it
        mainHamper = new Hampers(client, inventory, success);
    }

    //method to return the ID of each application form
    public int getID(){
        return ID;
    }

    //method to return the orderName of each order form
    public String getOrderName() {
        return ORDER_NAME;
    }

    //method to return the orderDate of each order form
    public String getOrderDate() {
        return ORDER_DATE;
    }


}
