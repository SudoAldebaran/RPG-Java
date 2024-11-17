package RPG;

// Classe générale Obstacle
public abstract class Obstacle extends Destructible {

    public Obstacle(double health) {
        super(health);
    }

    // Définir si un obstacle est destructible ou non
    public abstract boolean isDestructible();

    public double getHealth() {
        return this.health;
    }
}

// Classe pour les obstacles destructibles
class DestructibleObstacle extends Obstacle {
    private static final double DEFAULT_HEALTH = 100;

    public DestructibleObstacle() {
        super(DEFAULT_HEALTH);
    }

    @Override
    public boolean isDestructible() {
        return true; // Peut être détruit
    }

    @Override
    public void hit(double damage) {
        super.hit(damage);
        System.out.println("The obstacle took " + damage + " damage!");
    }
}

// Classe pour les obstacles non destructibles
class NonDestructibleObstacle extends Obstacle {

    public NonDestructibleObstacle() {
        super(Double.POSITIVE_INFINITY); // Vie infinie
    }

    @Override
    public boolean isDestructible() {
        return false; // Ne peut pas être détruit
    }
}
