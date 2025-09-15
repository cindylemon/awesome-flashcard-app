package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.channels.Pipe.SourceChannel;
import java.util.Scanner;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

// Manages console output of the application
public class Output {
    private static final String JSON_STORE = "./data/handler.json";
    Handler handler = new Handler();
    Set current;
    private JsonWriter jsonWriter = new JsonWriter(JSON_STORE);
    private JsonReader jsonReader = new JsonReader(JSON_STORE);
    
    // EFFECTS: initializaes everything
    public void start() {
        home();
    }

    // EFFECTS: the home page of the app
    public void home() {
        System.out.println("Welcome to Cindy's Super Awesome Flashcards!");
        System.out.println("What would you like to do?");
        System.out.println("    [1] Make a new set");
        System.out.println("    [2] View a current set");
        System.out.println("    [3] Exit");
        System.out.println("    [4] Save data");
        System.out.println("    [5] Load data from file");
        Scanner scan = new Scanner(System.in);
        int options = numInput(scan, 1, 5, "What would you like to do?");
        if (options == 1) {
            newSet();
        } else if (options == 2) {
            currentSets();
        } else if (options == 3) {
            System.exit(0);
        } else if (options == 4) {
            saveHandler();
        } else {
            loadHandler();
        }
    }

    // MODIFIES: handler
    // EFFECTS: makes a new set
    public void newSet() {
        System.out.println("What would you like to name your new set?");
        Scanner scan = new Scanner(System.in);
        String setName = scan.nextLine();
        handler.newSet(setName);
        System.out.println("Current sets:");
        currentSets();
        
    }

    
    // EFFECTS: displays current sets and shows options for sets
    public void currentSets() {
        if (handler.getSets().isEmpty()) {
            System.out.println("You have no sets");
            System.out.println("");
            home();
        }
        int x = 0;
        for (Set s : handler.getSets()) {
            x++;
            System.out.println("    [" + x + "] " + s.getName());
        }
        
        Scanner scan = new Scanner(System.in);
        int index = numInput(scan, 1, x, "Which set would you like to view?") - 1;
        current = handler.getSets().get(index);
        System.out.println("You have seleced " + current.getName());
        System.out.println("What would you like to do? \n ");
        System.out.println("    [1] Use this set");
        System.out.println("    [2] Edit this set");
        System.out.println("    [3] Go back");
        int choice = numInput(scan, 1, 3, "Choose by typing the number");
        choices(choice);
    }

    // EFFECTS: the choices of the sets
    public void choices(int index) {
        if (index == 1) {
            use();
        } else if (index == 2) {
            edit();
        } else if (index == 3) {
            currentSets();
        }
    }
    

    // EFFECTS: use the set or add/remove cards
    public void selectedSet() {
        int i = 0;
        for (Card c : current.getCards()) {
            String m = "";
            if (c.getMastery() == true) {
                m = " (mastered)";
            }
            i++;
            System.out.println(i + ". " + c.getQuestion() + m);
            System.out.println("   " + c.getAnswer());
            System.out.println("");
        }
    }

    // MODIFIES: the selected set
    // EFFECTS: go through the selected set
    public void edit() {
        selectedSet();
        System.out.println("What would you like to do?");
        System.out.println("    [1] Add a new card");
        System.out.println("    [2] Edit a card");
        System.out.println("    [3] Go home");
        Scanner scan = new Scanner(System.in);
        int choice = numInput(scan, 1, 3, "Choose a number by typing");
        System.out.println("");
        if (choice == 1) {
            newCard();
        } else if (choice == 2) {
            cardEditor();
        } else {
            home();
        }
    }

    // MODIFIES: current set
    // EFFECTS: adds a new card to the set
    public void newCard() {
        Scanner scan = new Scanner(System.in);
        System.out.println("What's your question?");
        String question = scan.nextLine();
        System.out.println("What's the answer?");
        String answer = scan.nextLine();
        current.addNewCard(question, answer);
        System.out.println("What photo would you like to add? Press enter to skip");
        String photo = scan.nextLine();
        current.getCards().get(current.getCards().size() - 1).changePhoto(photo);
        System.out.println("New card added.");
        edit();
    }

    // MODIFIES: cards in current set
    // EFFECTS: Edits the cards
    public void cardEditor() {
        if (current.getCards().isEmpty()) {
            System.out.println("This set is currently empty");
            edit();
        }
        selectedSet();
        Scanner scan = new Scanner(System.in);
        int index = numInput(scan, 1, current.getCards().size(), "Which card would you like to edit?") - 1;
        print();
        int choice = numInput(scan, 1, 3, "Choose by typing a number");
        Scanner next = new Scanner(System.in);
        if (choice == 1) {
            String q = changeAns();
            current.getCards().get(index).changeQuestion(q);
        } else if (choice == 2) {
            String a = changeAns();
            current.getCards().get(index).changeQuestion(a);
            editPhoto(index);
        } else {
            System.out.println("Mastery has been toggled");
            current.getCards().get(index).toggleMastery();
        }
        edit();
    }

    // EFFECTS: the scanner for editing the cards
    public String changeAns() {
        System.out.println("What would you like to change it to?");
        Scanner scan = new Scanner(System.in);
        String string = scan.nextLine();
        return string;
    }

    // EFFECTS: editing the photo
    public void editPhoto(int index) {
        System.out.println("Would you like to change the photo?");
        Scanner scan = new Scanner(System.in);
        System.out.println("    [1] yes");
        System.out.println("    [2] no");
        int input = numInput(scan, 1, 2, "Choose by picking a number");
        if (input == 1) {
            System.out.println("What would you like to change it to?");
            String photo = scan.next();
            current.getCards().get(index).changePhoto(photo);
        }
    }

    // EFFECTS: prints out the options for editing cards
    public void print() {
        System.out.println("What would you like to do?");
        System.out.println("    [1] Change Question");
        System.out.println("    [2] Change Answer");
        System.out.println("    [3] Toggle mastery");
    }

    // EFFECTS: use the set
    public void use() {
        System.out.println("----- FLASHCARD MODE -----");
        System.out.println("Current set: " + current.getName());
        for (Card c : current.getQuizCards()) {
            System.out.println(c.getQuestion());
            Scanner scan = new Scanner(System.in);
            int choice = numInput(scan, 1, 2, "Choose [1] to flip, and [2] to go to the next card");
            // figure out how to go backwards.. maybe use indexes instead?
            if (choice == 1) {
                System.out.println(c.getAnswer());
                System.out.println(c.getPhoto());
            }
        }
        System.out.println("Congrats! You have completed this set.");
        edit();
    }


    // MODIFIES: this
    // EFFECTS: takes in the user input for numbers
    public int numInput(Scanner scan, int min, int max, String text) {
        int choice;
        while (true) {
            System.out.println(text);
            if (scan.hasNextInt()) {
                choice = scan.nextInt();
                if (choice < min || choice > max) {
                    System.err.println("ERROR. INPUT IS NOT IN RANGE");
                } else {
                    break;
                }
            } else {
                System.err.println("ERROR. INVALID INPUT.");
                scan.next();
            }
        } 
        return choice;
    }

    // EFFECTS: saves the handler to file
    private void saveHandler() {
        try {
            jsonWriter.open();
            jsonWriter.write(handler);
            jsonWriter.close();
            System.out.println("Saved flashcards to " + JSON_STORE);
            home();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads handler from file
    private void loadHandler() {
        try {
            handler = jsonReader.read();
            System.out.println("Loaded flashcards from " + JSON_STORE);
            home();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }

    }

}
