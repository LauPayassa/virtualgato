package br.com.virtualpet.model;

/**
 * Represents the various status states a pet can be in.
 */
public enum PetStatus {
    HAPPY("Happy"),
    NORMAL("Normal"),
    HUNGRY("Hungry"),
    TIRED("Tired"),
    SAD("Sad"),
    DEAD("Dead");
    
    private final String displayName;
    
    PetStatus(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    @Override
    public String toString() {
        return displayName;
    }
}
