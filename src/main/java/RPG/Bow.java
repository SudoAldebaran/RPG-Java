package RPG;

public class Bow extends Weapon {

    private static final double DAMAGE = 7;
    private static final double PRICE = 20;
    private static final String NAME = "Bow";

    private static final double MONSTER_DAMAGE_RATIO = 0.9;
    private static final double OBSTACLE_DAMAGE_RATIO = 1.1;


    public Bow() {
        super(DAMAGE, PRICE, NAME, MONSTER_DAMAGE_RATIO, OBSTACLE_DAMAGE_RATIO);
    }

    public String asciiArt() {
        return("\uD83C\uDFF9");
    }
}