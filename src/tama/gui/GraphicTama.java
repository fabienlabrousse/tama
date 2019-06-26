package tama.gui;

import tama.Tamagoshi;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;

public class GraphicTama extends Component {
    private final Tamagoshi tamagoshi;
    private final PropertyChangeSupport propertyChangeSupport;
    private final String imageIsAlive = "puppy.jpg";
    private final String imageIsVenerable = "senior.jpg";
    private final String imageRIP = "rip.jpg";
    private final String imageIsSick = "sick.jpg";
    private final String imageIsTired = "sick.jpg";

    public GraphicTama(int initNrj, int initMaxAge, int initStomachSize) {
        tamagoshi = new Tamagoshi(initNrj, initMaxAge, initStomachSize);
        propertyChangeSupport = new PropertyChangeSupport(tamagoshi);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        BufferedImage image = new BufferedImage(600, 500, BufferedImage.TYPE_INT_RGB);
        try {
            if (!isAlive()) {
                if (isVenerable() && isHealthy()) {
                    image = ImageIO.read(this.getClass().getResourceAsStream(imageIsVenerable));
                } else {
                    if (isVenerable()) {
                        image = ImageIO.read(this.getClass().getResourceAsStream(imageIsSick));
                    } else {
                        if (isHealthy()) {
                            image = ImageIO.read(this.getClass().getResourceAsStream(imageIsTired));
                        } else {
                            image = ImageIO.read(this.getClass().getResourceAsStream(imageRIP));
                        }
                    }
                }
            } else {
                image = ImageIO.read(this.getClass().getResourceAsStream(imageIsAlive));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Graphics2D graphics2d = (Graphics2D) g;
        graphics2d.drawImage(image, 0, 0, null);
    }

    @Override
    public void addPropertyChangeListener(String event, PropertyChangeListener propertyChangeListener) {
        propertyChangeSupport.addPropertyChangeListener(event, propertyChangeListener);
    }

    public void feed(int quantity) {
        final boolean previousWorkingValue = tamagoshi.stomachIsWorking();
        tamagoshi.feed(quantity);
        boolean newIsStomachWorking = tamagoshi.stomachIsWorking();
        propertyChangeSupport.firePropertyChange("working", previousWorkingValue, newIsStomachWorking);
        repaint();
        System.out.println(describe());
    }

    public void spendLife() {
        final boolean previousLifeValue = tamagoshi.isAlive();
        final boolean previousWorkingValue = tamagoshi.stomachIsWorking();
        tamagoshi.spendLife();
        propertyChangeSupport.firePropertyChange("working", previousWorkingValue, tamagoshi.stomachIsWorking());
        propertyChangeSupport.firePropertyChange("alive", previousLifeValue, tamagoshi.isAlive());
        repaint();
        System.out.println(describe());
    }

    public boolean isAlive() {
        return tamagoshi.isAlive();
    }

    public boolean isVenerable() {
        return tamagoshi.isVenerable();
    }

    public boolean isHealthy() {
        return !tamagoshi.isStarved() && tamagoshi.stomachIsWorking();
    }

    public String describe() {
        return tamagoshi.describe();
    }
}
