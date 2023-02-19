package org.example;

import java.text.SimpleDateFormat;

public class Eveniment implements Comparable<Eveniment> {
    private String nume;
    private String dataInceput;
    private String dataSfarsit;

    public Eveniment(String nume, String data_inceput, String data_sfarsit) {
        this.nume = nume;
        this.dataInceput = data_inceput;
        this.dataSfarsit = data_sfarsit;
    }

    public String getNume() {
        return nume;
    }

    public String getDataInceput() {
        return dataInceput;
    }

    public String getDataSfarsit() {
        return dataSfarsit;
    }

    @Override
    public int compareTo(Eveniment o) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MMMM-yyyy");
        try{
            if(format.parse(this.dataInceput).before(format.parse(o.dataInceput)))
                return -1;
            return 1;
        }
        catch(Exception e) {
            return 0;
        }
    }
}
