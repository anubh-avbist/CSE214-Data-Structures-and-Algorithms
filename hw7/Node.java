/**
 * Defines Node class corresponding to a city in the graph.
 * Each node comes with a name and its corresponding edges which remain constant.
 * Each node also contains a visited, path, and distance variable, which are all relative to another node when used in an algorithm.
 *
 * @author Anubhav Bist
 *    e-mail: anubhav.bist@stonybrook.edu
 *    Stony Brook ID: 115877801
 *    Recitation: R04
*/

package hw7;
import java.util.HashSet;
import java.util.LinkedList;

public class Node {
    
    private String name;
    private boolean visited;
    private LinkedList<String> path;
    private int distance;
    private HashSet<Edge> edges;


    /**
     * Default constructor intializes a node object to default values.
     */
    public Node(){
        visited = false;
        edges = new HashSet<>();
        path = new LinkedList<>();
        distance = 0;
        
    }

    /**
     * Constructor that initializes node to specific name, with a set of edges. Rest of the values are null or default.
     * @param name
     * @param edges
     */
    public Node(String name, HashSet<Edge> edges){
        this.edges = edges;
        this.name = name;
        visited = false;
        distance = 0;
        
    }


    /**
     * Getter method for visited vairable
     * @returns whether the node has been visited.
     */
    public boolean getVisited(){
        return visited;
    }

    /**
     * Getter method for distance
     * @returns the tentative distance to a specific node
     */
    public int getDistance() {
        return distance;
    }

    /**
     * Getter method for the edges of the node
     * @returns the set of all edges connecting to this node.
     */
    public HashSet<Edge> getEdges() {
        return edges;
    }

    /**
     * Getter method for the name variable
     * @returns the name of the city corresponding to the node.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter method returns the path needed from a certain node to achieve the current distance.
     * Initialized to empty linked list if distance is infinite.
     * @return
     */
    public LinkedList<String> getPath() {
        return path;
    }

    // Standard setter methods for all variables.
    public void setVisited(boolean visited){
        this.visited = visited;
    }
    public void setDistance(int distance) {
        this.distance = distance;
    }
    public void setEdges(HashSet<Edge> edges) {
        this.edges = edges;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPath(LinkedList<String> path) {
        this.path = path;
    }
    public String toString(){
        return this.name;
    }
}
