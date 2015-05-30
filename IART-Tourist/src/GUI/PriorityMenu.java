package GUI;

import Logic.Choices;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Joao on 30/05/2015.
 */
public class PriorityMenu extends JPanel {

    private JPanel contentPane;
    private String[] nodes;
    private Choices data;


    public PriorityMenu(JPanel panel, String [] selectedNodes, Choices recData) {
        contentPane = panel;
        data = recData;
        this.nodes = selectedNodes;
        JLabel display = new JLabel();
        JButton yesBtn = new JButton("Yes");
        final JButton noBtn = new JButton("No");
        JButton cancelBtn = new JButton("Cancel");
        display.setText("Do you want to define priorities?");

        display.setBounds(5, 1, 250, 50);
        yesBtn.setBounds(240, 100, 60, 20);
        noBtn.setBounds(310, 100, 80, 20);
        cancelBtn.setBounds(310, 100, 80, 20);

        yesBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PriorityChooser panel4 = new PriorityChooser(contentPane,nodes,data);
                contentPane.add(panel4, "PriorityChooser");
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.next(contentPane);
            }
        });

        noBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Container frame = noBtn.getParent();
                do
                    frame = frame.getParent();
                while (!(frame instanceof JFrame));
                ((JFrame) frame).dispose();
            }
        });

        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.first(contentPane);
            }
        });

        add (display);
        add (yesBtn);
        add (noBtn);
        add (cancelBtn);
    }


}




