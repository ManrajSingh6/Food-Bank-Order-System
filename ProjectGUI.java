/*
@Authors: Manraj Singh, Kartik Sharma, Sajan Hayer, Kirtan Kakadiya
@Version: 2.5
@Since: 1.0
 */

//declaring the package statement
package edu.ucalgary.ensf409;

//importing required libraries
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

//main ProjectGUI class to generate the GUI components and implement actions
public class ProjectGUI extends JFrame implements ActionListener{

    //declaring all required variables (frames, buttons, global vars etc.)
    //for the GUI, we are not using a pre-designed layout, we are designing a custom layout

    private static JFrame frame;                //main frame for GUI
    private static JPanel panel;                //main panel for GUI
    private static JLabel welcomeLabel;
    private static JLabel orderName;
    private static JTextField orderNameField;
    private static JLabel orderDate;
    private static JTextField orderDateField;
    private static JLabel family;
    private static JLabel adultMale;
    private static JLabel adultFemale;
    private static JLabel chUnder8;
    private static JLabel chOver8;
    private static JLabel success;
    private static JComboBox combo1;
    private static JComboBox combo2;
    private static JComboBox combo3;
    private static JComboBox combo4;
    private static JLabel choiceOfTrans;
    private static JRadioButton boxYes;
    private static JRadioButton boxNo;
    private static ButtonGroup group1;
    private static JButton generateForm;
    private static JButton endProgram;
    private static JButton genForm;
    private static int count = 0;
    private static JTextArea textArea;
    private static JScrollPane scrollBar;
    private static final String[] array = {"0","1","2","3","4","5","6","7","8","9","10"};
    private static FileWriter writer;

    private static Inventory inventory;     //creating an inventory object to be passed to other classes

