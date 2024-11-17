package RPG;

public enum PlayerClass {
    ELF("Elf", 50, 30),
    WIZARD("Wizard", 100, 20),
    VAMPIRE("Vampire", 70, 40);

    private final String name;
    private final double startingMana;
    private final int strength;

    PlayerClass(String name, double startingMana, int strength) {
        this.name = name;
        this.startingMana = startingMana;
        this.strength = strength;
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
}
