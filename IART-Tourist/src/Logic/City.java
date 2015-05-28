package Logic;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.spriteManager.SpriteManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by Gonçalo Lobo on 16/04/2015.
 */

public class City {

    int nColumn;
    ArrayList[][] city;
    Graph map;
    String name;

    String DIR_PATH = System.getProperty("user.dir");

    public City(String name) {
        this.name = name;
        if (name.equals("Porto"))
            this.city = loadCity(DIR_PATH + "\\src\\CSVs\\porto.csv");
        else if (name.equals("Lisboa"))
            this.city = loadCity(DIR_PATH + "\\src\\CSVs\\lisboa.csv");
        else System.err.println("There's no file database for that city.");

        map = generateGraph();

    }


    private Graph generateGraph() {
        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
        Graph graph = new MultiGraph(name);
        SpriteManager sman = new SpriteManager(graph);

        graph.addAttribute("ui.quality");
        graph.addAttribute("ui.antialias");

        String nodeId;
        for (int i = 1; i < nColumn; i++) {
            //create all nodes
            nodeId = getPlaceID(i);
            graph.addNode(nodeId);
            //System.out.println(nodeId);
            graph.getNode(nodeId).setAttribute("duration", getPlaceDuration(i));
            graph.getNode(nodeId).addAttribute("ui.label", getPlaceName(i));
            graph.getNode(nodeId).addAttribute("priority", getPlacePriority(i)); // default
            graph.getNode(nodeId).addAttribute("ui.duration", getPlaceDuration(i));
        }

        for (int i = 1; i < nColumn; i++) {
            for (int j = 2; j < nColumn; j++) {
                if (i < j) {
                    //create edges
                    String tmp = getPlaceID(i) + "-" + getPlaceID(j);
                    //System.out.println(tmp);
                    graph.addEdge(tmp, getPlaceID(i), getPlaceID(j));
                    graph.getEdge(tmp).setAttribute("distance", getPlacesDistance(i, j));
                    graph.getEdge(tmp).addAttribute("ui.label", graph.getEdge(tmp).getAttribute("distance"));
                    //System.out.println(getPlacesDistance(i, j));
                } else {
                    //percorre elementos acima da diagonal principal
                }
            }
        }

        graph.addAttribute("ui.stylesheet", loadStyle());

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

            for (int i = 0; (line = br.readLine()) != null; i++) {

                String[] info = line.split(cvsSplitBy);
                nColumn = info.length;
                for (int j = 0; j < info.length; j++) {
                    c[i][j] = new ArrayList();
                    c[i][j].add(info[j]);
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

    private String loadStyle() {

        String cssFile;

        if (name.equals("Porto"))
            cssFile = DIR_PATH + "\\src\\Stylesheets\\porto.css";
        else if (name.equals("Lisboa"))
            cssFile = DIR_PATH + "\\src\\Stylesheets\\lisboa.css";
        else {
            cssFile = "error";
            System.err.println("There are no stylesheet available for that city.");
        }


        BufferedReader br = null;
        String line = "";
        StringBuilder sb = new StringBuilder();

        try {

            br = new BufferedReader(new FileReader(cssFile));

            while ((line = br.readLine()) != null) {
                sb.append(line.replace("path", DIR_PATH));
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

        return sb.toString();

    }

    public String getCityName() {
        return city[0][0].toString().replace("[", "").replace("]", "");
    }

    public String getPlaceName(int i) {
        return city[0][i].toString().replace("[", "").replace("]", "");
    }

    public String getPlaceID(int i) {
        return city[i][0].toString().replace("[", "").replace("]", "");
    }

    public String getPlaceIDbyName(String name) {
        String placeName = "";
        int i = 0;

        while (!name.equals(placeName)){
            i++;
            placeName = getPlaceName(i);
        }

        return getPlaceID(i);

    }

    public float getPlaceDuration(int i) {
        return Float.parseFloat(city[i][i].toString().replace("[", "").replace("]", "").split("|")[0]);
    }

    public float getPlacePriority(int i) {
        return Float.parseFloat(city[i][i].toString().replace("[", "").replace("]", "").split("|")[0]);
    }

    public float getPlacesDistance(int i, int j) {
        return Float.parseFloat(city[i][j].toString().replace("[", "").replace("]", ""));
    }

    public Graph getMap() {
        return map;
    }

    public void setUserPriorities(Scanner sc) {
        listNodes();
        System.out.println("Where do you REALLY want to go?(done to complete)");
        System.out.print("->");
        String input = sc.nextLine();
        if (input.equals("done"))
        { return;}
           else{
        if (!input.equals("done")) {
                map.getNode(getPlaceIDbyName(input)).setAttribute("priority", 1);
                setUserPriorities(sc);
            }
        }

    }



    private void listNodes() {
        for(int i = 1; i<nColumn;i++)
            System.out.println(getPlaceName(i));
    }
}