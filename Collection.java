import java.util.ArrayList;
/*
Grace Tenney
CEN-3024C-31950
6/12/2024
Software Development

This class manages the collection, and the ids of all the Cats within the collection.
 */
public class Collection {
    private ArrayList<Cat> collection = new ArrayList<Cat>();
    private int iterator = 1;
    /**
     * removeById - Removes a cat in the collection with a matching id.
     * @param id id of cat to remove.
     */
    public boolean removeById(int id) {
        //Returns nothing
        return this.collection.removeIf(b -> b.getId() == id);
    }

    /**
     * findById - Finds and returns a cat with a matching id.
     * @param id id of cat to find.
     * @return Cat - returns cat from the list with a matching id.
     */
    public Cat findById(int id) {
        for(Cat c : this.collection) {
            if(c.getId() == id)
                return c;
        }
        return null;
    }

    /**
     * findByName - Finds and returns a cat with a matching name.
     * @param name name of cat to find.
     * @return ArrayList<Cat> finds all cats with matching names.
     */
    public ArrayList<Cat> findByName(String name) {
        ArrayList<Cat> result = new ArrayList<Cat>();
        for(Cat c : collection) {
            if(c.getName().equals(name)) {
                result.add(c);
            }
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
                int id = ConsoleIO.getInt("Enter id of cat to select: ");
                for (Cat c : matches) {
                    if (c.getId() == id) {
                        result = c;
                        badInput = false;
                        break;
                    }
                }
                if(badInput)
                    System.out.println("Id does not match presented ids.");
            }

        } else if(matches.size() == 1) {
            result = matches.get(0);
        } else {
            System.out.println("No cat found.");
        }
        return result;
    }

    /**
     * add - Adds a cat to the list. Sets the added cat's id to the appropriate index
     * @param cat - Cat to add to the list.
     */
    public void add(Cat cat) {
        //returns nothing
        cat.setId(iterator);
        collection.add(cat);
        iterator++;
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
                Cat catToAdd = new Cat(iterator, cattributes[0], cattributes[1], cattributes[2].trim().charAt(0), weight, cattributes[4], chipped);
                if(this.collection.add(catToAdd))
                    iterator++;
            } else {
                System.out.println("Invalid format at line: " + s);
            }
        }
    }


    @Override
    public String toString() {
        String result = "";
        for (Cat c : collection) {
            result += c.toString();
        }
        return result;
    }

}
