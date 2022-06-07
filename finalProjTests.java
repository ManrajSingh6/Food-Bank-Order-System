/*
@Authors: Manraj Singh, Kartik Sharma, Sajan Hayer, Kirtan Kakadiya
@Version: 1.0
@Since: 1.0
 */

package edu.ucalgary.ensf409;
import org.junit.Test;
import javax.swing.*;
import static org.junit.Assert.*;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class finalProjTests {

    public static int ID = 1;
    public static boolean tChoice = true;
    public static String orderName = "FirstOrder";
    public static String orderDate = "04/01/2022";
    public static int numMales = 1;
    public static int numFem = 1;
    public static int numO8 = 1;
    public static int numU8 = 1;
    public static int size = 4;
    public static String itemID = "22";
    public static String foodName = "Potato";
    public static Nutrition nutriFact;
    public static int wholeGrain = 30;
    public static int fruitVeggiesPer = 15;
    public static int proteinPer = 30;
    public static int otherPer = 25;
    public static JLabel success = new JLabel();
    private static FileWriter writer;
    private static int count = 1;

    static {
        try {
            writer = new FileWriter("testFile");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Nutrition nut = new Nutrition(wholeGrain, fruitVeggiesPer, proteinPer, otherPer);
    Food item = new Food(itemID, foodName, nut);

    // Test the constructor for ApplicationForm with good input
    @Test
    public void testApplicationFormConstructorGood() throws SQLException {
        Inventory inventory = new Inventory(item);
        ApplicationForm form = new ApplicationForm(ID, tChoice, orderName, orderDate, numMales, numFem, numU8, numO8, inventory, success); // Initialize Application Form object
        assertNotNull("ApplicationForm constructor did not create an object when given valid arguments were provided.", form);
    }


    // Test the constructor for ApplicationForm with bad input
    @Test
    public void testApplicationFormConstructorBad() throws SQLException {
        Inventory inventory = new Inventory(item);
        boolean expectedException = false;
        try {
            ApplicationForm form = new ApplicationForm(15, tChoice, orderName, orderDate, numMales, numFem, numU8, numO8, inventory, success);
        } catch (IllegalArgumentException | SQLException e) {
            expectedException = true;
        }
        assertEquals("ApplicationForm constructor did not throw an illegal argument exception when invalid argument were provided.", true, expectedException);
    }

    // Test the constructor for Client with good input
    @Test
    public void testClientConstructorGood() throws SQLException {
        Client client = new Client(ID, numMales, numFem, numO8, numU8, success);
        assertNotNull("Client constructor did not create an object when given valid arguments were provided.", client);
    }

    // Test the constructor for Client with bad input
    @Test
    public void testClientConstructorBad() {
        boolean expectedException = false;
        try {
            Client client = new Client(15, 15, 15, 15, 15, success);
        } catch (IllegalArgumentException | SQLException e) {
            expectedException = true;
        }
        assertEquals("Client constructor did not throw an illegal argument exception when invalid client ID was provided.", true, expectedException);
    }


    // Test the constructor for Hamper with good input
    @Test
    public void testHampersConstructorGood() throws SQLException {
        Client client = new Client(ID, numMales, numFem, numO8, numU8, success);
        Inventory inventory = new Inventory(item);
        Hampers hamper = new Hampers(client, inventory, success);
        assertNotNull("Hampers constructor did not create an object when given valid arguments were provided.", hamper);
    }


    // Test the constructor for GenerateOrderForm with good input
    @Test
    public void testGenerateOrderFormGood() throws SQLException {
        Inventory inventory = new Inventory(item);
        ArrayList<Food> temp = Hampers.getHamperContent();
        ApplicationForm form = new ApplicationForm(ID, tChoice, orderName, orderDate, numMales, numFem, numU8, numO8, inventory, success);
        GenerateOrderForm orderForm = new GenerateOrderForm(tChoice, orderName, orderDate, numMales, numFem, numU8, numO8, temp, writer, count);
        assertNotNull("GenerateOrderForm constructor did not create an object when given valid arguments were provided.", orderForm);
    }

    // Test the constructor for GenerateOrderForm with bad input
    @Test
    public void testGenerateOrderFormBad() throws SQLException {
        boolean expectedException = false;
        Inventory inventory = new Inventory(item);
        ArrayList<Food> temp = Hampers.getHamperContent();
        ApplicationForm form = new ApplicationForm(ID, tChoice, orderName, orderDate, numMales, numFem, numU8, numO8, inventory, success);
        try {
            GenerateOrderForm orderForm = new GenerateOrderForm(tChoice, orderName, orderDate, 12, 13, 14, 15, temp, writer, 12);
        } catch (IllegalArgumentException e) {
            expectedException = true;
        }
        assertEquals("GenerateOrderForm constructor did not throw an illegal argument exception when invalid orderForm argument was provided.", true, expectedException);
    }


    // Test the constructor for Food with good input
    @Test
    public void testFoodGood() {
        Food foodObject = new Food(itemID, foodName, nutriFact);
        assertNotNull("Food constructor did not create an object when given valid arguments were provided.", foodObject);
    }

    // Test the constructor for GenerateHampers with bad input
    @Test
    public void testFoodBad() {
        boolean expectedException = false;
        try {
            Food foodObject = new Food("-5", foodName, nutriFact);
        } catch (IllegalArgumentException e) {
            expectedException = true;
        }
        assertEquals("Food constructor did not throw an illegal argument exception when one or more invalid arguments were provided.", true, expectedException);
    }


    // Test the constructor for Nutrition with good input
    @Test
    public void testNutritionGood() {
        Nutrition nutriObject = new Nutrition(wholeGrain, fruitVeggiesPer, proteinPer, otherPer);
        assertNotNull("Nutrition constructor did not create an object when given valid arguments were provided.", nutriObject);
    }

    // Test the constructor for Nutrition with bad input
    @Test
    public void testNutritionBad() {
        boolean expectedException = false;
        try {
            Nutrition nutriObject = new Nutrition(-5, -10, -15, -20);
        } catch (IllegalArgumentException e) {
            expectedException = true;
        }
        assertEquals("Nutrition constructor did not throw an illegal argument exception when one or more invalid arguments were provided.", true, expectedException);
    }

    // Test the constructor for Inventory with good input
    @Test
    public void testInventoryGood() throws SQLException {
        Inventory inventoryObject = new Inventory(item);
        assertNotNull("Inventory constructor did not create an object when given valid arguments were provided.", inventoryObject);
    }


    //Testing getters for the ApplicationForm class
    @Test
    public void testApplicationFormGetters() throws SQLException {
        Inventory inventory = new Inventory(item);
        ApplicationForm form = new ApplicationForm(ID, tChoice, orderName, orderDate, numMales, numFem, numU8, numO8, inventory, success);

        String name = form.getOrderName();
        String expectedName = "FirstOrder";
        assertEquals("Method getOrderName did not return the expected result: ", expectedName, name);

        String date = form.getOrderDate();
        String expectedDate = "04/01/2022";
        assertEquals("Method getOrderDate did not return the expected result: ", expectedDate, date);
    }

    //Test the getters for the Client class
    @Test
    public void testClientGetters() throws SQLException {
        Client client = new Client(ID, numMales, numFem, numO8, numU8, success);

        //testing getClientID
        int id = client.getClientID();
        int expectedID = 1;
        assertEquals("Method getClientID did not return the expected result: ", expectedID, id);

        //testing getProtein
        int protein = client.getProtein();
        int expectedProtein = 16002;
        assertEquals("Method getProtein did not return the expected result: ", expectedProtein, protein);

        //testing getGrain
        int grain = client.getGrain();
        int expectedGrain = 10332;
        assertEquals("Method getGrain did not return the expected result: ", expectedGrain, grain);

        //testing getFV
        int FV = client.getFV();
        int expectedFV = 17136;
        assertEquals("Method getFV did not return the expected result: ", expectedFV, FV);

        //testing getOther
        int other = client.getOther();
        int expectedOther = 13230;
        assertEquals("Method getOther did not return the expected result: ", expectedOther, other);
    }

    //Testing getters in class Food
    @Test
    public void testFoodGetters() {
        String Name = "Potato";
        Food foodObject = new Food(itemID, foodName, nutriFact);

        String itemID = foodObject.getItemID();
        String expectedID = "22";
        assertEquals("Method getItemId did not return the expected result: ", expectedID, itemID);

        String name = foodObject.getFoodName();
        String expectedName = "Potato";
        assertEquals("Method getFoodName did not return the expected result: ", expectedName, name);
    }

    //Testing getters in class Nutrition
    @Test
    public void testNutritionGetters() {
        Nutrition nutrition = new Nutrition(wholeGrain, fruitVeggiesPer, proteinPer, otherPer);

        int grain = nutrition.getGrainPercentage();
        int expectedgrain = 30;
        assertEquals("Method getGrainPercentage did not return the expected result: ", expectedgrain, grain);

        int fruitVeggies = nutrition.getFVPercentage();
        int expectedFV = 15;
        assertEquals("Method getFVPercentage did not return the expected result: ", expectedFV, fruitVeggies);

        int protein = nutrition.getProteinPercentage();
        int expectedprotein = 30;
        assertEquals("Method getProteinPercentage did not return the expected result: ", expectedprotein, protein);

        int other = nutrition.getOtherPercentage();
        int expectedOther = 25;
        assertEquals("Method getOtherPercentage did not return the expected result: ", expectedOther, other);

    }

}



