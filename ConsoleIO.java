import java.util.Scanner;
/*
Grace Tenney
CEN-3024C-31950
6/12/2024
Software Development

This class manages inputs and outputs to the console.
 */
public class ConsoleIO {
    public static final Scanner input = new Scanner(System.in);

    /**
     * menu - creates the main menu.
     * @return char
     */
    public static char menu() {
        //menu
        //Creates the menu
        //Takes no arguments
        //Returns a char
        System.out.println("Choose one:\n1. Upload text file library" +
                "\n--Cats are formatted like so:\nName, Coloring, Gender (M/F), Weight, Disposition, Chipped (True or False)\n1, Emmy, Black, F, 15, Friendly, True\netc..." +
                "\n2. Add Cat" +
                "\n3. Select Cat" +
                "\n4. Remove cat by name" +
                "\n5. Remove cat by id" +
                "\n6. Modify Cat" +
                "\n7. Display Cats" +
                "\n8. Quit");
        String response = input.nextLine();
        if(!response.isEmpty()) {
            return response.charAt(0);
        }
        return 'f';
    }

    /**
     * getInt - Displays a message in console and creates a loop
     * until an integer is given by the user
     * @param mess String to display
     * @return int - Integer retrieved from user
     */
    public static int getInt(String mess) {
        //getInt
        //Takes a String
        //Returns an int
        int result = 0;
        boolean badInput = true;
        while(badInput) {
            System.out.println(mess);
            String output = input.nextLine();
            try {
                result = Integer.parseInt(output);
                System.out.println(result);
                badInput = false;
            } catch (NumberFormatException nfe) {
                System.out.println("Input must be an integer.");
            }
        }
        return result;
    }

    /**
     * getInput - Prompts the user for a String and loops until a String is given.
     * @param mess - Prompt to display
     * @return String
     */
    public static String getInput(String mess) {
        String result = " ";
        boolean badInput = true;
        while (badInput) {
            System.out.println(mess);
            result = input.nextLine();
            if (!result.isEmpty()) {
                badInput = false;
            } else
                result = " ";
        }
        return result;
    }

    /**
     * getDouble - Prompts the user for a double and loops until a double is given.
     * @param mess - Prompt to display.
     * @return double
     */
    public static double getDouble(String mess) {
        double result = 0;
        String response = "";
        boolean badInput = true;
        while(badInput) {
            System.out.println(mess);
            response = input.nextLine();
            try {
                result = Double.parseDouble(response);
                badInput = false;
            } catch (NumberFormatException nfe) {
                System.out.println("Input must be a number.");
            }
        }
        return result;
    }
}
