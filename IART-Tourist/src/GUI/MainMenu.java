package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import static javax.swing.GroupLayout.Alignment.BASELINE;

public class MainMenu extends JFrame
        implements ItemListener {

    public String getCity() {
        return display.getText();
    }

    private JLabel display;
    private JComboBox cityBox;
    private String[] distros;
    private JButton button1;
    private JButton button2;

    public MainMenu() {
        String[] cities = {"Porto","Lisboa","Aveiro"};
        initUI(cities);
        
        setTitle("Tourist Guide");
        display.setText("Select your city:");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void initUI(String[] cities) {

        distros = cities;

        cityBox = new JComboBox(distros);
        cityBox.addItemListener(this);

        display = new JLabel(cities[0]);

        button1 = new JButton("Next");
        button2 = new JButton("Cancel");

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        createLayout(display, cityBox, button1, button2);


    }

    private void createLayout(JComponent... arg) {

        Container pane = getContentPane();
        GroupLayout gl = new GroupLayout(pane);
        pane.setLayout(gl);

        gl.setAutoCreateContainerGaps(true);
        gl.setAutoCreateGaps(true);

        gl.setHorizontalGroup(gl.createSequentialGroup()
                        .addComponent(arg[0])
                        .addComponent(arg[1])
                        .addComponent(arg[2])
                        .addComponent(arg[3])
        );

        gl.setVerticalGroup(gl.createParallelGroup(BASELINE)
                        .addComponent(arg[0])
                        .addComponent(arg[1])
                        .addComponent(arg[2])
                        .addComponent(arg[3])
        );

        pack();
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

        if (e.getStateChange() == ItemEvent.SELECTED) {
            display.setText(e.getItem().toString());

        }
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainMenu ex = new MainMenu();
                ex.setVisible(true);
            }
        });
    }
}