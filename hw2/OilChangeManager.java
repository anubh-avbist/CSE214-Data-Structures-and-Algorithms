/**
* The OilChangeManager defines the main class that starts the program.
* It also defines the UI for the user.
* It creates 3 CarLists, two of which can be manipulated from user input.
*
* @author Anubhav Bist
*    e-mail: anubhav.bist@stonybrook.edu
*    Stony Brook ID: 115877801
*    Recitation: R04
*/

import java.util.Scanner;


public class OilChangeManager{
  public static Scanner scanner = new Scanner(System.in);
  private static CarList joeList;
  private static CarList donnyList;
  private static CarList finishedList;
  private static Car cutCar;


  public static void main(String[] args){
    
    joeList = new CarList("Joe");
    cutCar = null;
    donnyList = new CarList("Donny");
    finishedList = new CarList("Finished");
    System.out.println("Welcome to Tony's Discount Oil Change Computer" +
      " Management System! It's way better than a rolodex, cork board, post-its, and pre-chewed bubblegum!"
    );
    System.out.println("Menu:");
    System.out.println("L) Edit Job Lists for Joe and Donny\n" +
      "M) Merge Job Lists\n" +
      "P) Print Job Lists\n"+
      "F) Paste car to end of finished car list\n"+
      "S) Sort Job Lists //Extra Credit, you don't have to do this if you don't want to\n"+
      "Q) Quit."
    );
    boolean running = true;
    while(running){
      try{
          System.out.print("Please select an option: ");
          String input = scanner.nextLine().toLowerCase();
          switch(input){
            case "s":
              try{
                sort(0);
              } catch (EndOfListException e){
                System.out.println(e.getMessage());
              }
              try{
                sort(1);
              } catch (EndOfListException e){
                System.out.println(e.getMessage());
              }
              System.out.println("The lists have been sorted!");
              break;
            case "f":
              if(cutCar == null){
                throw new EndOfListException("Nothing to paste");
              } else {
                finishedList.insertBeforeCursor(cutCar);
                cutCar = null;
                System.out.println("Cursor pasted in finished list");
              }
              break;
            case "m":
              System.out.print("Please select a destination list - Joe (J) or Donny (D): ");
              CarList chosenList = chooseList();
              Car chosenCursorCar = chosenList.getCursorCar();
              merge(chosenList);
              chosenList.resetCursorToHead();
              while(chosenList.getCursor()!= null && !chosenList.getCursorCar().toString().equals(chosenCursorCar.toString())){
                chosenList.cursorForward();
              }
              break;
            case "p":
              System.out.printf("\nJoe\'s List: \n" + 
                String.format("%-12s %-12s %n", "Make", "Owner") +
                "----------------------\n" +joeList.toString() + 
                "Donny\'s List: \n" + 
                "Make        Owner\n" +
                "----------------------\n"+ donnyList.toString() +
                "Finished List: \n" + 
                "Make        Owner\n" +
                "----------------------\n" + finishedList.toString() 
              );
              break;        
            case "q":
              System.out.println("Enjoy your retirement!");
              running = false;
              break;
            case "l":
              listOptions();
              break;

            default:
              System.out.println("Please enter a valid option from the menu!");
              break;
          }
        } catch (EndOfListException e){
          System.out.println(e.getMessage());
        }
    }
  }

  /**
   * Method that lets the user decide a specific list to edit.
   * @return Returns the chosen list.
   * @throws EndOfListException
   */
  public static CarList chooseList() throws EndOfListException{
    String input = scanner.nextLine().toLowerCase();
    CarList chosenList;
    if(input.equals("j")){
      chosenList = joeList;
    } else if(input.equals("d")){
      chosenList = donnyList;
    } else {
      // HANDLE
      throw new EndOfListException("Invalid choice. Try again");
    }
    return chosenList;
  }

