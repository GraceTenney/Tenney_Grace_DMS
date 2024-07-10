import javax.swing.*;
import java.io.Console;

/*
Grace Tenney
CEN-3024C-31950
6/12/2024
Software Development

This class runs the whole program. The program is a database of Cats,
allowing the user to perform CRUD operations on each cat. Instead of
choosing an action first, the user may also select a cat first and decide
what to do with it later.
 */
public class Main {
    public static void main(String[] args) {
        FileIO.initialize();
        Collection cats = new Collection();
        boolean running = true;
        while(running) {
            if (DBHelper.getDATABASE_NAME() == null) {
                boolean badInput = true;
                while (badInput) {
                    if(FileIO.chooseDatabase() != 1) {
                        DBHelper.setDATABASE_NAME(String.valueOf(FileIO.fc.getSelectedFile()));
                        badInput = false;
                    }
                }

            }
            char choice = ConsoleIO.menu();
            if (!FileIO.isShowing()) {
                switch (choice) {
                    case '1':
                        if (FileIO.chooseFile() != 1)
                            cats.upload();
                        break;
                    case '2':
                        cats.add(new Cat());
                        break;
                    case '3':
                        Cat match = cats.select(ConsoleIO.getInput("Enter name of cat to select: "));
                        if (match != null) {
                            char choiceAction = ConsoleIO.getInput(match + "\n[M]odify\n[R]emove\nOther: Continue").toUpperCase().charAt(0);
                            switch (choiceAction) {
                                case 'M':
                                    Cat selectedCatToModify = cats.findById(match.getId());
                                    System.out.println(selectedCatToModify.getId());
                                    Collection.modify(selectedCatToModify);
                                    ConsoleIO.message(cats.findById(match.getId()).toString());
                                    break;
                                case 'R':
                                    cats.removeById(match.getId());
                                    ConsoleIO.message("Successfully removed cat.");
                                    break;
                                default:
                                    break;
                            }
                        }
                        break;
                    case '4':
                        Cat catToRemove = cats.select(ConsoleIO.getInput("Enter name of cat to remove: "));
                        if (catToRemove != null) {
                            if (cats.removeById(catToRemove.getId()))
                                ConsoleIO.message(catToRemove + "\nCat removed succesfully.");
                            else
                                ConsoleIO.message("No cat was removed.");
                        }
                        break;
                    case '5':
                        int catIdToRemove = ConsoleIO.getInt("Enter id of cat to remove: ");
                        Cat removedCat = cats.findById(catIdToRemove);
                        if (cats.removeById(catIdToRemove))
                            ConsoleIO.message(removedCat + "\nCat removed successfully");
                        else
                            ConsoleIO.message("No cat removed.");
                        break;
                    case '6':
                        Cat catToModify = cats.select(ConsoleIO.getInput("Enter name of cat to select: "));
                        if (catToModify != null) {
                            Collection.modify(cats.findById(catToModify.getId()));
                            ConsoleIO.message(cats.findById(catToModify.getId()).toString());
                        }
                        break;
                    case '7':
                        ConsoleIO.display(cats.toString());
                        break;
                    case '8':
                        running = false;
                        ConsoleIO.message("Goodbye!");
                        System.exit(0);
                        break;
                    default:
                        ConsoleIO.message("Invalid input.");
                        break;
                } //switch
            } // if
        }
    }
}
