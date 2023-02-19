package com.example.project;

import java.io.FileWriter;
import java.io.File;
import java.io.Writer;
import java.util.Scanner;
public class User{
    private final String username;
    private final String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public static String completedQuizz(String username, String password, int id) {
        try {
            File reader = new File("user.csv");
            Scanner scanner = new Scanner(reader);
            //Caut username si password in fisierul user.csv si verific daca au completat deja quizz-ul cu id-ul id
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if(line.charAt(0) == 'U' && line.contains(username)) {
                    line = scanner.nextLine();
                    if(line.charAt(0) == 'P' && line.contains(password)) {
                        line = scanner.nextLine();
                        line = scanner.nextLine();
                        //Daca au completat deja quizz-ul cu id-ul id, returnez "true"
                        if(line.charAt(0) == 'A' && line.contains(Integer.toString(id))) {
                            return "True";
                        }
                        return "False";
                    }
                }
            }
            return "False";
        }
        catch (Exception e) {
            System.out.println("Error");
            return "False";
        }
    }

    public static boolean isMyQuizz(String username, String password, int id) {
        //Caut username si password in fisierul user.csv si verific daca au creat deja quizz-ul cu id-ul id
        try {
            File reader = new File("user.csv");
            Scanner scanner = new Scanner(reader);
            String searchText = " " + id + " ";
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if(line.charAt(0) == 'U' && line.contains(username)) {
                    line = scanner.nextLine();
                    if(line.charAt(0) == 'P' && line.contains(password)) {
                        line = scanner.nextLine();
                        if(line.contains(searchText)) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
        catch (Exception e) {
            System.out.println("Error");
            return false;
        }
    }

    public static void deleteQuizz(String username, String password, int id) {
        //Caut username si password in fisierul user.csv si sterg quizz-ul cu id-ul id
        //Pentru asta ma folosesc de fisierul aux_user.csv in care scriu toate datele din user.csv, cu exceptia quizz-ului cu id-ul id
        try(Writer writer = new FileWriter("aux_user.csv")) {
            File reader = new File("user.csv");
            Scanner scanner = new Scanner(reader);
            String searchText = " " + id + " ";

            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                writer.write(line + "\n");
                if(line.charAt(0) == 'U' && line.contains(username)) {
                    line = scanner.nextLine();
                    writer.write(line + "\n");
                    if(line.charAt(0) == 'P' && line.contains(password)) {
                       line = scanner.nextLine();
                       line.replace(searchText, " ");
                       writer.write(line + "\n");

                       line = scanner.nextLine();
                       String[] ansQuizz = line.split(" ");
                       StringBuilder newAnsQuizz = new StringBuilder();
                       line = scanner.nextLine();
                       String[] scores = line.split(" ");
                       StringBuilder newScores = new StringBuilder();

                       for(int i = 0; i < ansQuizz.length; i++) {
                           if(!ansQuizz[i].equals(Integer.toString(id))) {
                               newAnsQuizz.append(ansQuizz[i]).append(" ");
                               newScores.append(scores[i]).append(" ");
                           }
                       }
                       writer.write(newAnsQuizz + "\n");
                       writer.write(newScores + "\n");
                    }
                }
            }

            //Inlocuiesc datele din fisierul user.csv cu datele din fisierul aux_user.csv
            Files.coppyFile("aux_user.csv", "user.csv");
            Files.eraseFile("aux_user.csv");
        }
        catch (Exception e) {
            System.out.println("Error");
        }
    }

    public static String printAllSolvedQuizzes(String username, String password){
        //Caut username si password in fisierul user.csv si returnez toate quizz-urile rezolvate de user in formatul cerut
        try {
            File reader = new File("user.csv");
            Scanner scanner = new Scanner(reader);
            StringBuilder result = new StringBuilder("[");

            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if(line.charAt(0) == 'U' && line.contains(username)) {
                    line = scanner.nextLine();
                    if(line.charAt(0) == 'P' && line.contains(password)) {
                        line = scanner.nextLine();
                        line = scanner.nextLine();
                        String[] solvedQuizzes = line.split(" ");
                        line = scanner.nextLine();
                        String[] scores = line.split(" ");
                        for(int i = 1; i < solvedQuizzes.length; i++) {
                            if(!solvedQuizzes[i].equals("")) {
                                int id = Integer.parseInt(solvedQuizzes[i]);
                                int score = Integer.parseInt(scores[i]);
                                result.append(printOneQuizz(Quizz.getTextbyId(id), id, score, i - 1)).append(", ");
                            }

                        }
                    }
                }
            }
            result = new StringBuilder(result.substring(0, result.length() - 2));
            result.append("]");
            return result.toString();
        }
        catch (Exception e) {
            System.out.println("Error");
            return "";
        }
    }
    private static String printOneQuizz(String name, int id, int score, int index){
        return "{\"quiz-id\" : \"" + id + "\", \"quiz-name\" : \"" + name + "\", \"score\" : \"" + score + "\", \"index_in_list\" : \"" + index + "\"}";
    }
    public static void addSolvedQuizz(String username, String password, int id, int totalScore) {
        //Caut username si password in fisierul user.csv si adaug quizz-ul cu id-ul id
        //Pentru asta ma folosesc de fisierul aux_user.csv in care scriu toate datele din user.csv, adaugand quizz-ului cu id-ul id
        try(FileWriter writer = new FileWriter("aux_user.csv")){
            File reader = new File("user.csv");
            Scanner scanner = new Scanner(reader);
            String searchUser = " " + username + " ";
            String searchPassword = " " + password + " ";
            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                writer.write(line + "\n");
                if(line.charAt(0) == 'U' && line.contains(searchUser)) {
                    line = scanner.nextLine();
                    writer.write(line + "\n");
                    if(line.charAt(0) == 'P' && line.contains(searchPassword)) {
                        line = scanner.nextLine();
                        writer.write(line + "\n");
                        line = scanner.nextLine();
                        line += " " + id + " ";
                        writer.write(line + "\n");
                        line = scanner.nextLine();
                        line += " " + totalScore + " ";
                        writer.write(line + "\n");
                    }
                }
            }
            writer.close();
            Files.coppyFile("aux_user.csv", "user.csv");
            Files.eraseFile("aux_user.csv");
        }catch(Exception e){
            System.out.println("Error");
        }
    }

    public void addUser() {
        try(FileWriter writer = new FileWriter("user.csv", true)){
        writer.write("Username: " + this.username + " " + "\n");
        writer.write("Password: " + this.password + " " + "\n");
        writer.write("My_quizz: " + "\n");
        writer.write("Ans_quizz: " + "\n");
        writer.write("Score: " + "\n");
        }catch(Exception e){
            System.out.println("Error");
        }
    }

    public static boolean checkUsername(String username) {
        //Verific daca username-ul exista deja in fisierul user.csv
        try{
            File reader = new File("user.csv");
            Scanner scanner = new Scanner(reader);
            String searchUser = " " + username + " ";
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.charAt(0) == 'U' && line.contains(searchUser))
                    return true;

            }
            return false;
        } catch (Exception e) {
            System.out.println("Error");
        }
        return false;
    }

    public static boolean checkPassword(String username, String password) {
        //Verific daca password-ul corespunde cu username-ul dat
        try{
            File reader = new File("user.csv");
            Scanner scanner = new Scanner(reader);
            String searchUser = " " + username + " ";
            String searchPassword = " " + password + " ";
            boolean result  = false;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.charAt(0) == 'U' && line.contains(searchUser))
                    result = true;
                else if (result) {
                    return line.charAt(0) == 'P' && line.contains(searchPassword);
                }

            }
            return false;
        } catch (Exception e) {
            System.out.println("Error");
        }
        return false;
    }
    public static String[] checkCommand(String[] args){
        String[] result = new String[2];
        boolean gaveUser = false, gavePassword = false;

        for (String command : args) {
            if (command.indexOf("-u") == 0) {
                gaveUser = true;
                result[0] = command.substring(4, command.length() - 1);
            }

            if (command.indexOf("-p") == 0) {
                gavePassword = true;
                result[1] = command.substring(4, command.length() - 1);
            }
        }

        if(!gaveUser || !gavePassword) {
            System.out.println("{'status':'error','message':'You need to be authenticated'}");
        }
        else
        if(!User.checkPassword(result[0], result[1])) {
            result[1] = null;
            System.out.println("{'status':'error','message':'Login failed'}");
        }

        return result;
    }


    public static void addQuizz(String username, String password, int id) {
        //Caut username si password in fisierul user.csv si adaug quizz-ul cu id-ul id
        try(FileWriter writer = new FileWriter("aux_user.csv")){
            File reader = new File("user.csv");

            Scanner scanner = new Scanner(reader);
            String searchUser = " " + username + " ";
            String searchPassword = " " + password + " ";

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                writer.write(line + "\n");
                if (line.charAt(0) == 'U' && line.contains(searchUser)) {
                    line = scanner.nextLine();
                    writer.write(line + "\n");
                    if (line.charAt(0) == 'P' && line.contains(searchPassword)) {
                        line = scanner.nextLine();
                        line += id + " ";
                        writer.write(line + "\n");
                    }
                }
            }
            writer.close();
            Files.coppyFile("aux_user.csv", "user.csv");
            Files.eraseFile("aux_user.csv");
        } catch(Exception e){
            System.out.println("Error");
        }
    }
}
