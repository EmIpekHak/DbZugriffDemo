package at.hakimst;

import java.sql.*;

public class Main {
    public static void main(String[] args) { // Changed to static
        selectAllDemo(); // Fixed method name case
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/test", "root", "");
            } catch (SQLException e) {
                throw new RuntimeException("Db Verbindung nicht möglich: " + e.getMessage());
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Treiber nicht gefunden: " + e.getMessage());
        }
    }

    public static void insertStudentDemo(){
        System.out.println("Select Demo mit JDBC");
        String connectionUrl = "jdbc:mysql://localhost:3306/jdbcdemo";
        String user = "root";
        String pwd = "";

        try (Connection con = DriverManager.getConnection(connectionUrl, user, pwd)) {
            System.out.println("Verbindung zur DB hergestellt!");

            PreparedStatement preparedStatement = con.prepareStatement("INSERT INTO students (id, name, email) VALUES (NULL, ?, ?)");
            try {
                preparedStatement.setString(1, "Emin");
                preparedStatement.setString(2, "emin@example.com");
                preparedStatement.executeUpdate();
                System.out.println("Student eingefügt!");
            } catch (SQLException ex){
                System.out.println("Fehler beim Erstellen des PreparedStatements: ");
            }
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString("name");
                String email = rs.getString("email");
                System.out.println("Student aus der DB: [Id] " + id + ", [Name] " + name + ", [Email] " + email);
            }
        } catch (SQLException e) { // Moved this catch block outside
            System.out.println("Fehler beim Aufbau der Verbindung zur Datenbank: " + e.getMessage());
        }

    }

    public static void selectAllDemo() { // Fixed method name case
        System.out.println("Select Demo mit JDBC");
        String sqlSelectAllPersons = "SELECT * FROM student";
        String connectionUrl = "jdbc:mysql://localhost:3306/jdbcdemo";
        String user = "root";
        String pwd = "";

        try (Connection con = DriverManager.getConnection(connectionUrl, user, pwd)) {
            System.out.println("Verbindung zur DB hergestellt!");

            PreparedStatement preparedStatement = con.prepareStatement(sqlSelectAllPersons);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString("name");
                String email = rs.getString("email");
                System.out.println("Student aus der DB: [Id] " + id + ", [Name] " + name + ", [Email] " + email);
            }
        } catch (SQLException e) { // Moved this catch block outside
            System.out.println("Fehler beim Aufbau der Verbindung zur Datenbank: " + e.getMessage());
        }
    }
}
