package com.example.orders;

/**
 * Immutable OrderLine to prevent mutations after adding to Order
 */
public final class OrderLine {
    private final String sku;
    private final int quantity;
    private final int unitPriceCents;

    public OrderLine(String sku, int quantity, int unitPriceCents) {
        if (sku == null || sku.trim().isEmpty()) {
            throw new IllegalArgumentException("SKU cannot be null or empty");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        if (unitPriceCents < 0) {
            throw new IllegalArgumentException("Unit price cannot be negative");
        }
        
        this.sku = sku.trim();
        this.quantity = quantity;
        this.unitPriceCents = unitPriceCents;
    }

    public String getSku() { return sku; }
    public int getQuantity() { return quantity; }
    public int getUnitPriceCents() { return unitPriceCents; }

    // Remove the setter to make OrderLine immutable
    // public void setQuantity(int q) { this.quantity = q; } // REMOVED

    @Override
    public String toString() {
        return String.format("OrderLine{sku='%s', quantity=%d, unitPriceCents=%d}", 
                           sku, quantity, unitPriceCents);
    }
}
