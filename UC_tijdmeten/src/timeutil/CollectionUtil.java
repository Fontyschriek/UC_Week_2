package timeutil;

import javax.swing.text.html.HTMLDocument;
import java.util.*;

/**
 * Created by Charles Korthout on 2/12/2017.
 */
public class CollectionUtil {

    final static int DEFAULT_STRING_LENGTH = 20;
    List<Integer> tests = Arrays.asList(500,1000,5000,10000,25000,100000, 1000000, 10000000);
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

    /**
     * Run tests according the predefined list of samples
     * @param aCollection The collection to test
     * @param samples the number of samples to execute
     */
    public static void runTests(Collection aCollection, int samples){
        // create a new Timestamp
        TimeStamp ts = new TimeStamp();
        ts.setBegin();
        addRandomStringsToCollection(samples, aCollection);
        ts.setEnd("Finished processing " + samples + " elements");
        // print the results
        System.out.print(ts.toString());
        aCollection.clear(); // clear the collection
    }

    /**
     * Run tests according the predefined list of samples and provide a time stamp to measure the duration
     * @param aCollection The collection to test
     * @param samples the number of samples to process
     * @param ts The Timesstamp to measure durations
     */
    public static void createCollection(Collection aCollection, int samples, TimeStamp ts){
        ts.setBegin();
        addRandomStringsToCollection(samples, aCollection);
        ts.setEnd("Finished processing " + samples + " elements");
    }

    public static void addToAndSearchCollection(List<String> aCollection, String basename, TimeStamp ts) {
        Iterator iterator = tests.iterator();
        while (iterator.hasNext()) {
            int samples = (int) iterator.next();
            ts.setBegin("Adding " + samples + "names at the beginning, middle and end of the list");
            for (int i = 0; i < samples; i += 3) {
                aCollection.add(0, basename + i);
                ts.setEnd();
                ts.setEndBegin("Adding name in the middle of the list");
                aCollection.add(aCollection.size() / 2, basename + i +1 );
                ts.setEnd();
                ts.setEndBegin("Adding name at the end of the list");
                aCollection.add(aCollection.size(), basename + i +2);
                ts.setEnd();
                ts.setEndBegin("searching " + basename + i + " in the collection ");
                aCollection.contains(basename + i);
                ts.setEnd();
                ts.setEndBegin("searching " + basename + i + "in the collection ");
                aCollection.contains(basename + i + 1);
                ts.setEnd();
                ts.setEndBegin("searching " + basename + "_1 in the collection ");
                aCollection.contains(basename + i + 2);
                ts.setEnd();
            }
            for (int i = 0; i < samples; i++) {
                aCollection.remove(basename + i);
            }
        }
    }

    public static void main(String[] args) {

        Iterator iterator = tests.iterator();
        while (iterator.hasNext()) {
            int samples = (int)iterator.next();
            System.out.println("Testing with " + samples + " elements");
            System.out.println("Running tests with an ArrayList implementation");
            List<String> list = new ArrayList();
            runTests(list,samples);
            System.out.println("Running tests with an ArrayList implementation with preset size");
            list = new ArrayList(samples);
            runTests(list,samples);
            System.out.println("Running tests with a LinkedList implementation");
            list = new LinkedList<>();
            runTests(list,samples);
            System.out.println("Running tests with a LinkedList implementation");
        }

        iterator = tests.iterator();
        while (iterator.hasNext()) {
            int samples = (int) iterator.next();
            TimeStamp ts = new TimeStamp();
            List<String> aCollection = new ArrayList();
            createCollection(aCollection, samples, ts);
            // reset ts not interested in creation timings
            ts.init();
            addToAndSearchCollection(aCollection,"Test", ts);
            // To someting the the ts.getPeriodTimes().

        }
    }

}
