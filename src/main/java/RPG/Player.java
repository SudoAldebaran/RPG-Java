package RPG;

public class Player {
    private String name;
    private PlayerClass playerClass; // Classe du joueur
    private int level;
    private int xp;
    private double money;
    private double mana;
    private int xPos, yPos;  // Position du joueur sur la carte
    private Weapon equippedWeapon;  // Arme équipée
    private int health;
    private int strength;

    public Player(String name, PlayerClass playerClass) {
        this.name = name;
        this.playerClass = playerClass;
        this.level = 1;
        this.xp = 0;
        this.money = 20;
        this.mana = playerClass.getStartingMana();
        this.xPos = 0;
        this.yPos = 0;
        this.equippedWeapon = null;
        this.health = this.playerClass.getHealth();
        this.strength = playerClass.getStrength(); // Initialisation de la force
    }

    public void displayInfo() {
        System.out.println("Name: " + this.name);
        System.out.println("Class: " + this.playerClass.getName());
        System.out.println("Level: " + this.level);
        System.out.println("XP: " + this.xp);
        System.out.println("Money: " + this.money);
        System.out.println("Mana: " + this.mana);
        if (this.equippedWeapon != null) {
            System.out.println("Strength: " + this.getStrength());
        } else {
            System.out.println("Strength: " + this.playerClass.getStrength());
        }
        displayEquippedWeapon();
        System.out.println("Life : " + this.health);
    }

    public void addXP(int xp) {
        this.xp += xp;
        if (this.xp >= 100) {  // Exemple : chaque 100 XP fait passer un niveau
            this.level++;
            this.xp = 0;
            System.out.println("Level up ! New level: " + this.level);

            // Augmentation de la force à chaque montée de niveau
            // Bonus de force par niveau
            int levelStrengthBonus = 5;
            increaseStrength(levelStrengthBonus);
        }
    }

    // Méthode pour augmenter la force
    public void increaseStrength(int amount) {
        this.strength += amount;  // Met à jour la force du joueur
        System.out.println("Strength increased by " + amount + ". New strength: " + this.strength);
    }

    public void increaseHealth(int amount) {
        this.health += amount;
        System.out.println("Health increased by " + amount + ". Current health: " + this.health);
    }

    public void addMoney(double amount) {
        this.money += amount;
    }

    public double getMoney() {
        return this.money;
    }

    public void subtractMoney(double amount) {
        this.money -= amount;
    }

    public PlayerClass getPlayerClass() {
        return this.playerClass;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setPosition(int x, int y) {
        this.xPos = x;
        this.yPos = y;
    }

    public void move(String direction, Map map) {
        int newX = xPos, newY = yPos;

        // Calcul de la nouvelle position
        switch (direction.toLowerCase()) {
            case "up":
                newX -= 1;
                break;
            case "down":
                newX += 1;
                break;
            case "left":
                newY -= 1;
                break;
            case "right":
                newY += 1;
                break;
            default:
                System.out.println("Direction invalide !");
                return;
        }

        // Vérification de la validité du déplacement
        if (map.isValidMove(newX, newY)) {
            this.xPos = newX;
            this.yPos = newY;
        } else {
            System.out.println("Déplacement impossible : obstacle non destructible détecté !");
        }
    }

    // Retourne les dégâts infligés par le joueur
    public double getDamage() {
        if (equippedWeapon != null) {
            return equippedWeapon.getDamage() + playerClass.getStrength(); // Dégâts de l'arme équipée
        } else {
            return playerClass.getStrength(); // Par défaut, force de la classe
        }
    }

    public int getStrength() {
        int weaponDamage = (this.equippedWeapon != null) ? (int) this.equippedWeapon.getDamage() : 0; // Bonus de l'arme
        return this.strength + weaponDamage;  // Force actuelle du joueur, incluant l'arme équipée
    }

    // Permet d'équiper une arme
    public void equipWeapon(Weapon weapon) {
        this.equippedWeapon = weapon;
        System.out.println("You equipped the " + weapon.getName() + "!");
    }

    // Affiche l'arme équipée
    public void displayEquippedWeapon() {
        if (equippedWeapon != null) {
            System.out.println("Equipped weapon: " + equippedWeapon.getName());
        } else {
            System.out.println("No weapon equipped.");
        }
    }

    // Retourne l'emoji correspondant à la classe du joueur
    public String getEmoji() {
        switch (playerClass) {
            case ELF:
                return "\uD83E\uDDDD\u200D♂\uFE0F"; // Emoji Elfique (feuille)
            case WIZARD:
                return "\uD83E\uDDD9\u200D♂\uFE0F"; // Emoji Sorcier
            case VAMPIRE:
                return "\uD83E\uDDDB"; // Emoji Vampire
            default:
                return "P"; // Par défaut
        }
    }

    public void reduceHealth(int amount) {
        this.health -= amount;
        if (this.health < 0) this.health = 0;
    }

    public int getHealth() {
        return health;
    }

    public double getMana() {
        return mana;
    }

    // Méthode pour l'attaque spéciale, consomme du mana
    public void specialAttack(Destructible target) {
        if (this.mana >= 20) {  // Vérification si le joueur a assez de mana (par exemple 20 mana par attaque spéciale)
            this.mana -= 20;  // Consommation de mana
            double specialDamage = getDamage() * 2;  // Dégâts doublés
            System.out.println("Special attack! Dealing " + specialDamage + " damage.");
            target.hit(specialDamage);  // Inflige les dégâts à la cible
        } else {
            System.out.println("Not enough mana for a special attack.");
        }
    }
}
