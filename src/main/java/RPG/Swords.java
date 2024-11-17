package RPG;

public class Swords extends Weapon {

    private static final double DAMAGE = 13;
    private static final double PRICE = 30;
    private static final String NAME = "Swords";

    private static final double MONSTER_DAMAGE_RATIO = 0.4;
    private static final double OBSTACLE_DAMAGE_RATIO = 1.5;


    public Swords() {
        super(DAMAGE, PRICE, NAME, MONSTER_DAMAGE_RATIO, OBSTACLE_DAMAGE_RATIO);
    }

    public String asciiArt() {
        return("⚔\uFE0F");

    }
}
