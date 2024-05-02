/**
 * The RoadCalculator class takes in an xml file and allows the user to traverse an undirected weighted graph represnting the cities and roads of an area.
 * The user inputs a path, local or online, to an xml file listing the nodes and edges of the graph.
 * The user is able to input city names to find the shortest path between cities.
 * 
 * @author Anubhav Bist
 *    e-mail: anubhav.bist@stonybrook.edu
 *    Stony Brook ID: 115877801
 *    Recitation: R04
*/

package hw7;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;

import big.data.DataSource;



public class RoadCalculator{
    private static HashMap<String, Node> graph;
    private static LinkedList<Edge> mst;
    private static Scanner scanner = new Scanner(System.in);
    private static LinkedList<Edge> allRoads = new LinkedList<>();


    /**
     * The main method of the class begins the program by asking for the user to input the xml file indicating the city-roads data.
     * Allows the user to traverse the graph to find the shortest paths.
     * Outputs the list of all cities and roads, as well as the minimum spanning tree.
     * @param args
     */
    public static void main(String[] args){
        System.out.println("Please enter graph URL");
        String URL = scanner.nextLine();
        System.out.println("Loading Map...\n");
        graph = buildGraph(URL);
        mst = buildMST(graph);

        // Print Cities
        System.out.println("Cities: \n");
        ArrayList<String> alphabeticallySorted = new ArrayList<>();
        for(String s: graph.keySet()){
            alphabeticallySorted.add(s);
        }
        Collections.sort(alphabeticallySorted);
        for(String city: alphabeticallySorted){
            System.out.println(city);
        }

        // Print Roads
        System.out.println("\nRoads:\n");
        printAlphabetically(allRoads);

        // Print Minimum Spanning Tree
        System.out.println("\nMinimum Spanning Tree:\n");
        printAlphabetically(mst);




        // Run User Input
        boolean running = true;
        while(running){
            System.out.println("\nEnter a starting point for shortest path or Q to quit:");
            String input = scanner.nextLine();
            if(input.toLowerCase().equals("q")){
                running = false;
                break;
            }

            System.out.println("Enter a destination:");
            String destination = scanner.nextLine();



            if(graph.containsKey(input) && graph.containsKey(destination)){
                Djikstra(graph, input, destination);
            } else {
                System.out.println("Cannot find the given city.");
            }
    

        }

        System.out.println("Goodbye; PSA, there's a cop on the right in 3 miles!");



    }


    /**
     * This method returns a HashMap that contains all the nodes required in the graph.
     * Each node itself contains the information of all the edges/roads connecting nodes.
     * @param location: gives the location of the xml file containing the node/road information.
     * @returns a HashMap with all nodes in the graph, with keys corresponding to the citynames.
     */
    public static HashMap<String, Node> buildGraph(String location){
        DataSource ds = DataSource.connectXML(location);
        ds.load();
        String cityNamesStr=ds.fetchString("cities");
        String[] cityNames=cityNamesStr.substring(1,cityNamesStr.length()-1).replace("\"","").split(",");
        String roadNamesStr=ds.fetchString("roads");
        String[] roadNames=roadNamesStr.substring(1,roadNamesStr.length()-1).split("\",\"");
        HashMap<String, Node> g = new HashMap<>();

        for(String cityName: cityNames){
            Node city = new Node();
            city.setName(cityName);
            g.put(cityName, city);
        }

        
        
        for(String roadName: roadNames){
            Edge road = new Edge();
            if(roadName.charAt(0) == '\"'){
                roadName = roadName.substring(1);
            }
            if(roadName.charAt(roadName.length()-1) == '\"'){
                roadName = roadName.substring(0, roadName.length()-1);
            }
            String[] roadData = roadName.split(",");
            road.setA(g.get(roadData[0]));
            road.setB(g.get(roadData[1]));
            road.setCost(Integer.parseInt(roadData[2]));
            road.getA().getEdges().add(road);
            road.getB().getEdges().add(road);
            allRoads.add(road);
        }
        return g;
    }

