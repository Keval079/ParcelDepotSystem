package model;

import java.util.LinkedList;
import java.util.Queue;

public class QueueofCustomers {
    private Queue<Customer> customerQueue;

    public QueueofCustomers() {
        this.customerQueue = new LinkedList<>();
    }

    // Add a customer to the queue
    public void addCustomer(Customer customer) {
        customerQueue.add(customer);
        System.out.println("Added customer to the queue: " + customer); // Debugging
    }

    // Remove and return the next customer from the queue
    public Customer removeCustomer() {
        if (isEmpty()) {
            System.out.println("No customers to remove. Queue is empty."); // Debugging
            return null; // Return null if queue is empty
        }
        Customer removedCustomer = customerQueue.poll();
        System.out.println("Removed customer: " + removedCustomer); // Debugging
        return removedCustomer;
    }

    // Peek at the next customer without removing them
    public Customer getNextCustomer() {
        if (isEmpty()) {
            System.out.println("No customers to view. Queue is empty."); // Debugging
            return null; // Return null if queue is empty
        }
        return customerQueue.peek();
    }

    // Check if the queue is empty
    public boolean isEmpty() {
        boolean empty = customerQueue.isEmpty();
        if (empty) {
            System.out.println("Customer queue is currently empty."); // Debugging
        }
        return empty;
    }

    // Return the entire queue of customers
    public Queue<Customer> getQueue() {
        return customerQueue;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "Customer Queue is empty.";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Customer Queue:\n");
        for (Customer customer : customerQueue) {
            sb.append(customer).append("\n");
        }
        return sb.toString();
    }
}
