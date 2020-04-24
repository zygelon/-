package AB;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MainPanel extends JPanel {
    int width;
    int height;
    private MainPanel panel;
    private ImageIcon background;
    private int maxDucks;
    final ConcurrentLinkedQueue<Duck> ducks;
    private Hunter hunter;
    private MainFrame mainFrame;

    MainPanel(MainFrame mainFrame, int width, int height) {
        this.setBackground(Color.WHITE);
        this.mainFrame = mainFrame;
        this.panel = this;
        this.background = new ImageIcon("./resources/background.png");
        this.maxDucks = 15;
        this.ducks = new ConcurrentLinkedQueue();
        this.hunter = null;
        this.width = width;
        this.height = height;
        this.setLayout((LayoutManager)null);
        this.setSize(width, height);
        MainPanel.GameMouseAdapter mouseAdapter = new MainPanel.GameMouseAdapter();
        this.addMouseListener(mouseAdapter);
        MainPanel.Game game = new MainPanel.Game();
        game.start();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.background.getImage(), 0, 0, this.width, this.height, (ImageObserver)null);
    }

    public Dimension getPreferredSize() {
        return new Dimension(this.width, this.height);
    }

    public Dimension getMinimumSize() {
        return new Dimension(this.width, this.height);
    }

    public Dimension getMaximumSize() {
        return new Dimension(this.width, this.height);
    }

    class GameMouseAdapter extends MouseAdapter {
        GameMouseAdapter() {
        }

        public void mouseReleased(MouseEvent e) {
            Iterator var2 = MainPanel.this.ducks.iterator();

            while(var2.hasNext()) {
                Duck duck = (Duck)var2.next();
                if (e.getX() >= duck.x && e.getX() <= duck.x + duck.labelWidth && e.getY() >= duck.y && e.getY() <= duck.y + duck.labelHeight) {
                    duck.interrupt();
                }
            }

        }
    }

    class Game extends Thread {
        Game() {
        }

        public void run() {
            if (MainPanel.this.hunter == null) {
                MainPanel.this.hunter = new Hunter(MainPanel.this.mainFrame, MainPanel.this.panel);
                MainPanel.this.hunter.start();
            }

            while(!this.isInterrupted()) {
                if (MainPanel.this.ducks.size() < MainPanel.this.maxDucks) {
                    Duck duck = new Duck(MainPanel.this.width, MainPanel.this.height, MainPanel.this.panel);
                    MainPanel.this.ducks.add(duck);
                    duck.start();
                }

                try {
                    sleep(400L);
                } catch (InterruptedException var2) {
                }
            }

        }
    }
}
