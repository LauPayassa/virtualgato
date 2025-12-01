package br.com.virtualpet;

/**
 * Configuration settings for the Virtual Pet game.
 */
public class GameConfig {
    public static final String GAME_TITLE = "Virtual Pet";
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;
    
    public static final int INITIAL_HUNGER = 100;
    public static final int INITIAL_HAPPINESS = 100;
    public static final int INITIAL_ENERGY = 100;
    public static final int INITIAL_HEALTH = 100;
    
    public static final int HUNGER_DECAY_RATE = 1;
    public static final int HAPPINESS_DECAY_RATE = 1;
    public static final int ENERGY_DECAY_RATE = 1;
    
    public static final int TICK_INTERVAL_MS = 1000;
    
    private GameConfig() {
        // Prevent instantiation
    }
}
