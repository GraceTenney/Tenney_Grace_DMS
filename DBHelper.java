import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;

public class DBHelper {
    private static String DATABASE_NAME = null;
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    public DBHelper() {
        connection = null;
        statement = null;
        resultSet = null;
    }

    public static String getDATABASE_NAME() {
        return DATABASE_NAME;
    }
    public static void setDATABASE_NAME(String path) {
        DATABASE_NAME = path;
    }
    public static boolean connectionIsValid() {
        DBHelper helper = new DBHelper();
        boolean result = helper.connect();
        helper.close();
        return result;
    }
    private boolean connect() {
        boolean result = false;
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + DATABASE_NAME);
            statement = connection.createStatement();
            result = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    private void close() {
        try {
            connection.close();
            statement.close();
            if(resultSet != null)
                resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private Object[][] arrayListTo2DArray(ArrayList<ArrayList<Object>> list) {
        Object[][] array = new Object[list.size()][];
        for (int i = 0; i < list.size(); i++) {
            ArrayList<Object> row = list.get(i);
            array[i] = row.toArray(new Object[row.size()]);
        }
        return array;
    }
    protected void execute(String sql) {
        try {
            connect();
            statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            close();
        }
    }
    protected DefaultTableModel executeQueryToTable(String sql) {
        ArrayList<ArrayList<Object>> result = new ArrayList<>();
        ArrayList<Object> columns = new ArrayList<>();
        connect();
        try {
            resultSet = statement.executeQuery(sql);
            int columnCount = resultSet.getMetaData().getColumnCount();
            for (int i = 1; i <= columnCount; i++)
                columns.add(resultSet.getMetaData().getColumnName(i));
            while (resultSet.next()) {
                ArrayList<Object> subresult = new ArrayList<>();
                for (int i = 1; i<= columnCount; i++)
                    subresult.add(resultSet.getObject(i));
                result.add(subresult);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        close();
        return new DefaultTableModel(arrayListTo2DArray(result), columns.toArray());
    }
    protected ArrayList<ArrayList<Object>> executeQuery(String sql) {
        ArrayList<ArrayList<Object>> result = new ArrayList<>();
        connect();
        try {
            resultSet = statement.executeQuery(sql);
            int columnCount = resultSet.getMetaData().getColumnCount();
            while(resultSet.next()) {
                ArrayList<Object> subresult = new ArrayList<>();
                for (int i = 1; i <= columnCount; i++) {
                    subresult.add(resultSet.getObject(i));
                }
                result.add(subresult);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        close();
        return result;
    }
}
