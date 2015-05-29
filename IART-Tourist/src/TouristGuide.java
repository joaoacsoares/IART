import Logic.City;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;

import java.io.IOException;
import java.util.*;

public class TouristGuide {

    public static boolean DEBUGGER = true;

    public static double SPEED = 4;     // km/h
    public static double timeLeft = 10;

    public static List<Node> open = new ArrayList<Node>();
    public static List<Node> closed = new ArrayList<Node>();

    public static void main(String[] args) throws IOException, InterruptedException {

        City c = new City("Porto");

        // Load list with unvisited nodes
        for(int i = 0;i<c.getMap().getNodeCount();i++)
            open.add(c.getMap().getNode(i));

        c.getMap().display();

        if(open.isEmpty())
            System.err.print("Solution can not be found.");

        // ASTAR
        AStar.run();

        //GFX
        if(closed.size()>2) {
            Thread.sleep(1500);
            c.getMap().getNode(closed.get(0).getId()).getEdgeToward(closed.get(1)).addAttribute("ui.class", "Start");
            for (int i = 1; i < closed.size() - 1; i++) {
                Thread.sleep(1500);
                c.getMap().getNode(closed.get(i).getId()).getEdgeToward(closed.get(i + 1)).addAttribute("ui.class", "Path");
            }
        }
        else System.err.print("Solution can not be found.");

    }


    public static double getTimeLeft() {
        return timeLeft;
    }

    public static void setTimeLeft(double timeLeft) {
        TouristGuide.timeLeft = timeLeft;
    }
}