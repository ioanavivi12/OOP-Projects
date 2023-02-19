package org.example;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class Cerere implements Comparable<Cerere> {
    private String textCerere;
    private String data;
    private int prioritate;

    public Cerere(String textCerere, String data, int prioritate) {
        this.textCerere = textCerere;
        this.data = data;
        this.prioritate = prioritate;
    }

    public String getTextCerere() {
        return textCerere;
    }

    public String getData() {
        return data;
    }

    public int getPrioritate() {
        return prioritate;
    }
    @Override
    public int compareTo(Cerere o) {

            SimpleDateFormat format = new SimpleDateFormat("dd-MMMM-yyyy HH:mm:ss");
            try{
                Date data1 = format.parse(this.data);
                Date data2 = format.parse(o.data);
                if(data1.before(data2))
                    return -1;
                return 1;
            }
            catch(ParseException e) {
                return 0;
            }
    }
}

class CerereComparator implements Comparator<Cerere> {
    @Override
    public int compare(Cerere o1, Cerere o2) {
        if(o1.getPrioritate() < o2.getPrioritate())
            return 1;
        if(o1.getPrioritate() > o2.getPrioritate())
            return -1;
        return o1.compareTo(o2);
    }
}
