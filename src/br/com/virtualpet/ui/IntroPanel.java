package br.com.virtualpet.ui;

import br.com.virtualpet.model.Pet;

import javax.swing.*;
import java.awt.*;

public class IntroPanel extends JPanel {

    private final ScreenManager screenManager;
    private final Pet pet;

    public IntroPanel(ScreenManager screenManager, Pet pet) {
        this.screenManager = screenManager;
        this.pet = pet;
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Olha! Um gatinho", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        add(label, BorderLayout.CENTER);

        JButton ok = new JButton("OK");
        ok.addActionListener(e -> askResponsibility());
        JPanel bottom = new JPanel();
        bottom.add(ok);
        add(bottom, BorderLayout.SOUTH);
    }

    private void askResponsibility() {
        Object[] options = {"Sim", "Não"};
        int choice = JOptionPane.showOptionDialog(
                this,
                "Você aceita que agora é responsável pela criação e evolução do gatinho?",
                "Responsabilidade",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );
        if (choice == JOptionPane.YES_OPTION) {
            screenManager.showMainGameScreen();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Você não tem escolha... o gatinho precisa de você!",
                    "Ops",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
}