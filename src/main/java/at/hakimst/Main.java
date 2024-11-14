package at.hakimst;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        selectAllDemo();
        insertStudentDemo();
        updateStudentDemo();
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

    public static void updateStudentDemo() {
        System.out.println("Update Demo mit JDBC");
        String connectionUrl = "jdbc:mysql://localhost:3306/jdbcdemo";
        String user = "root";
        String pwd = "";

        try (Connection con = DriverManager.getConnection(connectionUrl, user, pwd)) {
            System.out.println("Verbindung zur DB hergestellt!");

            String updateSql = "UPDATE students SET name = ?, email = ? WHERE id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(updateSql);
            preparedStatement.setString(1, "Neuer Name"); // new name
            preparedStatement.setString(2, "neue.email@example.com"); // new email
            preparedStatement.setInt(3, 1); // ID of the student to update

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student erfolgreich aktualisiert!");
            } else {
                System.out.println("Kein Student mit dieser ID gefunden.");
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Aktualisieren des Studenten: " + e.getMessage());
        }
    }

    public static void insertStudentDemo() {
        System.out.println("Insert Demo mit JDBC");
        String connectionUrl = "jdbc:mysql://localhost:3306/jdbcdemo";
        String user = "root";
        String pwd = "";

        try (Connection con = DriverManager.getConnection(connectionUrl, user, pwd)) {
            System.out.println("Verbindung zur DB hergestellt!");

            String insertSql = "INSERT INTO students (id, name, email) VALUES (NULL, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(insertSql);
            preparedStatement.setString(1, "Emin");
            preparedStatement.setString(2, "emin@example.com");

            preparedStatement.executeUpdate();
            System.out.println("Student eingefügt!");
        } catch (SQLException e) {
            System.out.println("Fehler beim Einfügen des Studenten: " + e.getMessage());
        }
    }

    public static void selectAllDemo() {
        System.out.println("Select Demo mit JDBC");
        String sqlSelectAllPersons = "SELECT * FROM students";
        String connectionUrl = "jdbc:mysql://localhost:3306/jdbcdemo";
        String user = "root";
        String pwd = "";

        try (Connection con = DriverManager.getConnection(connectionUrl, user, pwd)) {
            System.out.println("Verbindung zur DB hergestellt!");

            PreparedStatement preparedStatement = con.prepareStatement(sqlSelectAllPersons);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                System.out.println("Student aus der DB: [Id] " + id + ", [Name] " + name + ", [Email] " + email);
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Abrufen der Studenten: " + e.getMessage());
        }
    }
}
