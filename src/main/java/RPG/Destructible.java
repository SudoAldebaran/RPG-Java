package RPG;

public class Destructible {

    protected double health;

    public Destructible(double h) {
        this.health = h;
    }

    public void hit(double d) {
        this.health -= d;
        if (this.health < 0) {
            this.health = 0; // On s'assure que la santé ne devienne pas négative
        }
    }

    public double getHealth() {
        return health;
    }
}
