package com.example.project;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Quizz{

    private final String name;
    private static int id = 0;
    private static Queue<Integer> extraIds = new LinkedList<>();

    public Quizz(String name) {
        this.name = name;
        //Un quizz are un id unic care poate rezulta fie din incrementarea id-ului curent,
        // fie din extragerea unui id din coada celor ramase libere in urma stergerii unui quizz
        if(extraIds.isEmpty())
            id++;
        else {
            id = extraIds.remove();
        }
    }
    public static int getId() {
        return id;
    }
    public static void resetId(){
        id = 0;
        while(!extraIds.isEmpty())
            extraIds.remove();
    }

    public static String getTextbyId(int intValue) {
        //Caut in fisierul quizz.csv dupa id si in cazul in care il gasesc returnez textul de la linia urmatoare
        try {
            File file = new File("quizz.csv");
            Scanner scanner = new Scanner(file);
            String name = null;
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                if(line.charAt(0) == 'N')
                    name = line.substring(6, line.length() - 1);

                if (line.charAt(0) == 'I' && line.contains(Integer.toString(intValue))) {
                    return name;
                }
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error");
            return null;
        }
    }

    public static void deleteQuizz(int id) {
        //Citesc din fisierul quizze.csv si scriu in fisierul aux_quizz.csv toate quizzurile cu id diferit
        // de cel dat ca parametru (automat si restul datelor)
        try(Writer writer = new FileWriter("aux_quizz.csv", true)) {
            File file = new File("quizz.csv");
            Scanner scanner = new Scanner(file);

            String searchId = " " + id + " ";
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                if(line.charAt(0) == 'N') {
                    String line2 = scanner.nextLine();
                    if(line2.contains(searchId))
                        line = scanner.nextLine();
                    else {
                        writer.write(line + "\n");
                        writer.write(line2 + "\n");
                    }
                }
                else
                    writer.write(line + "\n");
            }

            Files.coppyFile("aux_quizz.csv", "quizz.csv");
            Files.eraseFile("aux_quizz.csv");
            //Adaug id-ul in lista de id-uri libere, care ar putea fi folosite de alte quizzuri
            extraIds.add(id);

        } catch (Exception e) {
            System.out.println("Error");
        }
    }

    public static ArrayList<Integer> getallQuizzId() {
        //Citesc din fisierul quizz.csv si adaug in lista de id-uri toate id-urile gasite
        ArrayList<Integer> ids = new ArrayList<>();
        try {
            File file = new File("quizz.csv");
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                if(line.charAt(0) == 'I')
                    ids.add(Integer.parseInt(line.substring(4, line.length() - 1)));
            }
            return ids;
        } catch (Exception e) {
            System.out.println("Error");
            return null;
        }
    }

    public void addQuizz(int[] questionsId) {
        //Scriu in fisierul quizz.csv numele quizzului, id-ul si id-urile intrebarilor
        try(FileWriter writer = new FileWriter("quizz.csv", true)){
            writer.write("Name: " + this.name + " " + "\n");
            writer.write("Id: " + id + " " + "\n");

            StringBuilder questions = new StringBuilder("Questions: ");
            for (int j : questionsId) {
                questions.append(j).append(" ");
            }
            writer.write(questions + "\n");

        }catch(Exception e){
            System.out.println("Error");
        }
    }
    public static int checkCommand(String[] command){
        String name = null;
        int[] questionsId = new int[10];
        int numQuestions = 0;
        for (String s : command) {
            if (s.indexOf("-name") == 0) {
                name = s.substring(7, s.length() - 1);
                if(Quizz.searchName(name) != -1){
                    System.out.println("{'status':'error','message':'Quizz name already exists'}");
                    return -1;
                }
            }
            if (s.indexOf("-question") == 0) {
                int index = Integer.parseInt(s.substring(10, 11));
                int questionId = Integer.parseInt(s.substring(13, s.length() - 1));

                if(numQuestions == 10) {
                    System.out.println("{'status':'error','message':'Quizz has more than 10 questions'}");
                    return -1;
                }
                if(Question.getTextbyId(questionId) == null) {
                    System.out.println("{'status':'error','message':' Question ID for question " + questionId + " does not exist'}");
                    return -1;
                }
                questionsId[index - 1] = questionId;
                numQuestions++;
            }
        }
        Quizz quizz = new Quizz(name);
        questionsId = java.util.Arrays.copyOf(questionsId, numQuestions);
        quizz.addQuizz(questionsId);
        return getId();
    }

    public static int searchName(String name) {
        //Caut in fisierul quizz.csv dupa nume si in cazul in care il gasesc returnez id-ul
        try{
            File reader = new File("quizz.csv");
            Scanner scanner = new Scanner(reader);
            String searchText = " " + name + " ";
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                if(line.charAt(0) == 'N' && line.contains(searchText)) {
                    line = scanner.nextLine();
                    return Integer.parseInt(line.substring(4, line.length() - 1));
                }
            }
            return -1;
        }catch (Exception e) {
            System.out.println("Error");
            return -1;
        }
    }

    public static String print(int id) {
        ArrayList<Integer> questionsId = Quizz.getQuestionsId(id);
        String quizz = "[";
        for (int i = 0; i < questionsId.size(); i++) {
            quizz += Question.print(questionsId.get(i));
            if(i != questionsId.size() - 1)
                quizz += ", ";
        }
        quizz += "]";
        return quizz;
    }

    static ArrayList<Integer> getQuestionsId(int id) {
        //Citesc din fisierul quizz.csv si adaug in lista de id-uri toate id-urile intrebarilor de la quizz-ul cu id-ul dat ca parametru
        try{
            File reader = new File("quizz.csv");
            Scanner scanner = new Scanner(reader);
            ArrayList<Integer> questionsId = new ArrayList<>();
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                if(line.charAt(0) == 'I' && line.contains(Integer.toString(id))) {
                    line = scanner.nextLine();
                    String[] data = line.split(" ");
                    for (String idQuestion : data) {
                        if(!idQuestion.contains("Questions:"))
                            questionsId.add(Integer.parseInt(idQuestion));
                    }
                    return questionsId;
                }
            }
            return null;
        }catch (Exception e) {
            System.out.println("Error");
            return null;
        }
    }

}
