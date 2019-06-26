package tama;


public class Heart implements IHeart {

    //ATTRIBUTS
    private static final int NRJ = 3;
    private final int maxLifeTime;
    private final IEnergyStock energyStock;
    private int lifeTime;
    private int age;
    private boolean isBeating;

    //CONSTRUCTEUR
    public Heart(int max, IEnergyStock nrj) {
        if ((max <= 0) || (nrj == null)) {
            throw new IllegalArgumentException();
        }
        age = 0;
        maxLifeTime = max;
        lifeTime = max;
        energyStock = nrj;
        isBeating = true;
    }

    //REQUETES
    public int age() {
        return age;
    }

    public String describe() {
        String v;
        if (isBeating) {
            v = "vivant";
        } else {
            v = "mort";
        }
        return "heart: " + age + "/" + lifeTime + "/" + maxLifeTime + "/" + v;
    }

    public int lifeTime() {
        return lifeTime;
    }

    public IEnergyStock energyStock() {
        return energyStock;
    }

    public int maxLifeTime() {
        return maxLifeTime;
    }

    public boolean isBeating() {
        return isBeating;
    }

    //COMMANDES
    public void spendLife() {
        if (!isBeating) {
            throw new IllegalStateException();
        }
        age = age + 1;
        int delta = maxLifeTime - lifeTime;
        if ((NRJ + delta) <= energyStock.crtLevel()) {
            energyStock.consume(NRJ + delta);
            lifeTime = maxLifeTime;
        } else {
            if (energyStock.crtLevel() >= NRJ) {
                lifeTime = lifeTime + energyStock.crtLevel() - NRJ;
                energyStock.consume(energyStock.crtLevel());
            } else {
                lifeTime = max((lifeTime - (NRJ - energyStock.crtLevel())), age);
                if (energyStock.crtLevel() != 0) {
                    energyStock.consume(energyStock.crtLevel());
                }
            }
        }
        if (age == lifeTime) {
            stopBeating();
        }
    }

    public void stopBeating() {
        isBeating = false;
    }

    //OUTILS
    private int max(int a, int b) {
        if (a > b) {
            return a;
        } else {
            return b;
        }
    }

}