  /**
   * If the user decides to edit a list, gives the user options for editing the list.
   * @throws EndOfListException
   */
  public static void listOptions() throws EndOfListException{  
    System.out.print("Please select a list - Joe (J) or Donny (D): ");
    CarList chosenList = chooseList();
    if(chosenList != null){
      System.out.println("Options: ");
      System.out.println("A) Add a car to the end of the list\n"+
        "F) Cursor Forward\n"+
        "H) Cursor to Head\n"+
        "T) Cursor to Tail\n"+
        "B) Cursor Backward\n"+
        "I) Insert car before cursor\n"+
        "X) Cut car at cursor\n"+
        "V) Paste before cursor\n"+
        "R) Remove cursor\n"
      );
    } else {
      System.out.println("Please input J or D!");
      return;
    }
    System.out.print("Please select an option: ");
    String input = scanner.nextLine().toLowerCase();
    Car newCar;
    switch(input){
      case "x":
        cutCar = chosenList.removeCursor();
        System.out.println("Cursor cut in " + chosenList.getName() + "'s List.");
        break;
      case "v":
        if(cutCar == null){
          throw new EndOfListException("Nothing to paste");
        } else {
          chosenList.insertBeforeCursor(cutCar);
          cutCar = null;
          System.out.println("Cursor pasted in " + chosenList.getName() + "'s List.");
        }
        break;
      case "r":
        chosenList.removeCursor();
        System.out.println("Cursor removed in " + chosenList.getName() +"'s List.");
        break;
      case "i":
        newCar = createCar();
        chosenList.insertBeforeCursor(newCar);
        System.out.println(newCar.getOwner() + "'s " + newCar.getMake() + 
          " has been scheduled for an oil change with" + chosenList.getName() +
          " and has been added to his list before the cursor.");
        break;
      case "h":
        chosenList.resetCursorToHead();
        System.out.println("Cursor Moved To Head in " + chosenList.getName() + "'s List.");

        break;
      case "t":
        chosenList.resetCursorToHead();
        for(int i = 1; i < chosenList.numCars(); i++){
          chosenList.cursorForward();
        }
        System.out.println("Cursor Moved To Tail in " + chosenList.getName() + "'s List.");
        break;
      case "a":
        newCar = createCar();
        chosenList.appendToTail(newCar);
        System.out.println(newCar.getOwner() + "'s " + newCar.getMake() + " has been scheduled for an oil change with Joe and has been added to his list.");
        break;
      case "f":
        chosenList.cursorForward();
        System.out.println("Cursor Moved Forward in " + chosenList.getName() +"'s List.");
        break;
      case "b":
        chosenList.cursorBackward();
        System.out.println("Cursor Moved Backward in " + chosenList.getName() +"'s List.");
        break;
      default:
        System.out.println("Please enter a valid option from the menu!");
    }
  }

  /**
   * Reusable method for any instance where a new car needs to be created based on user input.
   * @return Returns the new car based on user's input.
   * @throws EndOfListException
   */
  public static Car createCar() throws EndOfListException{
    System.out.print("Please enter vehicle make (Ford, GMC, Chevy, Jeep, Dodge, Chrysler, and Lincoln): ");
    String test = scanner.nextLine();
    Make model = null;
    for(Make m: Make.values()){
      if(test.toLowerCase().equals(m.name().toLowerCase())){
        model = m;
      }
    }
    if(model == null){
      throw new EndOfListException("We don't service " + test +"!");
    }
    System.out.print("Please enter owner's name: "); 
    String name = scanner.nextLine();
    return new Car(name, model);
  }

  /**
   * Method that merges one list to a destination.
   * 
   * @param destination - The list to be merged INTO.
   * @throws EndOfListException
   * @custom.preconditions: destination must not be null
   * @custom.postconditions: destination list is filled with alternating cars from both lists.
   */

  public static void merge(CarList destination) throws EndOfListException{
    CarList source;
    if(destination == joeList){
      source = donnyList;
      System.out.println("Donny's list merged into Joe's.");
    } else {
      source = joeList;
      System.out.println("Joe's list merged into Donny's.");
    }
    try{
      destination.resetCursorToHead();
      source.resetCursorToHead();
      while(source.getCursorCar() != null){
        try{
          destination.cursorForward();
          destination.insertBeforeCursor(source.getCursorCar());;
          source.removeCursor();
        } catch (EndOfListException e){
          destination.appendToTail(source.getCursorCar());
          source.removeCursor();
          destination.cursorForward();
        }
      }
    } catch (EndOfListException e){
      return;
    }
  }

  /**
   * Swaps the cursor with the previous node in the list.
   * 
   * @param list the list to be swapped to
   * @throws EndOfListException
   * @custom.preconditions: cursor cannot be the head of the list.
   */
  public static void swap(CarList list) throws EndOfListException{
    if(list.getCursor().getPrev() != null){
      Car swappedCar = list.removeCursor();
      list.insertBeforeCursor(swappedCar);
    }
  }

  /**
   * Sorts the lists using bubblesort.
   * @param a determines which list to sort
   * @throws EndOfListException
   */

  public static void sort(int a) throws EndOfListException{
    CarList list;
    if(a == 0){
      list = joeList;
    } else {
      list = donnyList;
    }

    Car cursorCar = list.getCursorCar();
    list.resetCursorToHead();
    if(list.getCursor() == null){
      return;
    }
    for(int i = list.numCars(); i>1; i--){
      list.resetCursorToHead();
      for(int j = 1; j < i; j++){
        list.cursorForward();
        CarListNode cursor = list.getCursor();
        CarListNode prev = cursor.getPrev();

        if(cursor.getData().getMake().toString().compareTo(prev.getData().getMake().toString()) == 0){
          if(cursor.getData().getOwner().toString().compareTo(prev.getData().getOwner().toString()) < 0){
            swap(list);
          }
        } if(cursor.getData().getMake().toString().compareTo(prev.getData().getMake().toString()) < 0) {
          swap(list);
        }
      }
    }
    list.resetCursorToHead();
    while(list.getCursorCar()!=cursorCar && list.getCursor() != null){
      list.cursorForward();
    }
  }
}

