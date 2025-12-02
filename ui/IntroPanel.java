package br.com.virtualpet.ui;

import br.com.virtualpet.model.Pet;
import br.com.virtualpet.util.FrameAnimation;

import javax.swing.*;
import java.awt.*;

public class IntroPanel extends JPanel {

    private final ScreenManager screenManager;
    private final Pet pet;
    private FrameAnimation boxAnimation;
    private int currentFrame = 0;
    private Timer animationTimer;

    public IntroPanel(ScreenManager screenManager, Pet pet) {
        this.screenManager = screenManager;
        this.pet = pet;
        initUI();
        initAnimation();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(154, 193, 207));
        
        JPanel centerPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (boxAnimation != null) {
                    Image frame = boxAnimation.getFrame(currentFrame);
                    if (frame != null) {
                        int imgW = frame.getWidth(null);
                        int imgH = frame.getHeight(null);
                        int scaledW = imgW * 3;
                        int scaledH = imgH * 3;
                        int x = (getWidth() - scaledW) / 2;
                        int y = (getHeight() - scaledH) / 2 - 50;
                        g.drawImage(frame, x, y, scaledW, scaledH, null);
                    }
                }
            }
        };
        centerPanel.setPreferredSize(new Dimension(800, 400));
        centerPanel.setBackground(new Color(154, 193, 207));
        
        JLabel label = new JLabel("Olha! Um gatinho", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setOpaque(false);
        
        JPanel textAndCatPanel = new JPanel(new BorderLayout());
        textAndCatPanel.setBackground(new Color(154, 193, 207));
        textAndCatPanel.add(centerPanel, BorderLayout.CENTER);
        textAndCatPanel.add(label, BorderLayout.SOUTH);
        
        add(textAndCatPanel, BorderLayout.CENTER);

        JButton startButton = new JButton("START");
        startButton.setFont(new Font("Arial", Font.BOLD, 16));
        startButton.setBackground(new Color(100, 180, 255));
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        startButton.setBorderPainted(false);
        startButton.setPreferredSize(new Dimension(150, 45));
        startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        startButton.addActionListener(e -> {
            if (animationTimer != null) {
                animationTimer.stop();
            }
            askResponsibility();
        });
        
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 20));
        bottom.setBackground(new Color(154, 193, 207));
        bottom.add(startButton);
        add(bottom, BorderLayout.SOUTH);
    }

    private void initAnimation() {
        try {
            String folderPath = "C:\\Users\\kwill\\Downloads\\files\\src\\br\\com\\virtualpet\\cat_frames\\box2";
            boxAnimation = FrameAnimation.fromFolder(folderPath);
            
            if (boxAnimation != null) {
                animationTimer = new Timer(150, e -> {
                    currentFrame = (currentFrame + 1) % boxAnimation.getFrameCount();
                    repaint();
                });
                animationTimer.start();
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar animação da box: " + e.getMessage());
            boxAnimation = null;
        }
    }

    private void askResponsibility() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Responsabilidade", true);
        dialog.setLayout(new BorderLayout());
        
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        mainPanel.setBackground(new Color(255, 248, 240));
        
        JLabel messageLabel = new JLabel("<html><div style='text-align: center; width: 350px;'>"
                + "Voce aceita que agora e responsavel pela criacao e evolucao do gatinho?"
                + "</div></html>");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(messageLabel, BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(new Color(255, 248, 240));
        
        final boolean[] accepted = {false};
        
        JButton yesButton = new JButton("Sim");
        yesButton.setFont(new Font("Arial", Font.BOLD, 14));
        yesButton.setBackground(new Color(150, 200, 100));
        yesButton.setForeground(Color.WHITE);
        yesButton.setFocusPainted(false);
        yesButton.setBorderPainted(false);
        yesButton.setPreferredSize(new Dimension(100, 40));
        yesButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        yesButton.addActionListener(e -> {
            accepted[0] = true;
            dialog.dispose();
        });
        
        JButton noButton = new JButton("Nao");
        noButton.setFont(new Font("Arial", Font.BOLD, 14));
        noButton.setBackground(new Color(255, 100, 100));
        noButton.setForeground(Color.WHITE);
        noButton.setFocusPainted(false);
        noButton.setBorderPainted(false);
        noButton.setPreferredSize(new Dimension(100, 40));
        noButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        noButton.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(yesButton);
        buttonPanel.add(noButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.add(mainPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);
        dialog.setVisible(true);
        
        if (accepted[0]) {
            screenManager.showMainGameScreen();
        } else {
            showNoChoiceMessage();
        }
    }
    
    private void showNoChoiceMessage() {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Ops", true);
        dialog.setLayout(new BorderLayout());
        
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        mainPanel.setBackground(new Color(255, 248, 240));
        
        JLabel messageLabel = new JLabel("<html><div style='text-align: center; width: 300px;'>"
                + "Voce nao tem escolha... o gatinho precisa de voce!"
                + "</div></html>");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 15));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(messageLabel, BorderLayout.CENTER);
        
        JButton okButton = new JButton("OK");
        okButton.setFont(new Font("Arial", Font.BOLD, 14));
        okButton.setBackground(new Color(100, 180, 255));
        okButton.setForeground(Color.WHITE);
        okButton.setFocusPainted(false);
        okButton.setBorderPainted(false);
        okButton.setPreferredSize(new Dimension(100, 35));
        okButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        okButton.addActionListener(e -> {
            dialog.dispose();
            askResponsibility();
        });
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(255, 248, 240));
        buttonPanel.add(okButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.add(mainPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);
        dialog.setVisible(true);
    }
}