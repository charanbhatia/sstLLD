package com.example.profiles;

/**
 * ProfileService now creates immutable UserProfile objects using Builder pattern.
 * No more mutation after creation.
 */
public class ProfileService {

    // Creates a minimal profile with just required fields
    public UserProfile createMinimal(String id, String email) {
        return UserProfile.builder(id, email).build();
    }

    // Creates a profile with additional optional fields
    public UserProfile createProfile(String id, String email, String displayName, 
                                   String phone, boolean marketingOptIn) {
        return UserProfile.builder(id, email)
                .displayName(displayName)
                .phone(phone)
                .marketingOptIn(marketingOptIn)
                .build();
    }

    // Creates a new profile with updated display name (returns new immutable instance)
    public UserProfile withUpdatedDisplayName(UserProfile original, String displayName) {
        return UserProfile.builder(original.getId(), original.getEmail())
                .phone(original.getPhone())
                .displayName(displayName)  // Updated field
                .address(original.getAddress())
                .marketingOptIn(original.isMarketingOptIn())
                .twitter(original.getTwitter())
                .github(original.getGithub())
                .build();
    }

    // Creates a new profile with updated marketing preference
    public UserProfile withUpdatedMarketingOptIn(UserProfile original, boolean marketingOptIn) {
        return UserProfile.builder(original.getId(), original.getEmail())
                .phone(original.getPhone())
                .displayName(original.getDisplayName())
                .address(original.getAddress())
                .marketingOptIn(marketingOptIn)  // Updated field
                .twitter(original.getTwitter())
                .github(original.getGithub())
                .build();
    }
}
