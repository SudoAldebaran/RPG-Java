package GameSwing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CharacterCreationPanel extends JPanel {
    private JTextField nameField;
    private JComboBox<String> weaponSelector; // Sélecteur d'arme
    private MainPanel mainPanel;

    public CharacterCreationPanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
        this.setLayout(new GridLayout(5, 1));

        JLabel title = new JLabel("Create Your Character", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        this.add(title);

        // Champ de texte pour le nom du joueur
        nameField = new JTextField("Enter your name");
        this.add(nameField);

        // Liste des armes disponibles
        String[] weapons = {"Sword", "Bow", "Axe", "Staff"};
        weaponSelector = new JComboBox<>(weapons); // Sélecteur d'arme
        this.add(weaponSelector);

        // Bouton de confirmation
        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playerName = nameField.getText();
                String selectedWeapon = (String) weaponSelector.getSelectedItem(); // Obtenir l'arme sélectionnée

                // Ici, tu peux définir des actions pour l'arme sélectionnée, par exemple :
                // - Créer un objet représentant l'arme.
                // - Passer à l'écran du donjon avec l'arme sélectionnée.

                System.out.println("Player Name: " + playerName);
                System.out.println("Selected Weapon: " + selectedWeapon);

                // Transition vers le donjon
                mainPanel.navigateTo("Dungeon");
            }
        });

        this.add(confirmButton);
    }
}
