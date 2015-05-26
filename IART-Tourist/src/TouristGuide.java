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

/*      SAMPLE INPUTS
        map.addNode("A");
        map.addNode("B");
        map.addNode("C");
        map.addNode("D");
        map.addNode("E");

        map.getNode(0).addAttribute("duration",1);
        map.getNode(1).addAttribute("duration",2);
        map.getNode(2).addAttribute("duration",3);
        map.getNode(3).addAttribute("duration",2);
        map.getNode(4).addAttribute("duration",1);


        map.getNode(0).addAttribute("priority",1);
        map.getNode(1).addAttribute("priority",3);
        map.getNode(2).addAttribute("priority",5);
        map.getNode(3).addAttribute("priority",7);
        map.getNode(4).addAttribute("priority",1);


        //initialize the edges

        map.addEdge("A-B",0,1).addAttribute("distance", 10);
        map.addEdge("A-C",0,2).addAttribute("distance", 20);
        map.addEdge("A-D",0,3).addAttribute("distance", 30);
        map.addEdge("A-E",0,4).addAttribute("distance", 40);

        map.addEdge("B-C",1,2).addAttribute("distance", 50);
        map.addEdge("B-D",1,3).addAttribute("distance", 60);
        map.addEdge("B-E",1,4).addAttribute("distance", 50);

        map.addEdge("C-D",2,3).addAttribute("distance", 40);
        map.addEdge("C-E",2,4).addAttribute("distance", 30);

        map.addEdge("D-E",3,4,true).addAttribute("distance", 20);
*/



        // Load list with unvisited nodes
        for(int i = 0;i<c.getMap().getNodeCount();i++)
            open.add(c.getMap().getNode(i));



        c.getMap().display();

        if(open.isEmpty())
            System.err.print("Solution can not be found.");

        // ASTAR
        AStar.run();

        //GFX
        c.getMap().getNode(closed.get(0).getId()).getEdgeToward(closed.get(1)).addAttribute("ui.class", "Start");
        for (int i = 1; i < closed.size() - 1; i++) {
            c.getMap().getNode(closed.get(i).getId()).getEdgeToward(closed.get(i + 1)).addAttribute("ui.class", "Path");
            Thread.sleep(1000);
        }


    }


    public static double getTimeLeft() {
        return timeLeft;
    }

    public static void setTimeLeft(double timeLeft) {
        TouristGuide.timeLeft = timeLeft;
    }
}