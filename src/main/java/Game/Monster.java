package Game;

public class Monster extends Destructible {

    private static final double LIFE = 50;
    private static final double DAMAGE = 10; // Dégâts infligés au joueur

    public Monster() {
        super(LIFE);
    }

    public void attack(Player player) {
        System.out.println("Le monstre attaque !");
        player.takeDamage(DAMAGE);
    }

    public boolean isAlive() {
        return this.health > 0;
    }
}
