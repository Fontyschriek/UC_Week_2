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

//**
      * Run tests according the predefined list of samples
      * @param aCollection The collection to test
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
 
 
     public static void main(String[] args) {
         List<Integer> tests = Arrays.asList(500,1000,5000,10000,25000,100000, 1000000, 10000000);
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
         }
     }


```

Het uitvoer resultaat is :


Nu heb je 100x gemeten hoelang het duurt om een list te vullen.

Doe hetzelfde met het toevoegen van 2000 strings, 5000 strings, 10.000 strings, 20.000, 50.000, 100.000, 200.000, 500.000, 1.000.000, 2.000.000, 5.000.000 etc. totdat de looptijd te lang wordt. 
Noteer alle resultaten en geef deze weer in een grafiek.

Indien je JVM uit zijn heap geheugen loopt: voeg run parameters toe aan je project:
 (rechter muisklik op project, properties, run, arguments: –Xms en –Xmx). Zie duckduckgo/ixquick/google om te weten wat de JVM run arguments betekenen.
## Opdracht 1.4

Pak een andere List implementatie en doe hetzelfde als bij opdracht 1.3,  
Naast de standaard ArrayList implementatie heb ik ook de implementatie met vooraf gedefinieerde grote ingevoerd. Hiermee hoop ik het verschil te laten zien in de kosten voor de implementatie in het vergroten van de standaard array.

Verklaar eventuele verschillen of het ontbreken ervan. (iets met ordes of zo)

### Testing with 500 elements
- Running tests with an ArrayList implementation; From '0' till 'Finished processing 500 elements' is 9 mSec.
- Running tests with an ArrayList implementation with preset size; From '0' till 'Finished processing 500 elements' is 11 mSec.
- Running tests with a LinkedList implementation;  From '0' till 'Finished processing 500 elements' is 1 mSec.
### Testing with 1000 elements
- Running tests with an ArrayList implementation; From '0' till 'Finished processing 1000 elements' is 5 mSec.
- Running tests with an ArrayList implementation with preset size; From '0' till 'Finished processing 1000 elements' is 1 mSec.
- Running tests with a LinkedList implementation; From '0' till 'Finished processing 1000 elements' is 1 mSec.
### Testing with 5000 elements
- Running tests with an ArrayList implementation; From '0' till 'Finished processing 5000 elements' is 8 mSec.
- Running tests with an ArrayList implementation with preset size; From '0' till 'Finished processing 5000 elements' is 1 mSec.
- Running tests with a LinkedList implementation; From '0' till 'Finished processing 5000 elements' is 8 mSec.
### Testing with 10000 elements
- Running tests with an ArrayList implementation; From '0' till 'Finished processing 10000 elements' is 4 mSec.
- Running tests with an ArrayList implementation with preset size; From '0' till 'Finished processing 10000 elements' is 4 mSec.
- Running tests with a LinkedList implementation;From '0' till 'Finished processing 10000 elements' is 8 mSec.
### Testing with 25000 elements
- Running tests with an ArrayList implementation; From '0' till 'Finished processing 25000 elements' is 12 mSec.
- Running tests with an ArrayList implementation with preset size; From '0' till 'Finished processing 25000 elements' is 6 mSec.
- Running tests with a LinkedList implementation; From '0' till 'Finished processing 25000 elements' is 21 mSec.
### Testing with 100000 elements
- Running tests with an ArrayList implementation; From '0' till 'Finished processing 100000 elements' is 150 mSec.
- Running tests with an ArrayList implementation with preset size;From '0' till 'Finished processing 100000 elements' is 15 mSec.
- Running tests with a LinkedList implementation; From '0' till 'Finished processing 100000 elements' is 96 mSec.
### Testing with 1000000 elements
- Running tests with an ArrayList implementation; From '0' till 'Finished processing 1000000 elements' is 588 mSec.
- Running tests with an ArrayList implementation with preset size; From '0' till 'Finished processing 1000000 elements' is 573 mSec.
- Running tests with a LinkedList implementation; From '0' till 'Finished processing 1000000 elements' is 642 mSec.
### Testing with 10000000 elements
- Running tests with an ArrayList implementation; From '0' till 'Finished processing 10000000 elements' is 8954 mSec.
- Running tests with an ArrayList implementation with preset size; From '0' till 'Finished processing 10000000 elements' is 6460 mSec.
- Running tests with a LinkedList implementation; From '0' till 'Finished processing 10000000 elements' is 5906 mSec.

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


## Opdracht 1.6
Doe hetzelfde als 1.5, maar meet nu het verwijderen van de bekende strings.



## Opdracht 1.7
Doe hetzelfde als 1.5 en 1.6, maar nu met twee verschillende Set implementaties. Hou weer alle resultaten bij en zet ze uit in een grafiek. Verklaar verschillen tussen gebruik van de List en de Set implementaties.


## Opdracht 1.8 (optioneel)
Meet ook aan andere methoden die aangeboden worden door List en/of Set. Zoek hiertoe eerst methoden waarvan je denkt dat ze zeker verschillen zullen laten zien. Beargumenteer vooraf waarom je dit vindt, en voer dan pas de metingen uit.
Verklaar de gevonden resultaten. Doe dit ook voor methoden waarvan je zeker weet dat ze nauwelijks verschillen opleveren.

## Opdracht 1.9 (optioneel)
Breidt de klasse ‘CollectionUtil’ uit met een methode om ook een Map implementatie te vullen met bijvoorbeeld random Strings. Kies hiervoor zelf een mapping (bijv. De omgekeerde string als key), of gebruik een eigen klasse die gemakkelijker mapbaar is (bijv. Zoiets als de Persoon klasse uit SWE).  
Meet aan releveante methoden die aangeboden worden door een Map implementatie. Vergelijk je metingen met voorgaande metingen aan List en Set en zet ze uit in een grafiek. Verklaar verschillen en overeenkomsten.


