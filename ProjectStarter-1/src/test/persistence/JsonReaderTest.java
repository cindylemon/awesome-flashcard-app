package persistence;

import model.Card;
import model.Handler;
import model.Set;

import persistence.JsonReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

// adapted from test file
public class JsonReaderTest extends JsonTest {
    
    Card c1;
    Card c2;
    Set s1;
    Set s2;

    @BeforeEach
    void runBefore() {
        c1 = new Card("q1", "a1");
        c2 = new Card("q2", "a2");
        s1 = new Set("s1");
        s2 = new Set("s2");
        s1.addToSet(c1);
        s1.addToSet(c2);
        s2.addToSet(c1);
    }
    
    @Test
    public void testReaderNoFile() {
        JsonReader test = new JsonReader("./data/nonexistent.json");
        try {
            Handler handler = test.read();
            fail("exception expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    public void testReaderHandler() {
        JsonReader test = new JsonReader("./data/testReaderEmptySet.json");
        List<Card> list = new ArrayList<>();
        try {
            Handler handler = test.read();
            assertEquals(list, handler.getSets());
        } catch (IOException e) {
            fail("File could not be read");
        }
    }

    @Test
    public void testReaderNormal() {
        JsonReader test = new JsonReader("./data/testReaderNormal.json");
        try {
            Handler handler = test.read();

            assertEquals(1, handler.getSets().size());
            assertEquals("s1", handler.getSets().get(0).getName());


        } catch (IOException e) {
            fail("File could not be read");
        }
    }

}
