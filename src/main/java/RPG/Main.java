package RPG;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=============================");
        System.out.println("\uD83D\uDD25 WELCOME TO OGRE DUNGEON \uD83D\uDD25  ");
        System.out.println("=============================");
        System.out.println("\nRULES :");
        System.out.println("QUIT THE DUNGEON");
        System.out.println("BUT DON'T ENCOUNTER THE OGRE! \uD83E\uDDCC ");
        System.out.println("\n");

        // 1. Sélection du nom et de la classe
        System.out.print("Enter your name: ");
        String playerName = scanner.nextLine();

        System.out.println("Choose your class:");
        System.out.println("[1] Elf \uD83E\uDDDD\u200D♂\uFE0F");
        System.out.println("[2] Wizard \uD83E\uDDD9\u200D♂\uFE0F");
        System.out.println("[3] Vampire \uD83E\uDDDB");

        PlayerClass chosenClass = null;
        while (chosenClass == null) {
            System.out.print("Enter a number (1-3): ");
            int classChoice = scanner.nextInt();
            scanner.nextLine(); // Nettoyer le scanner
            switch (classChoice) {
                case 1 -> chosenClass = PlayerClass.ELF;
                case 2 -> chosenClass = PlayerClass.WIZARD;
                case 3 -> chosenClass = PlayerClass.VAMPIRE;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }

        Player player = new Player(playerName, chosenClass);

        // 2. Créer une carte
        Map map = new Map();

        // 3. Créer un magasin
        WeaponStore store = new WeaponStore();

        // 4. Boucle du jeu
        while (true) {
            // AFFICHAGE DES STATS
            System.out.println("=============================");
            System.out.println("           STATS");
            System.out.println("=============================");
            player.displayInfo();

            // AFFICHAGE DE LA MAP
            System.out.println("=============================");
            System.out.println("            MAP");
            System.out.println("=============================");
            map.printMap(player);

            // Instructions d'action
            System.out.println("Move : (Z = ↑) (Q = ← ) (S = ↓ ) (D = → )");
            System.out.println("Other actions : (B = Visit shop) (X = Exit) ");
            System.out.print("> ");

            String input = scanner.nextLine().toUpperCase(); // Accepter l'entrée utilisateur, en majuscules

            if (input.equals("X")) { // Quitter le jeu
                System.out.println("Goodbye!");
                break;
            }

            if (input.equals("B")) { // Accéder à la boutique
                store.printWeapons();
                System.out.print("Choose a weapon to buy (enter the number): ");
                int weaponIndex = scanner.nextInt();
                scanner.nextLine(); // Nettoyer le scanner
                store.buyWeapon(weaponIndex, player);
                continue; // Revenir au début de la boucle
            }

            if (input.equals("Z") || input.equals("Q") || input.equals("S") || input.equals("D")) {
                // Calculer la nouvelle position
                int newX = player.getxPos();
                int newY = player.getyPos();

                if (input.equals("Z")) newX--; // Haut
                if (input.equals("S")) newX++; // Bas
                if (input.equals("Q")) newY--; // Gauche
                if (input.equals("D")) newY++; // Droite

                // Vérification de la validité du mouvement
                if (map.isValidMove(newX, newY)) {
                    player.setPosition(newX, newY);
                } else {
                    System.out.println("You cannot move there! It's blocked by an obstacle.");
                }

                // Interaction avec la carte
                char tile = map.getTile(player.getxPos(), player.getyPos());

                if (tile == 'G') { // Si le joueur tombe sur l'Ogre
                    System.out.println("You encountered the Ogre! \uD83E\uDDCC You died in horrible agony.");
                    break; // Terminer le jeu
                } else if (tile == 'M') { // Si le joueur tombe sur un monstre
                    System.out.println("You encountered a monster!");
                    // Ajout d'un combat ici
                } else if (tile == 'S') { // Si le joueur atteint la sortie
                    System.out.println("Congratulations! You reached the exit and won the game!");
                    break; // Terminer le jeu
                }
            } else {
                System.out.println("Invalid input. Please use Z, Q, S, D, B, or X.");
            }
        }

        scanner.close();
    }
}
