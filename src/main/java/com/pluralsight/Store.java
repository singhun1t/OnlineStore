package com.pluralsight;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Store {

    public static void main(String[] args) {
        // Initialize variables
        ArrayList<Product> inventory = new ArrayList<Product>();
        ArrayList<Product> cart = new ArrayList<Product>();
        double totalAmount = 0.0;

        // Load inventory from CSV file
        loadInventory("products.csv", inventory);

        // Create scanner to read user input
        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        // Display menu and get user choice until they choose to exit
        while (choice != 3) {
            System.out.println("Welcome to the Online Store!");
            System.out.println("1. Show Products");
            System.out.println("2. Show Cart");
            System.out.println("3. Exit");

            choice = scanner.nextInt();
            scanner.nextLine();

            // Call the appropriate method based on user choice
            switch (choice) {
                case 1:
                    displayProducts(inventory, cart, scanner);
                    break;
                case 2:
                    displayCart(cart, scanner, totalAmount);
                    break;
                case 3:
                    System.out.println("Thank you for shopping with us!");
                    break;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }
    }

    public static void loadInventory(String fileName, ArrayList<Product> inventory) {

        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            while((line = reader.readLine())!=null){
                String []parts = line.split("\\|");
                if(parts.length ==3){
                    String id = parts[0];
                    String name = parts[1];
                    double price = Double.parseDouble(parts[2]);
                    inventory.add(new Product(id,name,price));
                }else{
                    System.out.println("CSV file not properly configured");
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Unable to locate file");
        } catch (IOException e) {
            System.out.println("Could not read file");;
        }

    }

    public static void displayProducts(ArrayList<Product> inventory, ArrayList<Product> cart, Scanner scanner) {

        System.out.println("Available Products: ");
        for(Product product: inventory){
            System.out.println(product.getId() + "  " + product.getName() + "  $" + product.getPrice() );
        }
        System.out.println("Enter the ID for the product to add it to the cart");
        String prodID = scanner.nextLine();

        Product productAdd = findProductById(prodID, inventory);
        if(productAdd !=null){
            cart.add(productAdd);
            System.out.println("Product added.");
        }else{
            System.out.println("Product could not be found");
        }
    }

    public static void displayCart(ArrayList<Product> cart, Scanner scanner, double totalAmount) {
        if(cart.isEmpty()){
            System.out.println("Cart is empty.");
        }
            for (Product product: cart){
                System.out.println(product.getId() + "  " +product.getName() + "  $" + product.getPrice());
                totalAmount += product.getPrice();
            }
            System.out.println("Total: $" + totalAmount);
        while(!cart.isEmpty()) {
            System.out.println("Enter ID of product that you wish to remove or Type 'Finish'");
            String input = scanner.nextLine();


            if (!input.equalsIgnoreCase("Finish")) {
                Product productRemove = findProductById(input, cart);
                if (productRemove != null ) {
                    cart.remove(productRemove);
                    totalAmount -= productRemove.getPrice();
                    System.out.println("Product has been removed from the cart");

                }else{
                    System.out.println("Product you wanted to remove is not in the cart");
                }
            }
            if (input.equalsIgnoreCase("Finish")) {
                checkOut(cart, totalAmount);
            }
        }
    }

    public static void checkOut(ArrayList<Product> cart, double totalAmount) {

        if(cart.isEmpty()){
            System.out.println("Cart is empty, unable to checkout");
            return;
        }

        System.out.println("Checkout: ");
        for(Product product : cart){
            System.out.println(product.getName() + "  $" + product.getPrice());
        }
        System.out.println("Total: $" + totalAmount);

        System.out.println("Proceed with the checkout (yes/no)");
        Scanner checkOutScanner = new Scanner(System.in);
        String userInput = checkOutScanner.nextLine();
        while(!userInput.equalsIgnoreCase("yes")&& !userInput.equalsIgnoreCase("no")){
            System.out.println("Invalid input, please enter yes or no");
            userInput = checkOutScanner.nextLine();
        }
        if(userInput.equalsIgnoreCase("yes")){
            System.out.println("Thank you for shopping");
            cart.clear();
        }else{
            System.out.println("Checkout has been canceled");
        }
    }

    public static Product findProductById(String id, ArrayList<Product> inventory) {

        for(Product product: inventory){
            if(product.getId().equalsIgnoreCase(id)){
                return product;
            }
        }
        return null;
    }
}