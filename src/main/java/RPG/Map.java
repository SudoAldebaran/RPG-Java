package RPG;

public class Map {
    private static final int SIZE = 10; // Taille de la carte
    private char[][] map; // La carte sera une matrice de caractères ('.' pour vide, 'M' pour monstre, 'O' pour obstacle, 'S' pour sortie)

    public Map() {
        map = new char[SIZE][SIZE];
        initializeEmptyMap();
        configureStandardMap();
    }

    // Initialise une carte vide
    private void initializeEmptyMap() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = '.'; // Par défaut, vide
            }
        }
    }

    // Configure la carte avec une disposition standard
    private void configureStandardMap() {
        // Obstacles destructibles formant une partie du labyrinthe
        int[][] destructibleObstaclePositions = {
                {1, 2}, {1, 5}, {3, 3}, {5, 3}, {7, 5}, {8, 1}, {8, 3}
        };

        // Obstacles non destructibles formant une partie du labyrinthe
        int[][] nonDestructibleObstaclePositions = {
                {0, 7}, {1, 1}, {1, 3}, {1, 6}, {1, 7}, {2, 1}, {3, 1},
                {3, 5}, {3, 7}, {4, 7}, {5, 7}, {5, 5}, {6, 3}, {7, 3},
                {7, 4}, {7, 6}, {7, 7}, {8, 2}, {9, 3}
        };

        // Monstres sur des chemins clés
        int[][] monsterPositions = {
                {2, 4}, {4, 4}, {6, 6}
        };

        // Placer les obstacles destructibles ('X')
        for (int[] pos : destructibleObstaclePositions) {
            map[pos[0]][pos[1]] = 'X'; // Place un obstacle destructible
        }

        // Placer les obstacles non destructibles ('O')
        for (int[] pos : nonDestructibleObstaclePositions) {
            map[pos[0]][pos[1]] = 'O'; // Place un obstacle non destructible
        }

        // Placer les monstres ('M')
        for (int[] pos : monsterPositions) {
            map[pos[0]][pos[1]] = 'M'; // Place un monstre
        }

        // Ajouter un Ogre ('G') à une position clé
        map[3][6] = 'G'; // Exemple : placer l'Ogre à la position (3, 6)

        // Ajouter la sortie en bas à droite ('S')
        map[SIZE - 1][SIZE - 1] = 'S'; // La sortie est en bas à droite (9, 9)
    }

    public boolean isValidMove(int x, int y) {
        // Vérifie les limites de la carte
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) {
            return false;
        }

        // Vérifie si la case est un obstacle non destructible
        char tile = map[x][y];
        if (tile == 'O') { // 'O' représente un obstacle non destructible
            return false;
        }

        // Déplacement autorisé si ce n'est pas un obstacle non destructible
        return true;
    }



    public void printMap(Player player) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (i == player.getxPos() && j == player.getyPos()) {
                    System.out.print(player.getEmoji()); // Affiche l'emoji du joueur
                } else if (map[i][j] == 'X') {
                    System.out.print("\uD83E\uDEA8"); // Emoji arbre pour les obstacles destructibles
                } else if (map[i][j] == 'G') {
                    System.out.print("\uD83E\uDDCC"); // Emoji arbre pour les obstacles destructibles
                } else if (map[i][j] == 'O') {
                    System.out.print("\uD83C\uDF33"); // Emoji panneau interdit pour les obstacles non destructibles
                } else if (map[i][j] == 'M') {
                    System.out.print("\uD83E\uDDDF"); // Emoji monstre
                } else if (map[i][j] == 'S') {
                    System.out.print("\uD83D\uDEAA"); // Emoji porte pour la sortie
                } else {
                    System.out.print(". "); // Case vide
                }
            }
            System.out.println();
        }
    }



    public char getTile(int x, int y) {
        return map[x][y];
    }

    public void setTile(int x, int y, char c) {
        map[x][y] = c;
    }
}
