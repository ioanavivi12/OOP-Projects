package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class CentruEvenimente {
    private String locatie;
    private PriorityQueue<Eveniment> evenimente = new PriorityQueue<Eveniment>();
    public CentruEvenimente(String locatie) {
        this.locatie = locatie;
    }

    public String getLocatie() {
        return locatie;
    }

    /**
     *
     * @param dataInceput
     * @param dataSfarsit
     * @return - true daca nu exista evenimente in intervalul de timp specificat
     *        - false daca exista evenimente in intervalul de timp specificat
     */
    private boolean verificaDisponibilitate(String dataInceput, String dataSfarsit) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MMMM-yyyy");
        for(Eveniment e : evenimente) {
            try{
                if((format.parse(dataInceput).after(format.parse(e.getDataInceput())) || dataInceput.equals(e.getDataInceput()) )
                        && (format.parse(dataInceput).before(format.parse(e.getDataSfarsit())) || dataInceput.equals(e.getDataSfarsit())))
                    return false;
                if((format.parse(dataSfarsit).after(format.parse(e.getDataInceput())) || dataSfarsit.equals(e.getDataInceput()))
                        && (format.parse(dataSfarsit).before(format.parse(e.getDataSfarsit()))) || dataSfarsit.equals(e.getDataSfarsit()))
                    return false;
            }
            catch(Exception ex) {
                return false;
            }
        }
        return true;
    }

    /**
     *
     * @param nume
     * @param dataInceput
     * @param dataSfarsit
     * @return "ok" daca evenimentul a fost adaugat cu succes
     *  un mesaj de eroare in cazul in care exista un eveniment in perioada respectiva
     */
    public String adaugaEveniment(String nume, String dataInceput, String dataSfarsit) {
        if(verificaDisponibilitate(dataInceput, dataSfarsit)) {
            evenimente.add(new Eveniment(nume, dataInceput, dataSfarsit));
            return "ok";
        }
        else
            return "Evenimentul " + nume + " nu poate fi creat pentru ca se suprapune cu unul din evenimentele existente in locatia " + locatie;
    }

    /**
     *
     * @param nume
     * @return Cauta prin coada cu evenimente cu numele specificat si il sterge
     */
    public void stergeEveniment(String nume) {
        for (Eveniment e : evenimente) {
            if (e.getNume().equals(nume)) {
                evenimente.remove(e);
                break;
            }
        }
    }

    /**
     * Afiseaza toate evenimentele din coada de evenimente, in ordine cronologica
     */
    public void afiseazaToateEvenimentele(String outputPath) {
        try{
            File file = new File(outputPath);
            FileWriter fileWriter = new FileWriter(file, true);
            if(evenimente.size() == 0) {
                fileWriter.write("Nu exista evenimente in locatia " + locatie + "\n");
                fileWriter.close();
                return;
            }
            fileWriter.write("Evenimentele din " + locatie + " sunt: \n");
            PriorityQueue<Eveniment> evenimenteCopy = new PriorityQueue<Eveniment>(evenimente);
            while(!evenimenteCopy.isEmpty()) {
                Eveniment e = evenimenteCopy.poll();
                fileWriter.write(e.getDataInceput() + " " + e.getDataSfarsit() + " " + e.getNume() + " " +  "\n");
            }
            fileWriter.close();
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Afiseaza toate evenimentele din coada de evenimente, care au loc in data specificata ca si parametru
     */
    public void afiseazaEvenimenteDupaData(String outputPath, String command) {
        try{
            File file = new File(outputPath);
            FileWriter fileWriter = new FileWriter(file, true);
            int ok = 0;
            PriorityQueue<Eveniment> evenimenteCopy = new PriorityQueue<Eveniment>(evenimente);
            while(!evenimenteCopy.isEmpty()) {
                Eveniment e = evenimenteCopy.poll();
                if(verificaData(e.getDataInceput(), e.getDataSfarsit(), command)) {
                    if(ok == 0)
                        fileWriter.write("Evenimentele din " + locatie + " sunt: \n");
                    fileWriter.write(e.getDataInceput() + " " + e.getDataSfarsit() + " " + e.getNume() + " " + "\n");
                    ok = 1;
                }
            }

            if(ok != 1)
                fileWriter.write("Nu exista evenimente in data de " + command + " in locatia " + locatie + "\n");

            fileWriter.close();
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Verifica daca data specificata ca si parametru se afla in intervalul de timp al evenimentului
     */
    private boolean verificaData(String dataInceput, String dataSfarsit, String command) {
        SimpleDateFormat format = new SimpleDateFormat("dd-MMMM-yyyy");
        try{
            if((format.parse(command).after(format.parse(dataInceput)) || command.equals(dataInceput))
                    && (format.parse(command).before(format.parse(dataSfarsit)) || command.equals(dataSfarsit)))
                return true;
        }
        catch(Exception e) {
            return false;
        }
        return false;
    }
}
