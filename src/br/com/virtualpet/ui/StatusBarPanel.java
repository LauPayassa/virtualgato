package br.com.virtualpet.ui;

import br.com.virtualpet.model.Pet;
import br.com.virtualpet.time.GameClock;

import javax.swing.*;
import java.awt.*;

public class StatusBarPanel extends JPanel {

    private final Pet pet;
    private final GameClock clock;

    public StatusBarPanel(Pet pet, GameClock clock) {
        this.pet = pet;
        this.clock = clock;
        setOpaque(false);
    }

    public void refresh() {
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int x = 10;
        int y = 10;
        int w = 200;
        int h = 15;
        int gap = 20;

        drawBar(g, "Fome", pet.getHunger(), x, y, w, h, Color.RED);
        drawBar(g, "Felicidade", pet.getHappiness(), x, y + gap, w, h, Color.YELLOW);
        drawBar(g, "Higiene", pet.getHygiene(), x, y + gap * 2, w, h, Color.CYAN);
        drawBar(g, "Energia", pet.getEnergy(), x + 250, y, w, h, Color.GREEN);
        drawBar(g, "Saúde", pet.getHealth(), x + 250, y + gap, w, h, Color.PINK);

        g.setColor(Color.BLACK);
        g.drawString("Hora: " + clock.getFormattedGameTime(), x + 250, y + gap * 2 + 10);
    }

    private void drawBar(Graphics g, String label, int value, int x, int y, int w, int h, Color color) {
        g.setColor(Color.BLACK);
        g.drawString(label + ": " + value, x, y - 2);

        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(x, y, w, h);

        g.setColor(color);
        int filled = (int)(w * (value / 100.0));
        g.fillRect(x, y, filled, h);

        g.setColor(Color.BLACK);
        g.drawRect(x, y, w, h);
    }
}