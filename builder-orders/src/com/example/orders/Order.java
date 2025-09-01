package com.example.orders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Immutable Order class with Builder pattern.
 * All fields are final and cannot be modified after construction.
 */
public final class Order {
    private final String id;
    private final String customerEmail;
    private final List<OrderLine> lines;
    private final Integer discountPercent;
    private final boolean expedited;
    private final String notes;

    // Private constructor - only Builder can create instances
    private Order(Builder builder) {
        this.id = builder.id;
        this.customerEmail = builder.customerEmail;
        this.lines = Collections.unmodifiableList(new ArrayList<>(builder.lines)); // Defensive copy
        this.discountPercent = builder.discountPercent;
        this.expedited = builder.expedited;
        this.notes = builder.notes;
    }

    // Only getters - no setters for immutability
    public String getId() { return id; }
    public String getCustomerEmail() { return customerEmail; }
    public List<OrderLine> getLines() { return lines; } // Returns unmodifiable view
    public Integer getDiscountPercent() { return discountPercent; }
    public boolean isExpedited() { return expedited; }
    public String getNotes() { return notes; }

    // Factory method to create a new Builder
    public static Builder builder(String id, String customerEmail) {
        return new Builder(id, customerEmail);
    }

    public int totalBeforeDiscount() {
        int sum = 0;
        for (OrderLine l : lines) {
            sum += l.getQuantity() * l.getUnitPriceCents();
        }
        return sum;
    }

    public int totalAfterDiscount() {
        int base = totalBeforeDiscount();
        if (discountPercent == null) return base;
        return base - (base * discountPercent / 100);
    }

    /**
     * Builder class for Order with validation
     */
    public static class Builder {
        // Required fields
        private final String id;
        private final String customerEmail;
        
        // Optional fields with defaults
        private final List<OrderLine> lines = new ArrayList<>();
        private Integer discountPercent;
        private boolean expedited = false;
        private String notes;

        // Constructor requires id and customerEmail
        public Builder(String id, String customerEmail) {
            if (id == null || id.trim().isEmpty()) {
                throw new IllegalArgumentException("Order ID cannot be null or empty");
            }
            if (!PricingRules.isValidEmail(customerEmail)) {
                throw new IllegalArgumentException("Invalid email format");
            }
            
            this.id = id.trim();
            this.customerEmail = customerEmail.trim();
        }

        public Builder addLine(OrderLine line) {
            if (line == null) {
                throw new IllegalArgumentException("OrderLine cannot be null");
            }
            this.lines.add(line);
            return this;
        }

        public Builder addLines(List<OrderLine> lines) {
            if (lines != null) {
                for (OrderLine line : lines) {
                    addLine(line);
                }
            }
            return this;
        }

        public Builder discountPercent(Integer discountPercent) {
            this.discountPercent = discountPercent;
            return this;
        }

        public Builder expedited(boolean expedited) {
            this.expedited = expedited;
            return this;
        }

        public Builder notes(String notes) {
            this.notes = notes;
            return this;
        }

        // Centralized validation in build() method
        public Order build() {
            // Validate required fields
            if (lines.isEmpty()) {
                throw new IllegalArgumentException("Order must have at least one line item");
            }

            // Validate discount range
            if (!PricingRules.isValidDiscount(discountPercent)) {
                throw new IllegalArgumentException("Discount percent must be between 0 and 100, got: " + discountPercent);
            }

            // Validate email format (double-check)
            if (!PricingRules.isValidEmail(customerEmail)) {
                throw new IllegalArgumentException("Invalid customer email format: " + customerEmail);
            }

            // Additional business rule validations
            validateBusinessRules();

            return new Order(this);
        }

        private void validateBusinessRules() {
            // Validate that total amount is reasonable
            int total = 0;
            for (OrderLine line : lines) {
                total += line.getQuantity() * line.getUnitPriceCents();
            }
            
            if (total <= 0) {
                throw new IllegalArgumentException("Order total must be positive");
            }

            // Validate SKU uniqueness
            List<String> skus = new ArrayList<>();
            for (OrderLine line : lines) {
                if (skus.contains(line.getSku())) {
                    throw new IllegalArgumentException("Duplicate SKU found: " + line.getSku());
                }
                skus.add(line.getSku());
            }
        }
    }
}
