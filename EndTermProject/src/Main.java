
import java.sql.*;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) {


        try {            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "admin");
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Coffee Suppliers");
                System.out.println("Choose an option:");
                System.out.println("1. View all available coffee bean suppliers:");
                System.out.println("2. Modify coffee bean supplier database");
                System.out.println("3. Order coffee beans");
                System.out.println("4. Exit");
                System.out.print("Type number operation(1-4): ");
                int choice = scanner.nextInt();
                scanner.nextLine();
                System.out.println("");
                System.out.println("*******************************");
                switch (choice) {
                    case 1:
                        viewAllCoffeeSuppliers(connection);
                        break;
                    case 2:
                        boolean isExit = true;
                        while(isExit) {
                            System.out.println("1. Add coffee bean supplier");
                            System.out.println("2. Update coffee bean supplier");
                            System.out.println("3. Delete coffee bean supplier");
                            System.out.println("4. Exit");
                            System.out.print("Type number operation(1-4): ");
                            int databaseChoice = scanner.nextInt();
                            scanner.nextLine();
                            System.out.println("");
                            System.out.println("*******************************");
                            switch (databaseChoice){
                                case 1:
                                    addCoffeeSuppliar(connection, scanner);
                                    break;
                                case 2:
                                    updateCoffeeSupplier(connection, scanner);
                                    break;
                                case 3:
                                    deleteCoffeeSupplier(connection, scanner);
                                    break;
                                case 4:
                                    isExit = false;
                                default:
                                    System.out.println("Non existent number operation was chosen. Choose again.");
                                    break;
                            }
                        }
                    case 3:
                        break;
                    case 4:
                        connection.close();
                        System.out.println("Work is done");
                        return;
                    default:
                        System.out.println("Non existent number operation was chosen. Choose again.");
                        break;                }
            }        } catch (SQLException e) {
            e.printStackTrace();
        }    }
    public static void viewAllCoffeeSuppliers(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM COFFEE_BEAN_SUPPLIERS");
        ResultSet r = statement.executeQuery();
        while (r.next()) {
            System.out.println("Id: " + r.getInt("id"));
            System.out.println("Coffee bean name: " + r.getString("coffeeBeanType "));
            System.out.println("Country of origin: " + r.getString("country"));
            System.out.println("Price/kg: " + r.getInt("price"));
            System.out.println("Can be shipped by train(TRUE of FALSE): " + r.getBoolean("byTrain"));
            System.out.println("Can be shipped by plane(TRUE of FALSE): " + r.getBoolean("byPlane"));
            System.out.println("Can be shipped by ship(TRUE of FALSE): " + r.getBoolean("byShip"));
            System.out.println("*******************************");
            System.out.println("");
        }    }
    public static void addCoffeeSuppliar(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Type coffee supplier name: ");
        String coffeeBeanSupplier = scanner.nextLine();
        System.out.print("Type country of origin: ");
        String countryOfOrigin = scanner.nextLine();
        scanner.nextLine();
        System.out.print("Type price per kg: ");
        int pricePerKg = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Available by train(TRUE or FALSE): ");
        Boolean trainAvailable = scanner.nextBoolean();
        System.out.print("Available by plane(TRUE or FALSE): ");
        Boolean planeAvailable = scanner.nextBoolean();
        System.out.print("Available by ship(TRUE or FALSE): ");
        Boolean shipAvailable = scanner.nextBoolean();

        PreparedStatement statement = connection.prepareStatement("INSERT INTO COFFEE_BEAN_SUPPLIERS(coffeeBeanType ,country, price,  byTrain , byPlane, byShip) VALUES (?, ?, ?, ?,?,?)");
        statement.setString(1, coffeeBeanSupplier);
        statement.setString(2, countryOfOrigin);
        statement.setInt(3, pricePerKg);
        statement.setBoolean(4,trainAvailable );
        statement.setBoolean(5,planeAvailable);
        statement.setBoolean(6,shipAvailable);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Coffee bean supplier was added");
        } else {
            System.out.println("Error occurred while adding new coffee bean supplier type.");
        }
    }
    public static void updateCoffeeSupplier(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Type new coffee supplier id: ");
        int id = scanner.nextInt();
        System.out.print("Type coffee supplier's name: ");
        String coffeeType = scanner.nextLine();
        System.out.print("Type country of origin: ");
        String countryOfOrigin = scanner.nextLine();
        System.out.print("Type coffee bean price/kg: ");
        int pricePerkg = scanner.nextInt();
        System.out.print("Available by train(TRUE or FALSE): ");
        Boolean trainAvailable = scanner.nextBoolean();
        System.out.print("Available by plane(TRUE or FALSE): ");
        Boolean planeAvailable = scanner.nextBoolean();
        System.out.print("Available by ship(TRUE or FALSE): ");
        Boolean shipAvailable = scanner.nextBoolean();
        PreparedStatement statement = connection.prepareStatement("UPDATE  COFFEE_BEAN_SUPPLIERS SET coffeeBeanType =?,  country =?, price=?, byTrain=?, byPlane=?, byShip=? WHERE id=?");
        statement.setString(1, coffeeType);
        statement.setString(2, countryOfOrigin);
        statement.setInt(3, pricePerkg);
        statement.setBoolean(4, trainAvailable);
        statement.setBoolean(5, planeAvailable);
        statement.setBoolean(6, shipAvailable);
        statement.setInt(7, id);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Coffee supplier information was updated.");
        } else {
            System.out.println("Error occurred while updating coffee supplier information.");
        }
    }
    public static void deleteCoffeeSupplier(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Type coffee bean supplier id:  ");
        int id = scanner.nextInt();
        PreparedStatement statement = connection.prepareStatement("DELETE FROM  COFFEE_BEAN_SUPPLIERS WHERE id=?");
        statement.setInt(1, id);
        int rowsAffected = statement.executeUpdate();
        System.out.println("Coffee bean supplier was successfully deleted.");
        if (rowsAffected > 0) {
            System.out.println("Coffee bean supplier was successfully deleted.");
        } else {
            System.out.println("Error occurred while deleting coffee bean supplier .");
        }
    }
}