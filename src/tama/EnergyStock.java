package tama;

public class EnergyStock implements IEnergyStock {

    //ATTRIBUTS
    private int crtLevel;

    //CONSTRUCTEUR
    public EnergyStock(int nrj) {
        if (nrj < 0) {
            throw new IllegalArgumentException();
        }
        crtLevel = nrj;
    }

    //REQUETES
    public int crtLevel() {
        return crtLevel;
    }
    
    public String describe() {
        return "nrjLevel : " + crtLevel;
    }
    
    public boolean isEmpty() {
        return (crtLevel == 0);
    }
    
    //COMMANDES
    public void raiseLevel(int qty) {
        if (qty <= 0) {
            throw new IllegalArgumentException();
        }
        crtLevel = crtLevel + qty;
    }
    
    public void consume(int qty) {
        if ((qty <= 0) || (qty > crtLevel)) {
            throw new IllegalArgumentException();
        }
        if (crtLevel == 0) {
            throw new IllegalStateException();
        }
        crtLevel = crtLevel - qty;
    }
}
