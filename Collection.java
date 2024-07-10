import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/*
Grace Tenney
CEN-3024C-31950
6/12/2024
Software Development

This class manages the collection, and the ids of all the Cats within the collection.
 */
public class Collection {
    private ArrayList<Cat> collection = new ArrayList<Cat>();
    public static final Cats cats = new Cats();

    /**
     * modify - Prompts the user to modify a Cat.
     */
    public static void modify(Cat cat) {
        //takes no arguments
        //returns nothing
        char choiceAttribute = ConsoleIO.getInput("Select attribute to modify: " +
                "\n1. Name" +
                "\n2. Coloring" +
                "\n3. Gender" +
                "\n4. Weight" +
                "\n5. Disposition" +
                "\n6. Chipped" +
                "\nOther: Continue").toUpperCase().charAt(0);
        switch (choiceAttribute) {
            case '1', 'N':
                String name = ConsoleIO.getInput("Enter new name: ");
                if(name != null)
                    cats.update("name",name,"ID", String.valueOf(cat.getId()));
                break;
            case '2':
                String coloring = ConsoleIO.getInput("Enter new color: ");
                if(coloring != null)
                    cats.update("coloring",coloring,"ID", String.valueOf(cat.getId()));
                break;
            case '3', 'G':
                String gender = ConsoleIO.getInput("Set new gender: ");
                if(gender != null)
                    cats.update("gender",gender,"ID", String.valueOf(cat.getId()));
                break;
            case '4', 'W':
                double weight = ConsoleIO.getDouble("Set new weight: ");
                cats.update("weight", String.valueOf(weight),"ID", String.valueOf(cat.getId()));
                break;
            case '5', 'D':
                String disposition = ConsoleIO.getInput("Enter new name: ");
                cats.update("disposition",disposition,"ID", String.valueOf(cat.getId()));
                break;
            case '6':
                String value = ConsoleIO.getInput("Enter new value [T/F]: ");
                switch(value.toUpperCase().charAt(0)) {
                    case 'T', 'Y':
                        value = "1";
                        break;
                    default:
                        value = "0";
                        break;
                }
                cats.update("chipped", value, "ID", String.valueOf(cat.getId()));
                break;
        }
    }
    /**
     * removeById - Removes a cat in the collection with a matching id.
     * @param id id of cat to remove.
     * @return Whether a cat was removed.
     */
    public boolean removeById(int id) {
        boolean catExists = !cats.select(null, "ID", String.valueOf(id), null, null).isEmpty();
        cats.delete("id", "" +id);
        System.out.println(cats.select(null, "ID", String.valueOf(id), null, null));
        ArrayList<ArrayList<Object>> tempCats = cats.select(null, "ID", String.valueOf(id), null, null);
        System.out.println(tempCats);
        return tempCats.isEmpty() && catExists;
    }

    /**
     *
     * @param cat - SQL row to create a Cat object from.
     * @return - Cat object created from input row.
     */
    public static Cat toCat(ArrayList<Object> cat) {
        boolean chipped = cat.get(6).toString().equals("1");
        double weight = Double.parseDouble(cat.get(4).toString());
        return new Cat((int)cat.get(0), cat.get(1).toString(), cat.get(2).toString(), cat.get(3).toString().charAt(0), weight,cat.get(5).toString(), chipped);
    }
    public ArrayList<Cat> getCollection() {
        return collection;
    }

    /**
     * findById - Finds and returns a cat with a matching id.
     * @param id id of cat to find.
     * @return Cat - returns cat from the list with a matching id.
     */
    public Cat findById(int id) {
        ArrayList<ArrayList<Object>> initialArray = cats.select(null, "ID", String.valueOf(id), null, null);
        if(!initialArray.isEmpty()) {
            return Collection.toCat(initialArray.get(0));
        }
        return null;
    }

    /**
     * findByName - Finds and returns a cat with a matching name.
     * @param name name of cat to find.
     * @return ArrayList<Cat> finds all cats with matching names.
     */
    public ArrayList<Cat> findByName(String name) {
        ArrayList<ArrayList<Object>> list = cats.select(null,"name",name,null,null);
        ArrayList<Cat> result = new ArrayList<Cat>();
        for(ArrayList<Object> a : list) {
            result.add(Collection.toCat(a));
        }
        return result;
    }

    /**
     * select - Retrieves a cat based on user input.
     * @param name - Name of cat to find.
     * @return Cat - returns the cat from the list with matching name.
     */
    public Cat select(String name) {
        ArrayList<Cat> matches = this.findByName(name);
        System.out.println(matches);
        Cat result = null;
        boolean badInput = true;
        if(matches.size() > 1) {
            while(badInput) {
                int id = ConsoleIO.getInt(matches + "Enter id of cat to select: ");
                for (Cat c : matches) {
                    if (c.getId() == id) {
                        result = c;
                        badInput = false;
                        break;
                    }
                }
                if(badInput)
                    ConsoleIO.message("Id does not match presented ids.");
            }

        } else if(matches.size() == 1) {
            result = matches.get(0);
        } else {
            ConsoleIO.message("No cat found.");
        }
        return result;
    }

    /**
     * add - Adds a cat to the list. Sets the added cat's id to the appropriate index
     * @param cat - Cat to add to the list.
     */
    public void add(Cat cat) {
        //returns nothing
        cats.insert(cat.getName(), cat.getColoring(), " " + cat.getGender(), cat.getWeight(), cat.getDisposition(), cat.isChipped());
        Cat displayCat = Collection.toCat(cats.executeQuery("SELECT * FROM cats ORDER BY ID DESC LIMIT 1;").get(0));
        ConsoleIO.message("Added cat: \n" + displayCat);
    }
    /**
     * upload - uploads file from FileIO to the collection
     */
    public void upload() {
        //upload
        //Reads the selected .txt file, formats it, validates it,
        //and adds it to the collection
        //Takes no arguments
        //Returns nothing
        int catsAdded = 0;
        String[] cats = FileIO.read().split("\n");
        double weight;
        boolean chipped;
        for (String s : cats) {
            String[] cattributes = s.split(",");
            if(cattributes.length == 6) {
                try {
                    weight = Double.parseDouble(cattributes[3]);
                } catch(NumberFormatException nfe) {
                    System.out.println("Invalid format at line: " + s);
                    break;
                }
                chipped = switch (cattributes[5].toUpperCase().trim().charAt(0)) {
                    case 'T', 'Y' -> true;
                    default -> false;
                };
                Cat catToAdd = new Cat(0, cattributes[0].trim(), cattributes[1].trim(), cattributes[2].trim().charAt(0), weight, cattributes[4].trim(), chipped);
                Collection.cats.insert(catToAdd.getName(),catToAdd.getColoring(),catToAdd.getGender() + "", catToAdd.getWeight(), catToAdd.getDisposition(),catToAdd.isChipped());
                catsAdded++;
            } else {
                System.out.println("Invalid format at line: " + s);
            }
        }
        ConsoleIO.message(catsAdded + " cats added.");
    }


    @Override
    public String toString() {
        String result = "";
        ArrayList<ArrayList<Object>> list = cats.select(null, null, null, null, null);
        for(ArrayList<Object> a : list) {
            result += a + "\n\n";
        }
        return result;
    }

}

