import java.io.IOException;
import java.io.StringReader;

import org.graphstream.algorithm.AStar;
import org.graphstream.algorithm.AStar.DistanceCosts;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.stream.file.FileSourceDGS;

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
    static String my_graph =
            "DGS004\n"
                    + "my 0 0\n"
                    + "an A xy: 0,1\n"
                    + "an B xy: 1,2\n"
                    + "an C xy: 2,2\n"
                    + "an D xy: 1,0\n"
                    + "an E xy: 2,0\n"
                    + "an F xy: 3,1\n"
                    + "ae AB A B weight:1 \n"
                    + "ae AD A D weight:1 \n"
                    + "ae BC B C weight:1 \n"
                    + "ae CF C F weight:10 \n"
                    + "ae DE D E weight:1 \n"
                    + "ae EF E F weight:1 \n"
            ;

    public static void main(String[] args) throws IOException {
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

        Graph graph = new DefaultGraph("A Test");
        StringReader reader = new StringReader(my_graph);
/*
        graph.addNode("A" );
        graph.addNode("B" );
        graph.addNode("C" );
        graph.addEdge("AB", "A", "B");
        graph.addEdge("AD", "A", "D");
        graph.addEdge("BC", "B", "C");
        graph.addEdge("CF", "C", "F");
        graph.addEdge("DE", "D", "E");
        graph.addEdge("EF", "E", "F");


        Node A = graph.getNode("A");
        Node B = graph.getNode("B");
        Node C = graph.getNode("C");
        Node D = graph.getNode("D");
        Node E = graph.getNode("E");
        Node F = graph.getNode("F");

        A.setAttribute("xy",0,1);
        B.setAttribute("xy",1,2);
        C.setAttribute("xy",2,2);
        D.setAttribute("xy",1,0);
        E.setAttribute("xy",2,0);
        F.setAttribute("xy",3,1);
*/

        FileSourceDGS source = new FileSourceDGS();
        source.addSink(graph);
        source.readAll(reader);

        AStar astar = new AStar(graph);

        AStar.Costs c = new DistanceCosts();

       astar.setCosts(new DistanceCosts());
        astar.compute("C", "F");

        System.out.println(astar.getShortestPath());

        //Viewer viewer = graph.display();
        graph.display();
    }


}