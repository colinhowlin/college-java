//Assignment 1 November2017
//Program to take user input of race participants and finish time
//and present statistical information of data

import java.util.Scanner;

public class Assignment1 {
    public static void main(String args[]){
        //Display program greeting
        System.out.println("\n**********************************************");
        System.out.println("Welcome to ITB Athletics race analysis program");
        System.out.println("**********************************************");

        Scanner inputMenu = new Scanner(System.in);
        
        //Display menu options
        int menuChoice = 1;        
        System.out.println("\nMain Menu");
        System.out.println("---------");
        System.out.print("Enter 1 to begin entering data, -1 to quit: ");
        
        int numRunners;
        do {
            while (inputMenu.hasNextInt() == false){    //check that user entered correct datatype
                System.out.print("Enter 1 to begin entering data, -1 to quit: ");
                inputMenu.next();                       //keep trying until they do
            }
            menuChoice = inputMenu.nextInt();
            
            if (menuChoice == 1) {
                //do while loop to ensure valid input
                System.out.println("\nHow many runners were there?");
                do {
                    System.out.print("Please enter integer between 2 and 7: ");
                    while (inputMenu.hasNextInt() == false) {   //ensure user enters correct datatype
                        System.out.print("Please enter integer between 2 and 7: ");
                        inputMenu.next();                       //keep trying until they do
                    }      
                    numRunners = inputMenu.nextInt();
                } while (numRunners < 2 || numRunners > 7);
                
                //initialise 2 arrays for names and times
                double [] times = new double[numRunners];
                String [] names = new String[numRunners];
                
                System.out.println("Please enter race details");
                
                //loop to gather user input
                for (int i = 0; i < numRunners; i++) {

                    System.out.print("\nRunners name (no spaces): ");
                    String runnerName = inputMenu.next();
                    names[i] = runnerName;
                    
                    //check for valid input for race time
                    double raceTime;
                    do {
                        System.out.print("Please enter Race Time (0.00 - 10.00): ");
                        while (inputMenu.hasNextDouble() == false) {
                           System.out.print("\nRace time must be greater than 0 minutes and less than 10 minutes: ");
                           inputMenu.next(); 
                        }
                        raceTime = inputMenu.nextDouble();
                    } while (raceTime <= 0 || raceTime > 10);
                    times[i] = raceTime;
                }
                
                System.out.println();                
                optionMenu(times, names);   
               
            } else if (menuChoice == -1) {
                System.out.print("Goodbye!");
                break;
            } else 
                System.out.println("Invalid input.");
                System.out.print("Please enter 1 to enter data, or -1 to quit: ");
        }while (menuChoice != -1);         
    }
    
    //Display main option menu
    public static void optionMenu(double[] times, String[] names){
        Scanner inputOption = new Scanner(System.in);
        int optionChoice = 1;
        
        printOptionMenu();
        
        while (optionChoice != -1) 
        do {
            while (inputOption.hasNextInt() == false){
                System.out.print("Please enter a valid integer value: ");
                inputOption.next();
            }
            optionChoice = inputOption.nextInt();
            
            //Use switch statement to process user input and display selected data
            switch(optionChoice){
                case -1:
                    System.out.print("Returning to main menu.\n");
                    break;
                    
                case 1:
                    double average = getAverage(times);
                    System.out.println("\n----------------------------------");
                    System.out.printf("Average time is %.2f\n", average);
                    System.out.println("----------------------------------\n");
                    printOptionMenu();
                    break;
                    
                case 2:
                    getFastestTime(times, names);
                    printOptionMenu();
                    break;
                    
                case 3:
                    getSlowestTime(times, names);
                    printOptionMenu();
                    break;
                    
                case 4:
                    sortTimes(times, names, 1);
                    printOptionMenu();
                    break;
                    
                case 5:
                    //ensure data is sorted before searching
                    //The third parameter '2' is to ensure the sorted list does not get printed each time this method is called
                    sortTimes(times, names, 2);
                    System.out.print("Who are you searching for: ");
                    String search = inputOption.next();
                    int searchIndex = linearSearchByName(names, search);
                    if (searchIndex == -1){
                        System.out.println("\n-----------------------------------------");
                        System.out.println("That name is not in the inputted data");
                        System.out.println("-----------------------------------------\n");
                    } else {
                        System.out.println("\n-----------------------------------------");
                        System.out.printf("%s is at index %d. Their time was %.2f\n", search, searchIndex, times[searchIndex]); 
                        System.out.println("-----------------------------------------\n");
                    }
                    printOptionMenu();
                    break;
                default:
                    System.out.print("Please select an option between 1 and 5: ");
            }
        } while (optionChoice != -1);
    }
    
