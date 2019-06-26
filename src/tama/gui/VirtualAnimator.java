package tama.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import tama.Tamagoshi;
import tama.gui.GraphicTama;

public class VirtualAnimator {
    // ATTRIBUTS
    private static final int DELAY = 1000;
    private static final int MAX_MEAL = 3;
    
    private GraphicTama tama;
    private JButton feed;
    private JButton oneStep;
    private JCheckBox stepByStep;
    private Timer timer;
    private JFrame frame;

    // CONSTRUCTEURS

    public VirtualAnimator(Tamagoshi tamagoshi) {
        createView(tamagoshi);
        placeComponents();
        createController();
        createTimer();
    }

    // REQUETES

    // COMMANDES
    
    public void display() {
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    // OUTILS

    private void createView(Tamagoshi tamagoshi) {
        frame = new JFrame("Tamagoshi v2");
        tama = new GraphicTama(tamagoshi);
        feed = new JButton("Feed");
        oneStep = new JButton("One Step");
        stepByStep = new JCheckBox("Step by step", true);
    }

    private void placeComponents() {
        JPanel p = new JPanel(); {
            p.add(stepByStep);
            p.add(oneStep);
            p.add(feed);
        }
        frame.add(p, BorderLayout.NORTH);

        frame.add(tama, BorderLayout.CENTER);

        p = new JPanel(new FlowLayout(FlowLayout.CENTER)); {
            p.add(new JLabel(""));
        }
        frame.add(p, BorderLayout.SOUTH);
    }

    private void createController() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tama.addPropertyChangeListener("alive", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getNewValue().equals(Boolean.FALSE)) {
                    feed.setEnabled(false);
                    stepByStep.setEnabled(false);
                    oneStep.setEnabled(false);
                }
            }
        });
        tama.addPropertyChangeListener("working", new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getNewValue().equals(Boolean.FALSE)) {
                    feed.setEnabled(false);
                }
            }
        });
        feed.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int x = (int) (Math.random() * MAX_MEAL) + 1;
                tama.feed(x);
                manageState();
            }
        });
        oneStep.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tama.spendLife();
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        manageState();
                    }
                });
            }
        });
        stepByStep.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (stepByStep.isSelected()) {
                    oneStep.setEnabled(true);
                    timer.stop();
                } else {
                    oneStep.setEnabled(false);
                    timer.restart();
                }
            }
        });
    }
    private void createTimer() {
        timer = new Timer(DELAY, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tama.isAlive()) {
                    tama.spendLife();
                    manageState();
                } else {
                    timer.stop();
                }
            }
        });
    }
    private void manageState() {
        if (!tama.isAlive()) {
            int msgType;
            String title;
            String msg;
            if (tama.isVenerable() && tama.isHealthy()) {
                msgType = JOptionPane.INFORMATION_MESSAGE;
                title = "Vous avez gagné :-)";
                msg = "Félicitations !!!\nVotre tamagoshi est mort "
                        + "vieux et en bonne santé.";
            } else {
                msgType = JOptionPane.ERROR_MESSAGE;
                title = "Vous avez perdu :'(";
                msg = "Bouuuh !!!\nVotre tamagoshi est mort ";
                if (tama.isVenerable()) {
                    msg += "vieux mais avec des problèmes de santé !";
                } else {
                    if (tama.isHealthy()) {
                        msg += "en bonne santé mais trop jeune";
                    } else {
                        msg += "trop jeune et avec des problèmes d'estomac";
                    }
                }
            }
            JOptionPane.showMessageDialog(frame, msg, title, msgType);
        }
    }
}
