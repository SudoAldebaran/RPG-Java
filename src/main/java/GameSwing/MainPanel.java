package GameSwing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel extends JPanel {

    private CardLayout cardLayout;
    private JPanel mainPanel;

    public MainPanel() {
        this.setLayout(new BorderLayout());

        // Créer une zone principale avec un gestionnaire de cartes
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Ajouter différents panneaux
        mainPanel.add(new CharacterCreationPanel(this), "CharacterCreation");
        mainPanel.add(new DungeonPanel(this), "Dungeon");

        this.add(mainPanel, BorderLayout.CENTER);

        // Passer à l'écran de création de personnage
        cardLayout.show(mainPanel, "CharacterCreation");
    }

    public void navigateTo(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }
}