    static {
        try {
            inventory = new Inventory(success);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //the following variables are for the user input through the GUI
    //these variables will be later passed onto the ApplicationForm class where the application form
    //for the order is generated
    private boolean choice;
    private String familyName;
    private String currDate;
    private int numAdultMales;
    private int numAdultFemales;
    private int numChildUnder8;
    private int numChildOver8;

    public ProjectGUI(){}

    //main function for the ProjectGUI class
    public static void main(String[] args) throws IOException {
        writer = new FileWriter("orderform.txt");

        //setting the look and feel of the GUI to a predefined look and feel
        try{
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        }catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException exception){
            exception.printStackTrace();
        }

        //declaring all the required fonts and colors for the GUI
        //the theme was selected using a Color theme picker online
        Font fontHeader = new Font(Font.DIALOG, Font.BOLD, 20);
        Font secondHeader = new Font(Font.DIALOG, Font.BOLD, 15);
        Font textFont = new Font(Font.DIALOG, Font.PLAIN, 12);
        Color bgColor = new Color(17, 75, 95, 255);
        Color textColor = new Color(198, 218, 191);
        Color buttonColor = new Color(136, 212, 152);
        Color buttonTextColor = new Color(26, 147, 111);

        //creating the main frame and panel for the GUI and setting sizes
        //also setting a default close operation for the program when the user exits the menu
        frame = new JFrame("Order Window");
        frame.setSize(500, 700);
        frame.setLocationRelativeTo(null);
        panel = new JPanel();
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //setting background color for the main panel and declaring a null layout to
        //create the custom layout
        panel.setBackground(bgColor);
        panel.setLayout(null);
        frame.add(panel);

        //welcome label - main title for GUI
        welcomeLabel = new JLabel("Welcome to the Application Form!");
        welcomeLabel.setFont(fontHeader);
        welcomeLabel.setForeground(textColor);
        welcomeLabel.setBounds(90,10,400,25);
        panel.add(welcomeLabel);

        //label declaration and adding to panel
        choiceOfTrans = new JLabel("Do you require delivery?");
        choiceOfTrans.setFont(secondHeader);
        choiceOfTrans.setForeground(textColor);
        choiceOfTrans.setBounds(5, 50, 300, 25);
        panel.add(choiceOfTrans);

        //adding radio buttons to ask for user input for transportation required
        boxYes = new JRadioButton("Yes");
        boxNo = new JRadioButton("No");
        boxNo.setForeground(textColor);
        boxYes.setForeground(textColor);
        boxYes.setBounds(5, 80, 100, 25);
        boxNo.setBounds(50, 80, 100, 25);
        boxNo.setActionCommand("No");
        boxYes.setActionCommand("Yes");
        panel.add(boxNo);
        panel.add(boxYes);

        //creating a label and input for order name
        orderName = new JLabel("Please enter a name for the current order (optional):");
        orderName.setFont(secondHeader);
        orderName.setForeground(textColor);
        orderName.setBounds(5, 120, 600, 25);
        orderNameField = new JTextField(100);
        orderNameField.setBounds(4, 145, 100, 30);
        panel.add(orderName);
        panel.add(orderNameField);

        //creating a label and input for order date
        orderDate = new JLabel("Please enter the current date (MM/DD/YYYY) (optional):");
        orderDate.setForeground(textColor);
        orderDate.setFont(secondHeader);
        orderDate.setBounds(5, 185, 600, 25);
        orderDateField = new JTextField(300);
        orderDateField.setBounds(4, 210, 100, 30);
        panel.add(orderDate);
        panel.add(orderDateField);

        //creating a label and input for family size
        family = new JLabel("Please enter your family size: ");
        family.setFont(secondHeader);
        family.setBounds(5, 250, 300, 25);
        family.setForeground(textColor);
        panel.add(family);

        //creating a label and input for number of adult males
        adultMale = new JLabel("Number of Adult Males: ");
        adultMale.setBounds(5, 280, 300, 25);
        adultMale.setForeground(textColor);
        adultMale.setFont(textFont);
        panel.add(adultMale);

        //creating a label and input for number of adult females
        adultFemale = new JLabel("Number of Adult Females: ");
        adultFemale.setBounds(5, 330, 300, 25);
        adultFemale.setForeground(textColor);
        adultFemale.setFont(textFont);
        panel.add(adultFemale);

        //creating a label and input for number of children under 8
        chUnder8 = new JLabel("Number of Children under 8: ");
        chUnder8.setBounds(5, 380, 300, 25);
        chUnder8.setForeground(textColor);
        chUnder8.setFont(textFont);
        panel.add(chUnder8);

        //creating a label and input for number of children over 8
        chOver8 = new JLabel("Number of Children over 8: ");
        chOver8.setBounds(5, 430, 300, 25);
        chOver8.setForeground(textColor);
        chOver8.setFont(textFont);
        panel.add(chOver8);

        //the following combo boxes are for selecting the number of different types of clients
        //(number of family members)
        combo1 = new JComboBox(array);
        combo1.setBounds(4, 302, 100, 25);
        combo1.addActionListener(new ProjectGUI());
        panel.add(combo1);

        combo2 = new JComboBox(array);
        combo2.setBounds(4, 352, 100, 25);
        combo2.addActionListener(new ProjectGUI());
        panel.add(combo2);

        combo3 = new JComboBox(array);
        combo3.setBounds(4, 402, 100, 25);
        combo3.addActionListener(new ProjectGUI());
        panel.add(combo3);

        combo4 = new JComboBox(array);
        combo4.setBounds(4, 452, 100, 25);
        combo4.addActionListener(new ProjectGUI());
        panel.add(combo4);

        //creating a button to add the hampers based on different configurations
        generateForm = new JButton("Add Hamper");
        generateForm.setBounds(5, 600, 120, 30);
        generateForm.setBackground(buttonColor);
        generateForm.setForeground(buttonTextColor);
        generateForm.addActionListener(new ProjectGUI());
        panel.add(generateForm);

        //creating a text area to display the number of hampers in the order form
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setBounds(5, 500, 250, 75);
        panel.add(textArea);

        //creating a scrollbar for the text area when number of hampers increases
        scrollBar = new JScrollPane(textArea);
        scrollBar.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollBar.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollBar.setBounds(5, 500, 200, 95);
        panel.add(scrollBar);

        //grouping buttons
        group1 = new ButtonGroup();
        group1.add(boxNo);
        group1.add(boxYes);
        boxYes.addActionListener(new ProjectGUI());
        boxNo.addActionListener(new ProjectGUI());

        //success that displays success notifications or error messages
        //this label only becomes visible when displaying information
        success = new JLabel();
        success.setFont(textFont);
        success.setForeground(textColor);
        success.setBounds(5, 630, 500, 30);
        panel.add(success);

        genForm = new JButton("Create Order Form");
        genForm.setFont(textFont);
        genForm.setForeground(buttonTextColor);
        genForm.setBackground(buttonColor);
        genForm.setBounds(130, 600, 150, 30);
        genForm.addActionListener(new ProjectGUI());
        panel.add(genForm);

        //creating a button to end the program and exit the GUI
        endProgram = new JButton("End Program");
        endProgram.setFont(textFont);
        endProgram.setBackground(buttonColor);
        endProgram.setForeground(buttonTextColor);
        endProgram.setBounds(285, 600, 130, 30);
        endProgram.addActionListener(new ProjectGUI());
        panel.add(endProgram);

        //setting the frame visibility to true
        frame.setVisible(true);
    }

    //main actionPerformed method that does various things based on the button pressed
    //to use all buttons with one actionPerformed method, we use the e.getSource() method
    @Override
    public void actionPerformed(ActionEvent e) {
        //if the button for 'Add Hamper' is clicked, it does the following
        if (e.getSource().equals(generateForm)){
            //the following two if statements are to set the values for the choice of requirement
            if (boxNo.isSelected()){
                this.choice = false;
            }
            if (boxYes.isSelected()){
                this.choice = true;
            }

            //the following statements set the variables from user input text field boxes
            //these variables get send to the Application Form class
            this.familyName = orderNameField.getText();
            this.currDate = orderDateField.getText();
            this.numAdultMales = combo1.getSelectedIndex();
            this.numAdultFemales = combo2.getSelectedIndex();
            this.numChildUnder8 = combo3.getSelectedIndex();
            this.numChildOver8 = combo4.getSelectedIndex();

            //error checking for transportation requirement
            if (!(boxYes.isSelected()) && !(boxNo.isSelected())){
                success.setText("Please select a choice of delivery!");
            }
            //error checking for family size and if valid family sizes are selected
            else if (combo1.getSelectedIndex() == 0 && combo2.getSelectedIndex() == 0 && combo3.getSelectedIndex() == 0
                    && combo4.getSelectedIndex() == 0){
                success.setText("Please enter valid values for family size!");
            }
            //error checking if there are more than 10 hamper
            else if (count > 10){
                success.setText("Reached hamper limit of 10!");
            }
            //if all error checks are passed, we can create an application form object
            else{
                count++;
                success.setText("Added hamper!");
                textArea.append("Hamper " + count + "\n");

                try {
                    generateApplicationForm();
                } catch (SQLException ex) {
                    success.setText("Could not connect to database.");
                }

            }
        }

        //code for creating order form - closes the writer object and prints success message
        //this method also removes all items in the database that were used for creating the hamper
        if (e.getSource().equals(genForm)){
            if (count == 0){
                success.setText("Please enter atleast 1 hamper!");
            }
            try {
                writer.close();
            } catch (IOException ex) {
                success.setText("Initial hamper order was overwritten.");
            }

            success.setText("Successfully generated order form (orderform.txt)!");
        }

        //code for ending the program if endProgram button clicked
        if (e.getSource().equals(endProgram)){
            System.exit(1);
            frame.setVisible(false);
        }
    }

    //this method uses the user input from the GUI to generate a new application form for each hamper
    public void generateApplicationForm() throws SQLException{
        ApplicationForm form = new ApplicationForm(count, choice, familyName, currDate, numAdultMales,
                numAdultFemales, numChildUnder8, numChildOver8, inventory, success);

        ArrayList<Food> toDelete = Hampers.getHamperContent();
        for (Food item : toDelete){
            try {
                inventory.deleteItemFromAvailableFood(item.getItemID());
            } catch (SQLException ex) {
                success.setText("Not able to delete from the database.");
            }
        }

        ArrayList<Food> temp = Hampers.getHamperContent();
        GenerateOrderForm orderForm = new GenerateOrderForm(choice, familyName, currDate, numAdultMales, numAdultFemales,
                numChildUnder8, numChildOver8, temp, writer, count);

        try {
            orderForm.printOrderForm();
        } catch (IOException ex) {
            success.setText("Could not print to order form.");
        }
    }

}