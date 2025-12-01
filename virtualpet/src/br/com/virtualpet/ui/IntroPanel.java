package br.com.virtualpet.ui;

import br.com.virtualpet.GameConfig;

import javax.swing.*;
import java.awt.*;

/**
 * Introduction screen panel where users can start a new game.
 */
public class IntroPanel extends JPanel {
    private ScreenManager screenManager;
    private JTextField petNameField;
    private JButton startButton;
    
    public IntroPanel(ScreenManager screenManager) {
        this.screenManager = screenManager;
        setLayout(new BorderLayout());
        setBackground(new Color(135, 206, 235));
        
        initComponents();
    }
    
    private void initComponents() {
        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(100, 0, 0, 0));
        
        JLabel titleLabel = new JLabel(GameConfig.GAME_TITLE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setForeground(new Color(50, 50, 100));
        titlePanel.add(titleLabel);
        
        // Center Panel
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        
        JLabel nameLabel = new JLabel("Enter your pet's name:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        petNameField = new JTextField(15);
        petNameField.setMaximumSize(new Dimension(200, 30));
        petNameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        startButton = new JButton("Start Game");
        startButton.setFont(new Font("Arial", Font.BOLD, 16));
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.addActionListener(e -> startGame());
        
        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(nameLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(petNameField);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(startButton);
        centerPanel.add(Box.createVerticalGlue());
        
        add(titlePanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }
    
    private void startGame() {
        String petName = petNameField.getText().trim();
        if (petName.isEmpty()) {
            petName = "Pet";
        }
        screenManager.startGame(petName);
    }
}
