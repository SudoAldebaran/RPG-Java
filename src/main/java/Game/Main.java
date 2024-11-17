package Game;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Initialisation du jeu
        Map map = new Map(5, 5);
        Player player = new Player("Hero");
        WeaponStore store = new WeaponStore();

        // Ajout d'armes au joueur
        for (Weapon w : store.getWeapons()) {
            player.addWeapon(w);
        }

        Scanner scanner = new Scanner(System.in);

        while (player.isAlive()) {
            // Affiche la carte
            map.printMap(player.getX(), player.getY());

            // Déplacement
            System.out.println("Entrez une direction (haut, bas, gauche, droite) :");
            String direction = scanner.nextLine();
            player.move(direction);

            // Vérifie la case où se trouve le joueur
            char tile = map.getTile(player.getX(), player.getY());
            if (tile == 'M') {
                System.out.println("Vous avez rencontré un monstre !");
                Monster monster = new Monster();

                // Combat avec le monstre
                while (monster.isAlive() && player.isAlive()) {
                    System.out.println("Choisissez une action : [1] Attaquer [2] Changer d'arme");
                    int action = scanner.nextInt();
                    scanner.nextLine(); // Consomme le retour à la ligne
                    if (action == 1) {
                        if (player.getCurrentWeapon() == null) {
                            System.out.println("Vous devez d'abord sélectionner une arme !");
                        } else {
                            player.getCurrentWeapon().attack(monster);
                            System.out.println("Vous avez attaqué le monstre !");
                        }
                    } else if (action == 2) {
                        player.selectWeapon();
                    }
                    // Le monstre riposte
                    if (monster.isAlive()) {
                        monster.attack(player);
                    }
                }
                if (!monster.isAlive()) {
                    System.out.println("Le monstre est vaincu !");
                    map.clearTile(player.getX(), player.getY());
                }
            } else if (tile == 'O') {
                System.out.println("Un obstacle bloque votre chemin !");
                System.out.println("Vous utilisez votre arme pour le détruire !");
                if (player.getCurrentWeapon() != null) {
                    Obstacle obstacle = new Obstacle();
                    player.getCurrentWeapon().attack(obstacle);
                    if (obstacle.health <= 0) {
                        System.out.println("L'obstacle a été détruit !");
                        map.clearTile(player.getX(), player.getY());
                    } else {
                        System.out.println("L'obstacle est encore debout !");
                    }
                } else {
                    System.out.println("Vous devez d'abord sélectionner une arme !");
                }
            } else if (tile == 'E') {
                System.out.println("Vous avez atteint la sortie ! Félicitations !");
                break;
            }
        }

        if (!player.isAlive()) {
            System.out.println("Game Over !");
        }
    }
}
