package Game;

public class Obstacle extends Destructible {

    private static final double LIFE = 30; // Points de vie par défaut de l'obstacle

    public Obstacle() {
        super(LIFE);
    }

    @Override
    public String toString() {
        return "Obstacle avec " + this.health + " points de vie.";
    }
}
