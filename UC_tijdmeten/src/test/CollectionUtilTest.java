package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static timeutil.CollectionUtil.testAdding;

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
        testAdding(lijst,0);
        int actual = lijst.size();
        assertEquals(expected, actual);
    }

    @Test
    void testAddRandomStringsToCollectionWithSingleEntry() {
        int expected = 1;
        testAdding(lijst,1);
        int actual = lijst.size();
        System.out.println("Eerste element : " + lijst.get(0));
        assertEquals(expected, actual);
    }
}