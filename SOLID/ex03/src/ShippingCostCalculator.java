public class ShippingCostCalculator {
    double cost(Shipment s){
        if ("STANDARD".equals(s.type)) return 50 + 5*s.weightKg;
        if ("EXPRESS".equals(s.type))  return 80 + 8*s.weightKg;
        if ("OVERNIGHT".equals(s.type))return 120 + 10*s.weightKg;
        throw new IllegalArgumentException("Unknown type: " + s.type);
    }
}
