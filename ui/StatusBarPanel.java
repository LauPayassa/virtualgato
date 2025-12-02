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
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int x = 15;
        int y = 20;
        int w = 190;
        int h = 18;
        int gap = 28;

        String[] labels = {"Saciedade", "Felicidade", "Higiene", "Energia", "Saude"};
        int[] values = {100 - pet.getHunger(), pet.getHappiness(), pet.getHygiene(), pet.getEnergy(), pet.getHealth()};
        Color[] colors = {
            new Color(255, 100, 100),
            new Color(255, 200, 50),
            new Color(100, 200, 255),
            new Color(150, 220, 100),
            new Color(255, 120, 150)
        };

        for (int i = 0; i < 3; i++) {
            drawModernBar(g2d, labels[i], values[i], x, y + (gap * i), w, h, colors[i]);
        }

        for (int i = 3; i < 5; i++) {
            drawModernBar(g2d, labels[i], values[i], x + 240, y + (gap * (i - 3)), w, h, colors[i]);
        }

        // Clock display
        g2d.setFont(new Font("Arial", Font.BOLD, 13));
        g2d.setColor(new Color(80, 80, 80));
        String timeText = clock.getFormattedGameTime();
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(timeText);
        g2d.drawString(timeText, x + 240 + (w - textWidth) / 2, y + (gap * 2) + 12);
    }

    private void drawModernBar(Graphics2D g2d, String label, int value, int x, int y, int w, int h, Color barColor) {
        // Label
        g2d.setFont(new Font("Arial", Font.BOLD, 11));
        g2d.setColor(new Color(60, 60, 60));
        g2d.drawString(label, x, y - 4);

        // Background bar with rounded corners
        g2d.setColor(new Color(220, 220, 220));
        g2d.fillRoundRect(x, y, w, h, 10, 10);

        // Filled bar with gradient
        if (value > 0) {
            int filled = (int)(w * (value / 100.0));
            GradientPaint gradient = new GradientPaint(
                x, y, barColor,
                x, y + h, barColor.darker()
            );
            g2d.setPaint(gradient);
            g2d.fillRoundRect(x, y, filled, h, 10, 10);
        }

        // Border
        g2d.setColor(new Color(150, 150, 150));
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawRoundRect(x, y, w, h, 10, 10);

        // Value text
        g2d.setFont(new Font("Arial", Font.BOLD, 10));
        g2d.setColor(Color.WHITE);
        String valueText = value + "%";
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(valueText);
        int textX = x + (w - textWidth) / 2;
        int textY = y + h - 4;
        
        // Text shadow
        g2d.setColor(new Color(0, 0, 0, 100));
        g2d.drawString(valueText, textX + 1, textY + 1);
        
        // Text
        g2d.setColor(value < 30 ? Color.WHITE : new Color(80, 80, 80));
        g2d.drawString(valueText, textX, textY);
    }
}