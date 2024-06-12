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
        cats.upload();
        boolean running = true;
        while(running) {
            char choice = ConsoleIO.menu();
            switch(choice) {
                case '1':
                    if(FileIO.chooseFile() != 1)
                        cats.upload();
                    System.out.println(cats);
                    break;
                case '2':
                    cats.add(new Cat());
                    System.out.println(cats);
                    break;
                case '3':
                    Cat match = cats.select(ConsoleIO.getInput("Enter name of cat to select: "));
                    if (match != null) {
                        char choiceAction = ConsoleIO.getInput("[M]odify\n[R]emove\nOther: Continue").toUpperCase().charAt(0);
                        switch (choiceAction) {
                            case 'M':
                                System.out.println(match.getId() + " id of cat");
                                cats.findById(match.getId()).modify();
                                break;
                            case 'R':
                                cats.removeById(match.getId());
                                break;
                            default:
                                break;
                        }
                    }
                    System.out.println(cats);
                    break;
                case '4':
                    Cat catToRemove = cats.select(ConsoleIO.getInput("Enter name of cat to remove: "));
                    if (catToRemove != null)
                        cats.removeById(catToRemove.getId());
                    System.out.println(cats);
                    break;
                case '5':
                    if(cats.removeById(ConsoleIO.getInt("Enter id of cat to remove: ")))
                        System.out.println("Cat removed successfully");
                    else
                        System.out.println("No cat removed.");
                    System.out.println(cats);
                    break;
                case '6':
                    Cat catToModify = cats.select(ConsoleIO.getInput("Enter name of cat to select: "));
                    if(catToModify != null)
                        cats.findById(catToModify.getId()).modify();
                    System.out.println(cats);
                    break;
                case '7':
                    System.out.println(cats);
                    break;
                case '8':
                    running = false;
                    break;
                default:
                    break;
            }
        }
    }
}
