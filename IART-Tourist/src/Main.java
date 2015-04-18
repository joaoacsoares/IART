import Logic.City;
import org.graphstream.algorithm.AStar;
import org.graphstream.algorithm.AStar.DistanceCosts;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.stream.file.FileSourceDGS;

import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws IOException {

        City map = new City("Porto");

        FileSourceDGS source = new FileSourceDGS();

        AStar astar = new AStar(map.getMap());

        AStar.Costs c = new GraphCosts();

        astar.setCosts(c);
//      astar.compute("Clerigos", "Piolho");

        System.out.println("Where should it start?");
        System.out.print("->");
        Scanner sc = new Scanner(System.in);
        String startPoint = sc.nextLine();

        System.out.println("Where should it end?");
        System.out.print("->");
        String endPoint = sc.nextLine();
        System.out.print(map.getPlaceIDbyName(startPoint)+ map.getPlaceIDbyName(endPoint));
        map.setUserPriorities(sc);

        astar.compute(map.getPlaceIDbyName(startPoint), map.getPlaceIDbyName(endPoint));

        System.out.println(astar.getShortestPath());

        //Viewer viewer = graph.display();
        map.getMap().display();

    }


}