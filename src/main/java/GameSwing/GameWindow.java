package GameSwing;

import javax.swing.*;

public class GameWindow {
    public static void main(String[] args) {
        // Crée la fenêtre principale
        JFrame frame = new JFrame("Dungeon Adventure");

        // Ajoute le panneau principal au cadre
        MainPanel mainPanel = new MainPanel(); // Doit contenir les transitions et les autres panneaux
        frame.add(mainPanel);

        // Configure les dimensions et les propriétés de la fenêtre
        frame.setSize(800, 600); // Taille fixe
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Centre la fenêtre sur l'écran
        frame.setResizable(false); // Empêche le redimensionnement (optionnel)
        frame.setVisible(true); // Affiche la fenêtre
    }
}
