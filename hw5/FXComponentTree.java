
/**
* The tree class is composed of FXTreeNode objects.
* It defines the abstract structure of a tree with just the root node, and a cursor that allows traversal of the tree.
*
* @author Anubhav Bist
*    e-mail: anubhav.bist@stonybrook.edu
*    Stony Brook ID: 115877801
*    Recitation: R04
*/

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class FXComponentTree {
    private FXTreeNode root;
    private FXTreeNode cursor;
    private int numNodes = 0;

    /**
     * Default constructor initializes the root and cursor to null values.
     */
    public FXComponentTree(){
        root = new FXTreeNode();
        cursor = root;
    }


    /**
     * Sets the cursor to the root as needed.
     */
    public void cursorToRoot(){
        cursor = root;
    }

    /**
     * Deletes a child at a specific index, along with all of its children.
     * @param index
     * @custom.preconditions: The index must be within the number of children of the cursor.
     * @custom.postconditions: The number of nodes is decremented, and the node is removed from the tree along with its children.
     */
    public void deleteChild(int index){
        while(index<cursor.getMaxChildren()){
            if(index + 1 < cursor.getMaxChildren()){

                cursor.getChildren()[index]=cursor.getChildren()[index+1];
            } else {
                cursor.getChildren()[index]=null;
            }
            numNodes--;
            index++;
        }
    }

    /**
     * Adds a new child to the specified index of the cursor.
     * 
     * @param index
     * @param node
     * @custom.postconditions: All respective children are shifted one space if they come after the specified index.
     */
    public void addChild(int index, FXTreeNode node){
        FXTreeNode[] children = cursor.getChildren();
        if(children[cursor.getMaxChildren()-1]==null){ // Checks if max nodes reached. // Check if hole!!! not done YET
            FXTreeNode temp;
            while(index<cursor.getMaxChildren()){
                temp = children[index];
                children[index]=node;
                index++;
                node = temp;
            }
        }   
    }

    /**
     * Sets text of cursor node.
     * @param text
     */
    public void setTextAtCursor(String text){
        cursor.setText(text);
    }

    /**
     * Moves cursor to child at specified index.
     * @param index
     * @custom.preconditions: there must be an initialized child at the index.
     * @throws Exception if the precondition is violated
     */
    public void cursorToChild(int index) throws Exception{ // If child is valid
        if(cursor.getChildren()[index] == null){
            throw new Exception();
        }
        cursor = cursor.getChildren()[index];
    }

    /**
     * Moves cursor to parent
     * @custom.preconditions: The cursor cannot be at the root.
     * @throws Exception
     */
    public void cursorToParent() throws Exception{ // If not root.
        if(cursor.equals(root)){
            throw new Exception();
        } else {
            cursor = cursor.getParent();
        }
    }

    /**
     * Creates a full FXComponentTree object with various FXTreeNode objects initialized as indicated by the specified file.
     * @param filename
     * @custom.preconditions: the file must exist.
     * @returns the Tree object with properly initialized objects.
     */
    public FXComponentTree readFromFile(String filename){
        FXComponentTree fileTree = new FXComponentTree();
        File file = new File(filename);
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                
                String[] parts = line.split(" ");
                String index = parts[0];
                String[] indexChars = index.split("-");
                int[] indices = new int[indexChars.length];
                for(int i = 0; i < indexChars.length; i++){
                    indices[i] = ((int) indexChars[i].charAt(0))-48;
                }

                ComponentType component = ComponentType.valueOf(parts[1]);
                String name = "";
                for(int i = 2; i < parts.length; i++){
                    name+=parts[i] + " ";
                }
                name=name.trim();

                cursorToRoot();
                if(numNodes==0){
                    setRoot(new FXTreeNode(name, component, null)); 
                    numNodes++;
                    cursor = root;
                } else {
                    for(int i = 1; i < indices.length-1; i++){
                        cursorToChild(indices[i]);
                    }
                    addChild(indices[indices.length-1], new FXTreeNode(name, component, cursor));
                    numNodes++;
                }
            }
        } 
        catch (Exception fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }

        return fileTree;
    }


    /**
     * Writes the specified tree, based on a node, to the specified file, using recursion.
     * Writes the tree in pre-order, where the root is written first and then all children in order.
     * @param file
     * @param node
     * @param depth
     * @param indices
     * @param writer
     * @return
     * @throws Exception if file cannot be created.
     */
    public String writeToFile(File file, FXTreeNode node, int depth, String indices, PrintWriter writer) throws Exception{
        String output = "";
        writer.println(indices + " " + node.getType() + " " + node.getText());
        int i = 0;

        FXTreeNode child = node.getChildren()[i];
        while(child!= null){
            writeToFile(file, child, depth+1, indices + "-" + i, writer);
            i++;
            child = node.getChildren()[i];
        }
        return output;
    }

    // Standard getters and setters

    public String toString(){
        String output = "";
        output += root.getType() + " | \n";
        return output;
    }

    public FXTreeNode getRoot() {
        return root;
    }

    public void setRoot(FXTreeNode root) {
        this.root = root;
    }

    public FXTreeNode getCursor() {
        return cursor;
    }
    public void setCursor(FXTreeNode cursor) {
        this.cursor = cursor;
    }

    public int getNumNodes() {
        return numNodes;
    }

    public void setNumNodes(int numNodes) {
        this.numNodes = numNodes;
    }
}
