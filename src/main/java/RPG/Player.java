package RPG;

public class Player {
    private String name;
    private PlayerClass playerClass; // Classe du joueur
    private int level;
    private int xp;
    private double money;
    private double mana;
    private int xPos, yPos;  // Position du joueur sur la carte

    public Player(String name, PlayerClass playerClass) {
        this.name = name;
        this.playerClass = playerClass; // Initialisation de la classe
        this.level = 1;
        this.xp = 0;
        this.money = 100;
        this.mana = playerClass.getStartingMana(); // Mana spécifique à la classe
        this.xPos = 0;  // Position initiale (en haut à gauche)
        this.yPos = 0;
    }

    public void displayInfo() {
        System.out.println("Name: " + this.name);
        System.out.println("Class: " + this.playerClass.getName());
        System.out.println("Level: " + this.level);
        System.out.println("XP: " + this.xp);
        System.out.println("Money: " + this.money);
        System.out.println("Mana: " + this.mana);
        System.out.println("Strength: " + this.playerClass.getStrength());
    }

    public void addXP(int xp) {
        this.xp += xp;
        if (this.xp >= 100) {  // Exemple : chaque 100 XP fait passer un niveau
            this.level++;
            this.xp = 0;
            System.out.println("Level up! New level: " + this.level);
        }
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
}
