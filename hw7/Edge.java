/**
 * Defines Edge class corresponding to a road in the graph between cities.
 * Since edges are directionless, edges simply are defined by the two nodes they connect and the cost.
 *
 * @author Anubhav Bist
 *    e-mail: anubhav.bist@stonybrook.edu
 *    Stony Brook ID: 115877801
 *    Recitation: R04
*/

package hw7;
public class Edge {
    private Node A;
    private Node B;
    private int cost;


    /**
     * Default constructor method initializes the two nodes of the edge to empty nodes, with 0 cost.
     */
    public Edge(){
        A = new Node();
        B = new Node();
        cost = 0;
    }
    
    /**
     * Constructor specifying the two nodes the node connects and a specific cost.
     * @param A
     * @param B
     * @param cost
     */
    public Edge(Node A, Node B, int cost){
        this.A = A;
        this.B = B;
        this.cost = cost;
    }

    /**
     * toString method returns the roads that the edge connects and their cost in a printable format.
     */
    public String toString(){
        return this.A + " to " + this.B + " " + this.cost;
    }

    /**
     * This method compares the edge to another object by its cost. 
     * @preconditions: otherEdge should be an instanceof Edge class. -2 output indicates type error.
     * @param otherEdge 
     * @returns 0 if the costs are equal, -1 if the cost of the other edge is greater, and 1 otherwise.
     */
    public int compareTo(Object otherEdge){
        if(!(otherEdge instanceof Edge)){
            return -2;
        } else {
            if(this.cost==((Edge) otherEdge).cost){
                return 0;
            }
            if(this.cost<((Edge) otherEdge).cost){
                return -1;
            } else {
                return 1;
            }
        }
    }

    /**
     * Standard getter and setter methods for the two nodes and the cost of the edge.
     * @return
     */
    public Node getA() {
        return A;
    }
    public Node getB() {
        return B;
    }
    public int getCost() {
        return cost;
    }
    public void setA(Node a) {
        A = a;
    }
    public void setB(Node b) {
        B = b;
    }
    public void setCost(int cost) {
        this.cost = cost;
    }
}
