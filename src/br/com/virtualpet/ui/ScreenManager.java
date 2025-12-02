package br.com.virtualpet.ui;

import br.com.virtualpet.model.Pet;
import br.com.virtualpet.time.GameClock;

import javax.swing.*;
import java.awt.*;

public class ScreenManager {

    private final JFrame frame;
    private final Pet pet;
    private final GameClock clock;

    private IntroPanel introPanel;
    private MainGamePanel mainGamePanel;

    public ScreenManager(JFrame frame, Pet pet, GameClock clock) {
        this.frame = frame;
        this.pet = pet;
        this.clock = clock;
    }

    public void showIntroScreen() {
        if (introPanel == null) {
            introPanel = new IntroPanel(this, pet);
        }
        setContent(introPanel);
    }

    public void showMainGameScreen() {
        if (mainGamePanel == null) {
            mainGamePanel = new MainGamePanel(this, pet, clock);
        }
        mainGamePanel.refreshUI();
        setContent(mainGamePanel);
    }

    public void resetGame() {
        pet.reset();
        showIntroScreen();
    }

    private void setContent(JPanel panel) {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
        panel.requestFocusInWindow();
    }
}