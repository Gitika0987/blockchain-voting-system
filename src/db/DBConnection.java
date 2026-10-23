package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class DBConnection {
    // Standard SQLite connection, saves it to a file exactly where your code runs
    private static final String URL = "jdbc:sqlite:blockchain_votes.db";

    public static Connection getConnection() throws SQLException {
        try {
            // Load the SQLite driver
            Class.forName("org.sqlite.JDBC");
            return DriverManager.getConnection(URL);
        } catch (ClassNotFoundException e) {
            System.err.println("SQLite JDBC Driver not found. Please add the sqlite-jdbc.jar to your classpath.");
            throw new SQLException(e);
        }
    }

    public static void initializeDatabase() {
        try (Connection con = getConnection(); Statement stmt = con.createStatement()) {
            
            // 1. VOTERS Table
            String createVoters = "CREATE TABLE IF NOT EXISTS voters ("
                    + "voter_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "name TEXT,"
                    + "age INTEGER,"
                    + "aadhaar TEXT UNIQUE,"
                    + "password TEXT,"
                    + "has_voted INTEGER DEFAULT 0,"
                    + "pin TEXT)";
                    
            // 2. CANDIDATES Table
            String createCandidates = "CREATE TABLE IF NOT EXISTS candidates ("
                    + "candidate_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "name TEXT,"
                    + "party TEXT)";
                    
            // 3. VOTES Table
            String createVotes = "CREATE TABLE IF NOT EXISTS votes ("
                    + "vote_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "voter_id INTEGER,"
                    + "candidate_id INTEGER,"
                    + "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,"
                    + "block_hash TEXT,"
                    + "previous_hash TEXT)";

            // 4. ADMIN Table
            String createAdmin = "CREATE TABLE IF NOT EXISTS admin ("
                    + "admin_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + "username TEXT,"
                    + "password TEXT)";

            // Execute all creation statements
            stmt.execute(createVoters);
            stmt.execute(createCandidates);
            stmt.execute(createVotes);
            stmt.execute(createAdmin);

            // Insert default admin if not exists (using SQLite UPSERT pattern logic)
            stmt.execute("INSERT OR IGNORE INTO admin (admin_id, username, password) VALUES (1, 'admin', 'admin123')");
            
            // Insert default candidates if the table is completely empty
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS count FROM candidates");
            if (rs.next() && rs.getInt("count") == 0) {
                stmt.execute("INSERT INTO candidates (name, party) VALUES ('Alice Smith', 'Party A')");
                stmt.execute("INSERT INTO candidates (name, party) VALUES ('Bob Johnson', 'Party B')");
            }
            
            System.out.println("SQLite Database Initialized! The DB file has been safely created.");
            
        } catch (SQLException e) {
            System.err.println("Database Initialization Failed.");
            e.printStackTrace();
        }
    }
}
