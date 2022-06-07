ENSF409 Final Project
Group 31
April 18th, 2022

Group Members:
Sajan Hayer (30114632)
Manraj Singh (30115660)
Kirtan Kakadiya (30120318)
Kartik Sharma (30115769)

This is a README.txt file that will show you how to compile and run our application. The main driver code for the application
is the 'ProjectGUI.java' class. Before compiling and running the GUI, please ensure that all necessary code files (included) are in
the proper directory, and that you have changed to the proper working directory.

Keep the 'lib' folder beside the 'edu' folder when running the application and tests.

To compile the 'ProjectGUI.java' file, run the following command:

javac -cp .;lib/mysql-connector-java-8.0.23.jar edu/ucalgary/ensf409/ProjectGUI.java

To run the 'ProjectGUI.java' file, run the following command:

java -cp .;lib/mysql-connector-java-8.0.23.jar edu/ucalgary/ensf409/ProjectGUI

This will open the application form where users can enter input, create hampers and generate an order form.


To run the tests that we have designed, named 'finalProjTests.java', compile thhe file using this command:

javac -cp .;lib/mysql-connector-java-8.0.23.jar;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar edu/ucalgary/ensf409/finalProjTests.java

To run the file after compilation, enter the following:

java -cp .;lib/mysql-connector-java-8.0.23.jar;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar org.junit.runner.JUnitCore edu.ucalgary.ensf409.finalProjTests