/**
* The Mailroom Manager class defines the main class that starts the program.
* It also defines the UI for the user.
* It creates a 5 different Package Stacks stored in an array, and an additional FloorStack with no capacity limitations
* 
*
* @author Anubhav Bist
*    e-mail: anubhav.bist@stonybrook.edu
*    Stony Brook ID: 115877801
*    Recitation: R04
*/
import java.util.InputMismatchException;
import java.util.Scanner;

public class MailroomManager {
  public static Scanner scanner = new Scanner(System.in);
  public static final int NUMSTACKS = 5;
  public static int day = 0;
  private static PackageStack<Package>[] stacks = new PackageStack[NUMSTACKS+1];
  private static PackageStack<Package> floorStack = new PackageStack<>(true);
  private static String[] stackBounds = {"(A-G)", "(H-J)", "(K-M)", "(N-R)", "(S-Z)"};


  /**
   * The main method that starts the program and displays the UI.
   * @param args
   */
  public static void main(String[] args){
    System.out.println("----------");
    for(int i = 0; i < NUMSTACKS; i++){
      stacks[i] = new PackageStack<>(false);
    }
    stacks[NUMSTACKS] = floorStack;

    System.out.println("Welcome to the Irving Mailroom Manager. You can try to make it better, " +
      "but the odds are stacked against you. It is day 0.");

    System.out.println("Menu \n" + 
      "D) Deliver a package \n" +
      "G) Get someone's package \n" +
      "T) Make it tomorrow \n" +
      "P) Print the stacks \n" +
      "M) Move a package from one stack to another \n" +
      "F) Find packages in the wrong stack and move to floor \n" +
      "L) List all packages awaiting a user \n" +
      "E) Empty the floor \n" +
      "Q) Quit \n"
    );

    boolean running = true;
    while(running){
      System.out.print("Please select an option: ");
        String input = scanner.nextLine().toLowerCase();
        switch(input){
          case "d":
            try{
              Package newPackage = createPackage();
              newPackage.setCorrectStack(getCorrectStack(newPackage.getRecipient()));
              int originalIndex = newPackage.getCorrectStack();
              int offset=0;
              int stackIndex=originalIndex+offset;
              int i = 0;
              while(stacks[stackIndex].isFull()){
                offset = (-1)^(i+1) * i/2; // Offset index to move to closest stack.
                stackIndex = (originalIndex+offset)%6;
                
                if(stackIndex<0){
                  stackIndex*=-1;
                }
                i++;
              }
              PackageStack chosenStack = stacks[stackIndex];

              Package p = (Package) chosenStack.push(newPackage);
              System.out.println("A " + ((int) p.getWeight()) + " lb package is awaiting pickup by " + p.getRecipient());
              scanner.nextLine();
              break;
            } catch (InputMismatchException e){
              System.out.println("Please enter a number!");
              scanner.nextLine();
              break;
            }

          case "g":
            System.out.print("Please enter the recipient name: ");
            String chosenName = scanner.nextLine();
            Package gottenPackage;
            try {
              gottenPackage = getPackage(chosenName);
              
              if(gottenPackage == null){
                throw new EmptyStackException();
              } else {
                System.out.println("Give " + chosenName + ((int)gottenPackage.getWeight()) + " lb package delivered on day " + gottenPackage.getArrivalDate());
              }
            } catch (EmptyStackException e) {
              System.out.println("Empty Stack therefore cannot get packages.");
            }
            break;
          case "t":
            System.out.println("It is now day " + ++day);
            break;
          case "m":
            System.out.print("Please enter the source stack (enter 0 for floor): ");
            int source = scanner.nextInt()-1;
            System.out.print("Please enter destination stack: ");
            int destination = scanner.nextInt()-1;
            if(destination == -1){
              destination = NUMSTACKS; // Sets to floor
            }
            if(source == -1){
              source = NUMSTACKS;
            }
            if((source >= NUMSTACKS && source <0) || (destination >= NUMSTACKS && destination <0)){
              System.out.println("Please enter a valid index!");
              break;
            }
            if(source != destination){
              if(!stacks[source].isEmpty()){
                stacks[destination].push(stacks[source].pop());
                System.out.println("Package moved successfully from stack " + (source+1) + " to stack " + ((1+destination)%6));
                scanner.nextLine();
              } else {
                System.out.println("Source stack is empty. Cannot move a package.");
              }
            } else { 
              System.out.print("You cannot move it into its own stack!");
            }
            break;
          case "f":
            System.out.println("Misplaced packages moved to floor.");
            for(int j = 0; j < NUMSTACKS; j++){
              PackageStack temporaryPackageStack = new PackageStack<>(true);
              int originalJPackages = stacks[j].getNumPackages();
              for(int k = 0; k < originalJPackages; k++){
                Package currentPackage = (Package) stacks[j].pop();
                if(currentPackage.getCorrectStack() != j){
                  floorStack.push(currentPackage);
                } else {
                  temporaryPackageStack.push(currentPackage);
                }
              }
              int originalTemporaryPackages = temporaryPackageStack.getNumPackages();
              for(int k = 0; k < originalTemporaryPackages; k++){
                stacks[j].push((Package) temporaryPackageStack.pop());
              }
            }
            break;
          case "l":
            System.out.print("Please enter recipient name: ");
            String name = scanner.nextLine();
            int numRecipientPackages = 0;
            int[][] recipientPackageStack = new int[7*5 + floorStack.getNumPackages()][3];
            for(int i = 0; i < NUMSTACKS; i++){
              PackageStack currentStack = stacks[i];
              PackageStack temporaryStack = new PackageStack<>(true);
              
              int currentNumberPackages = currentStack.getNumPackages();
              for(int j = 0; j < currentNumberPackages; j++){
                Package currentPackage = ((Package)currentStack.peek());
                if(currentPackage.getRecipient().toLowerCase().equals(name.toLowerCase())){
                  recipientPackageStack[numRecipientPackages][0] = (i+1)%6;
                  recipientPackageStack[numRecipientPackages][1] = currentPackage.getArrivalDate();
                  recipientPackageStack[numRecipientPackages][2] = (int) currentPackage.getWeight();
                  numRecipientPackages++;
                }
                temporaryStack.push(currentStack.pop());

              }
              for(int j = 0; j < currentNumberPackages; j++){
                currentStack.push(temporaryStack.pop());
              }
            }
            if(numRecipientPackages == 0){
              System.out.println("No packages found for " + name + ".");
            } else {
              if(numRecipientPackages == 1){
                System.out.println(name + " has " + numRecipientPackages + " package total.");
              } else {
                System.out.println(name + " has " + numRecipientPackages + " packages total.");
              }
              for(int i = 0; i < numRecipientPackages; i++){
                System.out.println("Package " + (i + 1) + " is in Stack " + (recipientPackageStack[i][0]) + ", it was delivered on day " 
                  +  (recipientPackageStack[i][1]+i) + ", and weighs " +  (recipientPackageStack[i][2]+i) + " lbs");
              }
            }


            break;
          case "e":
            System.out.println("The floor has been emptied. Mr. Trash Can is no longer hungry.");
            floorStack = new PackageStack<Package>(true);
            break;
          case "p":
            for (int j = 0; j < NUMSTACKS; j++) {
              System.out.println("Stack " + (j+1) + " " + stackBounds[j] + ": " + stacks[j].toString());
            }
            System.out.println("Floor: " + floorStack.toString());
            break;
          case "q":
            running = false;
            break;
          default:
            System.out.println("Please enter a valid input!");
            break;
        }
    }
    System.out.println("Use Amazon Locker next time.");
    System.out.println("(A-G, H-J, K-M, N-R, S-Z)");
  }
  

