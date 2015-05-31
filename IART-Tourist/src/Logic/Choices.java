package Logic;

import org.graphstream.graph.Node;

/**
 * Created by Joao on 30/05/2015.
 */
public class Choices {
    private City city;
    private String[] chosenNodes;
    private int[] userPriorities;
    private boolean done;
    private Node hotel;

    public Choices(City c, String[] nodes, int[] prios, Node hote)
    {
        city = c;
        chosenNodes = nodes;
        userPriorities = prios;
        done = false;
        hotel = hote;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String[] getChosenNodes() {
        return chosenNodes;
    }

    public void setChosenNodes(String[] chosenNodes) {
        this.chosenNodes = chosenNodes;
    }

    public int[] getUserPriorities() {
        return userPriorities;
    }

    public void setUserPriorities(int[] userPriorities) {
        this.userPriorities = userPriorities;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public Node getHotel() {
        return hotel;
    }

    public void setHotel(Node hotel) {
        this.hotel = hotel;
    }
}
