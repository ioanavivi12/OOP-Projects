package com.example.project;


import java.util.ArrayList;

public class Tema1 {
	private static void createUser(String[] command){
		boolean gaveUser = false, gavePassword = false;
		String username = "", password = "";
		for (String arg : command) {
			if(arg.indexOf("-u") == 0) {
				gaveUser = true;
				username = arg.substring(4, arg.length() - 1);
			}
			if(arg.indexOf("-p") == 0) {
				gavePassword = true;
				password = arg.substring(4, arg.length() - 1);
			}
		}
		if(!gaveUser) {
			System.out.println("{'status':'error','message':'Please provide username'}");
			return;
		}
		if(!gavePassword) {
			System.out.println("{'status':'error','message':'Please provide password'}");
			return;
		}
		if(User.checkPassword(username, password))
			System.out.println("{'status':'error','message':'User already exists'}");
		else {
			User newUser = new User(username, password);
			newUser.addUser();
			System.out.println("{'status':'ok','message':'User created successfully'}");
		}
	}
	private static void createQuestion(String[] command) {

		String text, type = "";

		String[] result;
		result = User.checkCommand(command);
		if(result[0] == null || result[1] == null)
			return;

		text = Question.checkCommand(command);
		if(text == null)
			return;

		if(Question.searchText(text) != -1) {
			System.out.println("{'status':'error','message':'Question already exists'}");
			return;
		}

		ArrayList<Integer> answersId = Answer.checkCommand(command);
		if(answersId == null)
			return;

		for (String arg : command) {
			if(arg.indexOf("-type") == 0) {
				type = arg.substring(7, arg.length() - 1);
			}
		}

		//BONUS
		if(type.equals("")) {
			System.out.println("{'status':'error','message':'Please provide question type'}");
			return;
		}

		Question newQuestion = new Question(text, type);
		newQuestion.addQuestion(answersId);

		System.out.println("{'status':'ok','message':'Question added successfully'}");

	}
	public static void getAllQuestions(String[] command) {
		String[] result;

		result = User.checkCommand(command);
		if(result[0] == null || result[1] == null)
			return;

		ArrayList<Integer> Ids = Question.getQuestionsId();

		//BONUS
		if(Ids == null)
			System.out.println("{'status':'error','message':'No questions found'}");

		StringBuilder output = new StringBuilder("{'status':'ok','message':'[");

		for (Integer id : Ids) {
			output.append("{\"question_id\" : " + "\"").append(id).append("\"").append(", \"question_name\" : ").append("\"").append(Question.getTextbyId(id)).append("\"}, ");
		}
		output = new StringBuilder(output.substring(0, output.length() - 2));
		output.append("]'}");
		System.out.println(output);
	}
	public static void getQuestionIdByText(String[] command) {
		String[] result;

		result = User.checkCommand(command);
		if(result[0] == null || result[1] == null)
			return;

		String text = Question.checkCommand(command);
		if(text == null)
			return;

		int id = Question.searchText(text);
		if(id == -1) {
			System.out.println("{'status':'error','message':'Question does not exist'}");
			return;
		}
		System.out.println("{'status':'ok','message':'" + id + "'}");
	}
	private static void cleanUp(){
		Files.eraseFile("user.csv");
		Files.eraseFile("question.csv");
		Question.resetId();
		Files.eraseFile("answer.csv");
		Answer.resetId();
		Files.eraseFile("quizz.csv");
		Quizz.resetId();
		System.out.println("{'status':'ok','message':'Cleanup finished successfully'}");
	}

	private static void createQuizz(String[] command){
		String username, password;
		String[] result;

		result = User.checkCommand(command);
		if(result[0] == null || result[1] == null)
			return;
		username = result[0];
		password = result[1];

		int id = Quizz.checkCommand(command);
		if(id == -1)
			return;

		User.addQuizz(username, password, id);
		System.out.println("{'status':'ok','message':'Quizz added succesfully'}");
	}
	public static void main(final String[] args)
	{
		//Parse command line arguments
		if(args == null || args.length == 0) {
			System.out.println("Hello world!");
			return;
		}
		if(args[0].equals("-create-user"))
			createUser(args);
		if(args[0].equals("-create-question"))
			createQuestion(args);
		if(args[0].equals("-get-question-id-by-text"))
			getQuestionIdByText(args);
		if(args[0].equals("-get-all-questions"))
			getAllQuestions(args);
		if(args[0].equals("-create-quizz"))
			createQuizz(args);
		if(args[0].equals("-get-quizz-by-name"))
			getQuizzByName(args);
		if(args[0].equals("-get-all-quizzes"))
			getAllQuizz(args);
		if(args[0].equals("-get-quizz-details-by-id"))
			getQuizzDetailsById(args);
		if(args[0].equals("-submit-quizz"))
			submitQuizz(args);
		if(args[0].equals("-delete-quizz-by-id"))
			deleteQuizzbyId(args);
		if(args[0].equals("-get-my-solutions"))
			getMySolutions(args);
		if(args[0].equals("-cleanup-all"))
			cleanUp();
	}

