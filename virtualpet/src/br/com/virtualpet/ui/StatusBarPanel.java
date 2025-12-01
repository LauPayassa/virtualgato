package br.com.virtualpet.ui;

import br.com.virtualpet.model.Pet;

import javax.swing.*;
import java.awt.*;

/**
 * Panel displaying the pet's status bars.
 */
public class StatusBarPanel extends JPanel {
    private JLabel nameLabel;
    private JProgressBar hungerBar;
    private JProgressBar happinessBar;
    private JProgressBar energyBar;
    private JProgressBar healthBar;
    private JLabel statusLabel;
    
    public StatusBarPanel() {
        setLayout(new GridBagLayout());
        setBackground(new Color(100, 100, 120));
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        initComponents();
    }
    
    private void initComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 10, 2, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Pet Name
        nameLabel = new JLabel("Pet Name");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(nameLabel, gbc);
        
        // Status Label
        statusLabel = new JLabel("Status: Happy");
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        statusLabel.setForeground(Color.YELLOW);
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(statusLabel, gbc);
        
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        
        // Hunger
        gbc.gridx = 0;
        add(createLabel("Hunger:"), gbc);
        gbc.gridx = 1;
        hungerBar = createProgressBar(Color.ORANGE);
        add(hungerBar, gbc);
        
        // Happiness
        gbc.gridx = 2;
        add(createLabel("Happiness:"), gbc);
        gbc.gridx = 3;
        happinessBar = createProgressBar(Color.PINK);
        add(happinessBar, gbc);
        
        // Energy
        gbc.gridx = 4;
        add(createLabel("Energy:"), gbc);
        gbc.gridx = 5;
        energyBar = createProgressBar(Color.CYAN);
        add(energyBar, gbc);
        
        // Health
        gbc.gridx = 6;
        add(createLabel("Health:"), gbc);
        gbc.gridx = 7;
        healthBar = createProgressBar(Color.GREEN);
        add(healthBar, gbc);
    }
    
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        label.setForeground(Color.WHITE);
        return label;
    }
    
    private JProgressBar createProgressBar(Color color) {
        JProgressBar bar = new JProgressBar(0, 100);
        bar.setValue(100);
        bar.setPreferredSize(new Dimension(80, 15));
        bar.setForeground(color);
        bar.setStringPainted(true);
        return bar;
    }
    
    public void updateStatus(Pet pet) {
        nameLabel.setText(pet.getName());
        hungerBar.setValue(pet.getHunger());
        happinessBar.setValue(pet.getHappiness());
        energyBar.setValue(pet.getEnergy());
        healthBar.setValue(pet.getHealth());
        statusLabel.setText("Status: " + pet.getStatus().getDisplayName());
        
        // Update status label color based on pet status
        switch (pet.getStatus()) {
            case HAPPY:
                statusLabel.setForeground(Color.GREEN);
                break;
            case NORMAL:
                statusLabel.setForeground(Color.YELLOW);
                break;
            case HUNGRY:
            case TIRED:
            case SAD:
                statusLabel.setForeground(Color.ORANGE);
                break;
            case DEAD:
                statusLabel.setForeground(Color.RED);
                break;
        }
    }
}
