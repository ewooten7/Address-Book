package AddressBook;

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class AddressBook {
/*Part 1: Program Functionality */
/*Goal: "Upon starting the program, it should ask the user what action they want to perform." */

/*I: Starting the Program: all must be private variables */
//Use Scanner: similar to my Java Calculator
static Scanner scanner = new Scanner(System.in); //collects inputs
    private static ArrayList<InformationCollection> addresses = new ArrayList<>(); //ArrayList for displaying information
    private static boolean dataChanged = false; 


//Main Function
public static void main(String[] args) {
    int mainMenuSelection;

    // if HunterContacts.txt file exists, load the data from the file
    fillBookFromFile();

    // run this code until told to stop!
    while (true) {
        // display menu
        System.out.println("\n*****************************************");
        System.out.println("\n  *** Address Book Application Menu ***");
        System.out.println("\n   What would you like to do?");
        System.out.println("\t1 - Add an entry");
        System.out.println("\t2 - Remove an entry");
        System.out.println("\t3 - Search for a specific entry");
        System.out.println("\t4 - Print the Address Book");
        System.out.println("\t5 - Delete ALL entries");
        System.out.println("\t6 - Quit the Program");

/*"The user must enter a number 1-6. " */
        mainMenuSelection = getNumber("\nPlease enter the number of your menu choice:");
        if (mainMenuSelection == 6) {
            if (dataChanged) {
                saveBookToFile();
            }
            return; // ends program
        }

/*Goal: "The user must enter a number 1-6. If the user enters invalid input, print an error message and show the main menu again to take in a new input." */
        if ((mainMenuSelection < 1) || (mainMenuSelection > 6)) {
            System.out.println(" Invalid menu choice.");
            continue;
        }

    // Request to Add an entry if list is empty (mainMenuSelection 1)
        if ((mainMenuSelection > 1) && (addresses.size() == 0)) {
            System.out.println("The address List is Empty!");
            continue;
        }

    // Menu Operations: Putting Calls 1-6 to use:
        switch (mainMenuSelection) {
            case 1: // Add an entry
                addEntry();
                break;
            case 2: // Remove an entry
                removeEntry();
                break;
            case 3: // Search for specific entry
                searchForEntry();
                break;
            case 4: // Print all addresses
                int count = 0;
                for (InformationCollection address : addresses) {
                    printEntry(address);
                    count++;
                }
/*Goal: informing the user of the number of Entries */
                System.out.println("Address Book contains " + count + " entr" + ((count == 1) ? "y." : "ies."));
                break;
            default: // clear the entire list
                deleteBook();
                break;
        }
        // stay in while loop to start over
    }
}

// Shows a way to get a number from the user, while verifying
// that the user enters a number within a specified range. Prompt will repeat if conditions failed.

public static int getNumber(String prompt) {
    // create local variables
    int result = 0;
    String userInput;

    // Failure message: repeats prompt
    while (result == 0) {
        // prompt for input
        System.out.println(prompt);

  
        userInput = scanner.nextLine();

    //While loop repeats if nothing is entered.
        if (!userInput.isEmpty()) {
        // invalid number will throw exception, so we check for that here
            try {
                // if number was entered, store it in result
                result = Integer.parseInt(userInput);
            } catch (Exception e) {
                // if what was entered cannot be parsed to a number,
                // ask for VALID number and stay in while loop
                System.out.println("Please enter a valid number.");
            }
        }
    }
    return result;
}

//getString Method

public static String getString(String prompt) {
    // ask for input and return it
    System.out.println(prompt);

    // read in and returns line from console
    return scanner.nextLine();
}


 /*Goal 2: Adding an entry: "If the user inputs 1, they should be asked to enter the details of an entry. This entry is then added to the address book."  */

public static void addEntry() {
    String firstName = "";
    String lastName = "";
    String phone = "";
    String email = "";
    int numOfErrors = 0;


    // gets firstName - If failed 10 times, restarts to beginning menu
    do {
        firstName = getString("Please enter First Name: ");
        if (firstName.isEmpty() && (++numOfErrors >= 10)) {
            numOfErrors = 0;
            return;
        }
    } while (firstName.isEmpty());

    // gets lastName - If failed 10 times, restarts to beginning menu
    do {
        lastName = getString("Please enter Last Name: ");
        if (lastName.isEmpty() && (++numOfErrors >= 10)) {
            numOfErrors = 0;
            return;
        }
    } while (lastName.isEmpty());

    // gets phone number - If failed 10 times, restarts to beginning menu
    do {
        phone = getString("Please enter Phone Number: ");
        if (phone.isEmpty() && (++numOfErrors >= 10)) {
            numOfErrors = 0;
            return;
        }
    } while (phone.isEmpty());

    // gets email - If failed 10 times, restarts to beginning menu
    do {
        email = getString("Please enter person's E-mail: ");
        if (email.isEmpty() && (++numOfErrors >= 10)) {
            numOfErrors = 0;
            return;
        }
    } while (email.isEmpty());

//Checker to prevent repeat email entries.
    if (addresses.size() > 0) {
        String testEmail = email.toLowerCase();
        for (InformationCollection address : addresses) {
            if (address.getEmail().toLowerCase().equals(testEmail)) {
                System.out.println("\n'" + email + "'" + " has already been added, entry has been ignored.");
                return;
            }
        }
    }

    // add address to addresses ArrayList
    InformationCollection address = new InformationCollection(firstName, lastName, phone, email);
    addresses.add(address);
    printEntry(address);
    System.out.println("Has been added to list.\n\n");
    dataChanged = true;
}

/*Goal: Print Contents of the Address Book */


public static void printEntry(InformationCollection address) {
    System.out.println("\n**********");
    System.out.println("  First Name: " + address.getFirstName());
    System.out.println("   Last Name: " + address.getLastName());
    System.out.println("Phone Number: " + address.getPhoneNumber());
    System.out.println("      E-Mail: " + address.getEmail());
    System.out.println("**********\n");

}

/*Goal: Remove an Entry:
 As the user to input an email to delete, and the delete that entry from the address book. Print out the entry that was deleted. 
•	If the entry was not found, notify the user. Then, show the main menu again to take in a new input.
 */

public static void removeEntry() {
    boolean found = false;

    // get email of entry userwants to remove
    String email = getString("Please enter E-mail for entry to remove:");

    // convert to lowercase for matching
    String testEmail = email.toLowerCase();

    // Loop through list to find entered email
    for (InformationCollection address : addresses) {
        if (address.getEmail().toLowerCase().equals(testEmail)) {
            found = true;
            // found match, so we display it
            printEntry(address);
        }
    }
    if (!found) {
        System.out.println("'" + email + "' Not Found, nothing removed.");
    } else {
        // Confirm user wants to delete it
        String response = getString("Are you sure you want to remove this entry? (Y/N)");
        if (response.toUpperCase().charAt(0) == 'Y') {
            Iterator<InformationCollection> itr = addresses.iterator();
            while (itr.hasNext()) {
                InformationCollection address = itr.next();
                if (address.getEmail().toLowerCase().equals(testEmail)) {
                    itr.remove();
                    System.out.println("'" + email + "' record removed.\n");
                    dataChanged = true;
                }
            }
        }
    }
    return;
}

/*Goal: Search for a Specific Entry: 
•	Show a user the list of options to search by. 
•	Once they select their search parameter, prompt them to enter their search query. If their search option is invalid 
    o	(e.g., not a number 1-4, alert them and bring back the main menu to take in a new input). 
•	If their search option is valid, search the database and print all matching entries.
•	 If there is nothing found, alert the user. Then, show the main menu again to take in a new input.
 */

public static void searchForEntry() {
    int mainMenuSelection;

    while (true) {
        System.out.println("\n*****************************************");
        System.out.println("\n\t*** Search Menu ***");
        System.out.println("\nWhich field would you like to search?");
        System.out.println("\t1 - First Name");
        System.out.println("\t2 - Last Name");
        System.out.println("\t3 - Phone");
        System.out.println("\t4 - E-Mail");
        System.out.println("\t5 - Done Searching.");

        // get user's choice
        mainMenuSelection = getNumber("\nPlease enter the number of your menu Choice:");
        if (mainMenuSelection == 5) {
            return; // Done searching
        }

        // if mainMenuSelection is not valid, redisplay menu and try again
        if ((mainMenuSelection < 1) || (mainMenuSelection > 5)) {
            System.out.println(" Invalid menu choice.");
            continue;
        }

        String srchStr = getString("\nPlease enter what you are looking for:");
        String lowSrchStr = srchStr.toLowerCase();
        int count = 0;
        String field = "";

        // search for whatever user entered
        switch (mainMenuSelection) {
            case 1:
                for (InformationCollection address : addresses) {
                    if (address.getFirstName().toLowerCase().contains(lowSrchStr)) {
                        printEntry(address);
                        count++;
                        field = "'First Name'";
                    }
                }
                break;
            case 2:
                for (InformationCollection address : addresses) {
                    if (address.getLastName().toLowerCase().contains(lowSrchStr)) {
                        printEntry(address);
                        count++;
                        field = "'Last Name'";
                    }
                }
                break;
            case 3:
                for (InformationCollection address : addresses) {
                    if (address.getPhoneNumber().toLowerCase().contains(lowSrchStr)) {
                        printEntry(address);
                        count++;
                        field = "'Phone'";
                    }
                }
                break;
            case 4:
                for (InformationCollection address : addresses) {
                    if (address.getEmail().toLowerCase().contains(lowSrchStr)) {
                        printEntry(address);
                        count++;
                        field = "'E-mail'";
                    }
                }
                break;
        }

        if (count == 0) {
            System.out.println("Searching " + field + " for '" + srchStr + "' did not find any matches.");
        } else {
            System.out.println("Searching " + field + " for '" + srchStr + "' found " + count + " match" +
                    ((count == 1) ? "." : "es."));
        }
    }
}

/*Goal: Remove an Entry:
 * As the user to input an email to delete, and the delete that entry from the address book. Print out the entry that was deleted. 
•	If the entry was not found, notify the user. Then, show the main menu again to take in a new input.

 */

/*Goal: Delete Book
    Clear the address book and notify the user that it was cleared.
 */

public static void deleteBook() {
    // Confirm user wants to delete EVERYTHING
    System.out.println("Warning! This will delete the entire address book. Are you sure you want to do this?");
    String response = getString("Final Warning: are you positive? (Y/N)");
    if (response.toUpperCase().charAt(0) == 'Y') {
        addresses.clear();
        System.out.println("The address List is now empty.");
        dataChanged = true;
    }
}

/*
 Goal: Print Address Book:
 Print all the entries in the address book. If the address book is empty, notify the user. Then, show the main menu again to take in a new input.
 */

 //Fills address book from HunterContacts.txt file

public static void fillBookFromFile() {
    try {
        File bookFile = new File("./InformationCollection.txt");
        Scanner myReader = new Scanner(bookFile);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            String[] fields = data.split(", ");
            InformationCollection address = new InformationCollection(
                // need to remove quotes from fields
                fields[0].substring(1, fields[0].length()-1), 
                fields[1].substring(1, fields[1].length()-1), 
                fields[2].substring(1, fields[2].length()-1), 
                fields[3].substring(1, fields[3].length()-1));
            addresses.add(address);
            System.out.println("Data read in from file:");
            printEntry(address);
        }
        myReader.close();
    } catch (FileNotFoundException e) {
        System.out.println("File Not Found.");
    }

}


public static void saveBookToFile() {
    File bookFile = new File("./HunterContacts.txt");
    String myData = "";
    // remove current file
    bookFile.delete();

    // create and fill new file
    try {
        if (bookFile.createNewFile()) {
            FileWriter myWriter = new FileWriter("./HunterContacts.txt");
            for (InformationCollection address : addresses) {
                myData = String.format("\"%s\", \"%s\", \"%s\", \"%s\"\n",
                        address.getFirstName(),
                        address.getLastName(),
                        address.getPhoneNumber(),
                        address.getEmail());
                myWriter.write(myData);
            }
            myWriter.close();
        }
    } catch (IOException e) {
        System.out.println("An error occurred trying to save file.");
    }
}
}