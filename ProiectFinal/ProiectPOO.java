import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ProiectPOO {

    public static void main(String[] args) {

        if(args == null) {
            System.out.println("Nothing to read here");
        }
        else {
            final String path = "src/main/resources/";

            String[] files = Arrays.stream(args).map(file -> path + file).toArray(String[]::new);

            Commands commands = new Commands(files[0], files[2], files[1]);
            try {

                FileReader fileReader = new FileReader(files[3]);
                Scanner scanner = new Scanner(fileReader);
                while(scanner.hasNextLine()) {
                    String[] data = scanner.nextLine().split(" ");

                    if(data[1].equals("LIST")) {
                        commands.list(Integer.parseInt(data[0]));
                    }
                    else if(data[1].equals("LISTEN")) {
                        commands.listen(Integer.parseInt(data[0]), Integer.parseInt(data[2]));
                    }
                    else if(data[1].equals("ADD")) {
                        commands.add(data);
                    }
                    else if(data[1].equals("SURPRISE")) {
                        commands.surprise(Integer.parseInt(data[0]), data[2]);
                    }
                    else if(data[1].equals("RECOMMEND")) {
                        commands.recommend(Integer.parseInt(data[0]), data[2]);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            commands.end();
        }

    }
}
