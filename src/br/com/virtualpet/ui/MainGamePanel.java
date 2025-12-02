package br.com.virtualpet.ui;

import br.com.virtualpet.model.Pet;
import br.com.virtualpet.time.GameClock;
import br.com.virtualpet.util.FrameAnimation;
import br.com.virtualpet.util.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGamePanel extends JPanel {

    private final ScreenManager screenManager;
    private final Pet pet;
    private final GameClock clock;

    private StatusBarPanel statusBarPanel;

    private JButton btnPlay;
    private JButton btnBath;
    private JButton btnToggleAC;
    private JButton btnMedicine;
    private JButton btnReset;

    private PopupManager popupManager;

    private Image backgroundImage;
    private FrameAnimation idleAnimation;
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
            backgroundImage = ImageLoader.loadImage("/resources/room/room_background.png");
        } catch (Exception e) {
            e.printStackTrace();
            backgroundImage = null;
        }

        statusBarPanel = new StatusBarPanel(pet, clock);
        statusBarPanel.setBounds(10, 10, 780, 100);
        add(statusBarPanel);

        JPanel controlsPanel = new JPanel();
        controlsPanel.setLayout(new GridLayout(5, 1, 0, 10));
        controlsPanel.setOpaque(false);
        controlsPanel.setBounds(10, 130, 180, 250);

        btnPlay = new JButton("Brincar");
        btnBath = new JButton("Banho");
        btnToggleAC = new JButton("Ar Condicionado: OFF");
        btnMedicine = new JButton("Remédio");
        btnReset = new JButton("Reset");

        controlsPanel.add(btnPlay);
        controlsPanel.add(btnBath);
        controlsPanel.add(btnToggleAC);
        controlsPanel.add(btnMedicine);
        controlsPanel.add(btnReset);

        add(controlsPanel);

        popupManager = new PopupManager(this, pet);

        btnPlay.addActionListener(e -> openMiniGame());
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
        try {
            String basePath = "/resources/sheets/idle/";
            String[] files = {
                    "frame_0.png",
                    "frame_1.png",
                    "frame_2.png",
                    "frame_3.png",
                    "frame_4.png",
                    "frame_5.png"
            };
            idleAnimation = new FrameAnimation(basePath, files);
        } catch (Exception e) {
            e.printStackTrace();
            idleAnimation = null;
        }

        if (idleAnimation != null) {
            catAnimationTimer = new Timer(200, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    currentCatFrame = (currentCatFrame + 1) % idleAnimation.getFrameCount();
                    repaint();
                }
            });
            catAnimationTimer.start();
        }
    }

    private void openMiniGame() {
        String[] options = {"Pedra", "Papel", "Tesoura"};
        int playerChoice = JOptionPane.showOptionDialog(
                this,
                "Escolha sua jogada:",
                "Pedra, Papel e Tesoura",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );
        if (playerChoice < 0) return;

        int computerChoice = (int) (Math.random() * 3);
        String result;
        if (playerChoice == computerChoice) result = "draw";
        else if ((playerChoice == 0 && computerChoice == 2) ||
                 (playerChoice == 1 && computerChoice == 0) ||
                 (playerChoice == 2 && computerChoice == 1)) result = "win";
        else result = "lose";

        pet.playMiniGame(result);
        refreshUI();
    }

    public void refreshUI() {
        statusBarPanel.refresh();
        btnToggleAC.setText("Ar Condicionado: " + (pet.isAirConditionerOn() ? "ON" : "OFF"));
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            int imgW = backgroundImage.getWidth(null);
            int imgH = backgroundImage.getHeight(null);
            int x = (getWidth() - imgW) / 2;
            int y = (getHeight() - imgH) / 2;
            g.drawImage(backgroundImage, x, y, null);
        } else {
            g.setColor(new Color(100, 180, 255));
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        Image catFrame = null;
        if (idleAnimation != null) {
            catFrame = idleAnimation.getFrame(currentCatFrame);
        }

        if (catFrame != null && !pet.isDead()) {
            int imgW = catFrame.getWidth(null);
            int imgH = catFrame.getHeight(null);

            // ajuste fino até o gato ficar na cama
            int x = 430;
            int y = 280;

            g.drawImage(catFrame, x, y, null);
        } else if (!pet.isDead()) {
            g.setColor(Color.ORANGE);
            g.fillOval(350, 250, 100, 100);
        }
    }
}