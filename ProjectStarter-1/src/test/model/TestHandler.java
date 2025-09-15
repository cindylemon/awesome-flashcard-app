package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestHandler {
    Handler handler;
    List<Set> testList;

    @BeforeEach
    void runBefore() {
        handler = new Handler();
        testList = new ArrayList<>();
    }

    @Test
    void testHandler() {
        assertEquals(testList, handler.getSets());
    }

    @Test
    void testNewSet() {
        handler.newSet("s1");
        Set s1 = handler.getSets().get(0);
        testList.add(s1);
        assertEquals(testList, handler.getSets());

        handler.newSet("s2");
        Set s2 = handler.getSets().get(1);
        testList.add(s2);
        assertEquals(testList, handler.getSets());
    }

    @Test
    void testGetSet() {
        handler.newSet("s1");
        Set set = handler.getSets().get(0);
        assertEquals(set, handler.getSet("s1"));
        handler.newSet("s3");
        handler.newSet("s2");
        Set set2 = handler.getSets().get(2);
        assertEquals(set2, handler.getSet("s2"));
        assertNull(handler.getSet("set"));
    }
}
