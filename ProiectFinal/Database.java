import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Baza de date a aplicatie care contine o lista cu persoanele si cu streamurile din aplicatie
 * Design pattern: Singleton
 */

public class Database {

    private HashMap<Integer, Person> persons;
    private HashMap<Integer, Stream> Songs, Podcasts, AudioBooks;

    private static Database instance = null;

    private Database() {
        persons = new HashMap<>();
        Songs = new HashMap<>();
        Podcasts = new HashMap<>();
        AudioBooks = new HashMap<>();
    }

    public static Database getInstance() {
        if(instance == null) {
            instance = new Database();
        }
        return instance;
    }

    private void storeAllStreams(List<Stream> streams, Streamer streamer) {
        for(Stream stream : streams) {
            if(stream.getStreamerId() == streamer.getId()) {
                streamer.getIterator().addStream(stream);
            }
        }
    }

    public void storePersonsData(String fileName) {
        try {
            File fileReader = new File(fileName);
            Scanner scanner = new Scanner(fileReader);

            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                String[] data = Arrays.stream(line.split(",")).map(data1 -> data1.substring(1, data1.length() - 1)).toArray(String[]::new);

                if (fileName.contains("users.csv")) {
                    User newUser = new User(new PersonBuilder()
                            .setId(Integer.parseInt(data[0]))
                            .setName(data[1])
                            .setIterator(new UserIterator())
                    );

                    Integer[] streamsId = Arrays.stream(data[2].split(" ")).map(Integer::parseInt).toArray(Integer[]::new);
                    for (Integer streamId : streamsId) {
                        if (Songs.containsKey(streamId)) {
                            newUser.getIterator().addStream(Songs.get(streamId));
                        } else if (Podcasts.containsKey(streamId)) {
                            newUser.getIterator().addStream(Podcasts.get(streamId));
                        } else if (AudioBooks.containsKey(streamId)) {
                            newUser.getIterator().addStream(AudioBooks.get(streamId));
                        }
                    }
                    persons.put(newUser.getId(), newUser);

                }
                if (fileName.contains("streamers.csv")) {
                    Streamer newStreamer = new Streamer(new PersonBuilder()
                            .setStreamType(Integer.parseInt(data[0]))
                            .setId(Integer.parseInt(data[1]))
                            .setName(data[2])
                            .setIterator(new StreamerIterator())
                    );

                    storeAllStreams(getStreams(newStreamer.getStreamType()), newStreamer);

                    persons.put(Integer.parseInt(data[1]), newStreamer);
                }

            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }
    public void addStream(Stream stream) {
        if(stream.getStreamType() == 1)
            Songs.put(stream.getId(), stream);
        else if(stream.getStreamType() == 2)
            Podcasts.put(stream.getId(), stream);
        else if(stream.getStreamType() == 3)
            AudioBooks.put(stream.getId(), stream);
    }
    public void storeStreamsData(String fileName) {
        try {
            File fileReader = new File(fileName);
            Scanner scanner = new Scanner(fileReader);

            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                String[] data = line.split("\",\"");
                data[0] = data[0].substring(1);
                data[7] = data[7].substring(0, data[7].length() - 1);

                Stream newStream = new Stream(new StreamBuilder()
                                        .withStreamType(Integer.parseInt(data[0]))
                                        .withId(Integer.parseInt(data[1]))
                                        .withStreamGenre(Integer.parseInt(data[2]))
                                        .withNoOfStreams(Long.parseLong(data[3]))
                                        .withStreamerId(Integer.parseInt(data[4]))
                                        .withLength(Long.parseLong(data[5]))
                                        .withDateAdded(Long.parseLong(data[6]))
                                        .withName(data[7]));
                addStream(newStream);
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public Person getPerson(int id) {
        return persons.get(id);
    }

    public void resetDatabase() {
        persons.clear();
        Songs.clear();
        Podcasts.clear();
        AudioBooks.clear();
        instance = null;
    }

    public Stream getStream(Integer streamId) {
        if(Songs.containsKey(streamId))
            return Songs.get(streamId);
        else if(Podcasts.containsKey(streamId))
            return Podcasts.get(streamId);
        else
            return AudioBooks.get(streamId);
    }

    public List<Stream> getStreams(Integer streamType) {
        if (streamType == 1)
            return new ArrayList<>(Songs.values());
        else if (streamType == 2)
            return new ArrayList<>(Podcasts.values());
        else
            return new ArrayList<>(AudioBooks.values());
    }
}

