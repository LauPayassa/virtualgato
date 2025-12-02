package br.com.virtualpet.ui;

import br.com.virtualpet.model.Pet;
import br.com.virtualpet.time.GameClock;
import br.com.virtualpet.util.FrameAnimation;
import br.com.virtualpet.util.ImageLoader;

import javax.swing.*;
import java.awt.*;

public class MainGamePanel extends JPanel {

    private final ScreenManager screenManager;
    private final Pet pet;
    private final GameClock clock;

    private StatusBarPanel statusBarPanel;

    private JButton btnPlay;
    private JButton btnFeed;
    private JButton btnBath;
    private JButton btnToggleAC;
    private JButton btnMedicine;
    private JButton btnReset;

    private PopupManager popupManager;

    private Image backgroundImage;
    private FrameAnimation currentAnimation;
    private FrameAnimation idleAnimation;
    private FrameAnimation sleepingAnimation;
    private FrameAnimation happyAnimation;
    private FrameAnimation cryingAnimation;
    private FrameAnimation deadAnimation;
    private int currentCatFrame = 0;
    private Timer catAnimationTimer;

    public MainGamePanel(ScreenManager screenManager, Pet pet, GameClock clock) {
        this.screenManager = screenManager;
        this.pet = pet;
        this.clock = clock;
        initUI();
        initAnimation();
    }

    private void initUI() {
        setLayout(null);

        try {
            backgroundImage = ImageLoader.loadImageFromFile("C:\\Users\\kwill\\Downloads\\files\\src\\br\\com\\virtualpet\\sprites\\ExampleRoom.png");
        } catch (Exception e) {
            backgroundImage = null;
        }

        statusBarPanel = new StatusBarPanel(pet, clock);
        statusBarPanel.setBounds(10, 5, 700, 100);
        add(statusBarPanel);

        int buttonWidth = 110;
        int buttonHeight = 45;
        int startX = 10;
        int startY = 525;
        int gap = 5;

        btnPlay = createStyledButton("Brincar", new Color(150, 100, 255));
        btnPlay.setBounds(startX, startY, buttonWidth, buttonHeight);
        add(btnPlay);

        btnFeed = createStyledButton("Comida", new Color(255, 150, 100));
        btnFeed.setBounds(startX + (buttonWidth + gap), startY, buttonWidth, buttonHeight);
        add(btnFeed);

        btnBath = createStyledButton("Banho", new Color(100, 200, 255));
        btnBath.setBounds(startX + (buttonWidth + gap) * 2, startY, buttonWidth, buttonHeight);
        add(btnBath);

        btnToggleAC = createStyledButton("AC: OFF", new Color(100, 180, 255));
        btnToggleAC.setBounds(startX + (buttonWidth + gap) * 3, startY, buttonWidth, buttonHeight);
        add(btnToggleAC);

        btnMedicine = createStyledButton("Remedio", new Color(255, 120, 150));
        btnMedicine.setBounds(startX + (buttonWidth + gap) * 4, startY, buttonWidth, buttonHeight);
        add(btnMedicine);

        btnReset = createStyledButton("Reset", new Color(200, 100, 100));
        btnReset.setBounds(startX + (buttonWidth + gap) * 5, startY, buttonWidth, buttonHeight);
        add(btnReset);

        popupManager = new PopupManager(this, pet);

        btnPlay.addActionListener(e -> openMiniGame());
        btnFeed.addActionListener(e -> {
            pet.eat();
            refreshUI();
        });
        btnBath.addActionListener(e -> {
            pet.giveBath();
            refreshUI();
        });
        btnToggleAC.addActionListener(e -> {
            pet.toggleAirConditioner();
            refreshUI();
        });
        btnMedicine.addActionListener(e -> {
            if (!pet.isSick()) {
                popupManager.showSimpleMessage("O gatinho não está doente agora.");
                return;
            }
            pet.giveMedicine();
            refreshUI();
        });
        btnReset.addActionListener(e -> screenManager.resetGame());

        new Timer(1000, e -> refreshUI()).start();
    }

