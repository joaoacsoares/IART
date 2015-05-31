package GUI;

import Logic.Choices;
import org.graphstream.graph.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Joao on 31/05/2015.
 */
public class HotelMenu extends JPanel {
    private JPanel contentPane;
    private Choices data;
    private Node hotel;

    public HotelMenu(JPanel panel, final String[] nodes, Choices recData) {
        data = recData;
        contentPane = panel;
        JLabel display = new JLabel();
        JButton nextBtn = new JButton("Next");
        JButton cancelBtn = new JButton("Back");
        display.setText("Choose your Hotel:");

        hotel = data.getCity().getHotels().elementAt(0);
        ButtonGroup bG = new ButtonGroup();
        for (int i = 0; i < data.getCity().getHotels().size(); i++) {

            final JRadioButton button1 = new JRadioButton(data.getCity().getHotels().elementAt(i).getId());
            add(button1);
            bG.add(button1);
            if(i==0)
                button1.setSelected(true);
            button1.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    String tmp = button1.getText();
                    for(Node n : data.getCity().getHotels())
                    {
                        if(n.getId() == tmp) {
                            hotel = n;
                            System.out.println(tmp);
                        }
                    }
                }
            });

        }


        //set component bounds (only needed by Absolute Positioning)
        display.setBounds(5, 1, 250, 50);
        nextBtn.setBounds(240, 100, 60, 20);
        cancelBtn.setBounds(310, 100, 80, 20);

        //add components

        add(display);
        add(nextBtn);
        add(cancelBtn);
        nextBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                data.setHotel(hotel);
                PriorityMenu panel3 = new PriorityMenu(contentPane, nodes,data);
                contentPane.add(panel3, "NodePriority");
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.next(contentPane);
            }
        });

        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.first(contentPane);
            }
        });
    }



}
