package persistence;

import model.Handler;
import model.Set;
import model.Card;
import org.junit.jupiter.api.Test;

import persistence.*;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterBadFile() {
        try {
            Handler handler = new Handler();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyHandler() {
        try {
            Handler handle = new Handler();
            JsonWriter writer = new JsonWriter("./data/handler.json");
            writer.open();
            writer.write(handle);
            writer.close();

            JsonReader reader = new JsonReader("./data/handler.json");
            handle = reader.read();
            assertEquals(0, handle.getSets().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralHandler() {
        try {
            Handler handle = new Handler();
            handle.newSet("s1");
            handle.getSet("s1").addNewCard("q", "a");
            JsonWriter writer = new JsonWriter("./data/handler.json");
            writer.open();
            writer.write(handle);
            writer.close();

            JsonReader reader = new JsonReader("./data/handler.json");
            handle = reader.read();
            List<Set> sets = handle.getSets();
            assertEquals(1, sets.size());
            checkSet("s1", 1, handle.getSet("s1"));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}