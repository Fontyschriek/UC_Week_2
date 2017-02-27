# Opdracht meten

## Doel: Kennismaken met meten aan een verzameling.

## Inleiding.
Vaak is het mogelijk om verschillende implementaties voor een collectie te gebruiken om met je data te werken. Deze verschillende implementaties hebben echter gevolgen voor het geheugengebruik en snelheid. In deze opdracht gaan we naar de snelheid van verschillende implementaties kijken. In deze opdracht maken we gebruik van de timeutil package (zie sharepoint).

Je code gaan we nog vaker gebruiken. Ontwerp je code dus zo, dat dit gemakkelijk mogelijk is. Of sla je code veilig op in SVN of Git.

## Opdracht 1.1
Maak een eigen klasse ‘ CollectionUtil’  in de timeutil package en voeg een methode toe die een op te geven aantal unieke String objecten toevoegt aan een Collection:
addRandomStringsToCollection(int nrOfString, Collection aCollection). 

Alleen het aantal toe te voegen strings wordt meegegeven. De inhoud van die strings dient random bepaalt te worden. Zorg ervoor, dat de Strings niet te lang zijn (max 6 characters).

``` java
import java.util.Collection;
import java.util.Random;

/**
 * Created by Charles Korthout on 2/12/2017.
 */
public class CollectionUtil {

   final static int DEFAULT_STRING_LENGTH = 20;
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
}


```

Vraag je af:
### Wat is het returntype van de methode?
De methode heeeft geen return type, maar past de collectie aan
### Wat voor access modifiers heeft deze methode nodig en waarom? 
De methode heeft nu een public modifier, afhankelijk van de class kan het ook een protected zijn. Dit is afhankelijk van het gebruik van de methode

## Opdracht 1.2
Voeg een unit test toe die aantoont dat de methode werkt. De eerste test is om te kijken dat, wanneer we niets tovoegen de lijst leeg blijft.

``` java
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
}
```
Een tweede test is om een enkel element toe te voegen. De lengte van de lijst is dan 1.

``` java
@Test
    void testAddRandomStringsToCollectionWithSingleEntry() {
        int expected = 1;
        addRandomStringsToCollection(1,lijst);
        int actual = lijst.size();
        System.out.println("Eerste element : " + lijst.get(0));
        assertEquals(expected, actual);
    }
```

Het uitvoer resultaat is een willekeurige string van kleine en hoofdletters met een lengte van 20 karakters
- Eerste element : iVvwMstVlRPROIfRzbJf

## Opdracht 1.3
Kies een List implementatie (naar eigen keuze), en voeg onderstaande stappen 100x uit, zodat er statistisch relevante resultaten ontstaan:
-creer een list
-start een meting met timeStamp
-voeg met je methode uit 1.1 1.000 strings toe
-stop de meting met timeStamp
-verwijder de List.

``` java

final static int DEFAULT_STRING_LENGTH = 20;
final static List<Integer> tests = Arrays.asList(500,1000,2000, 5000, 10000, 20000, 50000, 100000 /**, 200000, 500000, 1000000, 2000000, 5000000 , 10000000*/);

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
    }

```

Het uitvoer resultaat is :

            ArrayList |        500 |         11 |          7 |         25 
            ArrayList |       1000 |         10 |          2 |          5 
            ArrayList |       2000 |          1 |          7 |          8 
            ArrayList |       5000 |          2 |        227 |        168 
            ArrayList |      10000 |          2 |        734 |        310 
            ArrayList |      20000 |          5 |       3148 |        890 
            ArrayList |      50000 |         16 |      13135 |       7782 
            ArrayList |     100000 |         56 |      42582 |      29018 
                 Name |   Elements |     Adding |  Searching |   Removing |
           LinkedList |        500 |          1 |          6 |          2 
           LinkedList |       1000 |          4 |          2 |         17 
           LinkedList |       2000 |          1 |         21 |          6 
           LinkedList |       5000 |          1 |         86 |         54 
           LinkedList |      10000 |          2 |        367 |        245 
           LinkedList |      20000 |          3 |       4151 |       2131 
           LinkedList |      50000 |         25 |      13783 |       8187 
           LinkedList |     100000 |         32 |     133753 |      95160 

Nu heb je 100x gemeten hoelang het duurt om een list te vullen.

Doe hetzelfde met het toevoegen van 2000 strings, 5000 strings, 10.000 strings, 20.000, 50.000, 100.000, 200.000, 500.000, 1.000.000, 2.000.000, 5.000.000 etc. totdat de looptijd te lang wordt. 
Noteer alle resultaten en geef deze weer in een grafiek.

Indien je JVM uit zijn heap geheugen loopt: voeg run parameters toe aan je project:
 (rechter muisklik op project, properties, run, arguments: –Xms en –Xmx). Zie duckduckgo/ixquick/google om te weten wat de JVM run arguments betekenen.
## Opdracht 1.4

