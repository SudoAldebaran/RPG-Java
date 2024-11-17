package RPG;

public enum PlayerClass {
    ELF("Elf", 50, 30, 80),
    WIZARD("Wizard", 100, 20, 90),
    VAMPIRE("Vampire", 40, 35, 120);

    private final String name;
    private final double startingMana;
    private final int strength;
    private final int health;

    PlayerClass(String name, double startingMana, int strength, int health) {
        this.name = name;
        this.startingMana = startingMana;
        this.strength = strength;
        this.health = health;
    }

    public String getName() {
        return name;
    }

    public double getStartingMana() {
        return startingMana;
    }

    public int getStrength() {
        return strength;
    }

    public int getHealth() {
        return health;
    }


}
