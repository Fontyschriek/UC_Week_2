package timeutil;

import javax.swing.text.html.HTMLDocument;
import java.sql.Time;
import java.util.*;

/**
 * Created by Charles Korthout on 2/12/2017.
 */
public class CollectionUtil {

    final static int DEFAULT_STRING_LENGTH = 20;
    final static List<Integer> tests = Arrays.asList(500,1000,2000, 5000, 10000, 20000, 50000, 100000 /**, 200000, 500000, 1000000, 2000000, 5000000 , 10000000*/);
    private static enum InsertPosition { Begin, Middle, End};


    /**
     * Add a number of random strings to a collection
     * @param nrOfString The number of strings to add
     * @param aCollection The collection to add to
     */
    public static void addRandomStringsToCollection(int nrOfString, Collection aCollection) {
        Random random = new Random();
        for (int i = 0; i < nrOfString; i ++) {
            aCollection.add(randomName(DEFAULT_STRING_LENGTH,random));
        }
    }

    /**
     * test adding random strings to a collection (a name refactoring of the above method addRandomStringsToCollection) The names are now more aligned to searching and removing methods
     * @param aCollection The collection to add to
     * @param nrOfElements The number of strings to add
     */
    public static void testAdding( Collection aCollection,int nrOfElements) {
        addRandomStringsToCollection(nrOfElements, aCollection);
    }

    /**
     * Test the removal from the collection in a random sequence. the iterator contains the shuffled collection
     * @param aCollection The collection to remove elemens from
     * @param iterator The collection iterator in a shuffled order
     */
    public static void testRemoving(Collection aCollection, Iterator iterator)
    {
        while (iterator.hasNext()){
            aCollection.remove((String)iterator.next());
        }
    }

    /**
     * searches the collection for elements. The iterator is a shuffled version of the collection
     * @param aCollection The collection to be searched
     * @param iterator The iterator of elements to search
     */
    public static void testSearching(Collection aCollection, Iterator iterator ){
        int count = 0;
        while (iterator.hasNext()){
            if (aCollection.contains(iterator.next()))  count++;
        }
        // TODO warn when not all elements are found..

    }

    /**
     * Create a shuffled list from the collection to iterate over for search and removal
     * @param aCollection The collection to shuffle
     * @return An iterator for the shuffled collection
     */
    public static Iterator<String> createShuffledIterator(Collection aCollection) {
        List<String> list = new ArrayList(aCollection);
        Collections.shuffle(list);
        return list.iterator();
    }

    /**
     * Generates a random name with a predefined length
     * @return a random name with he specified number of characters
     */
    private static String randomName(int size, Random random) {
        String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < size; i++) {
            char c = alphabet.charAt(random.nextInt(alphabet.length()));
            sb.append(c);
        }
        return sb.toString();
    }

    private static void collectionTest(Collection aCollection, String name) {
        Iterator iterator = tests.iterator();
        System.out.format(" %20s | %10s | %10s | %10s | %10s |\n", "Name", "Elements", "Adding", "Searching", "Removing");
        while (iterator.hasNext()) {
            long adding = 0;
            long removing = 0;
            long searching = 0;
            // Start with adding elements to the collection
            TimeStamp ts = new TimeStamp();
            int elements = (int) iterator.next();
            ts.setBegin();
            testAdding(aCollection, elements);
            ts.setEnd();
            adding = ts.getDurations().get(0); // should only be one
            Iterator shuffled = createShuffledIterator(aCollection);
            ts.init();
            ts.setBegin();
            testSearching(aCollection, shuffled);
            ts.setEnd();
            searching = ts.getDurations().get(0); // should only be one
            ts.init();
            shuffled = createShuffledIterator(aCollection); //reset the shuffling iterator
            ts.setBegin();
            testRemoving(aCollection,shuffled);
            ts.setEnd();
            removing = ts.getDurations().get(0); // should only be one
            System.out.format(" %20s | %10d | %10d | %10d | %10d |\n", name, elements, adding, searching, removing);
        }
    }


    public static void main(String[] args) {
        collectionTest(new ArrayList(), "ArrayList");
        collectionTest(new LinkedList(), "LinkedList");
        collectionTest(new TreeSet(), "TreeSet");
        collectionTest(new HashSet(), "HashSet");
        collectionTest(new Vector(), "Vector");
    }

}

