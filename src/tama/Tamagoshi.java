package tama;

public class Tamagoshi implements ITamagoshi {
    // ATTRIBUTS
    private final IEnergyStock stock;
    private final IHeart heart;
    private final IStomach stomach;

    // CONSTRUCTEURS
    public Tamagoshi(int energyInitLevel, int heartMaxAge, int stomachSize) {
        stock = new EnergyStock(energyInitLevel);
        heart = new Heart(heartMaxAge, stock);
        stomach = new Stomach(stomachSize, stock);
    }

    // REQUETES
    public String describe() {
        return heart.describe() + " " + stomach.describe()
            + " " + stock.describe();
    }
    public boolean isAlive() {
        return heart.isBeating();
    }
    public boolean isStarved() {
        return stock.isEmpty();
    }
    public boolean isVenerable() {
        return heart.age() == heart.maxLifeTime();
    }
    public boolean stomachHasExploded() {
        return stomach.isExploded();
    }
    public boolean stomachIsWorking() {
        return stomach.isWorking();
    }

    // COMMANDES
    public void feed(int mealNb) {
        if (!isAlive() || !stomachIsWorking()) {
            throw new IllegalStateException();
        }
        if (mealNb <= 0) {
            throw new IllegalArgumentException();
        }
        int qty = 0;
        for (int i = 0; i < mealNb; i++) {
            qty += alea(MIN, MAX);
        }
        stomach.fill(qty);
        if (stomach.isExploded()) {
            heart.stopBeating();
        }
    }
    public void spendLife() {
        if (!isAlive()) {
            throw new IllegalStateException();
        }
        if (stomachIsWorking()) {
            stomach.spendLife();
        }
        heart.spendLife();
    }
    
    // OUTILS
    /**
     * Calcule une valeur « au hasard » entre 'a' et 'b'.
     */
    private int alea(int a, int b) {
        return a + (int) (Math.random() * (b - a + 1));
    }
}
