package com.example.orders;

import java.util.List;

/**
 * OrderService now creates immutable Order objects using Builder pattern.
 * No more mutation after creation.
 */
public class OrderService {

    // Creates order using the new Builder pattern
    public Order createOrder(String id, String email, List<OrderLine> lines, 
                           Integer discount, boolean expedited, String notes) {
        Order.Builder builder = Order.builder(id, email);
        
        if (lines != null) {
            builder.addLines(lines);
        }
        
        return builder
                .discountPercent(discount)
                .expedited(expedited)
                .notes(notes)
                .build();
    }

    // Convenience method for minimal order
    public Order createMinimalOrder(String id, String email, OrderLine... lines) {
        Order.Builder builder = Order.builder(id, email);
        
        for (OrderLine line : lines) {
            builder.addLine(line);
        }
        
        return builder.build();
    }

    // Create order with discount
    public Order createDiscountedOrder(String id, String email, int discountPercent, 
                                     OrderLine... lines) {
        Order.Builder builder = Order.builder(id, email);
        
        for (OrderLine line : lines) {
            builder.addLine(line);
        }
        
        return builder
                .discountPercent(discountPercent)
                .build();
    }

    // Create expedited order
    public Order createExpeditedOrder(String id, String email, String notes, 
                                    OrderLine... lines) {
        Order.Builder builder = Order.builder(id, email);
        
        for (OrderLine line : lines) {
            builder.addLine(line);
        }
        
        return builder
                .expedited(true)
                .notes(notes)
                .build();
    }
}