    //Method to print the option menu 
    public static void printOptionMenu(){
        System.out.println("************************************************");
        System.out.println("WHAT TASK DO YOU WANT TO PERFORM ON THE DATA");
        System.out.println(" 1. Find average time");
        System.out.println(" 2. Find the fastest runner and their time");
        System.out.println(" 3. Find the slowest runner and their time");
        System.out.println(" 4. Sort times and runners in descending order");
        System.out.println(" 5. Search for a given name to find their racetime");
        System.out.println("-1. Quit");
        System.out.println("*************************************************");
        System.out.print("\nPlease select your option 1, 2, 3, 4 or 5 from above. -1 to quit: ");
    }
    
    //Display average race completion time
    public static double getAverage(double times[]){        
        double total = 0;
        
        //iterate through times array and add each element to total
        for (double time : times)
            total += time;
        
        return total / times.length;    //return the average
    }
    
    //Display the Slowest race completion time
    public static void getSlowestTime(double times[], String names[]){
        
        //Assume first entry is the slowestTime/slowestRunner
        double slowestTime = times[0];
        String slowestRunner = names[0];
        
        //Iterate through times array to find the slowest time
        for (int counter = 0; counter < times.length; counter++){
            
            //If current element of times array is larger than slowestTime, assign current element to slowestTime
            if (times[counter] > slowestTime) {
                slowestTime = times[counter];
                slowestRunner = names[counter]; //slowestRunner is same array position as slowestTime
            }
        }
        System.out.println("\n---------------------------------------------------");
        System.out.printf("The slowest time was %.2f and it was run by %s\n", slowestTime, slowestRunner);
        System.out.println("---------------------------------------------------\n");
    }
    
    //Display the fastest race completion time
    public static void getFastestTime(double times[], String names[]){
        
        //Assume first entry is the fastestTime/fastestRunner
        double fastestTime = times[0];
        String fastestRunner = names[0];
        
        //Iterate through times array to find the fastest time
        for (int counter = 0; counter < times.length; counter++){
            
            //If current element of times array is smaller than fastestTime, assign current element to fastestTime
            if (times[counter] < fastestTime) {
                fastestTime = times[counter];
                fastestRunner = names[counter]; //fastestRunner is same array position as fastestTime
            }
        }    
        System.out.println("\n---------------------------------------------------");
        System.out.printf("The fastest time was %.2f and it was run by %s\n", fastestTime, fastestRunner);        
        System.out.println("---------------------------------------------------\n");
    }
    
    //Sort races times in descending order using bubble sort alogorithm
    public static void sortTimes(double times[], String names[], int toPrint){
        
        //declare temporary variables
        double holdTime;
        String holdName;
        
        //Iterate through array comparing element n with element n+1
        for (int i = 0; i < times.length; i++) {
            for (int j = 0; j < (times.length - 1); j++) {
                if (times[j] > times[j + 1]) {  //if element n+1 is greater than element n, swap the two elements
                    holdTime = times[j];
                    holdName = names[j];        //do same operation on names array to sort Strings
                    
                    times[j] = times[j + 1];
                    names[j] = names[j + 1];
                    
                    times[j + 1] = holdTime;
                    names[j + 1] = holdName;
                }
            }
        }
        
        //Print the results in descending order if method call did not come from search method
        if (toPrint == 1){
            System.out.println("\n----------------------------------------");
            System.out.println("Racetimes and runners in descending order:");
            System.out.println("\n----------------------------------------");
            System.out.printf("%-6s%-15s%-15s\n", "Index", "Runner", "Time");
            System.out.printf("%-6s%-15s%-15s\n", "------", "---------------", "----");
            
            for (int i = (names.length - 1); i >= 0; i--)   //loop through array starting from the last element
                System.out.printf("%-6d%-15s%-15.2f\n\n", i, names[i],times[i]); 
            System.out.println("----------------------------------\n");
            System.out.println();
        }
    }
    
    //Search for individual race participant by name
    public static int linearSearchByName(String[] names, String name){
        for (int i = 0; i < names.length; i++) {
            if (names[i].equals(name)) {
                return i;
            }
        }
        return -1;
    }
}