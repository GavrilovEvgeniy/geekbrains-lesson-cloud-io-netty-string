package gb.cloud.server.service.impl.authorize;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PGUse {

    public static Connection c = null;
    public static List<String> userList = new ArrayList<>();

    public static void connectToPG() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        c = DriverManager
                .getConnection("jdbc:postgresql://localhost:5435/","postgres", "postgrespass");
    }

    private static void createDB(String nameDB) {
        try {
            Statement st = c.createStatement();
            st.executeUpdate("CREATE DATABASE " + nameDB);
            st.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        System.out.println("Created database " + nameDB + " successfully");
    }

    private static void dropDB(String nameDB) {
        try {
            Statement st = c.createStatement();
            st.executeUpdate("DROP DATABASE " + nameDB);
            st.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        System.out.println("Deleted database " + nameDB + " successfully");
    }

    private static void createUserTable() throws SQLException {
        Statement st = c.createStatement();
        String sql = "CREATE TABLE USERS" +
                "(ID INT PRIMARY KEY     NOT NULL," +
                " NAME           TEXT    NOT NULL, " +
                " PASS           TEXT    NOT NULL)";
        st.executeUpdate(sql);
        st.close();
        System.out.println("Table USERS created successfully");
    }

    public static String addUser(int id, String userName, String password) throws SQLException {
        Statement st = c.createStatement();

        ResultSet rs = st.executeQuery( "SELECT * FROM USERS;" );
        while (rs.next()) {
            String name = rs.getString("NAME");
            String pass = rs.getString("PASS");
            if ((name.equals(userName)) | (pass.equals(password))) {
                System.out.println("User " + userName + " ignored, double name or pass");
                rs.close();
                st.close();
                return "Failure";
            }
        }
        rs.close();

        String sql = "INSERT INTO USERS (ID, NAME, PASS) " + "VALUES ( " + id + ", '" + userName + "', '" + password + "');";
        st.executeUpdate(sql);
        st.close();

//        userList.add(userName + "\\s" + password);

        return "Success";
    }

    private static void addFakeUsers() throws SQLException {
        int curId = 1;
        addUser(curId, "Anton", "riokl");
        curId++;
        addUser(curId, "Boris", "xvcbdf");
        curId++;
        addUser(curId, "Cobold", "5453");
    }

    public static int showUsers() throws SQLException {

        userList.clear();

        c.createStatement();
        Statement st;
        st = c.createStatement();
        int id = 0;
        ResultSet rs = st.executeQuery( "SELECT * FROM USERS;" );
        while ( rs.next() ) {
            id = rs.getInt("id");
            String name = rs.getString("NAME");
            String pass = rs.getString("PASS");

            userList.add(name + " " + pass);
        }

        System.out.println(userList);

        rs.close();
        st.close();
        return id;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

            connectToPG();

//            dropDB("testdb");

            createDB("testdb");
            createUserTable();

            addFakeUsers();
            showUsers();

            c.close();
        }
}