    /**
     * This method uses Prim's algorithm to build a minimum spanning tree. 
     * @preconditions: We assume that the graph is entirely connected.
     * @param graph: Hashset containing all nodes that need to be connected in the minimum spanning tree.
     * @returns a linked list of edges that are contained in the minimum spanning tree. The number of edges is 1 less than the number of nodes in the graph.
     */
    public static LinkedList<Edge> buildMST(HashMap<String, Node> graph){
        LinkedList<Edge> edgeList = new LinkedList<>();
        LinkedList<Node> visitedNodes = new LinkedList<>();
        LinkedList<Node> unvisitedNodes = new LinkedList<>();
        for(String name: graph.keySet()){
            unvisitedNodes.add(graph.get(name));
        }
        visitedNodes.add(unvisitedNodes.remove(unvisitedNodes.size()-1));
        visitedNodes.get(0).setVisited(true);
        while(!unvisitedNodes.isEmpty()){
            Edge shortestEdge = new Edge(null, null, Integer.MAX_VALUE);
            Node nextNode = null;
            for(int i = 0; i < visitedNodes.size(); i++){
                Node currentNode = visitedNodes.get(i);
                for(Edge e: currentNode.getEdges()){
                    Node destination = e.getA();
                    if(e.getA().equals(currentNode)){
                        destination = e.getB();
                    }
                    if(e.getCost()<=shortestEdge.getCost() && !destination.getVisited()){
                        nextNode = destination;
                        shortestEdge=e;
                    }
                }
            }
            nextNode.setVisited(true);
            unvisitedNodes.remove(nextNode);
            edgeList.add(shortestEdge);
            visitedNodes.add(nextNode);

        }
        return edgeList;
    }


    /**
     * Returns the shortest path from a source to a destination on a graph using Djikstra's algorithm
     * @preconditions: We assume the graph is connected and that there is a path from the source to the destination. The destination and source must also be valid nodes in the graph.
     * @param graph
     * @param source
     * @param dest
     * @postconditions: Prints the distance from the source to the distance, as well as the path that results in that distance.
     */
    public static void Djikstra(HashMap<String, Node> graph, String source, String dest){
        Node currentNode = graph.get(source);
        Node destinationNode = graph.get(dest);

        ArrayList<Node> unvisitedNodes = new ArrayList<>();
        for(String key: graph.keySet()){
            graph.get(key).setDistance(Integer.MAX_VALUE);
            graph.get(key).setVisited(false);
            graph.get(key).setPath(new LinkedList<>());
            unvisitedNodes.add(graph.get(key));
        }
        currentNode.setDistance(0);
        currentNode.getPath().add(currentNode.getName());

        while(!destinationNode.getVisited()){
            for(Edge edge:currentNode.getEdges()){
                Node endPoint = edge.getA();
                if(endPoint.getName().equals(currentNode.getName())){
                    endPoint=edge.getB();
                }

                if(endPoint.getDistance()>currentNode.getDistance()+edge.getCost()){
                    endPoint.setDistance(currentNode.getDistance()+edge.getCost());
                    endPoint.setPath(new LinkedList<>());
                    endPoint.getPath().addAll(currentNode.getPath());
                    endPoint.getPath().add(endPoint.getName());
                }
            }
            currentNode.setVisited(true);
            unvisitedNodes.remove(currentNode);

            if(unvisitedNodes.size()==0){
                break;
            }
            currentNode=unvisitedNodes.get(0);
            for(Node n: unvisitedNodes){
                if(n.getDistance()<currentNode.getDistance()){
                    currentNode=n;
                }
            }
        }

        System.out.println("\nDistance: " + destinationNode.getDistance());
        System.out.println("Path:");  
        for(int i = 0; i < destinationNode.getPath().size()-1; i++){
            System.out.print(destinationNode.getPath().get(i) + ",");
        }
        System.out.println(dest);

    }

    
    /**
     * Prints a list of edges in alphabetical order
     * @param list
     */
    public static void printAlphabetically(LinkedList<Edge> list){
        
        ArrayList<String> alphabeticallySorted = new ArrayList<>();
        for(Edge road:list){
            alphabeticallySorted.add(road.toString());
        }
        Collections.sort(alphabeticallySorted);
        for(String road:alphabeticallySorted){
            System.out.println(road);
        }
    }
}