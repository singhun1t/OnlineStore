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
        // This method should read a CSV file with product information and
        // populate the inventory ArrayList with Product objects. Each line
        // of the CSV file contains product information in the following format:
        //
        // id,name,price
        //
        // where id is a unique string identifier, name is the product name,
        // price is a double value representing the price of the product
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
        // This method should display a list of products from the inventory,
        // and prompt the user to add items to their cart. The method should
        // prompt the user to enter the ID of the product they want to add to
        // their cart. The method should
        // add the selected product to the cart ArrayList.
        System.out.println("Availabe Products: ");
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
        // This method should display the items in the cart ArrayList, along
        // with the total cost of all items in the cart. The method should
        // prompt the user to remove items from their cart by entering the ID
        // of the product they want to remove. The method should update the cart ArrayList and totalAmount
        // variable accordingly.
        if(cart.isEmpty()){
            System.out.println("Cart is empty.");
        }else{
            for (Product product: cart){
                System.out.println(product.getId() + "  " +product.getName() + "  $" + product.getPrice());
                totalAmount += product.getPrice();
            }
            System.out.println("Total: $" + totalAmount);
        }
        System.out.println("Enter ID of product that you wish to remove or Type 'Finish' to finish transaction ");
        String input = scanner.nextLine();

        if(!input.equalsIgnoreCase("Finish")){
            Product productRemove = findProductById(input, cart);
            if(productRemove != null){
                cart.remove(productRemove);
                totalAmount -= productRemove.getPrice();
                System.out.println("Product has been removed from the cart");
            }else{
                System.out.println("Product not found in cart");
            }
        }
    }

    public static void checkOut(ArrayList<Product> cart, double totalAmount) {
        // This method should calculate the total cost of all items in the cart,
        // and display a summary of the purchase to the user. The method should
        // prompt the user to confirm the purchase, and deduct the total cost
        // from their account if they confirm.
    }

    public static Product findProductById(String id, ArrayList<Product> inventory) {
        // This method should search the inventory ArrayList for a product with
        // the specified ID, and return the corresponding Product object. If
        // no product with the specified ID is found, the method should return
        // null.
        return null;
    }
}