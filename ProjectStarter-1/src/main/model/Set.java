package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

// Handles each set/subject of flashcards
public class Set implements Writable {
    List<Card> cards;
    String name;

    // EFFECTS: Initializes a new subject
    public Set(String name) {
        cards = new ArrayList<>();
        this.name = name;
    }

    // MODIFIES: this
    // EFFECTS: resets/deletes all the cards in the set (mostly for testing purposes)
    public void reset() {
        cards = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: adds a specific card (Card c) to this set if not already inside
    public void addToSet(Card c) {
        if (!cards.contains(c)) {
            cards.add(c);
        }
    }

    // MODIFIES: this
    // EFFECTS: removes a specific card (Card c) from this set
    public void removeFromSet(Card c) {
        // note to self when doing this --  can change this to index
        EventLog.getInstance().logEvent(new Event("Card question '" + c.getQuestion() + "' Removed"));
        cards.remove(c);
    }


    // MODIFIES: this
    // EFFECTS: makes a new flashcard and adds it to current set
    public void addNewCard(String question, String answer) {
        Card newCard = new Card(question, answer);
        cards.add(newCard);
        EventLog.getInstance().logEvent(new Event("New card: '" + question + "'\n \t  '" + answer + "'" + " created"));
    }

    
    // MODIFIES: this
    // EFFECTS: renames this set
    public void rename(String name) {
        this.name = name;
    }

    // MODIFIES: this
    // EFFECTS: returns a list of cards to quiz on (not mastered)
    public List<Card> getQuizCards() {
        List<Card> quizCards = new ArrayList<>();
        for (Card c : cards) {
            if (c.getMastery() == false) {
                quizCards.add(c);
            }
        }
        return quizCards;
    }

    // EFFECTS: finds a set with given name or null
    public Card getCard(String name) {
        for (Card card : cards) {
            if (card.getQuestion().equals(name)) {
                return card;
            }
        }
        return null;
    }

    public List<Card> getCards() {
        return cards; 
    }

    public String getName() {
        return name; 
    }

    // MODIFIES: this
    // EFFECTS: saves the sets as JSON
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("cards", cardsToJson());
        return json;
    }

    // MODIFIES: this
    // EFFECTS: saves the cards as JSON
    private JSONArray cardsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Card c : cards) {
            jsonArray.put(c.toJson());
        }
        return jsonArray;
        
    }

}
