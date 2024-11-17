package Game;

import java.util.Random;

public class Map {
    private final char[][] map;
    private final int rows;
    private final int cols;

    public Map(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.map = new char[rows][cols];
        generateMap();
    }

    private void generateMap() {
        Random random = new Random();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double rand = random.nextDouble();
                if (rand < 0.2) {
                    map[i][j] = 'M'; // Monstre
                } else if (rand < 0.4) {
                    map[i][j] = 'O'; // Obstacle
                } else {
                    map[i][j] = '.'; // Case vide
                }
            }
        }
        map[rows - 1][cols - 1] = 'E'; // La sortie est toujours en bas à droite
    }

    public char getTile(int x, int y) {
        return map[x][y];
    }

    public void clearTile(int x, int y) {
        map[x][y] = '.'; // Nettoie la case après destruction
    }

    public void printMap(int playerX, int playerY) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == playerX && j == playerY) {
                    System.out.print("P "); // Représente le joueur
                } else {
                    System.out.print(map[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
}
