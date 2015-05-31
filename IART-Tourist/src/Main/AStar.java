package Main;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;

import java.util.*;

/**
 * Created by Gonçalo Lobo on 22/05/2015.
 */

public class AStar {

    public static Node startNode;
    public static Node candidate;

    public static List<Node> open = new ArrayList<Node>();
    public static List<Node> closed = new ArrayList<Node>();

    public static void run() throws InterruptedException {

        open = TouristGuide.open;
        closed = TouristGuide.closed;

        startNode = open.get(0);

        int totalNodes = open.size()+1; // open nodes + end node

        if(open.isEmpty())
            System.err.print("Solution can not be found.");

        while(TouristGuide.timeLeft>0 && closed.size() != totalNodes){
            if(TouristGuide.DEBUGGER) {
                Thread.sleep(500);
                System.out.println("Time left: " + TouristGuide.timeLeft);
            }
            if(!open.isEmpty())
                if(closed.size()>2) { // end cycle when startNode is at the end of closed nodes
                    if (closed.get(closed.size() - 1).equals(startNode))
                        break;
                }
                closed.add(getMinimalCostNode());
                if (closed.get(closed.size()-1)==null){
                    closed.remove(null);
                    System.out.println("\nNo time to visit any places.");
                    break;}
        }


        System.out.println("\nSolution:      (time remaining: " + TouristGuide.timeLeft + " Hours" + ")");
        System.out.println(closed);

    }

    private static Node getMinimalCostNode() {

        if (closed.size() == 0)
            return open.get(0);

        // max min cost
        double minEdge = -1;
        double minST   = -1;

        // g is the distance from the start node to node
        double g = 0;

        // h estimates the length of the rest of the guide path
        // h = cost(MST) + cost(two shortest connections)
        double h = 0;

        Node ref = closed.get(closed.size() - 1);

        open.remove(ref);

        if(TouristGuide.DEBUGGER) {
            System.out.println("OPEN: " + open);
            System.out.println("CLOSED: " + closed);
            System.out.println("REF: " + ref);
        }

        if(!open.isEmpty()) {
            if (startNode.getEdgeToward(ref) != null)
                g = startNode.getEdgeToward(ref).getNumber("distance");
            else g = 0;

            minEdge = calcClosestEdge(ref);
            minST = calcMinimumSpanningTree(ref);

            h = minEdge + minST;

            if(minEdge+g>TouristGuide.timeLeft && closed.size() == 1)
                return null;

            if (TouristGuide.DEBUGGER)
                System.out.println("\nMinimal cost node is " + candidate.getId() + " with a cost of " + h +
                        "\n-------------------------------------------------------");

            TouristGuide.timeLeft -= candidate.getEdgeBetween(ref).getNumber("distance") / TouristGuide.SPEED + candidate.getNumber("duration");

            if (g / TouristGuide.SPEED > TouristGuide.timeLeft || open.isEmpty()) {
                if (!open.isEmpty()) {
                    if(TouristGuide.DEBUGGER)
                        System.out.println("\nNo more time to visit other nodes. Going back to the hotel.");
                    TouristGuide.timeLeft += candidate.getEdgeBetween(ref).getNumber("distance") / TouristGuide.SPEED + candidate.getNumber("duration");
                }
                TouristGuide.timeLeft -= g / TouristGuide.SPEED;
                return startNode;
            }
            return candidate;
        }
        else {
            if(TouristGuide.DEBUGGER)
                System.out.println("\nAll nodes were visited. Going back to the hotel.");
            g = startNode.getEdgeToward(ref).getNumber("distance");
            TouristGuide.timeLeft -= g / TouristGuide.SPEED;
            return startNode;
        }
    }

    private static double calcClosestEdge(Node ref) {

        double minEdge = -1;

        Iterator<Edge> edges = ref.getEdgeIterator();

        while (edges.hasNext()) {

            Edge e = edges.next();

            if(closed.contains(e.getOpposite(ref)))
                continue;

            if(minEdge > calcTravelCost(e,ref) || minEdge < 0)
            {
                minEdge = calcTravelCost(e,ref);
                candidate = e.getOpposite(ref);
            }
            if(TouristGuide.DEBUGGER)
                System.out.println("Min Distance from " + ref.getId() + " to " + e.getOpposite(ref).getId() + " with a cost of " +  calcTravelCost(e,ref));
        }

        return minEdge;
    }

    private static double calcMinimumSpanningTree(Node ref) {
        double minST = 0;

        TreeMap<String, Double> pathCostMap = new TreeMap<String, Double>();
        Iterator<Edge> edges = ref.getEdgeIterator();

        while (edges.hasNext()) {
            Edge e = edges.next();

            if(closed.contains(e.getOpposite(ref)))
                continue;

            pathCostMap.put(e.getId(),calcTravelCost(e,ref));
        }

        List paths = new ArrayList(pathCostMap.keySet());
        Collections.sort(paths);

        int time = 0;
        int index = 0;

        while(time < TouristGuide.timeLeft && index < paths.size()) {
            // estimated mst cost
            minST += pathCostMap.get(paths.get(index));
            index++;
        }

        if(TouristGuide.DEBUGGER)
            System.out.println("Min ST from " + ref.getId() + " is " +  minST);

        return minST;
    }

    private static double calcTravelCost(Edge e, Node ref) {
        return (e.getNumber("distance")+e.getOpposite(ref).getNumber("duration"))*e.getOpposite(ref).getNumber("priority");
    }

}

