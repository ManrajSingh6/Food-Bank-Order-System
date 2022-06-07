/*
@Authors: Manraj Singh, Kartik Sharma, Sajan Hayer, Kirtan Kakadiya
@Version: 2.5
@Since: 1.0
 */

//declaring package statement
package edu.ucalgary.ensf409;

//importing all required libraries
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

//main GenerateOrderForm class
public class GenerateOrderForm implements FormattedOutput{
    private final String ORDER_NAME;                //declaring orderName variable
    private final String ORDER_DATE;                //declaring orderDate variable
    private final String TRANS_REQ;                 //declaring transportationRequirement variable
    private ArrayList<Food> hamper;                 //declaring a hamper variable
    private int counter = 0;

    //declaring count variable for different client types
    private int countAdultMales;
    private int countAdultFemales;
    private int countChildUnder8;
    private int countChildOver8;

    private FileWriter writer;

    //This is the main constructor for the class
    //Sets values for all initialized variables
    public GenerateOrderForm(boolean req, String name, String date, int numAdultMales, int numAdultFemales,
                             int numChildUnder8, int numChildOver8, ArrayList<Food> hamperContent, FileWriter writer,
                             int count){

        if (count > 10 || numAdultMales > 10 || numAdultMales > 10 || numChildOver8 > 10 || numChildUnder8 > 10){
            throw new IllegalArgumentException();
        }

        this.ORDER_DATE = date;
        this.ORDER_NAME = name;
        this.hamper = hamperContent;
        this.writer = writer;
        this.countAdultMales = numAdultMales;
        this.countAdultFemales = numAdultFemales;
        this.countChildUnder8 = numChildUnder8;
        this.countChildOver8 = numChildOver8;
        this.counter = count;

        if (req){
            TRANS_REQ = "Required";
        }
        else{
            TRANS_REQ = "Not Required";
        }

    }

    //Main method to print the order form information and hamper contents to the 'orderform.txt' file
    //When printing the hamper contents (stored in an ArrayList), we loop through the array list and print
    //If an order is not able to fulfill an order due to insufficient items in the database, it prints a statement
    //regarding the reason
    public void printOrderForm() throws IOException {
        writer.write("Calgary General Food Bank");
        writer.write("\n");
        writer.write("Hamper Order Form");
        writer.write("\n\n");
        writer.write("Order Name: "  + ORDER_NAME);
        writer.write("\n");
        writer.write("Order Date: " + ORDER_DATE);
        writer.write("\n");
        writer.write("Transportation Requirement: " + TRANS_REQ);
        writer.write("\n");
        writer.write("Requests:");
        writer.write("\n");
        writer.write("--------------------");
        writer.write("\n");

        writer.write(getFormattedOutput());

        writer.write("Food Items in Hamper # " + counter);
        writer.write("\n");
        if (hamper.size() == 0){
            writer.write("There were not enough food items in the inventory to meet the minimum requirements for this" +
                    " hamper configuration!");
            writer.write("\n");
        }
        for(Food item: hamper){
            writer.write(item.getItemID() + "\t\t\t\t" + item.getFoodName());
            writer.write("\n");
        }
        writer.write("--------------------");
        writer.write("\n\n\n");

    }

    //Method to use the FormattedOutput interface for formatting a portion of the output
    @Override
    public String getFormattedOutput() {
        String string1 = "Number of adult males: " + countAdultMales + "\n" + "Number of adult females: " + countAdultFemales
                + "\n" + "number of children under 8: " + countChildUnder8 + "\n" + "Number of children over 8: " +
                countChildOver8 + "\n\n";

        return string1;
    }
}