Pak een andere List implementatie en doe hetzelfde als bij opdracht 1.3,  
Naast de standaard ArrayList en LinkedList implementatie heb ik ook de volgende collections toegevoegd:
- TreeSet
- Vector
- HashSet
- LinkedHashSet


Verklaar eventuele verschillen of het ontbreken ervan. (iets met ordes of zo)

``` java
public static void main(String[] args) {
        collectionTest(new ArrayList(), "ArrayList");
        collectionTest(new LinkedList(), "LinkedList");
        collectionTest(new TreeSet(), "TreeSet");
        collectionTest(new HashSet(), "HashSet");
        collectionTest(new Vector(), "Vector");        
    }
    
```
                 Name |   Elements |     Adding |  Searching |   Removing |
            ArrayList |        500 |         11 |         35 |          2 |
            ArrayList |       1000 |         26 |          2 |         13 |
            ArrayList |       2000 |          0 |         19 |         11 |
            ArrayList |       5000 |          2 |        293 |        105 |
            ArrayList |      10000 |          4 |        828 |        440 |
            ArrayList |      20000 |          5 |       4284 |       1094 |
            ArrayList |      50000 |         17 |      17082 |       8989 |
            ArrayList |     100000 |         56 |      46275 |      32231 |
                 Name |   Elements |     Adding |  Searching |   Removing |
           LinkedList |        500 |          0 |         14 |          1 |
           LinkedList |       1000 |          4 |         13 |         19 |
           LinkedList |       2000 |          0 |         31 |         21 |
           LinkedList |       5000 |          7 |        228 |         92 |
           LinkedList |      10000 |          7 |        538 |        303 |
           LinkedList |      20000 |          6 |       2038 |       1265 |
           LinkedList |      50000 |         12 |      29251 |      17223 |
           LinkedList |     100000 |         22 |     140310 |      83587 |
                 Name |   Elements |     Adding |  Searching |   Removing |
              TreeSet |        500 |          5 |          2 |          7 |
              TreeSet |       1000 |          0 |          0 |          1 |
              TreeSet |       2000 |         15 |          0 |          1 |
              TreeSet |       5000 |          4 |          5 |         31 |
              TreeSet |      10000 |          4 |          2 |          3 |
              TreeSet |      20000 |          9 |         12 |         11 |
              TreeSet |      50000 |         33 |         21 |         67 |
              TreeSet |     100000 |        133 |         62 |        108 |
                 Name |   Elements |     Adding |  Searching |   Removing |
              HashSet |        500 |          2 |          0 |          1 |
              HashSet |       1000 |          1 |          0 |          1 |
              HashSet |       2000 |          0 |          0 |          0 |
              HashSet |       5000 |         19 |         13 |         17 |
              HashSet |      10000 |          2 |          0 |          1 |
              HashSet |      20000 |          7 |          0 |          0 |
              HashSet |      50000 |         46 |          3 |         12 |
              HashSet |     100000 |         71 |          7 |         19 |
                 Name |   Elements |     Adding |  Searching |   Removing |
               Vector |        500 |          0 |         15 |          1 |
               Vector |       1000 |          1 |          1 |          1 |
               Vector |       2000 |          1 |         10 |          4 |
               Vector |       5000 |          4 |         73 |         40 |
               Vector |      10000 |          1 |        299 |        281 |
               Vector |      20000 |          3 |       1278 |        764 |
               Vector |      50000 |         12 |       5983 |       3943 |
               Vector |     100000 |         24 |      69271 |      40591 |
 
Zoals uit de bovenstaande resultaten blijkt scoren de TreeSet en HashSet erg goed in het zoeken en verwijderen van elementen.
Dit correspondeert met de metingen die in het boek Java Generics and Collections heeft, deze informatie (pagina: 188, 211, 222, 240).
geeft aan dat de LinkedList en ArrayList O(n) zijn terwijl de TreeSet en HashSet O(log(n)) zijn
List implementaties:

|                       | get  | add  | contains | next | remove(0) | iterator.remove |
| --------------------- |:----:|:----:|:--------:|:----:|:---------:|:---------------:|
| ArrayList             | O(1) | O(1) | O(n)     | O(1) | O(n)      | O(n)            |
| LinkedList            | O(n) | O(1) | O(n)     | O(1) | O(1)      | O(1)            |
| CopyOnWrite-ArrayList | O(1) | O(n) | O(n)     | O(1) | O(n)      | O(n)            |


Set implementaties:

|                      | add  |  contains | next   |  notes                     |
| -------------------- |:----:|:---------:|:------:| -------------------------- |
| HashSet              | O(1) |    O(1)   |  O(h/n)|  h is de tabel capaciteit  |
| LinkedHashSet        | O(1) |    O(1)   |  O(1)  |                            |
| CopyOnWriteArraySet  | O(n) |    O(n)   |  O(1)  |                            |
| EnumSet              | O(1) |    O(1)   |  O(1)  |                            |
| TreeSet              | O(log n)  | O(log n) | O(log n)  |                     | 
| ConcurrentSkipListSet  | O(log n) | O(log n) | O(1) |                         |

