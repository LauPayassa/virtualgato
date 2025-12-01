package br.com.virtualpet.model;

import br.com.virtualpet.GameConfig;

/**
 * Represents a virtual pet with various attributes and behaviors.
 */
public class Pet {
    private String name;
    private int hunger;
    private int happiness;
    private int energy;
    private int health;
    private PetStatus status;
    
    public Pet(String name) {
        this.name = name;
        this.hunger = GameConfig.INITIAL_HUNGER;
        this.happiness = GameConfig.INITIAL_HAPPINESS;
        this.energy = GameConfig.INITIAL_ENERGY;
        this.health = GameConfig.INITIAL_HEALTH;
        this.status = PetStatus.HAPPY;
    }
    
    public void feed() {
        hunger = Math.min(100, hunger + 20);
        updateStatus();
    }
    
    public void play() {
        if (energy > 10) {
            happiness = Math.min(100, happiness + 15);
            energy = Math.max(0, energy - 10);
            hunger = Math.max(0, hunger - 5);
            updateStatus();
        }
    }
    
    public void sleep() {
        energy = Math.min(100, energy + 30);
        hunger = Math.max(0, hunger - 10);
        updateStatus();
    }
    
    public void tick() {
        hunger = Math.max(0, hunger - GameConfig.HUNGER_DECAY_RATE);
        happiness = Math.max(0, happiness - GameConfig.HAPPINESS_DECAY_RATE);
        energy = Math.max(0, energy - GameConfig.ENERGY_DECAY_RATE);
        
        if (hunger < 20 || happiness < 20 || energy < 20) {
            health = Math.max(0, health - 1);
        }
        
        updateStatus();
    }
    
    private void updateStatus() {
        if (health <= 0) {
            status = PetStatus.DEAD;
        } else if (hunger < 20) {
            status = PetStatus.HUNGRY;
        } else if (energy < 20) {
            status = PetStatus.TIRED;
        } else if (happiness < 20) {
            status = PetStatus.SAD;
        } else if (hunger > 80 && happiness > 80 && energy > 80) {
            status = PetStatus.HAPPY;
        } else {
            status = PetStatus.NORMAL;
        }
    }
    
    // Getters
    public String getName() {
        return name;
    }
    
    public int getHunger() {
        return hunger;
    }
    
    public int getHappiness() {
        return happiness;
    }
    
    public int getEnergy() {
        return energy;
    }
    
    public int getHealth() {
        return health;
    }
    
    public PetStatus getStatus() {
        return status;
    }
    
    public boolean isAlive() {
        return status != PetStatus.DEAD;
    }
}
