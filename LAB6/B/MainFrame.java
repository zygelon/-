package B;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

class MainFrame extends JFrame {
    private JPanel fieldPanel;
    private JButton StartButton;
    private JButton StopButton;
    private int scrollPane = 1;

    MainFrame() {
        super("Conway's Life");
        this.setDefaultCloseOperation(3);
        this.setResizable(false);
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        this.add(toolBar, "South");
        this.fieldPanel = new MainPanel(40, 40, 20);
        this.add(this.fieldPanel);
        this.StartButton = new JButton("Run");
        toolBar.add(this.StartButton);
        this.StopButton = new JButton("Stop");
        this.StopButton.setEnabled(true);
        toolBar.add(this.StopButton);
        DefaultComboBoxModel<String> civilAmountModel = new DefaultComboBoxModel();
        civilAmountModel.addElement("1");
        civilAmountModel.addElement("2");
        civilAmountModel.addElement("3");
        JComboBox<String> civilCombo = new JComboBox(civilAmountModel);
        civilCombo.setSelectedIndex(0);
        JScrollPane civilScrollPane = new JScrollPane(civilCombo);
        toolBar.add(civilScrollPane);
        this.StartButton.addActionListener((e) -> {
            if (civilCombo.getSelectedIndex() != -1) {
                this.scrollPane = Integer.parseInt((String)civilCombo.getItemAt(civilCombo.getSelectedIndex()));
                System.out.println(this.scrollPane);
            }

            ((MainPanel)this.fieldPanel).StartSimul(this.scrollPane, 0.5F);
            this.StartButton.setEnabled(false);
            this.StopButton.setEnabled(true);
        });
        this.StopButton.addActionListener((e) -> {
            ((MainPanel)this.fieldPanel).stopSimul(this.StartButton);
            this.StopButton.setEnabled(false);
        });
        this.pack();
        this.setVisible(true);
    }
}
