package model;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
public class TestSet {
    Card c1;
    Card c2;
    Card c3;
    Card c4;
    Set s1;
    Set s2;
    Handler h;
    List<Card> testList;

    @BeforeEach
    void runBefore() {
        c1 = new Card("q1", "a1");
        c2 = new Card("q2", "a2");
        c3 = new Card("q3", "a3");
        c4 = new Card("q4", "q4");
        s1 = new Set("s1");
        s2 = new Set("s2");
        testList = new ArrayList<>();
    }

    @Test
    void testSet() {
        assertEquals(testList, s1.getCards());
        assertEquals(testList, s2.getCards());
        assertEquals("s2", s2.getName());
    }

    @Test
    void testReset() {
        s1.addToSet(c1);
        s1.addToSet(c2);
        testList.add(c1);
        testList.add(c2);
        assertEquals(testList, s1.getCards());
        s1.reset();
        testList = new ArrayList<>();
        assertEquals(testList, s1.getCards());
    }

    @Test
    void testAddToSet() {
        // adding cards to one set
        testList.add(c1);
        s1.addToSet(c1);
        assertEquals(testList, s1.getCards());

        // multiple times to one set
        s1.addToSet(c1);
        assertEquals(testList, s1.getCards());

        // multiple cards to one set
        s1.addToSet(c2);
        testList.add(c2);
        assertEquals(testList, s1.getCards());
        s1.addToSet(c3);
        testList.add(c3);
        assertEquals(testList, s1.getCards());

        // one card to multiple
        testList = new ArrayList<>();
        s1.reset();
        s2.reset();
        testList.add(c4);
        s1.addToSet(c4);
        s2.addToSet(c4);
        assertEquals(testList, s1.getCards());
        assertEquals(testList, s2.getCards());
    }

    @Test
    void testRemoveFromSet() {
        s1.addToSet(c1);
        s1.addToSet(c2);
        testList.add(c1);
        testList.add(c2);
        assertEquals(testList, s1.getCards());
        s1.removeFromSet(c1);
        testList.remove(c1);
        assertEquals(testList, s1.getCards());
    }

    @Test
    void testAddNewCard() {
        // empty list
        assertEquals(testList, s1.getCards());
        s1.addNewCard("q", "a");
        Card card = s1.getCards().get(0);
        testList.add(card);
        assertEquals("q", card.getQuestion());
        assertEquals(testList, s1.getCards());

        // non empty list
        testList = new ArrayList<>();
        assertEquals(testList, s2.getCards());
        testList.add(c1);
        s2.addToSet(c1);
        s2.addNewCard("a", "b");
        Card newCard2 = s2.getCards().get(1);
        testList.add(newCard2);
        assertEquals(testList, s2.getCards());
        testList.add(c2);
        s2.addToSet(c2);
        assertEquals(testList, s2.getCards());
    }

    @Test
    void testRename() {
        assertEquals("s2", s2.getName());
        s2.rename("new name");
        assertEquals("new name", s2.getName());
    }

    @Test
    void testGetQuizCards() {
        c2.mastered();
        testList.add(c1);
        testList.add(c3);
        s1.addToSet(c1);
        s1.addToSet(c2);
        s1.addToSet(c3);

        assertEquals(testList, s1.getQuizCards());
        
    }

    @Test
    void testGettersNotQuizCards() {
        // test getCards()
        testList.add(c1);
        s1.addToSet(c1);
        testList.add(c4);
        s1.addToSet(c4);
        assertEquals(testList, s1.getCards());

        // test getName()
        assertEquals("s1", s1.getName());
    }
}
