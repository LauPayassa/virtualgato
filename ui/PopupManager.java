package br.com.virtualpet.ui;

import br.com.virtualpet.model.Pet;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PopupManager {

    private final JComponent parent;
    private final Pet pet;

    public PopupManager(JComponent parent, Pet pet) {
        this.parent = parent;
        this.pet = pet;
    }

    public void showSimpleMessage(String message) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(parent), "Mensagem", true);
        dialog.setLayout(new BorderLayout(10, 10));
        
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        contentPanel.setBackground(new Color(255, 248, 240));
        
        JLabel messageLabel = new JLabel("<html><div style='text-align: center; width: 250px;'>" + message + "</div></html>");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        contentPanel.add(messageLabel, BorderLayout.CENTER);
        
        JButton okButton = new JButton("OK");
        okButton.setFont(new Font("Arial", Font.BOLD, 12));
        okButton.setBackground(new Color(100, 180, 255));
        okButton.setForeground(Color.WHITE);
        okButton.setFocusPainted(false);
        okButton.setBorderPainted(false);
        okButton.setPreferredSize(new Dimension(80, 35));
        okButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        okButton.addActionListener(e -> dialog.dispose());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(255, 248, 240));
        buttonPanel.add(okButton);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        dialog.add(contentPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(parent);
        dialog.setResizable(false);
        dialog.setVisible(true);
    }
}