    private void initAnimation() {
        String basePath = "C:\\Users\\kwill\\Downloads\\files\\src\\br\\com\\virtualpet\\cat_frames\\";
        
        try {
            idleAnimation = FrameAnimation.fromFolder(basePath + "idle");
            System.out.println("Idle animation loaded with " + idleAnimation.getFrameCount() + " frames");
        } catch (Exception e) {
            System.err.println("Erro ao carregar idle animation: " + e.getMessage());
        }
        
        try {
            sleepingAnimation = FrameAnimation.fromFolder(basePath + "sleeping");
            System.out.println("Sleeping animation loaded with " + sleepingAnimation.getFrameCount() + " frames");
        } catch (Exception e) {
            System.err.println("Erro ao carregar sleeping animation: " + e.getMessage());
        }
        
        try {
            happyAnimation = FrameAnimation.fromFolder(basePath + "happy");
            System.out.println("Happy animation loaded with " + happyAnimation.getFrameCount() + " frames");
        } catch (Exception e) {
            System.err.println("Erro ao carregar happy animation: " + e.getMessage());
        }
        
        try {
            cryingAnimation = FrameAnimation.fromFolder(basePath + "crying");
            System.out.println("Crying animation loaded with " + cryingAnimation.getFrameCount() + " frames");
        } catch (Exception e) {
            System.err.println("Erro ao carregar crying animation: " + e.getMessage());
        }
        
        try {
            deadAnimation = FrameAnimation.fromFolder(basePath + "dead");
            System.out.println("Dead animation loaded with " + deadAnimation.getFrameCount() + " frames");
        } catch (Exception e) {
            System.err.println("Erro ao carregar dead animation: " + e.getMessage());
        }
        
        currentAnimation = idleAnimation;

        catAnimationTimer = new Timer(150, e -> {
            updateCurrentAnimation();
            if (currentAnimation != null) {
                currentCatFrame = (currentCatFrame + 1) % currentAnimation.getFrameCount();
            }
            repaint();
        });
        catAnimationTimer.start();
    }
    
    private void updateCurrentAnimation() {
        FrameAnimation newAnimation = idleAnimation;
        
        if (pet.isDead() && deadAnimation != null) {
            newAnimation = deadAnimation;
        } else if (pet.getEnergy() < 30 && sleepingAnimation != null) {
            newAnimation = sleepingAnimation;
        } else if (pet.getHappiness() > 70 && happyAnimation != null) {
            newAnimation = happyAnimation;
        } else if (pet.getHappiness() < 30 && cryingAnimation != null) {
            newAnimation = cryingAnimation;
        }
        
        if (newAnimation != currentAnimation) {
            currentAnimation = newAnimation;
            currentCatFrame = 0;
        }
    }

    private void openMiniGame() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Pedra, Papel e Tesoura", true);
        dialog.setLayout(new BorderLayout());
        
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        mainPanel.setBackground(new Color(255, 248, 240));
        
