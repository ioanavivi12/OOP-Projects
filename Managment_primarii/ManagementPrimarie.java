package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class ManagementPrimarie {
    private String inputPath;
    private String outputPath;
    private ArrayList<Utilizator> utilizatori = new ArrayList<Utilizator>();
    private Birou<Utilizator> birou[];

    /**
     Pastrez 3 obiecte de tip CentruEvenimente, pentru a tine cont de 3 locatii diferite
     */
    private CentruEvenimente centruEvenimente[];
    /**
     * Construiesc un obiect de tip ManagementPrimarie, care are un fisier de input, unul de output si un array de birouri
    */
    public ManagementPrimarie(String fileName) {
        this.inputPath = "src/main/resources/input/" + fileName;
        this.outputPath = "src/main/resources/output/" + fileName;
        birou = new Birou[]{new Birou<Angajat>(), new Birou<Elev>(), new Birou<EntitateJuridica>(), new Birou<Pensionar>(), new Birou<Persoana>()};

        //BONUS
        centruEvenimente = new CentruEvenimente[]{new CentruEvenimente("parc"), new CentruEvenimente("casa de cultura"), new CentruEvenimente("centrul orasului")};
    }
    public static void main(String[] args) throws IOException, ParseException {
        try{
            String fileName = args[0];
            ManagementPrimarie managementPrimarie = new ManagementPrimarie(fileName);
            managementPrimarie.resetFile(managementPrimarie.outputPath);
            managementPrimarie.extractDataFromFile();
        }catch (IOException e){
            e.printStackTrace();
        }
        catch (ParseException e){
            e.printStackTrace();
        }
    }

    /**
     Metoda citeste informatiile din fisierul de input si le proceseaza
    */
    private void extractDataFromFile() throws IOException, ParseException {
        try{
            File fileReader = new File(this.inputPath);
            Scanner scanner = new Scanner(fileReader);

            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] commands = line.split("; ");

                if(commands[0].equals("adauga_functionar")){
                    Birou<Utilizator> birou = this.getBirou(commands[1]);
                    birou.adaugaFunctionarPublic(new FunctionarPublic<Utilizator>(commands[2]));
                }

                if(commands[0].equals("adauga_utilizator")) {
                    if(commands[1].equals("angajat")) {
                        utilizatori.add(new Angajat(commands[2], commands[3]));
                    }
                    else if(commands[1].equals("elev")) {
                        utilizatori.add(new Elev(commands[2], commands[3]));
                    }
                    else if(commands[1].equals("pensionar")) {
                        utilizatori.add(new Pensionar(commands[2]));
                    }
                    else if(commands[1].equals("entitate juridica")) {
                        utilizatori.add(new EntitateJuridica(commands[2], commands[3]));
                    }
                    else if(commands[1].equals("angaajat")) {
                        utilizatori.add(new Angajat(commands[2], commands[3]));
                    }
                    else if(commands[1].equals("persoana")) {
                        utilizatori.add(new Persoana(commands[2]));
                    }
                }

                if(commands[0].equals("cerere_noua")) {
                    Utilizator utilizator = this.getUser(commands[1]);
                    String rezultat = utilizator.creareCerere(commands[2], commands[3], Integer.parseInt(commands[4]), getBirou(utilizator.getTipUtilizator()));
                    if(!rezultat.equals("ok")){
                        insertDataInFile(rezultat);
                    }
                }

                if(commands[0].equals("afiseaza_cereri_in_asteptare")) {
                    Utilizator utilizator = this.getUser(commands[1]);
                    utilizator.afiseazaCereriInAsteptare(this.outputPath);
                }

                if(commands[0].equals("retrage_cerere")) {
                    Utilizator utilizator = this.getUser(commands[1]);
                    utilizator.retrageCerere(commands[2]);
                    getBirou(utilizator.getTipUtilizator()).retragereCerere(commands[2]);
                }

                if(commands[0].equals("afiseaza_cereri")) {
                    Birou<Utilizator> birou = this.getBirou(commands[1]);
                    birou.afiseazaCereri(this.outputPath, commands[1]);
                }

                if(commands[0].equals("rezolva_cerere")) {
                    Birou<Utilizator> birou = this.getBirou(commands[1]);
                    birou.rezolvaCerere(commands[2], "src/main/resources/output/");
                }

                if(commands[0].equals("afiseaza_cereri_finalizate")) {
                    Utilizator utilizator = this.getUser(commands[1]);
                    utilizator.afiseazaCereriFinalizate(this.outputPath);
                }

                //BONUS
                if(commands[0].equals("adauga_eveniment")){
                    CentruEvenimente centruEvenimente = this.getCentruEvenimente(commands[4]);
                    String result = centruEvenimente.adaugaEveniment(commands[1], commands[2], commands[3]);
                    if(!result.equals("ok")){
                        insertDataInFile(result);
                    }
                }

                if(commands[0].equals("sterge_eveniment")){
                    CentruEvenimente centruEvenimente = this.getCentruEvenimente(commands[2]);
                    centruEvenimente.stergeEveniment(commands[1]);
                }

                if(commands[0].equals("afiseaza_toate_evenimentele")){
                    centruEvenimente[0].afiseazaToateEvenimentele(this.outputPath);
                    centruEvenimente[1].afiseazaToateEvenimentele(this.outputPath);
                    centruEvenimente[2].afiseazaToateEvenimentele(this.outputPath);
                }

                if(commands[0].equals("afiseaza_evenimente_dupa_locatie")) {
                    CentruEvenimente centruEvenimente = this.getCentruEvenimente(commands[1]);
                    centruEvenimente.afiseazaToateEvenimentele(this.outputPath);
                }

                if(commands[0].equals("afiseaza_evenimente_dupa_data")) {
                    centruEvenimente[0].afiseazaEvenimenteDupaData(this.outputPath, commands[1]);
                    centruEvenimente[1].afiseazaEvenimenteDupaData(this.outputPath, commands[1]);
                    centruEvenimente[2].afiseazaEvenimenteDupaData(this.outputPath, commands[1]);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private CentruEvenimente getCentruEvenimente(String command) {
        for(CentruEvenimente centruEvenimente : this.centruEvenimente){
            if(centruEvenimente.getLocatie().equals(command)){
                return centruEvenimente;
            }
        }
        return null;
    }

    /**
     * @param tip - String ce reprezinta tipul utilizatorului
     * @return - returneaza un obiect de tip Birou, in functie de tipul utilizatorului
     */
    public Birou<Utilizator> getBirou(String tip) {
        if(tip.equals("angajat")) {
            return birou[0];
        }
        else if(tip.equals("elev")) {
            return birou[1];
        }
        else if(tip.equals("pensionar")) {
            return birou[3];
        }
        else if(tip.equals("entitate juridica")) {
            return birou[2];
        }
        else if(tip.equals("persoana")) {
            return birou[4];
        }
        return null;
    }

    /**
     * Introduce stringul primit ca si parametru in fisierul de output
     */
    private void insertDataInFile(String data) throws IOException {
        try{
            File fileWriter = new File(this.outputPath);
            FileWriter fileWriter1 = new FileWriter(fileWriter, true);
            fileWriter1.write(data + "\n" );
            fileWriter1.close();
        }catch (IOException e){
            System.out.println("Eroare");
        }
    }

    /**
     * @param Name - String ce reprezinta numele utilizatorului
     * @return - returneaza un obiect de tip Utilizator, in functie de numele utilizatorului
     */
    private Utilizator getUser(String Name){
        for(Utilizator utilizator : utilizatori){
            if(utilizator.getNume().equals(Name))
                return utilizator;
        }
        return null;
    }

    private void resetFile(String filename){
        try{
            File fileWriter = new File(filename);
            FileWriter fileWriter1 = new FileWriter(fileWriter, false);
            fileWriter1.write("");
            fileWriter1.close();
        }catch (IOException e){
            System.out.println("Eroare");
        }
    }
}
