package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static timeutil.CollectionUtil.addRandomStringsToCollection;

/**
 * Created by Charles Korthout.
 */
class CollectionUtilTest {
    List<String> lijst;
    @BeforeEach
    void setUp() {
        lijst = new ArrayList();
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void testAddRandomStringsToCollectionWithZeroEntries() {
        int expected = 0;
        addRandomStringsToCollection(0,lijst);
        int actual = lijst.size();
        assertEquals(expected, actual);
    }

    @Test
    void testAddRandomStringsToCollectionWithSingleEntry() {
        int expected = 1;
        addRandomStringsToCollection(1,lijst);
        int actual = lijst.size();
        System.out.println("Eerste element : " + lijst.get(0));
        assertEquals(expected, actual);
    }
}