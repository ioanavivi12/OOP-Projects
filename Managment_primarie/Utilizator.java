package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.PriorityQueue;

public abstract class Utilizator {
    /**
     * Coada de prioritate care pune cererile care se afla in asteptare in ordine cronologica
     */
    PriorityQueue<Cerere> cereriInAsteptare = new PriorityQueue<Cerere>();
    /**
     * Coada de prioritate care pune cererile rezolvate in ordine cronologica
     */
    PriorityQueue<Cerere> cereriRezolvate = new PriorityQueue<Cerere>();
    /**
    Enumeratie cu toate tipuririle de cereri
    */
     enum TipCerere {
        buletin ("inlocuire buletin"), venit ("inregistrare venit salarial"), sofer ("inlocuire carnet de sofer"), elev("inlocuire carnet de elev"), constituiv("creare act constitutiv"), autorizatie("reinnoire autorizatie"), pensie("inregistrare cupoane de pensie");
        private String tipCerere;

        public String getTipCerere() {
            return tipCerere;
        }
        TipCerere(String tipCerere){
            this.tipCerere = tipCerere;
        }
    }
    private String nume;

    public Utilizator(String nume) {
        this.nume = nume;
    }

    /**
     * @param tipCerere - Un string care reprezinta tipul cererii
     * @return - Un obiect din enumeratie, corespunzator tipului cererii
     */
    public TipCerere getTipCerere(String tipCerere) {
        for(TipCerere tip : TipCerere.values()) {
            if(tip.getTipCerere().equals(tipCerere))
                return tip;
        }
        return null;
    }

    public String getNume() {
        return nume;
    }

    /**
     * @param tipCerere - Un element din enumeratie care indica tipul cererii
     * @return Un string care contine textul cererii personalizat pentru fiecare tip de utilizator
     * @throws IllegalArgumentException - Daca tipul cererii nu este valid pentru utilizatorul curent
     */
    public abstract String textCerere(TipCerere tipCerere) throws IllegalArgumentException;

    /**
     * @param tipCerere - String care reprezinta tipul cererii
     * @param data - Data la care s-a inaintat cererea
     * @param prioritate - Prioritatea cererii
     * @param birou - Biroul specializat cererii
     * @return - "ok" daca cererea a fost inaintata cu succes, un mesaj de eroare personalizat in cazul in care meroda textCerere a aruncat o exceptie
     */
    public String creareCerere(String tipCerere, String data, int prioritate, Birou <Utilizator> birou){
        try{
            String textCerere = this.textCerere(getTipCerere(tipCerere));
            Cerere cerere = new Cerere(textCerere, data, prioritate);

            cereriInAsteptare.add(cerere);
            birou.adaugaCerere(cerere, this);
            return "ok";
        }
        catch(IllegalArgumentException e){
            return e.getMessage();
        }
    }

    /**
      Metoda adauga un obiect de tip Cerere in coada de prioritati a cererilor rezolvate si o stergeti din coada de prioritati a cererilor in asteptare
     */
    public void adaugaCerereRezolvata(Cerere cerere){
        cereriRezolvate.add(cerere);
        cereriInAsteptare.remove(cerere);
    }

    /**
     Metoda care verifica pentru fiecare tip de utilizator daca cererea este permisa
     */
    public abstract boolean verificareTipCerere(TipCerere tipCerere);

    /**
     Se parcurge coada de prioritati a cererilor in asteptare.
     Se afiseaza data si textul fiecarei cererii
     */
    public void afiseazaCereriInAsteptare(String fileName) throws IOException {
        try {
            File fileWriter = new File(fileName);
            FileWriter file = new FileWriter(fileWriter,true);
            file.write(this.getNume() + " - cereri in asteptare:\n");

            PriorityQueue<Cerere> auxQueue = new PriorityQueue<>(cereriInAsteptare);
            while(!auxQueue.isEmpty()){
                Cerere cerere = auxQueue.poll();
                file.write(cerere.getData() + " - " + cerere.getTextCerere() + "\n");
            }
            file.close();
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Se parcurge coada de prioritati a cererilor rezolvate.
     * Se afiseaza data si textul fiecarei cererii
     */
    public void afiseazaCereriFinalizate(String outputPath) {
        try {
            File fileWriter = new File(outputPath);
            FileWriter file = new FileWriter(fileWriter,true);
            file.write(this.getNume() + " - cereri in finalizate:\n");

            PriorityQueue<Cerere> auxQueue = new PriorityQueue<>(cereriRezolvate);
            while(!auxQueue.isEmpty()){
                Cerere cerere = auxQueue.poll();
                file.write(cerere.getData() + " - " + cerere.getTextCerere() + "\n");
            }
            file.close();
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Metoda parcurge coada de prioritati a cererilor in asteptare si elimina cerea facuta la data oferita ca parametru
    */
     public void retrageCerere(String data) {
        for (Cerere cerere : cereriInAsteptare)
            if(cerere.getData().equals(data)) {
                cereriInAsteptare.remove(cerere);
                break;
            }

    }
    /**
    Metoda returneaza un String cu numele tipului de utilizator
     */
    public abstract String getTipUtilizator();

}
