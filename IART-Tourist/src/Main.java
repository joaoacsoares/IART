import Logic.City;
import org.graphstream.algorithm.AStar;
import org.graphstream.algorithm.AStar.DistanceCosts;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.stream.file.FileSourceDGS;

import java.io.IOException;
import java.io.StringReader;

public class Main {

    //     B-(1)-C
    //    /       \
    //  (1)       (10)
    //  /           \
    // A             F
    //  \           /
    //  (1)       (1)
    //    \       /
    //     D-(1)-E

    public static void main(String[] args) throws IOException {

        City map = new City("Porto");

        FileSourceDGS source = new FileSourceDGS();

        AStar astar = new AStar(map.getMap());

        AStar.Costs c = new GraphCosts();

        astar.setCosts(c);
        astar.compute("Clérigos", "Piolho");

        System.out.println(astar.getShortestPath());

        //Viewer viewer = graph.display();
        map.getMap().display();


    }


}