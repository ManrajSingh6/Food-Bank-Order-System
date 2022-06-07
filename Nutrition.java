/*
@Authors: Manraj Singh, Kartik Sharma, Sajan Hayer, Kirtan Kakadiya
@Version: 2.5
@Since: 1.0
 */

//declaring package statement
package edu.ucalgary.ensf409;

//main Nutrition class
public class Nutrition{

    //declaring required variables (percentages)
    private int wholeGrainPercentage = 0;
    private int fruitVeggiePercentage = 0;
    private int proteinPercentage = 0;
    private int otherPercentage = 0;

    //main Nutrition constructor
    public Nutrition(int wholeGrain, int fruitVeggie, int protein, int other) throws IllegalArgumentException{
        if (wholeGrain < 0 || fruitVeggie < 0 || protein < 0 || other < 0){
            throw new IllegalArgumentException();
        }

        this.wholeGrainPercentage = wholeGrain;
        this. fruitVeggiePercentage = fruitVeggie;
        this.proteinPercentage = protein;
        this.otherPercentage = other;


    }

    //returns whole grain percentage of food
    public int getGrainPercentage(){
        return this.wholeGrainPercentage;
    }

    //returns fruits and vegetables percentage of food
    public int getFVPercentage(){
        return this.fruitVeggiePercentage;
    }

    //returns protein percentage of food
    public int getProteinPercentage(){
        return this.proteinPercentage;
    }

    //returns other percentage of food
    public int getOtherPercentage(){
        return this.otherPercentage;
    }

}
