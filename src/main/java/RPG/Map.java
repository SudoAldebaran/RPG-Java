package RPG;

public class Map {
    private static final int SIZE = 10; // TAILLE DE LA CARTE
    private char[][] map; // LA CARTE SERA UNE MATRICE DE CARACTERES ('.' POUR VIDE, 'M' POUR MONSTRE, 'O' POUR OBSTACLE, 'S' POUR SORTIE)

    public Map() {
        map = new char[SIZE][SIZE];
        initializeEmptyMap();
        configureStandardMap();
    }

    // INITIALISE UNE CARTE VIDE
    private void initializeEmptyMap() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = '.'; // PAR DEFAUT, VIDE
            }
        }
    }

    // CONFIGURE LA CARTE AVEC UNE DISPOSITION STANDARD
    private void configureStandardMap() {
        // OBSTACLES DESTRUCTIBLES FORMANT UNE PARTIE DU LABYRINTHE
        int[][] destructibleObstaclePositions = {
                {1, 2}, {1, 5}, {5, 3}, {7, 5}, {7, 1}, {8, 3}, {9, 4}
        };

        // OBSTACLES NON DESTRUCTIBLES FORMANT UNE PARTIE DU LABYRINTHE
        int[][] nonDestructibleObstaclePositions = {
                {0, 7}, {1, 1}, {1, 3}, {1, 6}, {1, 7}, {2, 1}, {3, 1},
                {3, 5}, {3, 7}, {4, 7}, {5, 7}, {5, 5}, {6, 3}, {7, 3},
                {7, 4}, {7, 6}, {7, 7}, {8, 2}, {9, 3}, {8, 0}, {5, 0},
                {6, 0}, {3, 2}, {0, 8}, {0, 9}, {1, 8}, {1, 9}, {2, 9},
                {3, 9}, {4, 9}, {5, 9}, {6, 7}, {9, 5}, {9, 6}, {9, 7},
                {6, 9}, {7, 9}, {3, 3}, {4, 3}, {5, 6}, {9, 0}, {9, 1},
                {9, 2}, {7, 2}, {7, 0}
        };

        // MONSTRES SUR DES CHEMINS CLES
        int[][] monsterPositions = {
                {2, 4}, {4, 4}, {6, 5}, {6, 1}, {8, 7}
        };

        int[][] healPositions = {
                {8, 1}
        };

        // PLACER LES OBSTACLES DESTRUCTIBLES ('X')
        for (int[] pos : destructibleObstaclePositions) {
            map[pos[0]][pos[1]] = 'X'; // PLACE UN OBSTACLE DESTRUCTIBLE
        }

        // PLACER LES OBSTACLES NON DESTRUCTIBLES ('O')
        for (int[] pos : nonDestructibleObstaclePositions) {
            map[pos[0]][pos[1]] = 'O'; // PLACE UN OBSTACLE NON DESTRUCTIBLE
        }

        // PLACER LES MONSTRES ('M')
        for (int[] pos : monsterPositions) {
            map[pos[0]][pos[1]] = 'M'; // PLACE UN MONSTRE
        }

        for (int[] pos : healPositions) {
            map[pos[0]][pos[1]] = 'H'; // PLACE UN SOIN
        }

        // AJOUTER UN OGRE ('G') A UNE POSITION CLE
        map[5][8] = 'G';

        // AJOUTER LA SORTIE EN BAS A DROITE ('S')
        map[SIZE - 1][SIZE - 1] = 'S'; // LA SORTIE EST EN BAS A DROITE (9, 9)
    }

    // RETOURNE UN OBSTACLE DESTRUCTIBLE A UNE POSITION DONNEE
    public Obstacle getDestructibleObstacleAt(int x, int y) {
        if (map[x][y] == 'X') {
            return new DestructibleObstacle(); // CREE UN NOUVEL OBSTACLE DESTRUCTIBLE AVEC 100 PV
        }
        return null;
    }


    public boolean isValidMove(int x, int y) {
        // VERIFIE LES LIMITES DE LA CARTE
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) {
            return false;
        }

        // VERIFIE SI LA CASE EST UN OBSTACLE NON DESTRUCTIBLE
        char tile = map[x][y];
        if (tile == 'O') { // 'O' REPRESENTE UN OBSTACLE NON DESTRUCTIBLE
            return false;
        }

        // DEPLACEMENT AUTORISE SI CE N'EST PAS UN OBSTACLE NON DESTRUCTIBLE
        return true;
    }



    public void printMap(Player player) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (i == player.getxPos() && j == player.getyPos()) {
                    System.out.print(player.getEmoji()); // AFFICHE L'EMOJI DU JOUEUR
                } else if (map[i][j] == 'X') {
                    System.out.print("\uD83E\uDEA8"); // EMOJI ROCHER POUR LES OBSTACLES DESTRUCTIBLES
                } else if (map[i][j] == 'G') {
                    System.out.print("\uD83E\uDDCC"); // EMOJI OGRE
                } else if (map[i][j] == 'O') {
                    System.out.print("\uD83C\uDF33"); // EMOJI ARBRE
                } else if (map[i][j] == 'M') {
                    System.out.print("\uD83E\uDDDF"); // EMOJI MONSTRE
                } else if (map[i][j] == 'S') {
                    System.out.print("\uD83D\uDEAA"); // EMOJI PORTE POUR LA SORTIE
                } else if (map[i][j] == 'H') {
                    System.out.print("\uD83D\uDC8A"); // EMOJI PILULE POUR SOIN
                } else {
                    System.out.print(". "); // CASE VIDE
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
