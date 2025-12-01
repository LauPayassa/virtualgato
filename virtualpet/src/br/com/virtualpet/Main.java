package br.com.virtualpet;

import br.com.virtualpet.ui.GameFrame;

import javax.swing.SwingUtilities;

/**
 * Main entry point for the Virtual Pet game.
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameFrame gameFrame = new GameFrame();
            gameFrame.setVisible(true);
        });
    }
}