  /**
   * The createpackage method runs every time the user wishes to input a new package into the system.
   * @return The method returns the new package so it can be modified with the correct stack index.
   * @throws InputMismatchException In case the user does not input a valid number.
   */
  public static Package createPackage() throws InputMismatchException{
    System.out.print("Please enter the recipient name: ");
    String name = scanner.nextLine();
    System.out.print("Please enter the weight (lbs): ");
    double weight = scanner.nextDouble();
    Package newPackage = new Package(name,weight,day);
    return new Package(name, weight, day);
  }


  /**
   * Based on the first letter of the input name, it outputs which stack the name belongs to.
   * @param name
   * @returns the corresponding stack number based on the name.
   */
  public static int getCorrectStack(String name){
    name = name.toLowerCase();
      char recipientNameValue = name.charAt(0);
      // Alphabetizes the stack based on first letter of name
      if('a' <= recipientNameValue && recipientNameValue <= 'g'){ // a-g
        return 0;
      } else if('h' <= recipientNameValue && recipientNameValue <= 'j'){ // h-j
        return 1;
      } else if('k' <= recipientNameValue && recipientNameValue <= 'm'){ // k-m
        return 2;
      } else if('n' <= recipientNameValue && recipientNameValue <= 'r'){ // n-r
        return 3;
      } else if('s' <= recipientNameValue && recipientNameValue <= 'z'){ // s-z
        return 4;
      } else { // floor
        return 5;
      }
  }

  /**
   * Gets the topmost package of the chosen recipient.
   * @param name
   * @return It returns the topmost package of the chosen recipient.
   * @custom.postconditions: The desired package has been popped from the list, but the order remains unchanged..
   */
  public static Package getPackage(String name) throws EmptyStackException{
    int stackIndex = getCorrectStack(name);
    PackageStack<Package> temporaryStack = new PackageStack<Package>(true);
    PackageStack<Package> stack = stacks[stackIndex];
    Package returnPackage = null;
    int numCars = stack.getNumPackages();
    int removedPackages = 0;
    for(int i = 0; i < numCars; i++){  
      if(((Package) stack.peek()).getRecipient().toLowerCase().equals(name.toLowerCase()) && returnPackage ==null){
        returnPackage = stack.pop();
        removedPackages++;
      } else {
        temporaryStack.push(stack.pop());
      }
      
    }
    for(int i = 0; i< (numCars-removedPackages); i++){
      stack.push(temporaryStack.pop());
    }
    return returnPackage;
  }
}

