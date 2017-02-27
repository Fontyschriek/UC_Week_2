package timeutil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Deze klasse maakt het mogelijk om opeenvolgende tijdsperiodes een naam te
 * geven, deze op te slaan en deze daarna te printen (via toString).
 *
 * Tijdsperiodes worden bepaald door een begintijd en een eindtijd.
 *
 * begintijd van een periode kan gezet worden door setBegin, de eindtijd kan
 * gezet worden door de methode setEind.
 *
 * Zowel bij de begin- als eindtijd van ee periode kan een String meegegeven
 * worden die voor de gebruiker een betekenisvolle aanduiding toevoegt aan dat
 * tijdstip. Indien geen string meegegeven wordt, wordt een teller gebruikt, die
 * automatisch opgehoogd wordt.
 *
 * Na het opgeven van een begintijdstip (via setBegin of eenmalig via init ) kan
 * t.o.v. dit begintijdstip steeds een eindtijdstip opgegeven worden. Zodoende
 * kun je vanaf 1 begintijdstip, meerdere eindtijden opgeven.
 *
 * Een andere mogelijkheid is om een eindtijdstip direct te laten fungeren als
 * begintijdstip voor een volgende periode. Dit kan d.m.v. SetEndBegin of seb.
 *
 * alle tijdsperiodes kunnen gereset worden dmv init()
 *
 * @author erik
 *
 */
public class TimeStamp {

    private static long counter = 0;
    private long curBegin;
    private String curBeginS;
    private List<Period> list;

    public TimeStamp() {
        TimeStamp.counter = 0;
        this.init();
    }

    /**
     * initialiseer klasse. begin met geen tijdsperiodes.
     */
    public void init() {
        this.curBegin = 0;
        this.curBeginS = null;
        this.list = new LinkedList<>();
    }

    /**
     * zet begintijdstip. gebruik interne teller voor identificatie van het
     * tijdstip
     */
    public void setBegin() {
        this.setBegin(String.valueOf(TimeStamp.counter++));
    }

    /**
     * zet begintijdstip
     *
     * @param timepoint betekenisvolle identificatie van begintijdstip
     */
    public void setBegin(String timepoint) {
        this.curBegin = System.currentTimeMillis();
        this.curBeginS = timepoint;
    }

    /**
     * Create a list of begin and end times to easily do some graphing
     * @return
     */
    public List<Pair<Long,Long>> getPeriodTimes() {
        List<Pair<Long,Long>> values = new ArrayList();
        list.forEach(b-> values.add(new Pair(b.getBegin().getLeft(), b.getEnd().getLeft())));
        return values;
    }

    /**
     * Create a list with durations to easily do some graphing
     * @return the list with durations
     */
    public List<Long> getDurations() {
        List<Long> values = new ArrayList();
        getPeriodTimes().forEach(b-> values.add(b.getRight() - b.getLeft()));
        return values;
    }

    /**
     * zet eindtijdstip. gebruik interne teller voor identificatie van het
     * tijdstip
     */
    public void setEnd() {
        this.setEnd(String.valueOf(TimeStamp.counter++));
    }

    /**
     * zet eindtijdstip
     *
     * @param timepoint betekenisvolle identificatie vanhet eindtijdstip
     */
    public void setEnd(String timepoint) {
        this.list.add(new Period(this.curBegin, this.curBeginS, System.currentTimeMillis(), timepoint));
    }

    /**
     * zet eindtijdstip plus begintijdstip
     *
     * @param timepoint identificatie van het eind- en begintijdstip.
     */
    public void setEndBegin(String timepoint) {
        this.setEnd(timepoint);
        this.setBegin(timepoint);
    }

    /**
     * verkorte versie van setEndBegin
     *
     * @param timepoint
     */
    public void seb(String timepoint) {
        this.setEndBegin(timepoint);
    }

    /**
     * interne klasse voor bijhouden van periodes.
     *
     * @author erik
     *
     */
    private class Period {

        Pair<Long, String> start;
        Pair<Long, String> stop;

        public Period(long b, String sb, long e, String se) {
            this.setBegin(new Pair(b,sb));
            this.setEnd(new Pair(e,se));
        }

        private void setBegin(Pair<Long,String> start) {
            this.start = start;
        }

        private Pair<Long,String> getBegin() {
            return start;
        }

        private void setEnd(Pair<Long,String> stop) {
            this.stop = stop;
        }

        private Pair<Long, String> getEnd() {
            return stop;
        }
        @Override
        public String toString() {
            return "From '" + this.start.getRight() + "' till '" + this.stop.getRight() + "' is " + (this.stop.getLeft() - this.start.getLeft()) + " mSec.";
        }
    }

    /**
     * override van toString methode. Geeft alle tijdsperiode weer.
     */
    public String toString() {
        StringBuffer buffer = new StringBuffer();

        for (Period p : this.list) {
            buffer.append(p.toString());
            buffer.append('\n');
        }
        return buffer.toString();
    }
}
