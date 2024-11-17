package RPG;

public class Monster extends Destructible {

    private static final double LIFE = 100;

    public Monster() {
        super(LIFE); // On initialise la santé via la classe parent
    }

    // La méthode getHealth() est déjà héritée de Destructible
}
