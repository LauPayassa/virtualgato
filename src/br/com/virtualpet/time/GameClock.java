package br.com.virtualpet.time;

import br.com.virtualpet.GameConfig;
import br.com.virtualpet.model.Pet;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GameClock {

    private final Pet pet;
    private final Timer timer;
    private LocalDateTime lastUpdate;
    private final DateTimeFormatter fmt =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    public GameClock(Pet pet) {
        this.pet = pet;
        this.lastUpdate = LocalDateTime.now();
        this.timer = new Timer((int) GameConfig.TICK_INTERVAL_MS, e -> {
            pet.tick();
            lastUpdate = LocalDateTime.now();
        });
    }

    public void start() { timer.start(); }
    public void stop() { timer.stop(); }

    public String getFormattedGameTime() {
        return fmt.format(LocalDateTime.now());
    }
}