package model;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestCard {
    
    Card c1;
    Card c2;
    Card c3;
    Card c4;

    @BeforeEach 
    void runBefore() {
        c1 = new Card("q1", "a1");
        c2 = new Card("q2", "a2");
        c3 = new Card("q3", "a3");
        c4 = new Card("q4", "q4");
    }

    @Test
    public void testCard() {
        assertEquals("a1", c1.getAnswer());
        assertEquals("q1", c1.getQuestion());

        assertEquals("a3", c3.getAnswer());
        assertEquals("q3", c3.getQuestion());

        assertFalse(c1.getMastery());
        assertFalse(c2.getMastery());
    }

    @Test
    public void testChangeQuestion() {
        assertEquals("q2", c2.getQuestion());
        c2.changeQuestion("question 2");
        assertEquals("question 2", c2.getQuestion());

        c2.changeQuestion("q2");
        assertEquals("q2", c2.getQuestion());

        c2.changeQuestion("third change");
        assertEquals("third change", c2.getQuestion());
    }

    @Test
    public void testChangeAnswer() {
        assertEquals("a3", c3.getAnswer());
        c3.changeAnswer("answer 3");
        assertEquals("answer 3", c3.getAnswer());

        c3.changeAnswer("a3");
        assertEquals("a3", c3.getAnswer());

        c3.changeAnswer("third change");
        assertEquals("third change", c3.getAnswer());

        c3.changeAnswer("photo change");
        assertEquals("photo change", c3.getAnswer());

        c3.changePhoto("photo");
        assertEquals("photo", c3.getPhoto());
    }

    @Test
    public void testSetMastered() {
        assertFalse(c1.getMastery());
        c1.mastered();
        assertTrue(c1.getMastery());
        c1.mastered();
        assertTrue(c1.getMastery());
    }

    @Test
    public void testSetUnmastered() {
        assertFalse(c4.getMastery());
        c4.notMastered();
        assertFalse(c4.getMastery());

        c4.mastered();
        assertTrue(c4.getMastery());
        c4.notMastered();
        assertFalse(c4.getMastery());
    }

    @Test
    public void testToggleMastery() {
        assertFalse(c4.getMastery());
        c4.toggleMastery();
        assertTrue(c4.getMastery());

        c4.toggleMastery();
        assertFalse(c4.getMastery());
    }

    @Test
    public void testGetters() {
        assertEquals("q1", c1.getQuestion());
        assertEquals("q4", c4.getQuestion());

        assertEquals("a2", c2.getAnswer());
        assertEquals("a3", c3.getAnswer());

        assertFalse(c1.getMastery());
        c3.mastered();
        assertTrue(c3.getMastery());
    }
}
