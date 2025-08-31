public class OrderService {
    double taxRate = 0.18;
    EmailClient email = new EmailClient();

    double totalWithTax(double subtotal) {
        return subtotal + subtotal * taxRate;
    }
    void checkout(String customerEmail, double subtotal) {
        double total = totalWithTax(subtotal);
        email.send(customerEmail, "Thanks! Your total is " + total);
        System.out.println("Order stored (pretend DB).");
    }
}