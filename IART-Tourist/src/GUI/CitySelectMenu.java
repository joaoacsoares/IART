package GUI;

import Logic.Choices;
import Logic.City;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by Joao on 30/05/2015.
 */
public class CitySelectMenu extends JPanel implements ItemListener {
    private JLabel header;
    private JLabel display;
    private JPanel contentPane;
    private String city;
    private Choices data;


    public CitySelectMenu(JPanel panel, Choices recData) {
        contentPane = panel;
        data = recData;
        String[] cities = {"Porto","Lisboa","Aveiro"};
        city = cities[0];
        display = new JLabel(cities[0]);
        header = new JLabel();
        header.setText("Please, select your city:");
        JComboBox <String> cityBox = new JComboBox <String>(cities);
        cityBox.addItemListener(this);
        JButton nextBtn = new JButton ("Next");
        JButton cancelBtn = new JButton ("Cancel");

        //adjust size and set layout
        setPreferredSize (new Dimension(400, 300));
        setLayout(null);

        //set component bounds (only needed by Absolute Positioning)
        header.setBounds (5, 1, 185, 50);
        display.setBounds (145, 1, 185, 50);
        cityBox.setBounds(5, 40, 75,25);
        nextBtn.setBounds(240, 100, 60, 20);
        cancelBtn.setBounds(310, 100, 80, 20);

        nextBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                City c = new City(city);
                data.setCity(c);
                NodeSelectMenu panel2 = new NodeSelectMenu(contentPane, c, data);
                contentPane.add(panel2, "NodeSelection");
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.next(contentPane);
            }
        });

        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        //add components
        add(header);
        add(display);
        add(cityBox);
        add(nextBtn);
        add(cancelBtn);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            display.setText(e.getItem().toString());
            city = e.getItem().toString();
        }
    }


    public String getCity() {
        return city;
    }


}

