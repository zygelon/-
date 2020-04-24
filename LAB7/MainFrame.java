package AB;

import java.awt.Dimension;
import java.awt.LayoutManager;
import javax.swing.JFrame;

public class MainFrame extends JFrame {
    MainFrame(String title) {
        super(title);
        this.setDefaultCloseOperation(3);
        this.setResizable(false);
        this.setLayout((LayoutManager)null);
        this.setSize(new Dimension(1400, 800));
        MainPanel panel = new MainPanel(this, 1366, 768);
        this.add(panel);
        this.setVisible(true);
    }
}