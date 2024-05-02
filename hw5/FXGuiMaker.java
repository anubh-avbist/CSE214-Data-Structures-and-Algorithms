
/**
* This class runs the main method and allows the user to write and read in a pseudo-scene builder environment.
*
* @author Anubhav Bist
*    e-mail: anubhav.bist@stonybrook.edu
*    Stony Brook ID: 115877801
*    Recitation: R04
*/
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;

public class FXGuiMaker {
    private static FXComponentTree tree;
    public static Scanner scanner = new Scanner(System.in);
    public static PrintWriter writer;

    /**
     * main method starts the program and asks for user input.
     * @param args
     */
    public static void main(String[] args){
        tree = new FXComponentTree();
        System.out.println("Welcome to counterfeit SceneBuilder.");
        System.out.println("Menu:");
        System.out.println("  L) Load from file ");
        System.out.println("  P) Print tree ");
        System.out.println("  C) Move cursor to a child node ");
        System.out.println("  R) Move cursor to root ");
        System.out.println("  A) Add a child ");
        System.out.println("  U) Cursor up (to parent) ");
        System.out.println("  E) Edit text of cursor ");
        System.out.println("  D) Delete child ");
        System.out.println("  S) Save to file ");
        System.out.println("  X) Export FXML ");
        System.out.println("  Q) Quit ");
        boolean running = true;
        while(running){
            System.out.print("Please select an option: ");
            String input = scanner.nextLine().toLowerCase();
            switch(input){
                case "l":
                    System.out.print("Please enter filename:");
                    String filename = scanner.nextLine();
                    File file = new File(filename);
                    if(!file.exists()){
                        System.out.println(filename + " not found.");
                    } else {
                        tree.readFromFile(filename);
                        tree.cursorToRoot();
                        System.out.println(filename + " loaded.");
                    }
                    break;

                case "p":
                    System.out.println(printTree(tree.getRoot(), 0));
                    break;

                case "c":
                    System.out.print("Please enter number of child (starting with 1):");
                    int childIndex = scanner.nextInt()-1;
                    try {
                        tree.cursorToChild(childIndex);
                        System.out.println("Cursor Moved to " + tree.getCursor().toString());
                    } catch (Exception e) {
                        System.out.println("Please enter a valid index!");
                    }
                    scanner.nextLine();
                    break;
                case "e":
                    if(
                      tree.getCursor().getType()==null 
                      || tree.getCursor().getType()==ComponentType.VBox 
                      || tree.getCursor().getType()==ComponentType.HBox 
                      || tree.getCursor().getType()==ComponentType.AnchorPane
                    ){
                        System.out.println("Cannot edit text");
                    } else {
                        System.out.print("Please enter new text:");
                        String newText = scanner.nextLine();
                        tree.setTextAtCursor(newText);
                        System.out.println("Text edited.");
                    }
                    break;
                case "u":
                
                    try{
                        tree.cursorToParent();
                        System.out.println("Cursor Moved to " + tree.getCursor().toString());
                    } catch (Exception e){
                        System.out.println("There is no parent to move up to!");
                    }
                    break;

                case "a":
                    System.out.print("Select component type (H - HBox, V - VBox, T - TextArea, B - Button, L - Label):");
                    String componentInitial = scanner.nextLine();
                    ComponentType component = null;
                    String text = "";
                    int index = -1;
                    for (ComponentType type : ComponentType.values()) {
                        if( (Character.toString( (type.key).charAt(0))).toLowerCase().equals(componentInitial.toLowerCase())){
                            component = type;
                        }
                    }
                    if(component == null){
                        System.out.println("Please enter a valid component input!");
                        break;
                    }
                    System.out.print("Please enter text:");
                    text = scanner.nextLine();
                    System.out.print("Please enter an index:");
                    index = scanner.nextInt()-1; // Off by one error?
                    boolean validInput = true;
                    FXTreeNode[] children = tree.getCursor().getChildren();
                    if(children[children.length-1] != null){
                        System.out.println("The cursor is full!");
                        validInput = false;
                    }
                    if(index > 0){
                        if(children[index-1] == null ){
                            System.out.println("Invalid");
                            validInput = false;
                        }
                    } else if(index < 0){
                        System.out.println("Please enter a non negative integer!");
                        validInput = false;
                    }

                    if(validInput){
                        int i = index;
                        FXTreeNode newNode = new FXTreeNode(text, component, tree.getCursor().getParent());
                        FXTreeNode temp = children[i];
                        do{
                            temp = children[i];
                            children[i] = newNode;
                            newNode = temp;
                            i++;
                        }while(i < tree.getCursor().getChildren().length-1);
                        try {
                            tree.cursorToChild(index);
                        } catch (Exception e) {
                            System.out.println("Invalid index -- programmer's fault!");
                        }
                        System.out.println("Added.");
                    }

                    scanner.nextLine();

                    break;
                case "d":
                    System.out.print("Please enter number of child (starting with 1):");
                    int deletedIndex = scanner.nextInt()-1;
                    ComponentType deletedType;
                    if(tree.getCursor().getChildren()[deletedIndex] != null){
                        deletedType = tree.getCursor().getChildren()[deletedIndex].getType();
                        tree.deleteChild(deletedIndex);
                        System.out.println(deletedType + " removed.");
                    } else {
                        System.out.println("IndexOutOfBounds");
                    }
                    break;
                case "r":
                    tree.cursorToRoot();
                    System.out.println("Cursor is at root.");
                    break;

                case "s":
                    System.out.print("Please enter a filename:");
                    String newFileName = scanner.nextLine();
                    File newFile = new File(newFileName);
                    if(newFile.exists()!=true){
                        try{
                            newFile.createNewFile();
                        } catch (Exception e){
                            System.out.println("I/O error");
                        }
                    }
                    newFile.setWritable(true);
                    try{
                        writer = new PrintWriter(newFile);
                        tree.writeToFile(newFile, tree.getRoot(), 0, "0", writer);
                        writer.close();

                        System.out.println(newFile.getName() + " saved to computer");

                    } catch (Exception e){
                        System.out.println("File not found!");
                    }

                    break;
                case "q":
                    System.out.println("Make like a tree and leave!");
                    running = false;
                    break;
                default:
                    System.out.println("Please enter a valid input!");
                    break;
            
            }

        }


    }

    /**
     * Recursively prints the tree starting from the node in pre-oder, indicating the cursor.
     * @param node
     * @param depth
     * @return
     */
    public static String printTree(FXTreeNode node, int depth){
        String output = "";
        for(int i = 0; i < depth; i++){
            output+= "  ";
        }
        String bullet = "";
        if(node==tree.getCursor()){
            bullet += "==>";
        } else {
            bullet += "+--";
        }
        output+= bullet + node.toString();
        output+="\n";
        int i = 0;
        FXTreeNode child = node.getChildren()[i];
        while(child!= null){
            output+=printTree(child, depth+1);
            i++;
            child = node.getChildren()[i];
        }
        return output;

    

    }


}
