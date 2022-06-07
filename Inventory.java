/*
@Authors: Manraj Singh, Kartik Sharma, Sajan Hayer, Kirtan Kakadiya
@Version: 2.5
@Since: 1.0
 */

//declaring package statement
package edu.ucalgary.ensf409;

//importing libraries
import javax.swing.*;
import java.util.ArrayList;
import java.sql.*;

//main Inventory class
public class Inventory {

    //declaring required variables
    private Food foodItem;
    private String foodQuantity = "";
    private static ArrayList<Inventory> items = new ArrayList<>();
    private Nutrition nutritionalFacts;
    private Connection dbConnect;
    private ResultSet results;
    private JLabel success;

    //main Inventory constructor that receives food item
    public Inventory(Food foodItem) throws IllegalArgumentException, SQLException{
        this.foodItem = foodItem;
    }

    //additional overloaded constructor that also calls a method to get the data from the available food
    public Inventory(JLabel success) throws IllegalArgumentException, SQLException{
        getDataFromAvailableFood();
        this.success = success;
    }

    //method that returns an ArrayList of type Inventory - returns all the items in the array list
    public static ArrayList<Inventory> getItems(){
        return items;
    }

    //returns a foodItem of type Food, this method returns a specific item
    public Food getFoodItem(){
        return foodItem;
    }

    //initializing connection to the database - if the connection is unsuccessful, it prints an error message
    //to the GUI to let users know
    public void initializeConnection() throws SQLException {
        try{
            dbConnect = DriverManager.getConnection("jdbc:mysql://localhost/food_inventory", "student", "ensf409");
        } catch (SQLException e){
            success.setText("Connection to database failed! Exit and try again!");
            throw new SQLException("Cannot connect to the database!");
        }
    }

    //this method retrieves the data from the food that is available in the inventory
    //a new Nutrition object, Food object, and Inventory object is created to store all the
    //data that is retrieved from the database
    public void getDataFromAvailableFood() throws SQLException {
        initializeConnection();
        try {
            Statement myStatement = dbConnect.createStatement();
            results = myStatement.executeQuery("SELECT * FROM available_food");

            while (results.next()) {
                int calories = Integer.parseInt(results.getString("Calories"));

                int wholeGrain = (Integer.parseInt(results.getString("GrainContent")) * calories) / 100;
                int fruitVeggie = (Integer.parseInt(results.getString("FVContent")) * calories) / 100;
                int protein = (Integer.parseInt(results.getString("ProContent")) * calories) / 100;
                int other = (Integer.parseInt(results.getString("Other")) * calories) / 100;

                nutritionalFacts = new Nutrition(wholeGrain, fruitVeggie, protein, other);
                foodItem = new Food(results.getString("ItemID"), results.getString("Name"), nutritionalFacts);
                Inventory tmp = new Inventory(foodItem);
                items.add(tmp);

            }
            myStatement.close();

            //catching an exception, if caught, prints error to GUI
        } catch (Exception e) {
            success.setText("Connection to database failed! Exit and try again!");
        }
    }

    //This method deletes a specific food item from the database by using the food itemID
    //this method is used when the final order form is created and the food items must be deleted to updated
    //the database
    public void deleteItemFromAvailableFood(String itemID) throws SQLException{
        initializeConnection();
        try{
            String query = "DELETE FROM available_food WHERE ItemID = (?)";
            PreparedStatement myStmt = dbConnect.prepareStatement(query);
            myStmt.setString(1, itemID);

            int rowCount = myStmt.executeUpdate();
            if(rowCount == 0){
                throw new SQLException("No rows were changed.");
            }
            myStmt.close();

        } catch (SQLException e) {
            success.setText("Could not delete from database.");
        }

    }

}










