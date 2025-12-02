package br.com.virtualpet.model;

import br.com.virtualpet.GameConfig;

import java.time.Instant;

public class Pet {

    private final String name;

    private int hunger;
    private int happiness;
    private int hygiene;
    private int energy;
    private int health;

    private boolean airConditionerOn;
    private boolean sick;
    private boolean dead;

    private PetStatus currentStatus;

    private Instant lastCareTime;
    private final Instant creationTime;

    public Pet(String name) {
        this.name = name;
        this.creationTime = Instant.now();
        reset();
    }

    public void reset() {
        this.hunger = 20;
        this.happiness = 80;
        this.hygiene = 80;
        this.energy = 80;
        this.health = 100;

        this.airConditionerOn = false;
        this.sick = false;
        this.dead = false;
        this.currentStatus = PetStatus.NORMAL;
        this.lastCareTime = Instant.now();
    }

    public String getName() { return name; }
    public synchronized int getHunger() { return hunger; }
    public synchronized int getHappiness() { return happiness; }
    public synchronized int getHygiene() { return hygiene; }
    public synchronized int getEnergy() { return energy; }
    public synchronized int getHealth() { return health; }
    public synchronized boolean isAirConditionerOn() { return airConditionerOn; }
    public synchronized boolean isSick() { return sick; }
    public synchronized boolean isDead() { return dead; }
    public synchronized PetStatus getCurrentStatus() { return currentStatus; }
    public synchronized Instant getCreationTime() { return creationTime; }
    public synchronized Instant getLastCareTime() { return lastCareTime; }

    public synchronized void tick() {
        if (dead) return;

        hunger = clamp(hunger + GameConfig.HUNGER_INCREASE_PER_TICK);
        hygiene = clamp(hygiene - GameConfig.HYGIENE_DECREASE_PER_TICK);
        happiness = clamp(happiness - GameConfig.HAPPINESS_DECREASE_PER_TICK);

        int energyDecrease = GameConfig.ENERGY_DECREASE_PER_TICK;
        if (airConditionerOn) {
            energyDecrease = energyDecrease * (100 - GameConfig.AC_ENERGY_SAVING_PERCENT) / 100;
            happiness = clamp(happiness - GameConfig.AC_HAPPINESS_LOSS_PER_TICK);
        }
        energy = clamp(energy - energyDecrease);

        if (!sick && Math.random() * 100 < GameConfig.SICK_CHANCE_PERCENT) {
            sick = true;
        }

        if (sick) {
            health = clamp(health - GameConfig.HEALTH_DECREASE_WHEN_SICK_PER_TICK);
        }

        if (hunger > 80 || hygiene < 20 || energy < 20) {
            health = clamp(health - 5);
        }

        if (health <= 0 || hunger >= 100) {
            die();
            return;
        }

        long millisSinceCare = java.time.Duration.between(lastCareTime, Instant.now()).toMillis();
        if (millisSinceCare >= GameConfig.MAX_UNATTENDED_MS) {
            die();
            return;
        }

        updateStatusByConditions();
    }

    private void die() {
        dead = true;
        currentStatus = PetStatus.DEAD;
    }

    private void updateStatusByConditions() {
        if (dead) {
            currentStatus = PetStatus.DEAD;
        } else if (sick) {
            currentStatus = PetStatus.SICK;
        } else if (currentStatus != PetStatus.SLEEPING &&
                   currentStatus != PetStatus.PLAYING &&
                   currentStatus != PetStatus.BATHING &&
                   currentStatus != PetStatus.EATING) {
            currentStatus = PetStatus.NORMAL;
        }
    }

    private int clamp(int v) {
        if (v < 0) return 0;
        if (v > 100) return 100;
        return v;
    }

    private void caredNow() {
        lastCareTime = Instant.now();
    }

    // Ações manuais
    public synchronized void playMiniGame(String result) {
        if (dead) return;
        currentStatus = PetStatus.PLAYING;
        energy = clamp(energy - GameConfig.PLAY_ENERGY_COST);
        switch (result) {
            case "win" -> happiness = clamp(happiness + GameConfig.PLAY_HAPPINESS_BONUS);
            case "lose" -> happiness = clamp(happiness + GameConfig.PLAY_HAPPINESS_BONUS / 3);
            case "draw" -> happiness = clamp(happiness + GameConfig.PLAY_HAPPINESS_BONUS / 2);
        }
        caredNow();
        updateStatusByConditions();
    }

    public synchronized void giveBath() {
        if (dead) return;
        currentStatus = PetStatus.BATHING;
        hygiene = clamp(hygiene + GameConfig.BATH_HYGIENE_BONUS);
        happiness = clamp(happiness - GameConfig.BATH_HAPPINESS_COST);
        caredNow();
        updateStatusByConditions();
    }

    public synchronized void toggleAirConditioner() {
        if (dead) return;
        airConditionerOn = !airConditionerOn;
        caredNow();
    }

    public synchronized void giveMedicine() {
        if (dead) return;
        if (!sick) return;
        sick = false;
        health = clamp(health + GameConfig.MEDICINE_HEALTH_BONUS);
        currentStatus = PetStatus.NORMAL;
        caredNow();
        updateStatusByConditions();
    }

    public synchronized void eat() {
        if (dead) return;
        currentStatus = PetStatus.EATING;
        hunger = clamp(hunger - GameConfig.FEED_HUNGER_REDUCTION);
        health = clamp(health + GameConfig.FEED_HEALTH_BONUS);
        caredNow();
        updateStatusByConditions();
    }

    public synchronized void sleep() {
        if (dead) return;
        currentStatus = PetStatus.SLEEPING;
        energy = clamp(energy + GameConfig.SLEEP_ENERGY_BONUS);
        hunger = clamp(hunger + GameConfig.SLEEP_HUNGER_INCREASE);
        caredNow();
        updateStatusByConditions();
    }
}