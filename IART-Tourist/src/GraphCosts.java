import org.graphstream.algorithm.AStar;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;

/**
 * Created by Joao on 16/04/2015.
 */
public class GraphCosts implements AStar.Costs {
    @Override
    public double heuristic(Node node, Node target) {
        return 0;
    }

    @Override
    public double cost(Node parent, Edge from, Node next) {
        if (from != null && from.hasNumber("weight"))
            return ((Number) from.getNumber("weight")).doubleValue();

        return 1;
    }
}
