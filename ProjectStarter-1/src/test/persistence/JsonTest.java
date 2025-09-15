package persistence;

import model.Card;
import model.Handler;
import model.Set;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

// adapted from test file
public class JsonTest {
    
    protected void checkCard(String question, String answer, String photo, Boolean mastery, Card card) {
        assertEquals(question, card.getQuestion());
        assertEquals(answer, card.getAnswer());
        assertEquals(photo, card.getPhoto());
        assertEquals(mastery, card.getMastery());
    }

    protected void checkSet(String name, int size, Set set) {
        assertEquals(name, set.getName());
        assertEquals(size, set.getCards().size());
    }
}
