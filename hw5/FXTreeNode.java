/**
* Tree Node class defines each element in the abstract tree data type.
* Each node has a parent and an array of children, with a max number of children.
* 
*
* @author Anubhav Bist
*    e-mail: anubhav.bist@stonybrook.edu
*    Stony Brook ID: 115877801
*    Recitation: R04
*/
public class FXTreeNode {
    private String text;
    private ComponentType type;
    private FXTreeNode[] children;  
    private FXTreeNode parent;
    private final int maxChildren = 10;

    /**
     * Default constructor method initializes the node with null values.
     */
    public FXTreeNode(){
        this.text = "";
        this.type = null;
        this.parent = null;
        children = new FXTreeNode[maxChildren];
    }

    /**
     * Constructor that specifies text, type and parent node.
     * @param text
     * @param type
     * @param parent
     */
    public FXTreeNode(String text, ComponentType type, FXTreeNode parent){
        this.text = text;
        this.type = type;
        children = new FXTreeNode[maxChildren];
        this.parent = parent;
    }

    // Standard getter and setter methods.
    public String getText() {
        return text;
    }

    public ComponentType getType() {
        return type;
    }

    public FXTreeNode[] getChildren() {
        return children;
    }

    public FXTreeNode getParent() {
        return parent;
    }

    public int getMaxChildren() {
        return maxChildren;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setType(ComponentType type) {
        this.type = type;
    }

    public void setChildren(FXTreeNode[] children) {
        this.children = children;
    }

    public void setParent(FXTreeNode parent) {
        this.parent = parent;
    }

    public String toString(){
        String output = ""+ type;
        if(!this.text.equals("")){
            output += ": " + this.text;
        }
        return output;
        
    }

}
