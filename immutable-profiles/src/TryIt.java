import com.example.profiles.*;

public class TryIt {
    public static void main(String[] args) {
        System.out.println("=== Immutable UserProfile with Builder Pattern Demo ===\n");
        
        ProfileService svc = new ProfileService();
        
        // Create minimal profile
        UserProfile p1 = svc.createMinimal("u1", "alice@example.com");
        System.out.println("Minimal profile created:");
        System.out.println("  ID: " + p1.getId());
        System.out.println("  Email: " + p1.getEmail());
        System.out.println("  Display Name: " + p1.getDisplayName());
        System.out.println();
        
        // Create profile with optional fields using Builder
        UserProfile p2 = UserProfile.builder("u2", "bob@example.com")
                .displayName("Bob Smith")
                .phone("+1-555-123-4567")
                .marketingOptIn(true)
                .twitter("@bobsmith")
                .github("bobsmith")
                .address("123 Main St, City, State")
                .build();
        
        System.out.println("Full profile created:");
        System.out.println("  ID: " + p2.getId());
        System.out.println("  Email: " + p2.getEmail());
        System.out.println("  Display Name: " + p2.getDisplayName());
        System.out.println("  Phone: " + p2.getPhone());
        System.out.println("  Marketing Opt-in: " + p2.isMarketingOptIn());
        System.out.println("  Twitter: " + p2.getTwitter());
        System.out.println("  GitHub: " + p2.getGithub());
        System.out.println();
        
        // Demonstrate immutability - no setters available
        System.out.println("=== Immutability Demonstration ===");
        System.out.println("Original email: " + p1.getEmail());
        
        // This would not compile - no setters available:
        // p1.setEmail("evil@example.com"); // COMPILE ERROR - method doesn't exist!
        
        // To "update" a field, we create a new instance
        UserProfile updatedProfile = svc.withUpdatedDisplayName(p1, "Alice Johnson");
        System.out.println("Original profile display name: " + p1.getDisplayName());
        System.out.println("New profile display name: " + updatedProfile.getDisplayName());
        System.out.println("Original profile unchanged: " + (p1.getDisplayName() == null));
        System.out.println();
        
        // Demonstrate validation
        System.out.println("=== Validation Demonstration ===");
        try {
            UserProfile invalid = UserProfile.builder("", "invalid-email")
                    .displayName("This display name is way too long and exceeds the 100 character limit that we have set for display names")
                    .build();
        } catch (IllegalArgumentException e) {
            System.out.println("Validation caught: " + e.getMessage());
        }
        
        try {
            UserProfile invalidTwitter = UserProfile.builder("u3", "valid@example.com")
                    .twitter("invalid_handle") // Should start with @
                    .build();
        } catch (IllegalArgumentException e) {
            System.out.println("Validation caught: " + e.getMessage());
        }
        
        System.out.println("\n=> UserProfile is now immutable! No setters, thread-safe, and predictable.");
    }
}
