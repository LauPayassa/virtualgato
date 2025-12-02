package br.com.virtualpet;

public class GameConfig {

    public static final long TICK_INTERVAL_MS = 60_000; // 1 min

    public static final long MAX_UNATTENDED_MS = 6L * 60L * 60L * 1000L;

    public static final int HUNGER_INCREASE_PER_TICK = 5;
    public static final int HYGIENE_DECREASE_PER_TICK = 3;
    public static final int ENERGY_DECREASE_PER_TICK = 3;
    public static final int HAPPINESS_DECREASE_PER_TICK = 2;
    public static final int HEALTH_DECREASE_WHEN_SICK_PER_TICK = 5;

    public static final int SICK_CHANCE_PERCENT = 5;

    public static final int PLAY_HAPPINESS_BONUS = 15;
    public static final int PLAY_ENERGY_COST = 10;

    public static final int BATH_HYGIENE_BONUS = 30;
    public static final int BATH_HAPPINESS_COST = 5;

    public static final int FEED_HUNGER_REDUCTION = 30;
    public static final int FEED_HEALTH_BONUS = 5;

    public static final int SLEEP_ENERGY_BONUS = 30;
    public static final int SLEEP_HUNGER_INCREASE = 10;

    public static final int MEDICINE_HEALTH_BONUS = 40;

    public static final int AC_ENERGY_SAVING_PERCENT = 50;
    public static final int AC_HAPPINESS_LOSS_PER_TICK = 2;

    private GameConfig() {}
}