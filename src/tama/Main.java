package tama;

import tama.gui.VirtualAnimator;

import javax.swing.*;

public class Main {
    private static final int INIT_NRJ = 5;
    private static final int INIT_MAX_AGE = 20;
    private static final int INIT_STOMACH_SIZE = 20;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Tamagoshi tamagoshi = new Tamagoshi(INIT_NRJ, INIT_MAX_AGE, INIT_STOMACH_SIZE);
                new VirtualAnimator(tamagoshi).display();
            }
        });
    }
}