	private static void getMySolutions(String[] args) {
		String username, password;
		String[] result;

		result = User.checkCommand(args);
		if(result[0] == null || result[1] == null)
			return;
		username = result[0];
		password = result[1];

		String output = User.printAllSolvedQuizzes(username, password);
		System.out.println("{'status':'ok','message':'" + output + "'}");
	}

	private static void deleteQuizzbyId(String[] args) {
		String username, password;
		String[] result;

		result = User.checkCommand(args);
		if(result[0] == null || result[1] == null)
			return;
		username = result[0];
		password = result[1];

		if(args.length < 4 || !args[3].contains("-id ")) {
			System.out.println("{'status':'error','message':'No quizz identifier was provided'}");
			return;
		}

		int id = Integer.parseInt(args[3].substring(5, args[3].length() - 1));
		if(Quizz.getTextbyId(id) == null) {
			System.out.println("{'status':'error','message':'No quiz was found'}");
			return;
		}

		if(User.isMyQuizz(username, password, id)) {
			Quizz.deleteQuizz(id);
			User.deleteQuizz(username, password, id);
			System.out.println("{'status':'ok','message':'Quizz deleted successfully'}");
		}
		else {
			System.out.println("{'status':'error','message':'You are not the owner of this quizz'}");
		}
	}

	private static void submitQuizz(String[] args) {
		String username, password;
		String[] result;

		result = User.checkCommand(args);
		if(result[0] == null || result[1] == null)
			return;
		username = result[0];
		password = result[1];

		if(args.length < 4 || !args[3].contains("-quiz-id")) {
			System.out.println("{'status':'error','message':'No quizz identifier was provided'}");
			return;
		}
		int id = Integer.parseInt(args[3].substring(10, args[3].length() - 1));
		if(Quizz.getTextbyId(id) == null) {
			System.out.println("{'status':'error','message':'No quiz was found'}");
			return;
		}

		if(User.completedQuizz(username, password, id).equals("true")) {
			System.out.println("{'status':'error','message':'You already submitted this quizz'}");
			return;
		}

		if(User.isMyQuizz(username, password, id)) {
			System.out.println("{'status':'error','message':'You cannot answer your own quizz'}");
			return;
		}
		double totalScore = 0;
		int numberQuestions = Quizz.getQuestionsId(id).size();
		double ration = 1.0 / numberQuestions;
		for(String command: args) {
			if(command.contains("-answer-id")) {
				String[] data = command.split(" ");

				int index = Integer.parseInt(data[0].substring(11));
				int answerId = Integer.parseInt(data[1].substring(1, data[1].length() - 1));
				if(Answer.getTextbyId(answerId) == null) {
					System.out.println("{'status':'error','message':'Answer ID for answer" + index + " does not exist'}");
					return;
				}

				double score = Answer.getScorebyId(answerId);
				totalScore += (score * ration);
			}
		}
		if(totalScore < 0)
			totalScore = 0;

		totalScore *= 100;
		totalScore = Math.round(totalScore);
		User.addSolvedQuizz(username, password, id, (int)totalScore);
		System.out.println("{'status':'ok','message':'" + (int)totalScore + " points'}");
	}

	private static void getQuizzDetailsById(String[] args) {
		String[] result;

		result = User.checkCommand(args);
		if(result[0] == null || result[1] == null)
			return;

		//BONUS
		if(args.length < 3 || !args[3].contains("-id ")) {
			System.out.println("{'status':'error','message':'No quizz identifier was provided'}");
			return;
		}

		int id = Integer.parseInt(args[3].substring(5, args[3].length() - 1));
		if(Quizz.getTextbyId(id) == null) {
			System.out.println("{'status':'error','message':'Quizz does not exist'}");
			return;
		}

		System.out.println("{'status':'ok','message':'" + Quizz.print(id) + "'}");
	}

	private static void getAllQuizz(String[] args) {
		String username, password;
		String[] result;

		result = User.checkCommand(args);
		if(result[0] == null || result[1] == null)
			return;
		username = result[0];
		password = result[1];

		ArrayList<Integer> Ids = Quizz.getallQuizzId();

		//BONUS
		if(Ids == null) {
			System.out.println("{'status':'ok','message':'No quizzes was found'}");
			return;
		}

		StringBuilder output = new StringBuilder("{'status':'ok','message':'[");
		for (Integer id : Ids) {
			output.append("{\"quizz_id\" : " + "\"").append(id).append("\"").append(", \"quizz_name\" : ").append("\"").append(Quizz.getTextbyId(id)).append("\", \"is_completed\" : \"").append(User.completedQuizz(username, password, id)).append("\"}, ");
		}
		output = new StringBuilder(output.substring(0, output.length() - 2));
		output.append("]'}");
		System.out.println(output);
	}

	private static void getQuizzByName(String[] args) {
		String[] result;

		result = User.checkCommand(args);
		if(result[0] == null || result[1] == null)
			return;

		for (String command: args)
			if(command.indexOf("-name") == 0) {
				String name = command.substring(7, command.length() - 1);
				if(Quizz.searchName(name) == -1) {
					System.out.println("{'status':'error','message':'Quizz does not exist'}");
					return;
				}
				System.out.println("{'status':'ok','message':'" + Quizz.searchName(name) + "'}");
				return;
			}
		//BONUS
		System.out.println("{'status':'error','message':'No quizz name was provided'}");

	}
}
