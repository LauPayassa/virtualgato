package br.com.virtualpet.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Manages popup dialogs and messages in the game.
 */
public class PopupManager {
    private Component parentComponent;
    
    public PopupManager(Component parentComponent) {
        this.parentComponent = parentComponent;
    }
    
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(
            parentComponent,
            message,
            "Pet Message",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    public void showWarning(String message) {
        JOptionPane.showMessageDialog(
            parentComponent,
            message,
            "Warning",
            JOptionPane.WARNING_MESSAGE
        );
    }
    
    public void showError(String message) {
        JOptionPane.showMessageDialog(
            parentComponent,
            message,
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
    
    public void showGameOver() {
        JOptionPane.showMessageDialog(
            parentComponent,
            "Your pet has died! 😢\nGame Over.",
            "Game Over",
            JOptionPane.ERROR_MESSAGE
        );
    }
    
    public boolean showConfirmation(String message) {
        int result = JOptionPane.showConfirmDialog(
            parentComponent,
            message,
            "Confirmation",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        return result == JOptionPane.YES_OPTION;
    }
    
    public String showInputDialog(String message) {
        return JOptionPane.showInputDialog(
            parentComponent,
            message,
            "Input",
            JOptionPane.QUESTION_MESSAGE
        );
    }
}
