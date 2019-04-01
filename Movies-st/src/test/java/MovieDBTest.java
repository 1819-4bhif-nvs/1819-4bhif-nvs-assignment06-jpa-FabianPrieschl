import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.sql.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MovieDBTest {

    public static final String DRIVER_STRING = "org.apache.derby.jdbc.ClientDriver";
    private static final String CONNECTION_STRING = "jdbc:derby://localhost:1527/db";
    private static final String USER = "app";
    private static final String PASSWORD = "app";
    private static Connection conn;

    @BeforeClass
    public static void initJdbc() {
        // Verbindung zur DB herstellen
        try {
            Class.forName(DRIVER_STRING);
            conn = DriverManager.getConnection(CONNECTION_STRING, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Verbindung zur Datenbank nicht möglich:\n"
                    + e.getMessage() + "\n");
            System.exit(1);
        }

    }
    @AfterClass
    public static void teardownJdbc() {
        // Connection schließen
        try {
            if (conn != null || !conn.isClosed()) {
                conn.close();
                System.out.println("Goodbye!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void t01_readExemplars(){
        try{
            PreparedStatement pstmt = conn.prepareStatement("SELECT id, title, genre FROM movie");
            ResultSet rs = pstmt.executeQuery();

            rs.next();
            assertThat(rs.getInt("id"),is(1));
            assertThat(rs.getString("title"),is("Die Verurteilten"));
            assertThat(rs.getInt("genre"),is("Drama"));
            rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void t02_createExemplars(){
        int countInserts = 0;
        try {
            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO movie (id, genre, releasedate, title) VALUES (4, 'Sci-Fi', '1995-08-09', 'Test1')";
            countInserts += stmt.executeUpdate(sql);
            sql = "INSERT INTO movie (id, genre, releasedate, title) VALUES (5, 'Drama', '1995-02-03', 'Test2')";
            countInserts += stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        assertThat(countInserts, is(2));

        // Daten abfragen
        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT id, genre, releasedate, title FROM movie");
            ResultSet rs = pstmt.executeQuery();

            rs.next();
            assertThat(rs.getInt("id"),is(1));
            assertThat(rs.getString("title"),is("Die Verurteilten"));
            assertThat(rs.getInt("genre"),is("Drama"));
            rs.next();
            rs.next();
            rs.next();
            assertThat(rs.getInt("id"),is(4));
            assertThat(rs.getString("title"),is("Test1"));
            assertThat(rs.getInt("genre"),is("Sci-Fi"));
            rs.next();
            assertThat(rs.getInt("id"),is(5));
            assertThat(rs.getString("title"),is("Test2"));
            assertThat(rs.getInt("genre"),is("Drama"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void t03_updateExemplar(){
        int countUpdates = 0;
        try {
            Statement stmt = conn.createStatement();
            String sql = "UPDATE movie SET title='Test3' where id=5";
            countUpdates += stmt.executeUpdate(sql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        assertThat(countUpdates,is(1));
        try {
            PreparedStatement pstmt = conn.prepareStatement("SELECT id, genre, releasedate, title FROM movie where id=5");
            ResultSet rs = pstmt.executeQuery();

            rs.next();
            assertThat(rs.getInt("id"),is(5));
            assertThat(rs.getString("title"),is("Test3"));
            assertThat(rs.getInt("genre"),is("Drama"));
            rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
