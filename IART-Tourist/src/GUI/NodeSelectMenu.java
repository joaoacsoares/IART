package GUI;

import Logic.Choices;
import Logic.City;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
 * Created by Joao on 30/05/2015.
 */
public class NodeSelectMenu extends JPanel { //second menu

    private JPanel contentPane;
    private DefaultListModel<JCheckBox> model;
    private JCheckBoxList checkBoxList;
    private Choices data;


    public void genList(String[] nodes)
    {
        for(int i=0 ; i < nodes.length ; i++) {
            model.addElement(new JCheckBox(nodes[i]));
        }

    }

    public String[] getSelectedNodes()
    {
        String[] ret = new String[checkBoxList.getNumberOfNodes()];
        int j =0;
        for(int i = 0 ; i < model.getSize() ; i++)
        {
            JCheckBox checkbox = (JCheckBox) model.getElementAt(i);
            if (checkbox.isSelected()) {
                String tmp = checkbox.getText();
                System.out.println(tmp);
                ret[j] = tmp;
                j++;
            }
        }
        return ret;
    }



    public NodeSelectMenu(JPanel panel, City city, Choices recData) {
        //construct components
        data = recData;
        int j =0;
        String[] testNodes = new String[city.nColumn-2];
        for(int i = 2; i<city.nColumn;i++)
        {
            testNodes[j] = city.getPlaceName(i);
            j++;
        }


        contentPane = panel;
        JLabel display = new JLabel();
        JButton nextBtn = new JButton("Next");
        JButton cancelBtn = new JButton("Back");
        display.setText("Please, select the places you want to visit:");

        model = new DefaultListModel<JCheckBox>();
        checkBoxList = new JCheckBoxList(model);
        genList(testNodes);
        //adjust size and set layout



        setPreferredSize(new Dimension(395, 156));
        setLayout(null);

        //set component bounds (only needed by Absolute Positioning)
        display.setBounds(5, 1, 250, 50);
        checkBoxList.setBounds(5, 40, 200, 105);
        nextBtn.setBounds(240, 100, 60, 20);
        cancelBtn.setBounds(310, 100, 80, 20);

        //add components
        add(checkBoxList);
        add(display);
        add(nextBtn);
        add(cancelBtn);
        nextBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] nodes = getSelectedNodes();
                data.setChosenNodes(nodes);
                PriorityMenu panel3 = new PriorityMenu(contentPane, nodes,data);
                contentPane.add(panel3, "NodePriority");
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.next(contentPane);
            }
        });

        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                model.clear();
                CardLayout cardLayout = (CardLayout) contentPane.getLayout();
                cardLayout.first(contentPane);
            }
        });
    }
}