        JLabel titleLabel = new JLabel("Escolha sua jogada:", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 15, 0));
        buttonPanel.setBackground(new Color(255, 248, 240));
        
        final int[] playerChoice = {-1};
        String[] options = {"Pedra", "Papel", "Tesoura"};
        Color[] colors = {new Color(255, 100, 100), new Color(100, 180, 255), new Color(150, 200, 100)};
        
        for (int i = 0; i < options.length; i++) {
            final int choice = i;
            JButton btn = new JButton("<html><center>" + options[i] + "</center></html>");
            btn.setFont(new Font("Arial", Font.BOLD, 14));
            btn.setBackground(colors[i]);
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setPreferredSize(new Dimension(100, 80));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.addActionListener(e -> {
                playerChoice[0] = choice;
                dialog.dispose();
            });
            buttonPanel.add(btn);
        }
        
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        
        dialog.add(mainPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);
        dialog.setVisible(true);
        
        if (playerChoice[0] < 0) return;

        int computerChoice = (int) (Math.random() * 3);
        String result;
        if (playerChoice[0] == computerChoice) result = "draw";
        else if ((playerChoice[0] == 0 && computerChoice == 2) ||
                 (playerChoice[0] == 1 && computerChoice == 0) ||
                 (playerChoice[0] == 2 && computerChoice == 1)) result = "win";
        else result = "lose";

        // Show result
        showGameResult(playerChoice[0], computerChoice, result);

        pet.playMiniGame(result);
        refreshUI();
    }
    
    private void showGameResult(int playerChoice, int computerChoice, String result) {
        String[] choices = {"Pedra", "Papel", "Tesoura"};
        String resultText;
        Color resultColor;
        
        switch (result) {
            case "win" -> {
                resultText = "Voce ganhou!";
                resultColor = new Color(150, 200, 100);
            }
            case "lose" -> {
                resultText = "Voce perdeu!";
                resultColor = new Color(255, 100, 100);
            }
            default -> {
                resultText = "Empate!";
                resultColor = new Color(255, 200, 100);
            }
        }
        
        JDialog resultDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Resultado", true);
        resultDialog.setLayout(new BorderLayout());
        
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        mainPanel.setBackground(new Color(255, 248, 240));
        
        JLabel titleLabel = new JLabel(resultText, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(resultColor);
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(new Color(255, 248, 240));
        
        // Cat sprite panel
        JPanel catPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (currentAnimation != null) {
                    Image frame = currentAnimation.getFrame(currentCatFrame);
                    if (frame != null) {
                        int imgW = frame.getWidth(null);
                        int imgH = frame.getHeight(null);
                        int scaledW = imgW * 2;
                        int scaledH = imgH * 2;
                        int x = (getWidth() - scaledW) / 2;
                        int y = (getHeight() - scaledH) / 2;
                        g.drawImage(frame, x, y, scaledW, scaledH, null);
                    }
                }
            }
        };
        catPanel.setPreferredSize(new Dimension(200, 150));
        catPanel.setBackground(new Color(255, 248, 240));
        centerPanel.add(catPanel, BorderLayout.NORTH);
        
        JPanel choicesPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        choicesPanel.setBackground(new Color(255, 248, 240));
        
        JLabel playerLabel = new JLabel("Você: " + choices[playerChoice], SwingConstants.CENTER);
        playerLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        choicesPanel.add(playerLabel);
        
        JLabel computerLabel = new JLabel("Gatinho: " + choices[computerChoice], SwingConstants.CENTER);
        computerLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        choicesPanel.add(computerLabel);
        
        centerPanel.add(choicesPanel, BorderLayout.CENTER);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        
        JButton okButton = new JButton("OK");
        okButton.setFont(new Font("Arial", Font.BOLD, 14));
        okButton.setBackground(new Color(100, 180, 255));
        okButton.setForeground(Color.WHITE);
        okButton.setFocusPainted(false);
        okButton.setBorderPainted(false);
        okButton.setPreferredSize(new Dimension(100, 35));
        okButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        okButton.addActionListener(e -> resultDialog.dispose());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(255, 248, 240));
        buttonPanel.add(okButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        resultDialog.add(mainPanel);
        resultDialog.pack();
        resultDialog.setLocationRelativeTo(this);
        resultDialog.setResizable(false);
        resultDialog.setVisible(true);
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        return button;
    }

    public void refreshUI() {
        statusBarPanel.refresh();
        btnToggleAC.setText(pet.isAirConditionerOn() ? "AC: ON" : "AC: OFF");
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        } else {
            g.setColor(new Color(100, 180, 255));
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        Image catFrame = null;
        if (currentAnimation != null) {
            catFrame = currentAnimation.getFrame(currentCatFrame);
        }

        if (catFrame != null) {
            int imgW = catFrame.getWidth(null);
            int imgH = catFrame.getHeight(null);
            int x = (getWidth() - imgW) / 2;
            int y = (getHeight() - imgH) / 2;
            
            // Adjust position for sleeping animation to center on bed
            if (currentAnimation == sleepingAnimation) {
                x = (getWidth() - imgW) / 2 - 80;
                y = (getHeight() - imgH) / 2 + 50;
            }
            
            g.drawImage(catFrame, x, y, null);
        } else if (!pet.isDead()) {
            g.setColor(Color.ORANGE);
            g.fillOval(350, 250, 100, 100);
        }
    }
}