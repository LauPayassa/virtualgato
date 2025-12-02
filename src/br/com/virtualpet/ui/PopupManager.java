package br.com.virtualpet.ui;

import br.com.virtualpet.model.Pet;

import javax.swing.*;

public class PopupManager {

    private final JComponent parent;
    private final Pet pet;

    public PopupManager(JComponent parent, Pet pet) {
        this.parent = parent;
        this.pet = pet;
    }

    public void showSimpleMessage(String message) {
        JOptionPane.showMessageDialog(parent, message, "Mensagem", JOptionPane.INFORMATION_MESSAGE);
    }
}