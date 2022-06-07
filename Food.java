/*
@Authors: Manraj Singh, Kartik Sharma, Sajan Hayer, Kirtan Kakadiya
@Version: 2.5
@Since: 1.0
 */

//declaring package statement
package edu.ucalgary.ensf409;

//main Food class
public class Food {

    //declaring required variables
    private String itemID = "";
    private String foodName = "";
    private Nutrition nutritionalFacts;

    //main Food constructor
    public Food(String itemID, String foodName, Nutrition nutritionalFacts) throws IllegalArgumentException{

        if (Integer.parseInt(itemID) < 0){
            throw new IllegalArgumentException();
        }

        this.itemID = itemID;
        this.foodName = foodName;
        this.nutritionalFacts = nutritionalFacts;


    }

    //returns the itemID for a food
    public String getItemID(){
        return this.itemID;
    }

    //returns the name of a food
    public String getFoodName(){
        return this.foodName;
    }

    //returns nutritional facts of a food
    public Nutrition getNutritionalFacts(){
        return this.nutritionalFacts;
    }

}
