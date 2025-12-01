package br.com.virtualpet.ui;

import br.com.virtualpet.GameConfig;

import javax.swing.JFrame;
import java.awt.CardLayout;

/**
 * Main game window frame.
 */
public class GameFrame extends JFrame {
    private ScreenManager screenManager;
    
    public GameFrame() {
        setTitle(GameConfig.GAME_TITLE);
        setSize(GameConfig.WINDOW_WIDTH, GameConfig.WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        screenManager = new ScreenManager(this);
        setContentPane(screenManager);
    }
    
    public ScreenManager getScreenManager() {
        return screenManager;
    }
}
