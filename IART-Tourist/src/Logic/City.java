package Logic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;

/**
 * Created by Gonçalo Lobo on 16/04/2015.
 */

public class City {

    int nColumn;
    ArrayList[][] city;
    Graph map;
    String name;
    public City(String name){
        this.name = name;
        if(name.equals("Porto"))
            this.city = loadCity("D:\\iart - tourguide\\src\\porto.csv"); // hardcoded :|
        else
        if(name.equals("Lisboa"))
            this.city = loadCity("C:\\Users\\Gonçalo Lobo\\Desktop\\IART-Tourist\\lisboa.csv");
        else System.err.println("There's no file database for that city.");

        map = generateGraph();

    }

    private Graph generateGraph() {
        Graph graph = new MultiGraph(name);
        String nodeId;
        for(int i = 1; i < nColumn ; i++)
        {
            //create all nodes
            nodeId = getPlaceName(i);
            graph.addNode(nodeId);
            //System.out.println(nodeId);
            graph.getNode(nodeId).setAttribute("duration", getPlaceDuration(i));
        }

        for(int i = 1; i < nColumn ; i++)
        {
            for(int j = 2; j < nColumn ; j++)
            {
                if(i<j){
                    //create edges
                    String tmp = getPlaceName(i) + "-" + getPlaceName(j);
                    //System.out.println(tmp);
                    graph.addEdge(tmp, getPlaceName(i), getPlaceName(j));
                    graph.getEdge(tmp).setAttribute("weight", getPlacesDistance(i, j)); //get places distance dá sempre 0
                    graph.getEdge(tmp).addAttribute("ui.label", graph.getEdge(tmp).getAttribute("weight"));
                    System.out.println(getPlacesDistance(i,j)); //mas o array city está bem preenchido
                }
                else{
                    //percorre elementos acima da diagonal principal
                }
            }
        }

        for (Node node : graph) {
            node.addAttribute("ui.label", node.getId());
            node.addAttribute("priority",0);
        }



        return graph;
    }

    public ArrayList[][] getCity() {
        return city;
    }

    public ArrayList[][] loadCity(String csv) {

        ArrayList[][] c = new ArrayList[10][10];
        c[0][0] = new ArrayList();

        String csvFile = csv;
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {

            br = new BufferedReader(new FileReader(csvFile));

            for (int i = 0; (line = br.readLine()) != null;i++) {

                String[] info = line.split(cvsSplitBy);
                nColumn = info.length;
                for( int j = 0; j<info.length;j++ ) {
                    c[i][j] = new ArrayList();
                    c[i][j].add(info[j]);
                    //System.out.println(c[i][j]);
                }
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return c;

    }

    public String getCityName() {
        return city[0][0].toString().replace("[","").replace("]","");
    }

    public String getPlaceName(int i) {
        return city[i][0].toString().replace("[","").replace("]","");
    }

    public float getPlaceDuration(int i) {
        return Float.parseFloat(city[i][i].toString().replace("[", "").replace("]", ""));
    }

    public float getPlacesDistance(int i, int j) {
        //System.out.println(city[i][j].toString());
        return Float.parseFloat(city[i][j].toString().replace("[","").replace("]",""));
    }

    public Graph getMap() {
        return map;
    }

    public void setUserPriorities(Scanner sc) {
        listNodes();
        System.out.println("Where do you REALLY want to go?(done to complete)");
        System.out.print("->");
        String input = sc.nextLine();
        if (input ==  "done")
            return;
        else {
            map.getNode(input).setAttribute("priority",1);
            setUserPriorities(sc);
        }

    }

    private void listNodes() {
        for(Node n : map)
        {
         System.out.println(n.getId());
        }
    }
}
