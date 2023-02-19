package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Birou<E extends Utilizator>{
    /**
     * Coada de prioritate cu toate cererile biroului respectiv, ordonate in primul rand dupa prioritate,
     * iar la prioritati egale dupa data
     */
    private PriorityQueue<Cerere> cereri = new PriorityQueue<Cerere>(new CerereComparator());

    /**
     * O colectie cu toti functionarii biroului respectiv
     */
    private ArrayList<FunctionarPublic<E>> functionariPublici = new ArrayList<>();
    /**
     * Mapez fiecarei cereri utilizatorul care a creat-o
     */
    private HashMap<Cerere, Utilizator> maps = new HashMap<Cerere, Utilizator>();

    /**
     * Adaug cererea in coada de prioritati si o mapez cu utilizatorul care a creat-o
     */
    public void adaugaCerere(Cerere cerere, Utilizator utilizator) {
        cereri.add(cerere);
        maps.put(cerere, utilizator);
    }

    /**
     * Adaug un functionar public in colectia de functionari publici
     */
    public void adaugaFunctionarPublic(FunctionarPublic<E> functionarPublic) {
        functionariPublici.add(functionarPublic);
    }

    /**
     Parcurg coada de cereri si afisez prioritatea, data si texul fiecarei cereri
     */
    public void afiseazaCereri(String fileName, String type) {
        try{
            File fileWriter = new File(fileName);
            FileWriter file = new FileWriter(fileWriter,true);
            file.write(type + " - cereri in birou:\n");
            PriorityQueue<Cerere> cereriCopy = new PriorityQueue<Cerere>(cereri);
            while(!cereriCopy.isEmpty()) {
                Cerere cerere = cereriCopy.poll();
                file.write(cerere.getPrioritate() + " - " + cerere.getData() + " - " + cerere.getTextCerere() + "\n");
            }
            file.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Parcurg coada de cereri si, daca gasesc una cu data egala cu data ceruta, o sterg din coada de prioritati si ii sterg si maparea
     */
    public void retragereCerere(String data){
        for (Cerere cerere: cereri)
            if(cerere.getData().equals(data)){
                cereri.remove(cerere);
                maps.remove(cerere);
                break;
            }
    }

    /**
     * Extrag din coada de cereri pe cea din fata si ii scriu informatille in fisierul cerut. Dupa o sterg din coada de prioritati a utilizatorului si din mapare
     */
    public void rezolvaCerere(String command, String path) {
        try{
            Cerere cerere = cereri.poll();
            File fileWriter = new File(path + "functionar_" + command + ".txt");
            FileWriter file = new FileWriter(fileWriter,true);
            file.write(cerere.getData() + " - " + maps.get(cerere).getNume() + "\n");
            file.close();
            maps.get(cerere).adaugaCerereRezolvata(cerere);
            maps.remove(cerere);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
