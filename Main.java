package br.com.virtualpet;

import br.com.virtualpet.model.Pet;
import br.com.virtualpet.time.GameClock;
import br.com.virtualpet.ui.GameFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            Pet pet = new Pet("Gatinho");
            GameClock clock = new GameClock(pet);
            GameFrame frame = new GameFrame(pet, clock);
            frame.setVisible(true);
            clock.start();
        });
    }
}