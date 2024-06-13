package view;

import entity.Customer;
import service.CustomerService;

import java.util.Optional;
import java.util.Scanner;

public class CustomerView {
    private CustomerService customerService = new CustomerService();
    private Scanner scanner = new Scanner(System.in);

    public void showMenu() {
        while (true) {
            System.out.println("1. Add Customer");
            System.out.println("2. View Customer");
            System.out.println("3. View All Customers");
            System.out.println("4. Update Customer");
            System.out.println("5. Delete Customer");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    addCustomer();
                    break;
                case 2:
                    viewCustomer();
                    break;
                case 3:
                    viewAllCustomers();
                    break;
                case 4:
                    updateCustomer();
                    break;
                case 5:
                    deleteCustomer();
                    break;
                case 6:
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private void addCustomer() {
        // System.out.print("Enter Customer ID: ");
        // int id = scanner.nextInt();
        // scanner.nextLine();  // Consume newline
        System.out.print("Enter Customer Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Customer Email: ");
        String email = scanner.nextLine();
        
        Customer customer = new Customer(name, email);
        customerService.add(customer);
        System.out.println("Customer added successfully.");
    }

    private void viewCustomer() {
        System.out.print("Enter Customer ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        
        Optional<Customer> customer = customerService.getById(id);
        customer.ifPresentOrElse(
            System.out::println,
            () -> System.out.println("Customer not found.")
        );
    }

    private void viewAllCustomers() {
        customerService.getAll().forEach(System.out::println);
    }

    private void updateCustomer() {
        System.out.print("Enter Customer ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        Optional<Customer> optionalCustomer = customerService.getById(id);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            System.out.print("Enter new name (current: " + customer.getName() + "): ");
            String name = scanner.nextLine();
            System.out.print("Enter new email (current: " + customer.getEmail() + "): ");
            String email = scanner.nextLine();
            
            customer.setName(name);
            customer.setEmail(email);
            customerService.update(id,customer);
            System.out.println("Customer updated successfully.");
        } else {
            System.out.println("Customer not found.");
        }
    }

    private void deleteCustomer() {
        System.out.print("Enter Customer ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        customerService.delete(id);
        System.out.println("Customer deleted successfully.");
    }

    public static void main(String[] args) {
        CustomerView view = new CustomerView();
        view.showMenu();
    }
}