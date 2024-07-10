import javax.swing.*;
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
        String response = " ";
        response = JOptionPane.showInputDialog("Choose one:\n1. Upload text file library" +
                "\n--Cats are formatted like so:\nName, Coloring, Gender (M/F), Weight, Disposition, Chipped (True or False)\n1, Emmy, Black, F, 15, Friendly, True\netc..." +
                "\n2. Add Cat" +
                "\n3. Select Cat" +
                "\n4. Remove cat by name" +
                "\n5. Remove cat by id" +
                "\n6. Modify Cat" +
                "\n7. Display Cats" +
                "\n8. Quit");
        if(response != null && response.length() == 1) {
            System.out.println("Selecting option " + response.charAt(0));
            return response.charAt(0);
        } else if(response == null) {
            return '8';
        }
        return 'f';
    }

    /**
     * message - displays a message in JOptionPane.
     * @param message - String to display.
     */
    public static void message(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    /**
     * display - Displays a scrollable textbox of information.
     * @param message - String to display.
     */
    public static void display(String message) {
        JTextArea textArea = new JTextArea(6, 25);
        textArea.setText(message);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize( null );
        JOptionPane.showMessageDialog(null, scrollPane, "Cat Cafe Database",
                JOptionPane.INFORMATION_MESSAGE);
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
            String output = JOptionPane.showInputDialog(mess);
            try {
                if(output != null) {
                    result = Integer.parseInt(output);
                    badInput = false;
                }
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "Input must be an integer.");
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
            result = JOptionPane.showInputDialog(mess);
            if (result != null && !result.isEmpty()) {
                badInput = false;
            } else
                result = null;
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
            response = JOptionPane.showInputDialog(mess);
            try {
                if(response != null) {
                    result = Double.parseDouble(response);
                    badInput = false;
                }
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null,"Input must be a number.");
            }
        }
        return result;
    }
}

}
