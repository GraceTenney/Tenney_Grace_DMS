import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class Cats extends DBHelper {
    private final String TABLE_NAME = "cats";
    public static final String ID = "ID";
    public static final String name = "name";
    public static final String coloring = "coloring";
    public static final String gender = "gender";
    public static final String weight = "weight";
    public static final String disposition = "disposition";
    public static final String chipped = "chipped";

    /**
     * prepareSQL - prepares the tet of a SQL "select" command.
     * @param fields - fields to be displayed in the output
     * @param whatField - field to search for
     * @param whatValue - value to search for within whatField
     * @param sortField - use ASC or DESC to specify the sorting order
     * @param sort - the field the data will be sorted by
     * @return - String
     */
    private String prepareSQL(String fields, String whatField, String whatValue, String sortField, String sort) {
        String query = "SELECT ";
        query += fields == null ? " * FROM " + TABLE_NAME : fields + " FROM " + TABLE_NAME;
        query += whatField != null && whatValue != null ? " WHERE " + whatField + " = \"" + whatValue + "\"" : "";
        query += sort != null && sortField != null ? " order by " + sortField + " " + sort : "";
        return query;
    }

    /**
     * insert - insert a new record into the database.
     * @param name - name of Cat
     * @param coloring - coloring of Cat
     * @param gender - gender of Cat
     * @param weight - weight of Cat
     * @param disposition - disposition of Cat
     * @param chipped - whether Cat is microchipped
     */
    public void insert(String name, String coloring, String gender, Double weight, String disposition, Boolean chipped) {
        name = name != null ? "\"" + name + "\"" : null;
        coloring = coloring != null ? "\"" + coloring + "\"" : null;
        gender = gender != null ? "\"" + gender + "\"" : null;
        disposition = disposition != null ? "\"" + disposition + "\"" : null;
        int assignChipped = 0;
        if(chipped)
            assignChipped = 1;

        Object[] values_ar = {name, coloring, gender, weight, disposition, assignChipped};
        String[] fields_ar = {Cats.name, Cats.coloring, Cats.gender, Cats.weight, Cats.disposition, Cats.chipped};
        String values = "", fields = "";
        for (int i = 0; i < values_ar.length; i++) {
            if (values_ar[i] != null) {
                values += values_ar[i] + ", ";
                fields += fields_ar[i] + ", ";
            }
        }
        if (!values.isEmpty()) {
            values = values.substring(0, values.length() - 2);
            fields = fields.substring(0, fields.length() - 2);
            super.execute("INSERT INTO " + TABLE_NAME + "(" + fields + ") values(" + values + ");");
        }
    }

    /**
     * delete - remove a record from the database.
     * @param whatField - the field used to determine if a row should be deleted
     * @param whatValue - value of the row to be removed
     */
    public void delete(String whatField, String whatValue) {
        super.execute("DELETE from " + TABLE_NAME + " where " + whatField + " = " + whatValue + ";");
    }

    /**
     * update - update a record in the database.
     * @param whatField - field to be updated
     * @param whatValue - value to update to
     * @param whereField - conditional field determining whether to update
     * @param whereValue - conditional value determining whether to update
     */
    public void update(String whatField, String whatValue, String whereField, String whereValue) {
        super.execute("UPDATE " + TABLE_NAME + " set " + whatField + " = \"" + whatValue + "\" where " + whereField + " = \"" + whereValue + "\";");
    }

    /**
     * select - completes a SQL "select" command
     * @param fields - fields to be displayed in the output
     * @param whatField - field to search within
     * @param whatValue - value to search for within whatField
     * @param sortField - use ASC or DESC to specify sorting order
     * @param sort - field that data will be sorted by
     * @return ArrayList<ArrayList<Object>> - a 2D array of Objects.
     */
    public ArrayList<ArrayList<Object>> select(String fields, String whatField, String whatValue, String sortField, String sort) {
        return super.executeQuery(prepareSQL(fields, whatField, whatValue, sortField, sort));
    }
    public ArrayList<ArrayList<Object>> getExecuteResult(String query) {
        return super.executeQuery(query);
    }

    /**
     * execute - performs a SQL command
     * @param query - the String that is sent as a SQL command
     */
    public void execute(String query) {
        super.execute(query);
    }
    public DefaultTableModel selectToTable(String fields, String whatField, String whatValue, String sortField, String sort) {
        return super.executeQueryToTable(prepareSQL(fields, whatField, whatValue, sortField, sort));
    }
}
