/*
@Authors: Manraj Singh, Kartik Sharma, Sajan Hayer, Kirtan Kakadiya
@Version: 2.5
@Since: 1.0
 */

package edu.ucalgary.ensf409;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class Hampers{
    //class variables:
    private Client orderForm;                   // Client variable to access Client class
    private static int totalCalsClient;         // total calories needed for hamper
    private static int totalProteinClient;      // total protein needed for hamper
    private static int totalGrainsClient;       // total grains needed for hamper
    private static int totalFVClient;           // total fruits/veggies needed for hamper
    private static int totalOtherClient;        // total other needed for hamper

    private static int protein;         // hamper protein
    private static int grains;          // hamper grains
    private static int fv;              // hamper FV
    private static int other;           // hamper other

    private static int maxPro;          // used to keep track of maximum protein in the inventory
    private static int maxGrain;        // used to keep track of maximum grains in the inventory
    private static int maxFv;           // used to keep track of maximum fruit/veggies in the inventory
    private static int maxOther;        // used to keep track of maximum other in the inventory

    // Array list of Food used to store hamper contents:
    private static ArrayList<Food> hamperContents1;
    // Inventory object variable:
    private static Inventory inventory;

    // Constructor for hampers, takes in client, inventory, and JLabel arguments:
    public Hampers(Client client, Inventory inventory, JLabel success) throws SQLException{
        // extracting client object variables:
        this.orderForm = client;
        this.totalCalsClient = client.getCalories();
        this.totalProteinClient = client.getProtein();
        this.totalGrainsClient = client.getGrain();
        this.totalFVClient = client.getFV();
        this.totalOtherClient = client.getOther();

        // The following lines are initializing variables with a value of 0:
        this.protein = 0;
        this.grains = 0;
        this.fv =0 ;
        this.other = 0;

        this.maxPro = 0;
        this.maxGrain = 0;
        this.maxFv = 0;
        this.maxOther = 0;

        this.inventory= inventory;

        // Making a copy of the inventory:
        ArrayList<Inventory> copyInventory = inventory.getItems();
        // Initializing hamperContents1 as a new array list:
        hamperContents1 = new ArrayList<>();

        // This for loop iterates the inventory and increments the maximum nutrients available:
        for(Inventory check : copyInventory){
            maxPro += check.getFoodItem().getNutritionalFacts().getProteinPercentage();
            maxGrain += check.getFoodItem().getNutritionalFacts().getGrainPercentage();
            maxFv += check.getFoodItem().getNutritionalFacts().getFVPercentage();
            maxOther += check.getFoodItem().getNutritionalFacts().getOtherPercentage();
        }

        // Calling the method which builds the hamper:
        BuildHamper(maxPro, maxGrain, maxFv,maxOther, copyInventory, hamperContents1, success);

        // The following variables are used to keep track of nutrients in the hamper:
        int proCont = 0;
        int grainCont = 0;
        int fvCont = 0;
        int otherCont = 0;

        // Iterating through hamper contents and incrementing hamper's nutrients:
        for(Food hampItem : hamperContents1){
            proCont += hampItem.getNutritionalFacts().getProteinPercentage();
            grainCont += hampItem.getNutritionalFacts().getGrainPercentage();
            fvCont += hampItem.getNutritionalFacts().getFVPercentage();
            otherCont += hampItem.getNutritionalFacts().getOtherPercentage();
        }

    }

    // getter method to return hamper array list:
    public static ArrayList<Food> getHamperContent(){
        return hamperContents1;
    }

    // maxPro, maxGrain, maxFv, maxOther parameters are passed to keep track of nutrients available in the inventory:
    public static void BuildHamper(int maxPro, int maxGrain, int maxFv, int maxOther,ArrayList<Inventory> copyInventory,
                                   ArrayList<Food> hamperContents, JLabel success)
            throws SQLException{

        // This if statement checks if the requested nutrients exceed the available nutrients, returns if true:
        if(maxPro<totalProteinClient||maxGrain<totalGrainsClient||maxFv<totalFVClient||maxOther<totalOtherClient){
            success.setText("There is not enough food in the inventory to meet the minimum requirements!");
            return;
        }

        // The following booleans are used to keep track of what nutrition requirements are met, initialized as false:
        boolean proMet = false;
        boolean grainsMet = false;
        boolean fvMet = false;
        boolean otherMet = false;

        boolean allTrue = proMet && grainsMet && fvMet && otherMet;

        // Inventory size will keep track of how many items are in the inventory:
        int inventorySize = inventory.getItems().size();

        // The alt boolean will be explained in coming lines
        boolean alt = false;
        // The numOfRuns is used to count how many times the while loop runs:
        int numOfRuns = 0;
        // This while encompasses the algorithm:
        // The while loop continues to run until a valid hamper is found:
        while(allTrue == false){
            // This for loop iterates as many times as there are items in the inventory:
            for(int i = 0; i<inventorySize; i++){
                // Temporary Food object:
                Food tmp = copyInventory.get(i).getFoodItem();
                // If the hamper's nutrition content meets the requirements, return:

                if(proMet && grainsMet && fvMet && otherMet){
                    return;
                }

                if(proMet == false){ //If the hamper does not contain required protein:
                    // This if statement checks if all nutrients apart from protein in a food item are under 1000
                    // it's purpose is to add a food item containing protein
                    //but minimizing all other nutrients to conserve inventory:
                    if(tmp.getNutritionalFacts().getGrainPercentage() + tmp.getNutritionalFacts().getFVPercentage()+
                            tmp.getNutritionalFacts().getOtherPercentage() < 1000 && tmp.getNutritionalFacts().getProteinPercentage()>0){
                        // if the condition is met, add the food item to the hamper:
                        hamperContents.add(tmp);
                        // remove the food item from the inventory as it is no longer available:
                        copyInventory.remove(copyInventory.get(i));
                        // shrink the inventorySize variable by 1:
                        inventorySize--;
                        // incrementing the hamper's nutrient profile by what was contained in the food item:
                        protein += tmp.getNutritionalFacts().getProteinPercentage();
                        grains += tmp.getNutritionalFacts().getGrainPercentage();
                        fv += tmp.getNutritionalFacts().getFVPercentage();
                        other += tmp.getNutritionalFacts().getOtherPercentage();
                    }
                    // This alternate if statement runs if alt is true,
                    // it allows a wider range of food objects to be added to the hamper:
                    else if(alt==true && tmp.getNutritionalFacts().getGrainPercentage() + tmp.getNutritionalFacts().getFVPercentage()+
                            tmp.getNutritionalFacts().getOtherPercentage() < 20000){
                        hamperContents.add(tmp);
                        copyInventory.remove(copyInventory.get(i));
                        inventorySize--;
                        protein += tmp.getNutritionalFacts().getProteinPercentage();
                        grains += tmp.getNutritionalFacts().getGrainPercentage();
                        fv += tmp.getNutritionalFacts().getFVPercentage();
                        other += tmp.getNutritionalFacts().getOtherPercentage();
                    }

                }
                // The following if/else-if statements utilize the same reasoning as for protein above:
                else if(grainsMet == false){
                    if(tmp.getNutritionalFacts().getProteinPercentage() + tmp.getNutritionalFacts().getFVPercentage()+
                            tmp.getNutritionalFacts().getOtherPercentage() < 1000 && tmp.getNutritionalFacts().getGrainPercentage()>0){
                        hamperContents.add(tmp);
                        copyInventory.remove(copyInventory.get(i));
                        inventorySize--;
                        protein += tmp.getNutritionalFacts().getProteinPercentage();
                        grains += tmp.getNutritionalFacts().getGrainPercentage();
                        fv += tmp.getNutritionalFacts().getFVPercentage();
                        other += tmp.getNutritionalFacts().getOtherPercentage();
                    }
                    else if(alt==true && tmp.getNutritionalFacts().getProteinPercentage() + tmp.getNutritionalFacts().getFVPercentage()+
                            tmp.getNutritionalFacts().getOtherPercentage() < 20000){
                        hamperContents.add(tmp);
                        copyInventory.remove(copyInventory.get(i));
                        inventorySize--;
                        protein += tmp.getNutritionalFacts().getProteinPercentage();
                        grains += tmp.getNutritionalFacts().getGrainPercentage();
                        fv += tmp.getNutritionalFacts().getFVPercentage();
                        other += tmp.getNutritionalFacts().getOtherPercentage();
                    }
                }


                else if(fvMet == false){
                    if(tmp.getNutritionalFacts().getGrainPercentage() + tmp.getNutritionalFacts().getProteinPercentage()+
                            tmp.getNutritionalFacts().getOtherPercentage() < 1000 && tmp.getNutritionalFacts().getFVPercentage()>0){
                        hamperContents.add(tmp);
                        copyInventory.remove(copyInventory.get(i));
                        inventorySize--;
                        protein += tmp.getNutritionalFacts().getProteinPercentage();
                        grains += tmp.getNutritionalFacts().getGrainPercentage();
                        fv += tmp.getNutritionalFacts().getFVPercentage();
                        other += tmp.getNutritionalFacts().getOtherPercentage();
                    }
                    else if(alt==true && tmp.getNutritionalFacts().getGrainPercentage() + tmp.getNutritionalFacts().getProteinPercentage()+
                            tmp.getNutritionalFacts().getOtherPercentage() < 20000){
                        hamperContents.add(tmp);
                        copyInventory.remove(copyInventory.get(i));
                        inventorySize--;
                        protein += tmp.getNutritionalFacts().getProteinPercentage();
                        grains += tmp.getNutritionalFacts().getGrainPercentage();
                        fv += tmp.getNutritionalFacts().getFVPercentage();
                        other += tmp.getNutritionalFacts().getOtherPercentage();
                    }
                }

                else if(otherMet == false){
                    if(tmp.getNutritionalFacts().getGrainPercentage() + tmp.getNutritionalFacts().getFVPercentage()+
                            tmp.getNutritionalFacts().getProteinPercentage() < 1000 && tmp.getNutritionalFacts().getOtherPercentage()>0){
                        hamperContents.add(tmp);
                        copyInventory.remove(copyInventory.get(i));
                        inventorySize--;
                        protein += tmp.getNutritionalFacts().getProteinPercentage();
                        grains += tmp.getNutritionalFacts().getGrainPercentage();
                        fv += tmp.getNutritionalFacts().getFVPercentage();
                        other += tmp.getNutritionalFacts().getOtherPercentage();
                    }
                    else if(alt==true && tmp.getNutritionalFacts().getGrainPercentage() + tmp.getNutritionalFacts().getFVPercentage()+
                            tmp.getNutritionalFacts().getProteinPercentage() < 20000){
                        hamperContents.add(tmp);
                        copyInventory.remove(copyInventory.get(i));
                        inventorySize--;
                        protein += tmp.getNutritionalFacts().getProteinPercentage();
                        grains += tmp.getNutritionalFacts().getGrainPercentage();
                        fv += tmp.getNutritionalFacts().getFVPercentage();
                        other += tmp.getNutritionalFacts().getOtherPercentage();
                    }
                }

                else if(proMet == false && grainsMet == false && fvMet == false && otherMet == false){
                    hamperContents.add(tmp);
                    copyInventory.remove(copyInventory.get(i));
                    inventorySize--;
                    protein += tmp.getNutritionalFacts().getProteinPercentage();
                    grains += tmp.getNutritionalFacts().getGrainPercentage();
                    fv += tmp.getNutritionalFacts().getFVPercentage();
                    other += tmp.getNutritionalFacts().getOtherPercentage();
                }
                // The following if statements make the respective boolean variables true
                // when the required nutrition in the hampers is met:

                if(protein >= totalProteinClient){
                    proMet = true;
                }
                if(grains >= totalGrainsClient){
                    grainsMet = true;
                }
                if(fv >= totalFVClient){
                    fvMet = true;
                }
                if(other >= totalOtherClient){
                    otherMet = true;
                }
            }
            // Increment numOfRuns:
            numOfRuns++;
            // if the numOfRuns exceeds the inventory size, we know it has iterated through the entire inventory
            // making alt true
            if(numOfRuns>=copyInventory.size()){
                alt = true;
            }
        }
    }

}