Map implementaties:

|                     | get     | containsKey  | next   |  Notes                |
| ------------------- |:-------:|:------------:|:------:| --------------------- |
| HashMap             |  O(1)   |  O(1)        | O(h/n) |  h is de tabel capaciteit  |
| LinkedHashMap       |  O(1)   |  O(1)        | O(1)   |                            |
| IdentityHashMap     |  O(1)   |  O(1)        | O(h/n) |  h is de tabel capaciteit  |
| EnumMap             | O(1)    | O(1)         | O(1) | |
| TreeMap             |  O(log n) | O(log n)   |  O(log n) ||
| ConcurrentHashMap   |   O(1)  |   O(1)       |  O(h/n) | h is de tabel capaciteit  |
| ConcurrentSkipListMap | O(log n) | O(log n)  |  O(1) ||

Queue implementations:

|                     | offer    | peek | poll    | size |
| ------------------- |:--------:|:----:|:-------:| ---- |
| PriorityQueue       |  O(log n) | O(1) | O(log n) | O(1) |
| ConcurrentLinkedQueue | O(1)    | O(1) | O(1)     | O(n) | 
| ArrayBlockingQueue    | O(1)    | O(1) | O(1)     | O(1) | 
| LinkedBlockingQueue   | O(1)    | O(1) | O(1)     | O(1) |
| PriorityBlockingQueue | O(log n) | O(1) | O(log n) | O(1) |
| DelayQueue            | O(log n) | O(1) | O(log n) | O(1) |
| LinkedList            | O(1)     | O(1) | O(1)     | O(1) |
| ArrayDeque            | O(1)     | O(1) | O(1)     | O(1) |
| LinkedBlockingDeque   | O(1)     |O(1)  | O(1)     | O(1) |

## Opdracht 1.5
Kies een List implementatie (naar eigen keuze), en voeg onderstaande stappen 100x uit, zodat er statistisch relevante resultaten ontstaan:

-creer een list
-voeg met je methode uit 1.1 1.000 strings toe

-start een meting met timeStamp
-voeg de string <jenaam>1 toe aan het begin van de collectie
-stop de meting met timeStamp
-start een meting met timeStamp
-voeg de string <jenaam>2 toe precies in het midden van de collectie
-stop de meting met timeStamp
-start een meting met timeStamp
-voeg de string <jenaam>3 toe aan het eind van de collectie
-stop de meting met timeStamp

-start een meting met timeStamp
-zoek de string <jenaam>3 in de collectie met methode contains()
-stop de meting met timeStamp
-start een meting met timeStamp
-zoek de string <jenaam>2 in de collectie met methode contains()
-stop de meting met timeStamp
-start een meting met timeStamp
-zoek de string <jenaam>1 in de collectie met methode contains()
-stop de meting met timeStamp

-verwijder de List.

Nu heb je 100x gemeten hoelang het duurt om aan een List op 3 verschillende plekken een bekende string toe te voegen, en deze later weer te vinden.

Doe hetzelfde met het toevoegen van 2000 strings, 5000 strings, 10.000 strings, 20.000, 50.000, 100.000, 200.000, 500.000, 1.000.000, 2.000.000, 5.000.000 etc. totdat de looptijd te lang wordt. 

Zet de verschillende metingen van contains() uit in een grafiek
Verklaar eventuele verschillen of het ontbreken ervan. (iets met ordes of zo)
``` java 

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

```

## Opdracht 1.6
Doe hetzelfde als 1.5, maar meet nu het verwijderen van de bekende strings.

Uiteindelijk opgenomen in vraag 1.5


## Opdracht 1.7
Doe hetzelfde als 1.5 en 1.6, maar nu met twee verschillende Set implementaties. Hou weer alle resultaten bij en zet ze uit in een grafiek. Verklaar verschillen tussen gebruik van de List en de Set implementaties.
Voor deze opdracht heb ik de TreeSet en de HashSet gekozen. 

## Opdracht 1.8 (optioneel)
Meet ook aan andere methoden die aangeboden worden door List en/of Set. Zoek hiertoe eerst methoden waarvan je denkt dat ze zeker verschillen zullen laten zien. Beargumenteer vooraf waarom je dit vindt, en voer dan pas de metingen uit.
Verklaar de gevonden resultaten. Doe dit ook voor methoden waarvan je zeker weet dat ze nauwelijks verschillen opleveren.

## Opdracht 1.9 (optioneel)
Breidt de klasse ‘CollectionUtil’ uit met een methode om ook een Map implementatie te vullen met bijvoorbeeld random Strings. Kies hiervoor zelf een mapping (bijv. De omgekeerde string als key), of gebruik een eigen klasse die gemakkelijker mapbaar is (bijv. Zoiets als de Persoon klasse uit SWE).  
Meet aan releveante methoden die aangeboden worden door een Map implementatie. Vergelijk je metingen met voorgaande metingen aan List en Set en zet ze uit in een grafiek. Verklaar verschillen en overeenkomsten.


