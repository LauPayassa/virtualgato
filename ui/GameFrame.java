package br.com.virtualpet.ui;

import br.com.virtualpet.model.Pet;
import br.com.virtualpet.time.GameClock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameFrame extends JFrame {

    private final ScreenManager screenManager;

    public GameFrame(Pet pet, GameClock clock) {
        super("Virtual Pet - Gatinho");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);

        this.screenManager = new ScreenManager(this, pet, clock);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    screenManager.showMainGameScreen();
                }
            }
        });

        screenManager.showIntroScreen();
    }
}