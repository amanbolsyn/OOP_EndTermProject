import java.sql.*;
import java.util.Scanner;


interface OrderStrategy {
    void Order() throws SQLException;
}

class OrderByTrain implements OrderStrategy {
    @Override
    public void Order() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "admin");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input new coffee supplier id: ");
        int id = scanner.nextInt();
        PreparedStatement statement = connection.prepareStatement("SELECT byTrain, priceByTrain, price FROM COFFEE_BEAN_SUPPLIERS WHERE id=?");
        statement.setInt(1, id);
        ResultSet r = statement.executeQuery();
        r.next();
        if(r.getBoolean("byTrain")){
            System.out.print("Input kg amount: ");
            int kgAmount = scanner.nextInt();
            System.out.println("");
            int coffeeBeanPrice = kgAmount * r.getInt("price");
            System.out.println("Coffee bean price: " + coffeeBeanPrice + "$");
            int shippingPrice = kgAmount * r.getInt("priceByTrain");
            System.out.println("Shipping price: " + shippingPrice + "$");
            System.out.println("Total amount: " +(shippingPrice+coffeeBeanPrice) +"$");
        }
        else{
            System.out.println("");
            System.out.println("Chosen supplier doesn't ship by train. \nChoose another method of shipping or different supplier");

        }



        }
    }


class OrderByPlane implements OrderStrategy {
    @Override
    public void Order() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "admin");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input coffee supplier id: ");
        int id = scanner.nextInt();
        PreparedStatement statement = connection.prepareStatement("SELECT byPlane, priceByPlane, price FROM COFFEE_BEAN_SUPPLIERS WHERE id=?");
        statement.setInt(1, id);
        ResultSet r = statement.executeQuery();
        r.next();
        if(r.getBoolean("byPlane")){
            System.out.print("Input kg amount: ");
            int kgAmount = scanner.nextInt();
            System.out.println("");
            int coffeeBeanPrice = kgAmount * r.getInt("price");
            System.out.println("Coffee bean price: " + coffeeBeanPrice + "$");
            int shippingPrice = kgAmount * r.getInt("priceByPlane");
            System.out.println("Shipping price: " + shippingPrice + "$");
            System.out.println("Total amount: " +(shippingPrice+coffeeBeanPrice) +"$");
        }
        else{
            System.out.println("");
            System.out.println("Chosen supplier doesn't ship by plane. \nChoose another method of shipping or different supplier");

        }


    }
}

class OrderByShip implements OrderStrategy {
    @Override
    public void Order() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "admin");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Input coffee supplier id: ");
        int id = scanner.nextInt();
        PreparedStatement statement = connection.prepareStatement("SELECT byShip, priceByShip, price FROM COFFEE_BEAN_SUPPLIERS WHERE id=?");
        statement.setInt(1, id);
        ResultSet r = statement.executeQuery();
        r.next();
        if(r.getBoolean("byShip")){
              System.out.print("Input kg amount: ");
              int kgAmount = scanner.nextInt();
              System.out.println("");
              int coffeeBeanPrice = kgAmount * r.getInt("price");
              System.out.println("Coffee bean price: " + coffeeBeanPrice + "$");
              int shippingPrice = kgAmount * r.getInt("priceByShip");
              System.out.println("Shipping price: " + shippingPrice + "$");
              System.out.println("Total amount: " +(shippingPrice+coffeeBeanPrice) +"$");
        }
        else{
            System.out.println("");
           System.out.println("Chosen supplier doesn't ship by ship. \nChoose another method of shipping or different supplier");

        }
    }

}


class OrderContext {
    private OrderStrategy orderStrategy;

    public void setOrderStrategy(OrderStrategy  orderStrategy) {
        this.orderStrategy = orderStrategy;
    }

    public void executeOrder() throws SQLException {
        orderStrategy.Order();
    }
}

public class Main {


