package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
//import java.io.*;
//import java.util.*;

public class menuGUI
{
    private JPanel contentPane;
    private CitySelectMenu panel1;

    public menuGUI()
    {
        JFrame frame = new JFrame("Tourist Guide");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        contentPane = new JPanel();
        contentPane.setLayout(new CardLayout());

        panel1 = new CitySelectMenu(contentPane);

        contentPane.add(panel1, "CitySelection");
        frame.setContentPane(contentPane);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

}








