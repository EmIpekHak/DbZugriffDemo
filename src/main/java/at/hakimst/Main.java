package at.hakimst;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        insertCourseDemo();
        selectAllCoursesDemo();
        updateCourseDemo();
        deleteCourseDemo();

        selectAllDemo();
        insertStudentDemo();
        updateStudentDemo();
        deleteStudentDemo();
        findAllByName("zeck");
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

    public static void insertCourseDemo() {
        System.out.println("Insert Course Demo mit JDBC");
        String connectionUrl = "jdbc:mysql://localhost:3306/jdbcdemo";
        String user = "root";
        String pwd = "";

        try (Connection con = DriverManager.getConnection(connectionUrl, user, pwd)) {
            System.out.println("Verbindung zur DB hergestellt!");

            String insertSql = "INSERT INTO courses (course_name, credits) VALUES (?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(insertSql);
            preparedStatement.setString(1, "Java Programming");
            preparedStatement.setInt(2, 5);

            preparedStatement.executeUpdate();
            System.out.println("Kurs eingefügt!");
        } catch (SQLException e) {
            System.out.println("Fehler beim Einfügen des Kurses: " + e.getMessage());
        }
    }

    public static void selectAllCoursesDemo() {
        System.out.println("Select Courses Demo mit JDBC");
        String sqlSelectAllCourses = "SELECT * FROM courses";
        String connectionUrl = "jdbc:mysql://localhost:3306/jdbcdemo";
        String user = "root";
        String pwd = "";

        try (Connection con = DriverManager.getConnection(connectionUrl, user, pwd)) {
            System.out.println("Verbindung zur DB hergestellt!");

            PreparedStatement preparedStatement = con.prepareStatement(sqlSelectAllCourses);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String courseName = rs.getString("course_name");
                int credits = rs.getInt("credits");
                System.out.println("Kurs aus der DB: [Id] " + id + ", [Name] " + courseName + ", [Credits] " + credits);
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Abrufen der Kurse: " + e.getMessage());
        }
    }

    public static void deleteCourseDemo() {
        System.out.println("Delete Course Demo mit JDBC");
        String connectionUrl = "jdbc:mysql://localhost:3306/jdbcdemo";
        String user = "root";
        String pwd = "";

        try (Connection con = DriverManager.getConnection(connectionUrl, user, pwd)) {
            System.out.println("Verbindung zur DB hergestellt!");

            String deleteSql = "DELETE FROM courses WHERE id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(deleteSql);
            preparedStatement.setInt(1, 1); // ID des Kurses zum Löschen

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Kurs erfolgreich gelöscht!");
            } else {
                System.out.println("Kein Kurs mit dieser ID gefunden.");
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Löschen des Kurses: " + e.getMessage());
        }
    }

    public static void updateCourseDemo() {
        System.out.println("Update Course Demo mit JDBC");
        String connectionUrl = "jdbc:mysql://localhost:3306/jdbcdemo";
        String user = "root";
        String pwd = "";

        try (Connection con = DriverManager.getConnection(connectionUrl, user, pwd)) {
            System.out.println("Verbindung zur DB hergestellt!");

            String updateSql = "UPDATE courses SET course_name = ?, credits = ? WHERE id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(updateSql);
            preparedStatement.setString(1, "Advanced Java Programming"); // Neuer Kursname
            preparedStatement.setInt(2, 6); // Neue Credits
            preparedStatement.setInt(3, 1); // ID des Kurses

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Kurs erfolgreich aktualisiert!");
            } else {
                System.out.println("Kein Kurs mit dieser ID gefunden.");
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Aktualisieren des Kurses: " + e.getMessage());
        }
    }


    public static void findAllByName(String namePattern) {
        System.out.println("Search by Name Demo with JDBC");
        String connectionUrl = "jdbc:mysql://localhost:3306/jdbcdemo";
        String user = "root";
        String pwd = "";

        try (Connection con = DriverManager.getConnection(connectionUrl, user, pwd)) {
            System.out.println("Verbindung zur DB hergestellt!");

            // SQL query with LIKE for pattern matching
            String sql = "SELECT * FROM student WHERE name LIKE ?";
            PreparedStatement preparedStatement = con.prepareStatement(sql);

            // Setting the name pattern with wildcards for partial matching
            preparedStatement.setString(1, "%" + namePattern + "%");

            ResultSet rs = preparedStatement.executeQuery();

            // Process the result set
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                System.out.println("Gefundener Student: [Id] " + id + ", [Name] " + name + ", [Email] " + email);
            }
        } catch (SQLException e) {
            System.out.println("Fehler bei der Abfrage: " + e.getMessage());
        }
    }


    public static void deleteStudentDemo() {
        System.out.println("Delete Demo mit JDBC");
        String connectionUrl = "jdbc:mysql://localhost:3306/jdbcdemo";
        String user = "root";
        String pwd = "";

        try (Connection con = DriverManager.getConnection(connectionUrl, user, pwd)) {
            System.out.println("Verbindung zur DB hergestellt!");

            String deleteSql = "DELETE FROM student WHERE id = ?";
            PreparedStatement preparedStatement = con.prepareStatement(deleteSql);
            preparedStatement.setInt(1, 1); // ID of the student to delete

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Student erfolgreich gelöscht!");
            } else {
                System.out.println("Kein Student mit dieser ID gefunden.");
            }
        } catch (SQLException e) {
            System.out.println("Fehler beim Löschen des Studenten: " + e.getMessage());
        }
    }

    public static void updateStudentDemo() {
        System.out.println("Update Demo mit JDBC");
        String connectionUrl = "jdbc:mysql://localhost:3306/jdbcdemo";
        String user = "root";
        String pwd = "";

        try (Connection con = DriverManager.getConnection(connectionUrl, user, pwd)) {
            System.out.println("Verbindung zur DB hergestellt!");

            String updateSql = "UPDATE student SET name = ?, email = ? WHERE id = ?";
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

            String insertSql = "INSERT INTO student (id, name, email) VALUES (NULL, ?, ?)";
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
        String sqlSelectAllPersons = "SELECT * FROM student";
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
