package com.example.project;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Question{
    private static int id = 0;
    private final String question;
    private final String type;

    public Question(String question, String type) {
        this.question = question;
        this.type = type;
        id++;
    }
    public static void resetId(){
        id = 0;
    }

    public static String print(int id) {
        String text = Question.getTextbyId(id);
        String type = Question.getTypebyId(id);

        StringBuilder answers = new StringBuilder("[");
        ArrayList<Integer> answersId = Question.getAnswersId(id);

        for (Integer idAnswer: answersId)
            answers.append(Answer.print(idAnswer)).append(", ");
        answers = new StringBuilder(answers.substring(0, answers.length() - 2));
        answers.append("]");

        return "{\"question-name\":\"" + text + "\", \"question_index\":\"" + id + "\", \"question_type\":\"" + type + "\", \"answers\":\""  + answers + "\"}";
    }

    private static String getTypebyId(int id) {
        //Caut in fisierul question.csv dupa id si in cazul in care il gasesc returnez tipul intrebarii
        try{
            File reader = new File("question.csv");
            Scanner scanner = new Scanner(reader);
            String searchText = " " + id + " ";
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                if(line.charAt(0) == 'I' && line.contains(searchText)) {
                    line = scanner.nextLine();
                    line = scanner.nextLine();
                    return line.substring(6, line.length() - 1);
                }
            }
            return null;
        }catch(Exception e){
            System.out.println("Error");
            return null;
        }
    }

    public static ArrayList<Integer> getAnswersId(int id) {
        //Caut in fisierul question.csv dupa id si in cazul in care il gasesc returnez id-urile raspunsurilor
        ArrayList<Integer> answersId = new ArrayList<>();
        try{
            File reader = new File("question.csv");
            Scanner scanner = new Scanner(reader);
            String searchText = " " + id + " ";
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                if(line.charAt(0) == 'I' && line.contains(searchText)) {
                    line = scanner.nextLine();
                    line = scanner.nextLine();
                    line = scanner.nextLine();
                    String[] data = line.split(" ");
                    for (String idAnswer : data) {
                        if(!idAnswer.contains("Answers"))
                            answersId.add(Integer.parseInt(idAnswer));
                    }
                }
            }
        }catch(FileNotFoundException e){
            System.out.println("Error");
        }
        return answersId;
    }

    public void addQuestion(ArrayList<Integer> answers) {
        try(FileWriter writer = new FileWriter("question.csv", true)){
            writer.write("Id: " + id + " " + "\n");

            writer.write("Text: " + this.question + " " + "\n");
            writer.write("Type: " + this.type + " " + "\n");

            StringBuilder answersId = new StringBuilder("Answers: ");
            for(Integer answerId: answers)
                answersId.append(answerId).append(" ");

            writer.write(answersId + "\n");
        }catch(Exception e){
            System.out.println("Error");
        }
    }
    public static int searchText(String text){
        //Caut in fisierul question.csv dupa text si in cazul in care il gasesc returnez id-ul intrebarii
        try{
            File reader = new File("question.csv");
            Scanner scanner = new Scanner(reader);
            String searchText = " " + text + " ";
            int auxId = -1;
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                if(line.contains("Id:"))
                    auxId = Integer.parseInt(line.substring(4, line.length() - 1));
                if(line.contains(searchText)) {
                    return auxId;
                }
            }
            return -1;

        } catch (FileNotFoundException e) {
            System.out.println("Error");
            return -1;
        }
    }

    public static String checkCommand(String[] args){
        String text = null;
        boolean gaveText = false;

        for (String command: args)
            if(command.indexOf("-text") == 0) {
                gaveText = true;
                text = command.substring(7, command.length() - 1);
            }

        if(!gaveText)
            System.out.println("{'status':'error','message':'No question text provided'}");

        return text;
    }

    public static String getTextbyId(int id){
        try{
            File reader = new File("question.csv");
            Scanner scanner = new Scanner(reader);
            String searchText = " " + id + " ";
            String auxText;
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                if(line.contains("Id:"))
                    if(line.contains(searchText)) {
                        line = scanner.nextLine();
                        auxText = line.substring(6, line.length() - 1);
                        return auxText;
                    }
            }
            return null;

        } catch (FileNotFoundException e) {
            System.out.println("Error");
            return null;
        }
    }
    public static ArrayList<Integer> getQuestionsId(){
        try{
            File reader = new File("question.csv");
            Scanner scanner = new Scanner(reader);
            ArrayList<Integer> ids = new ArrayList<>();
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                if(line.contains("Id:")) {
                    ids.add(Integer.parseInt(line.substring(4, line.length() - 1)));
                }
            }
            return ids;

        } catch (FileNotFoundException e) {
            System.out.println("Error");
            return null;
        }
    }
}
