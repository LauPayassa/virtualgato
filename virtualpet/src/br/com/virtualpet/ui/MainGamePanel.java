package br.com.virtualpet.ui;

import br.com.virtualpet.model.Pet;
import br.com.virtualpet.time.GameClock;

import javax.swing.*;
import java.awt.*;

/**
 * Main game panel where the pet interaction takes place.
 */
public class MainGamePanel extends JPanel {
    private ScreenManager screenManager;
    private Pet pet;
    private GameClock gameClock;
    private StatusBarPanel statusBarPanel;
    private JPanel petDisplayPanel;
    private JPanel actionsPanel;
    private PopupManager popupManager;
    
    public MainGamePanel(ScreenManager screenManager) {
        this.screenManager = screenManager;
        this.gameClock = new GameClock();
        this.popupManager = new PopupManager(this);
        
        setLayout(new BorderLayout());
        setBackground(new Color(200, 230, 200));
        
        initComponents();
        setupGameClock();
    }
    
    private void initComponents() {
        // Status Bar at the top
        statusBarPanel = new StatusBarPanel();
        add(statusBarPanel, BorderLayout.NORTH);
        
        // Pet display in the center
        petDisplayPanel = new JPanel();
        petDisplayPanel.setOpaque(false);
        petDisplayPanel.setLayout(new GridBagLayout());
        
        JLabel petLabel = new JLabel("🐱", SwingConstants.CENTER);
        petLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 120));
        petDisplayPanel.add(petLabel);
        
        add(petDisplayPanel, BorderLayout.CENTER);
        
        // Actions panel at the bottom
        actionsPanel = new JPanel();
        actionsPanel.setOpaque(false);
        actionsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
        
        JButton feedButton = createActionButton("Feed", "🍖");
        feedButton.addActionListener(e -> feedPet());
        
        JButton playButton = createActionButton("Play", "🎾");
        playButton.addActionListener(e -> playWithPet());
        
        JButton sleepButton = createActionButton("Sleep", "💤");
        sleepButton.addActionListener(e -> petSleep());
        
        actionsPanel.add(feedButton);
        actionsPanel.add(playButton);
        actionsPanel.add(sleepButton);
        
        add(actionsPanel, BorderLayout.SOUTH);
    }
    
    private JButton createActionButton(String text, String emoji) {
        JButton button = new JButton(emoji + " " + text);
        button.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(120, 50));
        return button;
    }
    
    private void setupGameClock() {
        gameClock.addTickListener(() -> {
            if (pet != null && pet.isAlive()) {
                pet.tick();
                updateStatus();
                
                if (!pet.isAlive()) {
                    gameClock.stop();
                    popupManager.showGameOver();
                }
            }
        });
    }
    
    public void startGame(String petName) {
        pet = new Pet(petName);
        updateStatus();
        gameClock.start();
    }
    
    private void feedPet() {
        if (pet != null && pet.isAlive()) {
            pet.feed();
            updateStatus();
            popupManager.showMessage("Yum! " + pet.getName() + " enjoyed the food!");
        }
    }
    
    private void playWithPet() {
        if (pet != null && pet.isAlive()) {
            if (pet.getEnergy() > 10) {
                pet.play();
                updateStatus();
                popupManager.showMessage(pet.getName() + " had fun playing!");
            } else {
                popupManager.showMessage(pet.getName() + " is too tired to play!");
            }
        }
    }
    
    private void petSleep() {
        if (pet != null && pet.isAlive()) {
            pet.sleep();
            updateStatus();
            popupManager.showMessage(pet.getName() + " took a nice nap!");
        }
    }
    
    private void updateStatus() {
        if (pet != null) {
            statusBarPanel.updateStatus(pet);
        }
    }
    
    public Pet getPet() {
        return pet;
    }
    
    public GameClock getGameClock() {
        return gameClock;
    }
    
    public ScreenManager getScreenManager() {
        return screenManager;
    }
}
