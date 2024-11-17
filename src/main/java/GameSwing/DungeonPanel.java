package GameSwing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class DungeonPanel extends JPanel {
    private final int ROWS = 10;
    private final int COLS = 10;
    private JButton[][] gridButtons;
    private int playerRow = 0;
    private int playerCol = 0;

    // Grille des obstacles
    private boolean[][] obstacles = new boolean[ROWS][COLS];
    private int exitRow = 9;
    private int exitCol = 9;

    public DungeonPanel(MainPanel mainPanel) {
        this.setLayout(new BorderLayout());

        // Carte visuelle
        JPanel gridPanel = new JPanel(new GridLayout(ROWS, COLS));
        gridButtons = new JButton[ROWS][COLS];
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                gridButtons[i][j] = new JButton();
                gridButtons[i][j].setEnabled(false);
                gridPanel.add(gridButtons[i][j]);
            }
        }
        this.add(gridPanel, BorderLayout.CENTER);

        // Initialiser les obstacles et la porte
        generateObstacles();

        // Initialiser la position du joueur
        updatePlayerPosition();

        // Panneau de commandes pour les boutons directionnels
        JPanel controls = new JPanel(new GridLayout(3, 3));
        JButton upButton = new JButton("↑");
        JButton downButton = new JButton("↓");
        JButton leftButton = new JButton("←");
        JButton rightButton = new JButton("→");

        controls.add(new JLabel("")); // Case vide en haut à gauche
        controls.add(upButton);
        controls.add(new JLabel("")); // Case vide en haut à droite

        controls.add(leftButton);
        controls.add(new JLabel("")); // Case vide au centre
        controls.add(rightButton);

        controls.add(new JLabel("")); // Case vide en bas à gauche
        controls.add(downButton);
        controls.add(new JLabel("")); // Case vide en bas à droite

        upButton.addActionListener(e -> movePlayer(-1, 0));
        downButton.addActionListener(e -> movePlayer(1, 0));
        leftButton.addActionListener(e -> movePlayer(0, -1));
        rightButton.addActionListener(e -> movePlayer(0, 1));

        this.add(controls, BorderLayout.SOUTH);

        // Ajout d'un KeyListener pour capter les touches directionnelles
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        movePlayer(-1, 0); // Déplacer vers le haut
                        break;
                    case KeyEvent.VK_DOWN:
                        movePlayer(1, 0);  // Déplacer vers le bas
                        break;
                    case KeyEvent.VK_LEFT:
                        movePlayer(0, -1); // Déplacer vers la gauche
                        break;
                    case KeyEvent.VK_RIGHT:
                        movePlayer(0, 1);  // Déplacer vers la droite
                        break;
                }
            }
        });

        // Permet à ce JPanel de recevoir les événements clavier
        this.setFocusable(true);
        this.requestFocusInWindow(); // Donne le focus à ce JPanel immédiatement
    }

    // Générer des obstacles variés et placer la porte
    private void generateObstacles() {
        // Exemple de labyrinthe
        int[][] obstaclePositions = {
                {1, 1}, {1, 2}, {1, 3}, {1, 5}, {1, 6}, {1, 7},
                {3, 1}, {3, 3}, {3, 5}, {3, 6}, {3, 8},
                {4, 1}, {4, 3}, {4, 7}, {4, 8},
                {6, 1}, {6, 2}, {6, 3}, {6, 5}, {6, 6},
                {8, 2}, {8, 3}, {8, 6}, {8, 7}, {8, 8}
        };

        // Remplir la grille avec les positions d'obstacles
        for (int[] pos : obstaclePositions) {
            obstacles[pos[0]][pos[1]] = true;
        }

        // Positionner la porte de sortie
        gridButtons[exitRow][exitCol].setText("V"); // Porte marquée par "V"
    }

    // Déplacer le joueur
    private void movePlayer(int rowChange, int colChange) {
        int newRow = playerRow + rowChange;
        int newCol = playerCol + colChange;

        if (newRow >= 0 && newRow < ROWS && newCol >= 0 && newCol < COLS) {
            // Vérifier si la case suivante est un obstacle
            if (!obstacles[newRow][newCol]) {
                playerRow = newRow;
                playerCol = newCol;
                updatePlayerPosition();

                // Vérifier si le joueur a atteint la porte (V)
                if (playerRow == exitRow && playerCol == exitCol) {
                    JOptionPane.showMessageDialog(this, "Félicitations, vous avez atteint la sortie !");
                }
            }
        }
    }

    // Mettre à jour la position du joueur
    private void updatePlayerPosition() {
        // Réinitialiser tous les boutons
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                gridButtons[i][j].setText(""); // Réinitialise le texte
                if (obstacles[i][j]) {
                    gridButtons[i][j].setText("O"); // Marque les obstacles
                    gridButtons[i][j].setBackground(Color.GRAY); // Couleur des obstacles
                } else {
                    gridButtons[i][j].setBackground(Color.WHITE); // Case vide
                }
            }
        }

        // Mettre à jour la position de la porte
        gridButtons[exitRow][exitCol].setText("V");
        gridButtons[exitRow][exitCol].setBackground(Color.GREEN); // Porte de sortie

        // Mettre à jour la position du joueur
        gridButtons[playerRow][playerCol].setText("P");
        gridButtons[playerRow][playerCol].setBackground(Color.CYAN); // Couleur du joueur
    }
}
