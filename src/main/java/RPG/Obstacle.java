package RPG;

// CLASSE GENERALE OBSTACLE
public abstract class Obstacle extends Destructible {

    public Obstacle(double health) {
        super(health);
    }

    // DEFINIR SI UN OBSTACLE EST DESTRUCTIBLE OU NON
    public abstract boolean isDestructible();

    public double getHealth() {
        return this.health;
    }
}

// CLASSE POUR LES OBSTACLES DESTRUCTIBLES
class DestructibleObstacle extends Obstacle {
    private static final double DEFAULT_HEALTH = 100;

    public DestructibleObstacle() {
        super(DEFAULT_HEALTH);
    }

    @Override
    public boolean isDestructible() {
        return true; // PEUT ETRE DETRUIT
    }

    @Override
    public void hit(double damage) {
        super.hit(damage);
        System.out.println("L'OBSTACLE A PRIS " + damage + " D'ECHECS !");
    }
}

// CLASSE POUR LES OBSTACLES NON DESTRUCTIBLES
class NonDestructibleObstacle extends Obstacle {

    public NonDestructibleObstacle() {
        super(Double.POSITIVE_INFINITY); // VIE INFINIE
    }

    @Override
    public boolean isDestructible() {
        return false; // NE PEUT PAS ETRE DETRUIT
    }
}
