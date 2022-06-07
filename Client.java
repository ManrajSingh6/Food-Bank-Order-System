/*
@Authors: Manraj Singh, Kartik Sharma, Sajan Hayer, Kirtan Kakadiya
@Version: 2.5
@Since: 1.0
 */

package edu.ucalgary.ensf409;

import javax.swing.*;
import java.sql.*;
import java.util.*;


public class Client {
    //class variables
    private int clientID;
    private int recProtein = 0;
    private int recGrain = 0;
    private int recFV = 0;
    private int recCalories = 0;
    private int recOther = 0;
    private JLabel success;

    //variables to keep track of amount of members
    private int numberOfChildOver8 = 0;
    private int numberOfChildUnder8 = 0;
    private int numberOfFemales = 0;
    private int numberOfMales = 0;

    private HashMap<String,Nutrition> familyList = new HashMap<>();

    //connection variable
    private Connection dbConnect;
    //result variable to store results
    private ResultSet results;

    //constructor for class to set id and values of members
    //intialize connections and set required nutrition amounts
    public Client(
            int id,
            int numberOfMales,
            int numberOfFemales,
            int numberOfChildOver8,
            int numberOfChildUnder8,
            JLabel success
    ) throws SQLException, IllegalArgumentException
    {
        if (id > 10 || numberOfMales > 10 || numberOfFemales > 10 || numberOfChildOver8 > 10 || numberOfChildUnder8 > 10){
            throw new IllegalArgumentException();
        }
        this.clientID = id;
        this.numberOfMales = numberOfMales;
        this.numberOfFemales = numberOfFemales;
        this.numberOfChildUnder8= numberOfChildUnder8;
        this.numberOfChildOver8 = numberOfChildOver8;
        this.success = success;

        initializeConnection();
        getDataFromDatabase();
        setClientDailyNeedValues();
    }

    //getters for class
    public int getClientID(){
        return this.clientID;
    }

    public int getProtein(){
        return this.recProtein;
    }

    public int getGrain(){
        return this.recGrain;
    }

    public int getFV(){
        return this.recFV;
    }

    public int getCalories(){
        return this.recCalories;
    }

    public int getOther(){
        return this.recOther;
    }

    //setting the weekly needs for members of the family
    public void setClientDailyNeedValues(){
        //checks to see if there is adult males in the order
        if(numberOfMales != 0){
            Nutrition maleNeeds = familyList.get("Adult Male");
            //set the required nutrition values for each section for the number of males and for the week
            recProtein += (maleNeeds.getProteinPercentage()) * numberOfMales * 7;
            recFV += (maleNeeds.getFVPercentage()) * numberOfMales * 7;
            recGrain += (maleNeeds.getGrainPercentage())  * numberOfMales * 7;
            recOther += (maleNeeds.getOtherPercentage())  *numberOfMales * 7;
        }

        //the next if statements do the same as the above one but set the other members of the family
        if(numberOfFemales != 0){
            Nutrition femaleNeeds = familyList.get("Adult Female");
            recProtein += (femaleNeeds.getProteinPercentage())  * numberOfFemales * 7;
            recFV += (femaleNeeds.getFVPercentage())  * numberOfFemales * 7;
            recGrain += (femaleNeeds.getGrainPercentage())  * numberOfFemales * 7;
            recOther += (femaleNeeds.getOtherPercentage())  * numberOfFemales * 7;
        }

        if(numberOfChildOver8 != 0){
            Nutrition childrenOver8Needs = familyList.get("Child over 8");
            recProtein += (childrenOver8Needs.getProteinPercentage())   * numberOfChildOver8 * 7;
            recFV += (childrenOver8Needs.getFVPercentage())  * numberOfChildOver8 * 7;
            recGrain += (childrenOver8Needs.getGrainPercentage())  * numberOfChildOver8 * 7;
            recOther += (childrenOver8Needs.getOtherPercentage())   * numberOfChildOver8 * 7;
        }

        if(numberOfChildUnder8 != 0){
            Nutrition chilrenUnder8Needs = familyList.get("Child under 8");
            recProtein += (chilrenUnder8Needs.getProteinPercentage()) * numberOfChildUnder8 * 7;
            recFV += (chilrenUnder8Needs.getFVPercentage())  * numberOfChildUnder8 * 7;
            recGrain += (chilrenUnder8Needs.getGrainPercentage())  * numberOfChildUnder8 * 7;
            recOther += (chilrenUnder8Needs.getOtherPercentage())  * numberOfChildUnder8 * 7;
        }
    }

    //method to initialize connection to the database
    public void initializeConnection() throws SQLException {
        try{
            dbConnect = DriverManager.getConnection("jdbc:mysql://localhost/food_inventory", "student", "ensf409");
        } catch (SQLException e){
            success.setText("Connection to database failed! Exit and try again!");
            throw new SQLException("Cannot connect to the database!");
        }
    }

    //method to get the required amount for adult males, females, children over and under 8 from the database
    public void getDataFromDatabase() throws SQLException {
        initializeConnection();

        try {
            Statement myStatement = dbConnect.createStatement();
            results = myStatement.executeQuery("SELECT * FROM daily_client_needs");

            while (results.next()) {
                int calories = Integer.parseInt(results.getString("Calories"));
                int wholeGrain = (Integer.parseInt(results.getString("WholeGrains")) * calories) / 100;
                int fruitVeggie = (Integer.parseInt(results.getString("FruitVeggies")) * calories) / 100;
                int protein = (Integer.parseInt(results.getString("Protein")) * calories) / 100;
                int other = (Integer.parseInt(results.getString("Other")) * calories) / 100;

                Nutrition nutritionValues = new Nutrition(wholeGrain, fruitVeggie, protein, other);
                familyList.put(results.getString("Client"), nutritionValues);
            }
            myStatement.close();

        } catch (Exception e) {
            success.setText("Connection to database failed! Exit and try again!");
        }
    }

}

