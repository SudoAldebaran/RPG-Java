package Game;

import java.util.ArrayList;
import java.util.Scanner;

public class Player {
    private String name;
    private double health;
    private ArrayList<Weapon> inventory;
    private Weapon currentWeapon;
    private int x;
    private int y;

    public Player(String name) {
        this.name = name;
        this.health = 100; // Points de vie initiaux
        this.inventory = new ArrayList<>();
        this.x = 0; // Position initiale
        this.y = 0;
    }

    public void addWeapon(Weapon weapon) {
        inventory.add(weapon);
    }

    public void selectWeapon() {
        if (inventory.isEmpty()) {
            System.out.println("Vous n'avez aucune arme !");
            return;
        }
        System.out.println("Choisissez une arme :");
        for (int i = 0; i < inventory.size(); i++) {
            System.out.println("[" + i + "] " + inventory.get(i));
        }
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        if (choice >= 0 && choice < inventory.size()) {
            currentWeapon = inventory.get(choice);
            System.out.println("Vous avez sélectionné : " + currentWeapon);
        } else {
            System.out.println("Choix invalide !");
        }
    }

    public Weapon getCurrentWeapon() {
        return currentWeapon;
    }

    public void takeDamage(double damage) {
        this.health -= damage;
        if (this.health <= 0) {
            System.out.println(name + " est mort !");
        } else {
            System.out.println(name + " a " + this.health + " points de vie restants.");
        }
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    public void move(String direction) {
        switch (direction.toLowerCase()) {
            case "haut" -> x = Math.max(x - 1, 0);
            case "bas" -> x = Math.min(x + 1, 4);
            case "gauche" -> y = Math.max(y - 1, 0);
            case "droite" -> y = Math.min(y + 1, 4);
            default -> System.out.println("Direction invalide !");
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
