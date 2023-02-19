package com.example.project;

import java.io.FileWriter;
import java.io.File;
import java.util.Scanner;
public class Files {

    public static void eraseFile(String fileName) {
        //Sterge continutul fisierului cu numele fileName
        try(FileWriter writer = new FileWriter(fileName, false)){
            writer.write("");
        }catch(Exception e){
            System.out.println("Error");
        }
    }

    public static void coppyFile(String source, String destionation) {
        //Copiaza continutul fisierului source in fisierul destionation
        try(FileWriter writer = new FileWriter(destionation)){
            File reader = new File(source);
            Scanner scanner = new Scanner(reader);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                writer.write(line + "\n");
            }
        }catch(Exception e){
            System.out.println("Error");
        }
    }

}
