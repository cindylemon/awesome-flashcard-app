package ui;

import model.*;
import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class FlashcardsGUI extends JFrame implements ActionListener {

    public static final int WIDTH = 850;
    public static final int HEIGHT = 500;
    private static final String JSON_STORE = "./data/handler.json";

    int index;
    boolean flipped;
    
    Handler handler;
    JsonWriter writer;
    JsonReader reader;
    Set currentSet;

    
    private CardLayout cardLayout = new CardLayout();
    JPanel mainPanel = new JPanel(cardLayout);
    private JLabel flashcardLabel;
    private JPanel flashcardPanel;

    Constants constants = new Constants();
    List<String> buttonStrings = new ArrayList<>();
    List<String> buttonStringsCardView = new ArrayList<>();
    List<String> flashcardModeButtons = new ArrayList<>();

    // Constructor for flashgards gui
    public FlashcardsGUI() {
        super("Awesome Flashcard App");
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(printAndExit());
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setDefaultLookAndFeelDecorated(true);
        getContentPane().setBackground(constants.cream());
        setLayout(new BorderLayout());

        
        fillLists();

        handler = new Handler();
        writer = new JsonWriter(JSON_STORE);
        reader = new JsonReader(JSON_STORE);

        JPanel home = home();
        JPanel load = load();
        JPanel save = save();
        

        mainPanel.add(home, "home");
        mainPanel.add(load, "load");
        mainPanel.add(save, "save");

        add(mainPanel);
        cardLayout.show(mainPanel, "home");
        setVisible(true);
    }
    
    // EFFECTS: prints and exits
    private WindowListener printAndExit() {
        return new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                printLogs();
                System.exit(0);
            }
        };
    }

    // MODIFIES: this
    // EFFECTS: prints out the event logs
    private void printLogs() {
        System.out.println("Event Log:");
        for (Event event : EventLog.getInstance()) {
            System.out.println(event.toString());
        }
        EventLog.getInstance().clear();
    }

    // MODIFIES: this
    // EFFECTS: fills lists
    public void fillLists() {
        buttonStrings.add("Load");
        buttonStrings.add("Save");
        buttonStrings.add("New Set");
        buttonStrings.add("Exit");

        buttonStringsCardView.add("Home");
        buttonStringsCardView.add("Remove Set");
        buttonStringsCardView.add("New Card");
        buttonStringsCardView.add("Flashcard Mode");

        flashcardModeButtons.add("Previous");
        flashcardModeButtons.add("Flip");
        flashcardModeButtons.add("Next");
    }

    // MODIFIES: this
    // EFFECTS: adds buttons to the GUI
    public JPanel home() {
        JPanel homePanel = new JPanel(new BorderLayout());
        homePanel.setBackground(constants.cream());
        
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        
        Drawings d = drawing();
        layeredPane.add(d, Integer.valueOf(1));
        
        JPanel buttonPanel = new JPanel(new GridLayout(1, 5, 50, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.setBounds(50, 10, WIDTH - 100, 30);
        
    
        for (int b = 0; b < buttonStrings.size(); b++) {
            JButton button = new JButton(buttonStrings.get(b));
            button.setActionCommand("myButton");
            button.addActionListener(this);
            button.setBackground(constants.green());
            button.setForeground(constants.cream());
            buttonPanel.add(button);
        }
        
        layeredPane.add(buttonPanel, Integer.valueOf(2));

        JPanel view = viewSets();
        view.setBounds(50, 50, WIDTH - 100, HEIGHT - 150);

        layeredPane.add(view, Integer.valueOf(2));
        homePanel.add(layeredPane);

        return homePanel;
    }

    // MODIFIES: this
    // EFFECTS: drawings helper
    public Drawings drawing() {
        Drawings d = new Drawings();
        d.setBounds(0, 0, WIDTH, HEIGHT);
        return d;
    }

    // MODIFIES: this
    // EFFECTS: loads in a photo
    public JLabel loadPhoto() {
        URL imageUrl = getClass().getResource("/resources/thumbsup.png");
       
        ImageIcon icon = new ImageIcon(imageUrl);
        
        JLabel label = new JLabel(icon);
        return label;
    }

    // MODIFIES: this
    // EFFECTS: loads loading page
    public JPanel load() {
        JPanel loadPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        loadPanel.setLayout(new GridLayout(3, 1));
        buttonPanel.setLayout(new BorderLayout());
        loadPanel.setBackground(constants.cream());

        loadPanel.add(loadPhoto());
        JLabel loadLabel = new JLabel("Loaded!", SwingConstants.CENTER);
        loadLabel.setFont(new Font("Arial", Font.BOLD, 24));
        loadLabel.setForeground(constants.green());
        JButton button = new JButton("Home");
        button.setActionCommand("myButton");
        button.setBackground(constants.green());
        button.setForeground(constants.cream());
        
        button.addActionListener(this);


        buttonPanel.setBackground(constants.cream());
        
        buttonPanel.add(button, BorderLayout.SOUTH);
        loadPanel.add(buttonPanel);
        loadPanel.add(loadLabel, SwingConstants.CENTER);

        return loadPanel;
    }


    // MODIFIES: this
    // EFFECTS: loads saving page
    public JPanel save() {
        JPanel savePanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        savePanel.setLayout(new GridLayout(3, 1));
        buttonPanel.setLayout(new BorderLayout());
        savePanel.setBackground(constants.cream());

        JLabel saveLabel = new JLabel("Saved!", SwingConstants.CENTER);
        saveLabel.setFont(new Font("Arial", Font.BOLD, 24));
        saveLabel.setForeground(constants.green());

        savePanel.add(loadPhoto());

        JButton button = new JButton("Home");
        button.setActionCommand("myButton");
        button.setBackground(constants.green());
        button.setForeground(constants.cream());
        
        button.addActionListener(this);
        buttonPanel.setBackground(new Color(1, 1, 1, 0));
        
        buttonPanel.add(button, BorderLayout.SOUTH);
        savePanel.add(buttonPanel);
        savePanel.add(saveLabel, SwingConstants.CENTER);

        return savePanel;
    }


    // MODIFIES: this
    // EFFECTS: loads handler from file
    private void loadHandler() {
        try {
            handler = reader.read();
            System.out.println("Loaded flashcards from " + JSON_STORE);
            refreshEverything();
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }

    }

    // MODIFIES: this
    // EFFECTS: saves the handler to file
    private void saveHandler() {
        try {
            writer.open();
            writer.write(handler);
            writer.close();
            System.out.println("Saved flashcards to " + JSON_STORE);
            home();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // EFFECTS: loads view set page
    public JPanel viewSets() {
        JPanel setsPanel = new JPanel(new BorderLayout());
        JPanel textPanel = new JPanel();
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        setsPanel.setOpaque(false);
        textPanel.setOpaque(false);
        buttonPanel.setOpaque(false);

        JLabel saveLabel = new JLabel("Your Sets:");
        saveLabel.setFont(new Font("Arial", Font.BOLD, 20));
        saveLabel.setForeground(constants.olive());

        textPanel.add(saveLabel);
        textPanel.setBounds(0, 15, WIDTH, 30);

        setsPanel.add(textPanel, BorderLayout.NORTH);

        List<String> buttonNames = new ArrayList<>();


        for (Set set : handler.getSets()) {
            JButton button = new JButton(set.getName());
            button.addActionListener(this);
            button.setBackground(constants.green());
            button.setForeground(constants.cream());   
            button.setPreferredSize(new Dimension(150, 100));            
            buttonPanel.add(button);
        }
        
        setsPanel.add(buttonPanel, BorderLayout.CENTER);
        return setsPanel;
    }

    // MODIFIES: this
    // EFFECTS: displays the cards from the specified set
    public JPanel viewCards(String setName) {
        currentSet = handler.getSet(setName);
        JPanel cardsPanel = new JPanel(new BorderLayout());
        JPanel textPanel = new JPanel();

        cardsPanel.setBackground(constants.sand());
        textPanel.setOpaque(false);
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setOpaque(false);

        JLabel saveLabel = new JLabel("Cards in Set");
        saveLabel.setFont(new Font("Arial", Font.BOLD, 20));
        saveLabel.setForeground(constants.olive());

        textPanel.add(saveLabel);
        cardsPanel.add(textPanel, BorderLayout.NORTH);

        for (Card card : currentSet.getCards()) {
            JButton button = new JButton(card.getQuestion());
            button.addActionListener(this);
            button.setBackground(constants.cream());
            button.setForeground(constants.olive());   
            button.setPreferredSize(new Dimension(150, 100));            
            buttonPanel.add(button);
        }
        cardsPanel.add(cardButtonPanel(), BorderLayout.SOUTH);
        cardsPanel.add(buttonPanel, BorderLayout.CENTER);
        return cardsPanel;
    }

    // MODIFIES: this
    // EFFECTS: helper function for cards
    public JPanel cardButtonPanel() {
        JPanel cardButtonPanel = new JPanel(new GridLayout(1, 2, 0, 0));
        cardButtonPanel.setOpaque(false);
        cardButtonPanel.setBounds(0, 100, WIDTH - 300, 30);

        for (String buttonName : buttonStringsCardView) {
            JButton button = new JButton(buttonName);
            button.setActionCommand("myButton");
            button.addActionListener(this);
            button.setBackground(constants.green());
            button.setForeground(constants.cream());   
            button.setPreferredSize(new Dimension(50, 30));            
            cardButtonPanel.add(button);
        }
        return cardButtonPanel;
    }

    // MODIFIES: this
    // EFFECTS: makes a new card
    private void newCard() {
        var questionPane = JOptionPane.showInputDialog("What would you like to set as the question?");
        String question = questionPane.toString();
        var answerPane = JOptionPane.showInputDialog("What would you like to set as the answer?");
        String answer = answerPane.toString();

        currentSet.addNewCard(question, answer);

        JPanel viewCards = viewCards(currentSet.getName());
        mainPanel.add(viewCards, "cards");
        cardLayout.show(mainPanel, "cards");
    }


    // MODIFIES: this
    // EFFECTS: displays flashcards in a set
    private JPanel flashcardMode() {
        flashcardPanel = new JPanel(new BorderLayout());
        flashcardPanel.setBackground(constants.cream());
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 0, 0));
        

        JButton homeButton = new JButton("Home");
        JButton deleteButton = new JButton("Delete Card");

        
        homeButton.addActionListener(this);
        homeButton.setBackground(constants.green());
        homeButton.setForeground(constants.cream());   
        homeButton.setPreferredSize(new Dimension(50, 30));            
        buttonPanel.add(homeButton);
        buttonPanel.setBounds(50, 10, WIDTH - 100, 30);

        deleteButton.addActionListener(this);
        deleteButton.setBackground(constants.green());
        deleteButton.setForeground(constants.cream());   
        deleteButton.setPreferredSize(new Dimension(50, 30));            
        buttonPanel.add(deleteButton);

        flashcardPanel.add(buttonPanel, BorderLayout.NORTH);

        flashcardLabel = new JLabel("", SwingConstants.CENTER);
        flashcardLabel.setFont(new Font("Arial", Font.BOLD, 24));

        updateFlashcard();

        flashcardPanel.add(flashcardLabel, BorderLayout.CENTER);
        flashcardPanel.add(flashcardModeButtons(), BorderLayout.SOUTH);
        
        return flashcardPanel;
    }

    // MODIFIES: this
    // EFFECTS: helper function for cards
    public JPanel flashcardModeButtons() {
        JPanel flashcardButtons = new JPanel(new GridLayout(1, 2, 0, 0));
        flashcardButtons.setOpaque(false);
        flashcardButtons.setBounds(0, 100, WIDTH - 300, 30);


        for (String buttonName : flashcardModeButtons) {
            JButton button = new JButton(buttonName);
            button.setActionCommand("myButton");
            button.addActionListener(this);
            button.setBackground(constants.green());
            button.setForeground(constants.cream());   
            button.setPreferredSize(new Dimension(50, 30));            
            flashcardButtons.add(button);
        }
        return flashcardButtons;
    }

    // MODIFIES: this
    // EFFECTS: refreshes everything and return to home
    private void refreshEverything() {
        mainPanel.removeAll();
        
        JPanel home = home();
        JPanel load = load();
        JPanel save = save();
        
        mainPanel.add(home, "home");
        mainPanel.add(load, "load");
        mainPanel.add(save, "save");
        
        mainPanel.revalidate();
        mainPanel.repaint();
        
        cardLayout.show(mainPanel, "home");
    }


    // MODIFIES: this
    // EFFECTS: shows previous card
    private void previousCard() {
        if (currentSet.getCards().size() > 0) {
            index = (index + currentSet.getCards().size() - 1) % currentSet.getCards().size();
            flipped = false;
            updateFlashcard();
        }
    }

    // MODIFIES: this
    // EFFECTS: flips the card
    private void flipCard() {
        flipped = !flipped;
        updateFlashcard();
    }

    // MODIFIES: this
    // EFFECTS: shows next card
    private void nextCard() {
        if (currentSet.getCards().size() > 0) {
            index = (index + 1) % currentSet.getCards().size();
            flipped = false;
            updateFlashcard();
        }
    }

    // MODIFIES: this
    // EFFECTS: displays current flashcard mode
    private void updateFlashcard() {
        flashcardLabel.setForeground(constants.olive());
        if (currentSet.getCards().isEmpty()) {
            flashcardLabel.setText("This set is empty!");
        } else {
            Card currentCard = currentSet.getCards().get(index);
            if (flipped) {
                flashcardLabel.setText("<html>Back: <br>" + currentCard.getAnswer() + "</html>");
            } else {
                flashcardLabel.setText("<html>Front: <br>" + currentCard.getQuestion() + "</html>");
            }
        }
        mainPanel.add(flashcardPanel, "cards");
        cardLayout.show(mainPanel, "cards");
    }

    // MODIFIES: this
    // EFFECTS: flashcard event
    private void flashcardHelper() {
        if (currentSet != null | !currentSet.getCards().isEmpty()) {
            index = 0;
            flipped = false;
            mainPanel.add(flashcardMode(), "flashcardMode");
            cardLayout.show(mainPanel, "flashcardMode");
        } else {
            JOptionPane.showMessageDialog(null, "There are no cards!");
            cardLayout.show(home(), "home");
        }
    }

    // MODIFIES: this
    // EFFECTS: helper for event listener
    private void homeAction() {
        System.out.println("going home");
        cardLayout.show(mainPanel, "home");
        
    }

    // MODIFIES: this
    // EFFECTS: helper for event listener
    private void loadAction() {
        System.out.println("loading");
        loadHandler();
        cardLayout.show(mainPanel, "load");
    }

    // MODIFIES: this
    // EFFECTS: helper for event listener
    private void saveAction() {
        System.out.println("saving");
        saveHandler();
        cardLayout.show(mainPanel, "save");     
    }

    // MODIFIES: this
    // EFFECTS: helper for event listener
    private void newSetAction() {
        System.out.println("new set");
        var namePane = JOptionPane.showInputDialog("What would you like to name this set?");
        String setName = namePane.toString();
        handler.newSet(setName);
        refreshEverything();
        var output = "New Set Created!";
        cardLayout.show(mainPanel, "home");
        JOptionPane.showMessageDialog(null, output);
    }

    // MODIFIES: this
    // EFFECTS: helper for event listener
    private void exitAction() {
        System.out.println("exiting");
        printLogs();
        dispose();
        System.exit(0);
    }


    // MODIFIES: this
    // EFFECTS: helper for event listener, removes set
    private void removeAction() {
        System.out.println("remove");
                
        handler.removeSet(currentSet);
        var removed = "Successfully Removed!";
        refreshEverything();
        JOptionPane.showMessageDialog(null, removed);
    }

    // MODIFIES: this
    // EFECTS: delete card
    private void deleteCard() {
        if (currentSet.getCards().isEmpty()) {
            JOptionPane.showMessageDialog(null, "This set is empty!");
            return;
        }
        
        Card c = currentSet.getCards().get(index);
        currentSet.removeFromSet(c);

        if (index >= currentSet.getCards().size()) {
            if (index > 0) {
                index = currentSet.getCards().size() - 1;
            } else {
                index = 0;
            }
        }
        JOptionPane.showMessageDialog(null, "Card removed!");
        if (currentSet.getCards().isEmpty()) {
            flashcardLabel.setText("This set is empty!");
        } else {
            updateFlashcard();
        }
    }

    // MODIFIES: this
    // EFFECTS: event listener for all buttons
    @Override
    @SuppressWarnings("methodlength")
    public void actionPerformed(ActionEvent e) {
        JButton pressedButton = (JButton) e.getSource();
        String text = pressedButton.getText();
        List<String> sets = new ArrayList<>();

        for (Set set : handler.getSets()) {
            sets.add(set.getName());
        }

        switch (text) {
            case "Home": homeAction();
                break;

            case "Load": loadAction();
                break;

            case "Save": saveAction();  
                break;

            case "New Set": newSetAction();
                break;
            
            case "Exit": exitAction();
                break;

            case "Remove Set": removeAction();
                break;
            
            case "New Card": newCard();
                break;

            case "Flashcard Mode": flashcardHelper();
                break;
            
            case "Previous": previousCard();
                break;

            case "Flip": flipCard();
                break;

            case "Next": nextCard();
                break;
            
            case "Delete Card": deleteCard();
                break;
            
            default:
                if (sets.contains(text)) {
                    currentSet = handler.getSet(text);
                    System.out.println("opening set");
                    JPanel viewCards = viewCards(text);
                    mainPanel.add(viewCards, "cards");
                    cardLayout.show(mainPanel, "cards");
                } else if (currentSet.getCards().contains(currentSet.getCard(text))) {
                    System.out.println("showing answer to card");
                    String answer = currentSet.getCard(text).getAnswer();
                    JOptionPane.showMessageDialog(null, "The answer to this card is: " + answer);

                } else {
                    System.out.println("Unknown button: " + text);
                }
        }
        System.out.println("");
    }


}