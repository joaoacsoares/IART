import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Gonçalo Lobo on 22/05/2015.
 */

public class AStar {

    public static Node startNode;

    public static List<Node> open = new ArrayList<Node>();
    public static List<Node> closed = new ArrayList<Node>();

    public static void run() throws InterruptedException {

        open = TouristGuide.open;
        closed = TouristGuide.closed;

        startNode = open.get(0);

        int totalNodes = open.size();

        if(open.isEmpty())
            System.err.print("Solution can not be found.");

        while(TouristGuide.timeLeft>0 && closed.size() != totalNodes){
            if(TouristGuide.DEBUGGER) {
                Thread.sleep(4000);
                System.out.println("Time left: " + TouristGuide.timeLeft);
            }
            closed.add(getMinimalCostNode());
        }

        if(TouristGuide.DEBUGGER) {
            System.out.println("\nSolution:      (time remaining: " + TouristGuide.timeLeft + ")");
            System.out.println(closed);
        }

    }

    private static Node getMinimalCostNode() {

        if (closed.size() == 0)
            return open.get(0);

        // max min cost
        double minEdge = -1;

        // g is the distance from the start node to node
        double g = 0;

        // h estimates the length of the rest of the guide path
        // h = cost(MST) + cost(two shortest connections)
        double h = 0;

        Node ref = closed.get(closed.size() - 1);

        open.remove(ref);

        Node candidate = ref;

        Iterator<Node> nodes = open.listIterator();

        if(TouristGuide.DEBUGGER) {
            System.out.println("OPEN: " + open);
            System.out.println("CLOSED: " + closed);
            System.out.println("REF: " + ref);
        }

        if(startNode.getEdgeToward(ref) != null)
            g = startNode.getEdgeToward(ref).getNumber("distance");
        else g = 0;

        Iterator<Edge> edges = ref.getEdgeIterator();

        while (edges.hasNext()) {

            Edge e = edges.next();

            if(closed.contains(e.getOpposite(ref)))
                continue;

            if(minEdge > calcTravelCost(e) || minEdge < 0)
            {
                minEdge = calcTravelCost(e);
                candidate = e.getOpposite(ref);
            }
            if(TouristGuide.DEBUGGER)
                System.out.println("Min Distance from " + ref.getId() + " to " + e.getOpposite(ref).getId() + " with a cost of " +  calcTravelCost(e));
        }

        h = minEdge;

        TouristGuide.timeLeft -= minEdge/TouristGuide.SPEED +candidate.getNumber("duration");

        if(g/TouristGuide.SPEED > TouristGuide.timeLeft || open.isEmpty())
            return startNode;

        if(TouristGuide.DEBUGGER)
            System.out.println("\nMinimal cost node is " + candidate.getId() + " with a cost of " + h +
                    "\n-------------------------------------------------------");

        return candidate;
    }

    private static double calcTravelCost(Edge e) {
        return (e.getNumber("distance")+e.getTargetNode().getNumber("duration"))*e.getTargetNode().getNumber("priority");
    }

}