    public static void main(String[] args) {



        try {            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "admin");
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Coffee Suppliers database");
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
                                    break;

                                default:
                                    System.out.println("Non existent number operation was chosen. Choose again.");
                                    break;
                            }

                        }
                        break;
                    case 3:
                        OrderCoffeeBeans(scanner);
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
            System.out.println("Coffee bean name: " + r.getString("coffeebeantype"));
            System.out.println("Country of origin: " + r.getString("country"));
            System.out.println("Price/kg: " + r.getInt("price"));
            System.out.println("Can be shipped by train(TRUE of FALSE): " + r.getBoolean("byTrain") + " Shipment price by Train per kg: $" +r.getInt("priceByTrain"));
            System.out.println("Can be shipped by plane(TRUE of FALSE): " + r.getBoolean("byPlane")+ " Shipment price by Plane per kg: $" +r.getInt("priceByPlane"));
            System.out.println("Can be shipped by ship(TRUE of FALSE): " + r.getBoolean("byShip")+ " Shipment price by Ship per kg: $" +r.getInt("priceByShip"));
            System.out.println("*******************************");
            System.out.println("");
        }    }
    public static void addCoffeeSuppliar(Connection connection, Scanner scanner) throws SQLException {
        int byTrainPrice = 0, byPlanePrice = 0, byShipPrice = 0;

        System.out.print("Type coffee supplier name: ");
        String coffeeBeanSupplier = scanner.nextLine();
        System.out.print("Type country of origin: ");
        String countryOfOrigin = scanner.nextLine();
        System.out.print("Type price per kg: ");
        int pricePerKg = scanner.nextInt();
        System.out.print("Available by train(TRUE or FALSE): ");
        Boolean trainAvailable = scanner.nextBoolean();

        if (trainAvailable){
            System.out.print("Price by train per kg: $");
            byTrainPrice = scanner.nextInt();
        }
        System.out.print("Available by plane(TRUE or FALSE): ");
        Boolean planeAvailable = scanner.nextBoolean();

        if(planeAvailable){
            System.out.print("Price by plane per kg: $");
            byPlanePrice = scanner.nextInt();
        }
        System.out.print("Available by ship(TRUE or FALSE): ");
        Boolean shipAvailable = scanner.nextBoolean();


        if(shipAvailable){
            System.out.print("Price by ship per kg: $");
            byShipPrice = scanner.nextInt();
        }

        PreparedStatement statement = connection.prepareStatement("INSERT INTO COFFEE_BEAN_SUPPLIERS(coffeeBeanType ,country, price,  byTrain, priceByTrain , byPlane, priceByPlane, byShip, priceByShip) VALUES (?, ?, ?, ?,?,?,?,?,?)");
        statement.setString(1, coffeeBeanSupplier);
        statement.setString(2, countryOfOrigin);
        statement.setInt(3, pricePerKg);
        statement.setBoolean(4,trainAvailable );
        statement.setInt(5, byTrainPrice);
        statement.setBoolean(6,planeAvailable);
        statement.setInt(7,byPlanePrice);
        statement.setBoolean(8,shipAvailable);
        statement.setInt(9,byShipPrice);
        int rowsAffected = statement.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Coffee bean supplier was added");
            System.out.println("");
            System.out.println("*******************************");
        } else {
            System.out.println("Error occurred while adding new coffee bean supplier type.");
            System.out.println("");
            System.out.println("*******************************");
        }
    }
    public static void updateCoffeeSupplier(Connection connection, Scanner scanner) throws SQLException {
        int byTrainPrice = 0, byPlanePrice = 0, byShipPrice = 0;

        System.out.print("Type new coffee supplier id: ");
        int id = scanner.nextInt();
        System.out.print("Type coffee supplier's name: ");
        scanner.nextLine();
        String coffeeBeanType = scanner.nextLine();
        System.out.print("Type country of origin: ");
        String countryOfOrigin = scanner.nextLine();
        System.out.print("Type coffee bean price/kg: ");
        int pricePerkg = scanner.nextInt();
        System.out.print("Available by train(TRUE or FALSE): ");
        Boolean trainAvailable = scanner.nextBoolean();

        if (trainAvailable){
            System.out.print("Price by train per kg: $");
            byTrainPrice = scanner.nextInt();
        }

        System.out.print("Available by plane(TRUE or FALSE): ");
        Boolean planeAvailable = scanner.nextBoolean();

        if(planeAvailable){
            System.out.print("Price by plane per kg: $");
            byPlanePrice = scanner.nextInt();
        }

        System.out.print("Available by ship(TRUE or FALSE): ");
        Boolean shipAvailable = scanner.nextBoolean();

        if(shipAvailable){
            System.out.print("Price by ship per kg: $");
            byShipPrice = scanner.nextInt();
        }


        PreparedStatement statement = connection.prepareStatement("UPDATE  COFFEE_BEAN_SUPPLIERS SET coffeebeantype =?,  country =?, price=?, byTrain=?, priceByTrain = ?, byPlane=?, priceByPlane=?,  byShip=?, priceByShip =? WHERE id=?");
        statement.setString(1, coffeeBeanType);
        statement.setString(2, countryOfOrigin);
        statement.setInt(3, pricePerkg);
        statement.setBoolean(4, trainAvailable);
        statement.setInt(5,byTrainPrice);
        statement.setBoolean(6, planeAvailable);
        statement.setInt(7,byPlanePrice);
        statement.setBoolean(8, shipAvailable);
        statement.setInt(9, byShipPrice);
        statement.setInt(10, id);
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

    public static void OrderCoffeeBeans(Scanner scanner) throws SQLException {
        OrderContext context = new OrderContext();
        boolean isExit = true;
        while(isExit) {

            System.out.println("1. Order from supplier by Train");
            System.out.println("2. Order from supplier by Plane");
            System.out.println("3. Order from supplier by Ship");
            System.out.println("4. Exit");
            System.out.print("Type number operation(1-4): ");
            int orderChoice = scanner.nextInt();

            switch (orderChoice){
                case 1:
                    context.setOrderStrategy(new OrderByTrain());
                    context.executeOrder();
                    break;
                case 2:
                    context.setOrderStrategy(new OrderByPlane());
                    context.executeOrder();
                    break;
                case 3:
                    context.setOrderStrategy(new OrderByShip());
                    context.executeOrder();
                    break;
                case 4:
                    isExit = false;
                    break;
                default:
                    System.out.println("Non existent number operation was chosen. Choose again.");
                    break;
            }

            System.out.println("");
            System.out.println("*******************************");

        }

    }
}
    
