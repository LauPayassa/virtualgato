package br.com.virtualpet.ui;

import javax.swing.JPanel;
import java.awt.CardLayout;

/**
 * Manages different screens/panels in the game.
 */
public class ScreenManager extends JPanel {
    public static final String INTRO_SCREEN = "INTRO";
    public static final String GAME_SCREEN = "GAME";
    
    private CardLayout cardLayout;
    private GameFrame gameFrame;
    private IntroPanel introPanel;
    private MainGamePanel mainGamePanel;
    
    public ScreenManager(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
        this.cardLayout = new CardLayout();
        setLayout(cardLayout);
        
        introPanel = new IntroPanel(this);
        mainGamePanel = new MainGamePanel(this);
        
        add(introPanel, INTRO_SCREEN);
        add(mainGamePanel, GAME_SCREEN);
        
        showScreen(INTRO_SCREEN);
    }
    
    public void showScreen(String screenName) {
        cardLayout.show(this, screenName);
    }
    
    public void startGame(String petName) {
        mainGamePanel.startGame(petName);
        showScreen(GAME_SCREEN);
    }
    
    public GameFrame getGameFrame() {
        return gameFrame;
    }
    
    public IntroPanel getIntroPanel() {
        return introPanel;
    }
    
    public MainGamePanel getMainGamePanel() {
        return mainGamePanel;
    }
}
