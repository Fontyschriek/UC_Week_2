/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package map;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author erik
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Map<String,Persoon> mapje = new HashMap<>();
        //
        Persoon p = new Persoon(1, "erik", 2300);
        Persoon p2 = new Persoon(41, "evert", 3300);
        Persoon p3 = new Persoon(13, "rob", 4300);
        Persoon p4 = new Persoon(61, "johan", 1300);
        Persoon p5 = new Persoon(144, "roelof", 300);
        Persoon p6 = new Persoon(221, "peter", 300);
        Persoon p7 = new Persoon(1, "erik", 6300);
        
        // add keys + value to map
        mapje.put(p.getNaam(), p);
        mapje.put(p3.getNaam(),p3);
        mapje.put(p2.getNaam(),p2);
        mapje.put(p4.getNaam(),p4);
        mapje.put(p5.getNaam(),p5);
        mapje.put(p6.getNaam(),p6);
        mapje.put(p7.getNaam(),p7);
        
        //
        // zoek op key (naam), welke vind ik (p of p7 ?)
        Persoon pzoek = mapje.get("erik");
        System.out.println(pzoek);
        
        // aantal in collectie
        System.out.println(mapje.size());
        
        
        
        
    }
}
