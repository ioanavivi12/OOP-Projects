package com.example.project;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Answer{
        private final String text;
        private static int id = 0;
        private boolean isCorrect;
        private double score;
        public Answer(String text, boolean isCorrect, double score) {
            this.text = text;
            this.isCorrect = isCorrect;
            this.score = score;
            id++;
        }
        public static void resetId(){
            id = 0;
        }
        public void addAnswer() {
            try(FileWriter writer = new FileWriter("answer.csv", true)){
                writer.write("Id: " + id + " " + "\n");
                writer.write("Text: " + this.text + " " + "\n");
                writer.write("Correct: " + this.isCorrect + " " + "\n");
                writer.write("Score: " + this.score + " " + "\n");
            }catch(Exception e){
                System.out.println("Error");
            }
        }
        public int getId() {
            return id;
        }

        public static String print(int id) {
            String text = Answer.getTextbyId(id);

            return "{\"answer_name\":\"" + text + "\", \"answer_id\":\"" + id + "\"}";
        }

        public static String getTextbyId(int id){
            //Caut in fisierul answer.csv dupa id si in cazul in care il gasesc returnez textul de la linia urmatoare
            try{
                File reader = new File("answer.csv");
                Scanner scanner = new Scanner(reader);
                String searchText = " " + id + " ";
                while(scanner.hasNextLine()){
                    String line = scanner.nextLine();
                    if(line.charAt(0) == 'I' && line.contains(searchText)) {
                        line = scanner.nextLine();
                        return line.substring(6, line.length() - 1);
                    }
                }
                return "mac";
            }catch(Exception e){
                return e.toString();
            }
        }
        public static ArrayList<Integer> checkCommand(String[] command){
            int numAnswers = 0, rigthAnswers = 0, noAnswerDescription = 0, noAnswerFlag = 0;
            String type = null;
            String[] answers = new String[5];
            boolean[] isCorrect = new boolean[5];

            for (int i = 0; i < command.length; i++) {

                if(command[i].indexOf("-type") == 0) {
                    type = command[i].substring(6, command[i].length() - 1);
                }

                if(command[i].indexOf("-answer") == 0 && !command[i].contains("-is-correct")) {
                    int index = Integer.parseInt(command[i].substring(8, 9));
                    String answerText = command[i].substring(11, command[i].length() - 1);
                    answers[index - 1] = answerText;
                    isCorrect[index - 1] = false;

                    if( numAnswers == 5) {
                        numAnswers++;
                        break;
                    }

                    if(!command[i + 1].contains("-answer-" + index + "-is-correct") ) {
                        noAnswerFlag = index;
                        break;
                    }

                    numAnswers++;

                }

                if(command[i].indexOf("-answer") == 0 && command[i].contains("correct")) {
                    int index = Integer.parseInt(command[i].substring(8, 9));

                    if( numAnswers == 5) {
                        numAnswers++;
                        break;
                    }

                    if(i == 0 || command[i - 1].indexOf("-answer-" + index) != 0) {
                        noAnswerDescription = index;
                        break;
                    }

                    if(command[i].contains(" '1'")) {
                        rigthAnswers++;
                        isCorrect[index - 1] = true;
                    }
                }

            }

            if(noAnswerDescription != 0){
                System.out.println("{'status':'error','message':'Answer " + noAnswerDescription + " has no answer description'}");
                return null;
            }

            if(noAnswerFlag != 0){
                System.out.println("{'status':'error','message':'Answer " + noAnswerFlag  + " has no answer correct flag'}");
                return null;
            }

            if(numAnswers == 0) {
                System.out.println("{'status':'error','message':'No answer provided'}");
                return null;
            }

            if(numAnswers > 5){
                System.out.println("{'status':'error','message':'More than 5 answers were submitted'}");
                return null;
            }

            if(numAnswers == 1){
                System.out.println("{'status':'error','message':'Only one answer provided'}");
                return null;
            }

            //BONUS
            if(type == null) {
                System.out.println("{'status':'error','message':'No type provided'}");
                return null;
            }

            if(rigthAnswers > 1 && type.contains("single")){
                System.out.println("{'status':'error','message':'Single correct answer question has more than one correct answer'}");
                return null;
            }

            for(int i = 0; i < numAnswers - 1; i++)
                for(int j = i + 1; j < numAnswers; j++)
                    if(answers[i].equals(answers[j])) {
                        System.out.println("{'status':'error','message':'Same answer provided more than once'}");
                        return null;
                    }
            //Adaug toare raspunsurile in fisier
            double answerScore;
            ArrayList<Integer> list = new ArrayList<>();
            answers = java.util.Arrays.copyOf(answers, numAnswers);
            for (int i = 0; i < numAnswers; i++) {
                //Calculez scorul fiecarui raspuns
                if(isCorrect[i])
                    answerScore = 1.0 / rigthAnswers;
                else
                    answerScore = 0 - (1.0 / (numAnswers - rigthAnswers));
                Answer answer = new Answer(answers[i], isCorrect[i], answerScore);
                answer.addAnswer();
                list.add(answer.getId());
            }
            return list;
        }

        public static double getScorebyId(int id){
            //Caut in fisierul answer.csv dupa id si in cazul in care il gasesc returnez scorul specific id-ului
            try{
                File reader = new File("answer.csv");
                Scanner scanner = new Scanner(reader);
                String searchText = " " + id + " ";
                while(scanner.hasNextLine()){
                    String line = scanner.nextLine();
                    if(line.charAt(0) == 'I' && line.contains(searchText)) {
                        line = scanner.nextLine();
                        line = scanner.nextLine();
                        line = scanner.nextLine();
                        return Double.parseDouble(line.substring(7, line.length() - 1));
                    }
                }
                return 0.0;
            }catch(Exception e){
                return 0.0;
            }
        }
}

