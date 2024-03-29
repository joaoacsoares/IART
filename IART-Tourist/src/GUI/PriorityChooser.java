package GUI;

import Logic.Choices;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
 * Created by Joao on 30/05/2015.
 */
public class PriorityChooser extends JPanel {
    private JPanel contentPane;
    private Vector<SpinnerModel> spinners;
    private Choices data;


    public PriorityChooser(JPanel panel, String[] nodes, Choices redData)
    {
        data = redData;
        contentPane = panel;
        JLabel display = new JLabel();
        final JButton finishBtn = new JButton("Finish");
        JButton cancelBtn = new JButton("Back");
        display.setText("Prioritize:");

        setPreferredSize(new Dimension(395, 156));
        setLayout(null);

        //set component bounds (only needed by Absolute Positioning)
        display.setBounds(5, 1, 250, 50);

        finishBtn.setBounds(220, 100, 80, 20);
        cancelBtn.setBounds(310, 100, 80, 20);

        //add components
        genSpinners(nodes);
        add(display);
        add(finishBtn);
        add(cancelBtn);
        finishBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int[] priorities = getPriorities();
                data.setUserPriorities(priorities);
                data.setDone(true);

                Container frame = finishBtn.getParent();
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
    }

    private int[] getPriorities() {
        int[] ret = new int[spinners.size()];
        for(int i = 0 ; i < spinners.size() ; i++)
        {
            ret[i] = Integer.parseInt(spinners.elementAt(i).getValue().toString());
        }
        return ret;
    }


    private void genSpinners(String[] nodes) {
        ChangeListener listener = new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                repaint();
            }
        };
        spinners = new Vector<SpinnerModel>();
        String[] opts = {"1","2","3","4","5","6","7","8","9","10"};
        int aux=50;
        for (String node : nodes) {
            SpinnerModel model1 = new SpinnerListModel(opts);
            JSpinner spinner1 = new JSpinner(model1);
            spinners.addElement(model1);
            spinner1.addChangeListener(listener);
            JLabel label1 = new JLabel(node);
            spinner1.setBounds(5, aux, 185, 50);
            label1.setBounds(5, aux , 185, 50);
            add(label1);
            add(spinner1);
            aux += 80;
        }
    }
}
