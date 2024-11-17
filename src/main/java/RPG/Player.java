package RPG;

public class Player {
    private String name;
    private PlayerClass playerClass; // CLASSE DU JOUEUR
    private int level;
    private int xp;
    private double money;
    private double mana;
    private int xPos, yPos;  // POSITION DU JOUEUR SUR LA CARTE
    private Weapon equippedWeapon;  // ARME EQUIPEE
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
        this.strength = playerClass.getStrength();
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
        if (this.xp >= 100) {  // EXEMPLE : CHAQUE 100 XP FAIT PASSER UN NIVEAU
            this.level++;
            this.xp = 0;
            System.out.println("Level up ! New level: " + this.level);

            // AUGMENTATION DE LA PUISSANCE A CHAQUE MONTEE DE NIVEAU
            // BONUS DE FORCE PAR NIVEAU
            int levelStrengthBonus = 5;
            increaseStrength(levelStrengthBonus);
        }
    }

    // METHODE POUR AUGMENTER LA FORCE
    public void increaseStrength(int amount) {
        this.strength += amount;  // MET A JOUR LA PUISSANCE DU JOUEUR
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

        // CALCUL DE LA NOUVELLE POSITION
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

        // VERIFICATION DE LA VALIDITE DU DEPLACEMENT
        if (map.isValidMove(newX, newY)) {
            this.xPos = newX;
            this.yPos = newY;
        } else {
            System.out.println("Déplacement impossible : obstacle non destructible détecté !");
        }
    }

    // RETOURNE LES DEGATS INFLIGES PAR LE JOUEUR
    public double getDamage() {
        if (equippedWeapon != null) {
            return equippedWeapon.getDamage() + playerClass.getStrength(); // DEGATS DE L'ARME EQUIPEE
        } else {
            return playerClass.getStrength(); // PAR DEFAUT, FORCE DE LA CLASSE
        }
    }

    public int getStrength() {
        int weaponDamage = (this.equippedWeapon != null) ? (int) this.equippedWeapon.getDamage() : 0; // BONUS DE L'ARME
        return this.strength + weaponDamage;  // FORCE ACTUELLE DU JOUEUR, INCLUANT L'ARME EQUIPEE
    }

    // PERMET D'EQUIPER UNE ARME
    public void equipWeapon(Weapon weapon) {
        this.equippedWeapon = weapon;
        System.out.println("You equipped the " + weapon.getName() + "!");
    }

    // AFFICHE L'ARME EQUIPEE
    public void displayEquippedWeapon() {
        if (equippedWeapon != null) {
            System.out.println("Equipped weapon: " + equippedWeapon.getName());
        } else {
            System.out.println("No weapon equipped.");
        }
    }

    // RETOURNE L'EMOJI CORRESPONDANT A LA CLASSE DU JOUEUR
    public String getEmoji() {
        switch (playerClass) {
            case ELF:
                return "\uD83E\uDDDD\u200D♂\uFE0F"; // EMOJI ELFIQUE
            case WIZARD:
                return "\uD83E\uDDD9\u200D♂\uFE0F"; // EMOJI SORCIER
            case VAMPIRE:
                return "\uD83E\uDDDB"; // EMOJI VAMPIRE
            default:
                return "P"; // PAR DEFAUT
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

    // METHODE POUR L'ATTAQUE SPECIALE, CONSOMME DU MANA
    public void specialAttack(Destructible target) {
        if (this.mana >= 20) {  // VERIFICATION SI LE JOUEUR A ASSEZ DE MANA (PAR EXEMPLE 20 MANA PAR ATTAQUE SPECIALE)
            this.mana -= 20;  // CONSUMMATION DE MANA
            double specialDamage = getDamage() * 2;  // DEGATS DOUBLES
            System.out.println("Special attack! Dealing " + specialDamage + " damage.");
            target.hit(specialDamage);  // INFLIGE LES DEGATS A LA CIBLE
        } else {
            System.out.println("Not enough mana for a special attack.");
        }
    }
}
