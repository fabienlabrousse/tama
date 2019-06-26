package tama;

public class Stomach implements IStomach {

    //ATTIBUTS
    private static final int NRJ_GAIN = 2;
    private static final int QTY_PER_TURN = 4;
    private final IEnergyStock energyStock;
    private final int size;
    private int foodQuantity;
    private boolean isWorking;

    //CONSTRUCTEUR
    public Stomach(int c, IEnergyStock stock) {
        if ((c <= 0) || (stock == null)) {
            throw new IllegalArgumentException();
        }
        size = c;
        energyStock = stock;
        foodQuantity = 0;
        isWorking = true;
    }

    //REQUETES
    public String describe() {
        String w;
        if (isWorking) {
            w = "fonctionnel";
        } else {
            w = "détruit";
        }
        return "stomach: " + foodQuantity + "/" + size + "/" + w;
    }
    
    public IEnergyStock energyStock() {
        return energyStock;
    }
    
    public int foodQuantity() {
        return foodQuantity;
    }
    
    public boolean isEmpty() {
        return (foodQuantity == 0);
    }
    
    public boolean isExploded() {
        return (foodQuantity > size);
    }
    
    public boolean isWorking() {
        return isWorking;
    }
    
    public int size() {
        return size;
    }
    
    //COMMANDES
    public void fill(int qty) {
        if (qty <= 0) {
            throw new IllegalArgumentException();
        }
        if (!isWorking) {
            throw new IllegalStateException();
        }
        foodQuantity = foodQuantity + qty;
        if (isExploded()) {
            isWorking = false;
        }
    }
    
    public void spendLife() {
        if (!isWorking) {
            throw new IllegalStateException();
        }
        if (foodQuantity >= QTY_PER_TURN) {
            foodQuantity = foodQuantity - QTY_PER_TURN;
            energyStock.raiseLevel(((QTY_PER_TURN - 1) * NRJ_GAIN));
        } else {
            if (foodQuantity > 0) {
                if (foodQuantity != 1) {
                    energyStock.raiseLevel(((foodQuantity - 1) * NRJ_GAIN));
                }
                foodQuantity = 0;
            } else {
                if (energyStock.crtLevel() >= NRJ_GAIN) {
                    energyStock.consume(NRJ_GAIN);
                } else {
                    if (energyStock.crtLevel() != 0) {
                        energyStock.consume(energyStock.crtLevel());
                    }
                    isWorking = false;
                }
            }
        }
    }


}
