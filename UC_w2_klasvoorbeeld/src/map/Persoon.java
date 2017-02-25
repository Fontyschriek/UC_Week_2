/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package map;

import java.util.GregorianCalendar;

/**
 *
 * @author erik
 */
public class Persoon {
    private int persnr;
    private String naam;
    private long salaris;

    public Persoon(int persnr, String naam, long salaris) {
        this.persnr = persnr;
        this.naam = naam;
        this.salaris = salaris;
    }

 

    public String getNaam() {
        return naam;
    }

    public int getPersnr() {
        return persnr;
    }
    
    @Override
    public String toString(){
        return this.persnr+this.naam+this.salaris;
    }
    
}
    