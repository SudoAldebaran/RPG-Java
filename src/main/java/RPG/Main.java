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

        // SELECTION NOM ET CLASSE
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
            scanner.nextLine();
            switch (classChoice) {
                case 1 -> chosenClass = PlayerClass.ELF;
                case 2 -> chosenClass = PlayerClass.WIZARD;
                case 3 -> chosenClass = PlayerClass.VAMPIRE;
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }

        Player player = new Player(playerName, chosenClass);

        // CREATION CARTE
        Map map = new Map();

        // CREATION BOUTIQUE
        WeaponStore store = new WeaponStore();

        // BOUCLE DU JEU
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

            // ACTIONS
            System.out.println("Move : (Z = ↑) (Q = ← ) (S = ↓ ) (D = → )");
            System.out.println("Other actions : (B = Visit shop) (X = Exit) ");
            System.out.print("> ");

            String input = scanner.nextLine().toUpperCase();

            if (input.equals("X")) {
                System.out.println("Goodbye!");
                break;
            }

            if (input.equals("B")) { // ACCES BOUTIQUE
                store.printWeapons();
                System.out.print("Choose a weapon to buy (enter the number): ");
                int weaponIndex = scanner.nextInt();
                scanner.nextLine();
                store.buyWeapon(weaponIndex, player);
                continue;
            }

            if (input.equals("Z") || input.equals("Q") || input.equals("S") || input.equals("D")) {
                // CALCUL NOUVELLE POSITION
                int newX = player.getxPos();
                int newY = player.getyPos();

                if (input.equals("Z")) newX--;
                if (input.equals("S")) newX++;
                if (input.equals("Q")) newY--;
                if (input.equals("D")) newY++;

                // VERIF MOUVEMENT
                if (map.isValidMove(newX, newY)) {
                    player.setPosition(newX, newY);
                } else {
                    System.out.println("You cannot move there ! It's blocked by an obstacle");
                }

                if (map.isValidMove(newX, newY)) {
                    char tile = map.getTile(newX, newY);

                    if (tile == 'X') {
                        System.out.println("You encountered a destructible obstacle ! 🪓");
                        System.out.println("Do you want to attack it? (Y/N)");
                        String choice = scanner.nextLine().toUpperCase();

                        if (choice.equals("Y")) {

                            Obstacle obstacle = map.getDestructibleObstacleAt(newX, newY);
                            while (obstacle.getHealth() > 0) {
                                System.out.println("Attacking the obstacle! Remaining health: " + obstacle.getHealth());
                                obstacle.hit(player.getDamage());
                            }
                            System.out.println("You destroyed the obstacle! 🪓🔥");
                            map.setTile(newX, newY, '.');
                        } else {
                            System.out.println("You chose not to attack the obstacle.");
                        }
                    } else {
                        player.setPosition(newX, newY);
                    }
                } else {
                    System.out.println("You cannot move there! It's blocked by an obstacle.");
                }

                char tile = map.getTile(player.getxPos(), player.getyPos());

                if (tile == 'G') {
                    System.out.println("You encountered the Ogre ! \uD83E\uDDCC You died in horrible agony.");
                    break;
                } else if (tile == 'M') {
                    System.out.println("You encountered a monster!");

                    Monster monster = new Monster();

                    // COMBAT
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

                            System.out.println("You attack the monster !");
                            monster.hit(player.getDamage());
                            if (monster.getHealth() <= 0) {
                                System.out.println("You defeated the monster and received 10 coins!");
                                player.addMoney(10);
                                map.setTile(newX, newY, '.');
                                player.addXP(50);
                                break;
                            }

                            int monsterDamage = (int) (Math.random() * 21) + 10;
                            System.out.println("The monster attacks you for " + monsterDamage + " damage!");
                            player.reduceHealth(monsterDamage);
                        } else if (choice.equals("F")) {
                            System.out.println("You fled the fight but lost 20 HP!");
                            player.reduceHealth(20);
                            break;
                        } else if (choice.equals("S")) {

                            player.specialAttack(monster);
                            if (monster.getHealth() <= 0) {
                                System.out.println("You defeated the monster and received 10 coins!");
                                player.addMoney(10);
                                map.setTile(newX, newY, '.');
                                player.addXP(50);
                                break;
                            }


                            int monsterDamage = (int) (Math.random() * 21) + 10;
                            System.out.println("The monster attacks you for " + monsterDamage + " damage!");
                            player.reduceHealth(monsterDamage);
                        } else {
                            System.out.println("Invalid choice! Please choose [A] or [F].");
                        }
                    }

                    if (player.getHealth() <= 0) {
                        System.out.println("You died! Game over. 💀");
                        break;
                    }
                } else if (tile == 'S') { // SORTIE
                    System.out.println("Congratulations! You reached the exit and won the game!");
                    break;
                } else if (tile == 'H') {
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
