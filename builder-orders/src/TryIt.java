import com.example.orders.*;

public class TryIt {
    public static void main(String[] args) {
        System.out.println("=== Immutable Order with Builder Pattern Demo ===\n");
        
        try {
            // Create order lines (now immutable)
            OrderLine l1 = new OrderLine("LAPTOP", 1, 200_00); // $200.00 in cents
            OrderLine l2 = new OrderLine("MOUSE", 3, 25_00);   // $25.00 in cents

            // Create order using Builder pattern
            Order order = Order.builder("ORD-001", "customer@example.com")
                    .addLine(l1)
                    .addLine(l2)
                    .discountPercent(10)
                    .expedited(true)
                    .notes("Rush order for VIP customer")
                    .build();

            System.out.println("Order created successfully!");
            System.out.println("Order ID: " + order.getId());
            System.out.println("Customer: " + order.getCustomerEmail());
            System.out.println("Expedited: " + order.isExpedited());
            System.out.println("Notes: " + order.getNotes());
            System.out.println("Discount: " + order.getDiscountPercent() + "%");
            System.out.println("Lines: " + order.getLines().size());
            
            for (OrderLine line : order.getLines()) {
                System.out.println("  - " + line);
            }
            
            System.out.println("Total before discount: $" + (order.totalBeforeDiscount() / 100.0));
            System.out.println("Total after discount: $" + (order.totalAfterDiscount() / 100.0));
            System.out.println();

            // Demonstrate immutability - the following would not compile:
            // order.setDiscountPercent(50); // COMPILE ERROR - method doesn't exist!
            // order.getLines().add(new OrderLine("X", 1, 100)); // RUNTIME ERROR - unmodifiable list!

            System.out.println("=== Immutability Demonstration ===");
            try {
                // Try to modify the lines list - should fail
                order.getLines().add(new OrderLine("HACKER", 1, 1));
                System.out.println("ERROR: Should not reach here!");
            } catch (UnsupportedOperationException e) {
                System.out.println("✓ Prevented modification of order lines: " + e.getClass().getSimpleName());
            }

            // OrderLine is now immutable too - this would not compile:
            // l1.setQuantity(999); // COMPILE ERROR - method doesn't exist!
            
            System.out.println("✓ OrderLine is now immutable - no setters available");
            System.out.println("✓ Order totals remain stable: $" + (order.totalAfterDiscount() / 100.0));
            System.out.println();

            // Demonstrate validation
            System.out.println("=== Validation Demonstration ===");
            
            try {
                Order.builder("", "invalid-email").build();
            } catch (IllegalArgumentException e) {
                System.out.println("✓ Validation caught empty ID: " + e.getMessage());
            }

            try {
                Order.builder("ORD-002", "customer@example.com")
                        .discountPercent(150)  // Invalid discount
                        .build();
            } catch (IllegalArgumentException e) {
                System.out.println("✓ Validation caught invalid discount: " + e.getMessage());
            }

            try {
                Order.builder("ORD-003", "customer@example.com")
                        .build(); // No lines
            } catch (IllegalArgumentException e) {
                System.out.println("✓ Validation caught missing lines: " + e.getMessage());
            }

            // Demonstrate OrderService convenience methods
            System.out.println("\n=== OrderService Convenience Methods ===");
            OrderService service = new OrderService();
            
            Order simpleOrder = service.createMinimalOrder("ORD-004", "simple@example.com",
                    new OrderLine("BOOK", 2, 15_99),
                    new OrderLine("PEN", 5, 1_50)
            );
            
            System.out.println("Simple order total: $" + (simpleOrder.totalAfterDiscount() / 100.0));

            Order discountedOrder = service.createDiscountedOrder("ORD-005", "discount@example.com", 25,
                    new OrderLine("PREMIUM", 1, 500_00)
            );
            
            System.out.println("Discounted order (25% off): $" + (discountedOrder.totalAfterDiscount() / 100.0));

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("\n=> Order is now immutable, validated, and thread-safe!");
    }
}
