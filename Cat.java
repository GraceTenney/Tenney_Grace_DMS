/*
Grace Tenney
CEN-3024C-31950
6/12/2024
Software Development

This class creats Cat objects.
 */
public class Cat {
    private int id;
    private String name;
    private String coloring;
    private char gender;
    private double weight;
    private String disposition;
    private boolean chipped;

    public Cat() {
        setName(ConsoleIO.getInput("Enter the cat's name: "));
        setColoring(ConsoleIO.getInput("Describe the cat's color: "));
        setGender(ConsoleIO.getInput("Enter the cat's gender (M or F)").charAt(0));
        setWeight(ConsoleIO.getDouble("Enter the cat's weight: "));
        setDisposition(ConsoleIO.getInput("Describe the cat's attitude or personality: "));
        setChipped(ConsoleIO.getInput("Does the cat have a microchip? Y/N or True or False"));
    }

    public Cat(int id, String name, String coloring, char gender, double weight, String disposition, boolean chipped) {
        setId(id);
        setName(name);
        setColoring(coloring);
        setGender(gender);
        setWeight(weight);
        setDisposition(disposition);
        setChipped(chipped);
    }

    /**
     * modify - Prompts the user to modify a Cat.
     */
    public void modify() {
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
                    this.setName(name);
                break;
            case '2':
                String coloring = ConsoleIO.getInput("Enter new color: ");
                if(coloring != null)
                    this.setColoring(coloring);
                break;
            case '3', 'G':
                String gender = ConsoleIO.getInput("Set new gender: ");
                if(gender != null)
                    this.setGender(gender.charAt(0));
                break;
            case '4', 'W':
                this.setWeight(ConsoleIO.getDouble("Set new weight: "));
                break;
            case '5', 'D':
                this.setDisposition(ConsoleIO.getInput("Enter new name: "));
                break;
            case '6':
                this.setChipped(ConsoleIO.getInput("Enter new value [T/F]: "));
                break;
        }
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name != null)
            this.name = name;
    }

    public String getColoring() {
        return coloring;
    }

    public void setColoring(String coloring) {
        if(coloring != null)
            this.coloring = coloring;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        gender = Character.toUpperCase(gender);
        switch(gender) {
            case 'F', 'M':
                this.gender = gender;
                break;
            default:
                this.gender = 'N';
        }
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getDisposition() {
        return disposition;
    }

    public void setDisposition(String disposition) {
        this.disposition = disposition;
    }

    public boolean isChipped() {
        return chipped;
    }

    public void setChipped(boolean chipped) {
        this.chipped = chipped;
    }

    public void setChipped(String value) {
        switch(value.toUpperCase().charAt(0)) {
            case 'T', 'Y':
                this.chipped = true;
                break;
            default:
                this.chipped = false;
                break;
        }
    }

    @Override
    public String toString() {
        return
                "\nid: " + id +
                "\nname: " + name +
                "\ncoloring: " + coloring +
                "\ngender: " + gender +
                "\nweight: " + weight +
                "\ndisposition: " + disposition +
                "\nchipped: " + chipped + "\n";
    }
}
