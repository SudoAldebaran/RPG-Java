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
                    System.out.println("You cannot move there ! It's blocked by an obstacle");
                }

                if (map.isValidMove(newX, newY)) {
                    char tile = map.getTile(newX, newY);

                    if (tile == 'X') { // Obstacle destructible détecté
                        System.out.println("You encountered a destructible obstacle ! 🪓");
                        System.out.println("Do you want to attack it? (Y/N)");
                        String choice = scanner.nextLine().toUpperCase();

                        if (choice.equals("Y")) {
                            // Interaction de combat avec l'obstacle
                            Obstacle obstacle = map.getDestructibleObstacleAt(newX, newY); // Nouvelle méthode à implémenter
                            while (obstacle.getHealth() > 0) {
                                System.out.println("Attacking the obstacle! Remaining health: " + obstacle.getHealth());
                                obstacle.hit(player.getDamage());
                            }
                            System.out.println("You destroyed the obstacle! 🪓🔥");
                            map.setTile(newX, newY, '.'); // Libérer la case
                        } else {
                            System.out.println("You chose not to attack the obstacle.");
                        }
                    } else {
                        player.setPosition(newX, newY); // Déplacement normal
                    }
                } else {
                    System.out.println("You cannot move there! It's blocked by an obstacle.");
                }

                // Interaction avec la carte
                char tile = map.getTile(player.getxPos(), player.getyPos());

                if (tile == 'G') { // Si le joueur tombe sur l'Ogre
                    System.out.println("You encountered the Ogre ! \uD83E\uDDCC You died in horrible agony.");
                    break; // Terminer le jeu
                } else if (tile == 'M') { // Si le joueur tombe sur un monstre
                    System.out.println("You encountered a monster!");

                    // Création du monstre
                    Monster monster = new Monster();

                    // Boucle de combat
                    while (monster.getHealth() > 0 && player.getHealth() > 0) {
                        System.out.println("\nMonster Health: " + monster.getHealth());
                        System.out.println("Your Health: " + player.getHealth());
                        System.out.println("Your mana " + player.getMana());

                        System.out.println("\nWhat do you want to do?");
                        System.out.println("[A] Attack");
                        System.out.println("[F] Flee (-20 HP)");
                        System.out.println("[S] Special Attack (Costs 20 Mana)");

                        String choice = scanner.nextLine().toUpperCase();
                        if (choice.equals("A")) {
                            // Le joueur attaque
                            System.out.println("You attack the monster !");
                            monster.hit(player.getDamage());
                            if (monster.getHealth() <= 0) {
                                System.out.println("You defeated the monster and received 10 coins!");
                                player.addMoney(10);
                                map.setTile(newX, newY, '.');
                                player.addXP(50); // Gain d'XP pour avoir battu le monstre
                                break;
                            }

                            // Le monstre attaque
                            int monsterDamage = (int) (Math.random() * 21) + 10; // Dégâts aléatoires entre 10 et 30
                            System.out.println("The monster attacks you for " + monsterDamage + " damage!");
                            player.reduceHealth(monsterDamage);
                        } else if (choice.equals("F")) {
                            // Le joueur fuit
                            System.out.println("You fled the fight but lost 20 HP!");
                            player.reduceHealth(20);
                            break; // Sortie de la boucle de combat
                        } else if (choice.equals("S")) {
                            // Le joueur utilise l'attaque spéciale
                            player.specialAttack(monster); // Appel de l'attaque spéciale
                            if (monster.getHealth() <= 0) {
                                System.out.println("You defeated the monster and received 10 coins!");
                                player.addMoney(10);
                                map.setTile(newX, newY, '.');
                                player.addXP(50); // Gain d'XP pour avoir battu le monstre
                                break;
                            }

                            // Le monstre attaque après l'attaque spéciale
                            int monsterDamage = (int) (Math.random() * 21) + 10; // Dégâts aléatoires entre 10 et 30
                            System.out.println("The monster attacks you for " + monsterDamage + " damage!");
                            player.reduceHealth(monsterDamage);
                        } else {
                            System.out.println("Invalid choice! Please choose [A] or [F].");
                        }
                    }

                    // Vérifier si le joueur est mort
                    if (player.getHealth() <= 0) {
                        System.out.println("You died! Game over. 💀");
                        break; // Fin du jeu
                    }
                } else if (tile == 'S') { // Si le joueur atteint la sortie
                    System.out.println("Congratulations! You reached the exit and won the game!");
                    break; // Terminer le jeu
                } else if (tile == 'H') { // Si le joueur atteint la sortie
                    System.out.println("You healed by 50 life points !");
                    player.increaseHealth(50);
                    map.setTile(newX, newY, '.');
                }
            } else {
                System.out.println("Invalid input. Please use Z, Q, S, D, B, or X.");
            }
        }

        scanner.close();
    }
}
