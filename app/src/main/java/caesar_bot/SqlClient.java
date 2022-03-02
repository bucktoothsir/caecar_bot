package caesar_bot;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SqlClient {
    private Connection connect;
    private Statement statement;
    private ResultSet rs;
    private static String url = "jdbc:mysql://127.0.0.1:3306/TelegramBotDB?useSSL=FALSE";
    private static String user = "root";
    private static String password = "woshizhu";

    public void read() {
        System.out.println("read");
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Setup the connection with the DB
            //connect = DriverManager.getConnection(url+"&user="+user+"&password="+password);
            connect = DriverManager.getConnection(url, user, password);
             // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            rs = statement.executeQuery("select * from TaskTable");
            //STEP 5: Extract data from result set
            while(rs.next()){
                //Retrieve by column name
                int id  = rs.getInt("id");
                String create_time = rs.getString("create_time");
                String task_happend_time = rs.getString("task_happend_time");
            
                //Display values
                System.out.print("ID: " + id);
                System.out.print(", create_time: " + create_time);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void write() {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Setup the connection with the DB
            //connect = DriverManager.getConnection(url+"&user="+user+"&password="+password);
            connect = DriverManager.getConnection(url, user, password);
            // Statements allow to issue SQL queries to the database
            statement = connect.createStatement();
            String query = " INSERT INTO TaskTable SET create_time=\"2007-02-07 17:29:46\", task_happend_time=\"2007-02-07 17:29:46\", task_descrption=\"haha\", user_id=1;";
    
            // create a sql date object so we can use it in our INSERT statement
            // Calendar calendar = Calendar.getInstance();
            //java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());
            // String startDate = "2007-02-07 17:29:46";
            //create the mysql insert preparedstatement
            // PreparedStatement preparedStmt = connect.prepareStatement(query);
            // preparedStmt.setString (1, startDate);
            // preparedStmt.setString(2, startDate);
            // preparedStmt.setString (3, "Rubble");
            // preparedStmt.setInt (4, 5000);
            statement.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    
}
