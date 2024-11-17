package GameSwing;

public class Player {
    private String name;
    private int health;
    private int row;
    private int col;
    private String weapon; // Nouvelle propriété

    public Player(String name, int health, int startRow, int startCol) {
        this.name = name;
        this.health = health;
        this.row = startRow;
        this.col = startCol;
        this.weapon = "None"; // Arme par défaut
    }

    public void setWeapon(String weapon) {
        this.weapon = weapon;
    }

    public String getWeapon() {
        return weapon;
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public void decreaseHealth() {
        health--;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public boolean isAlive() {
        return health > 0;
    }
}
