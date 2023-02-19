import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Desing Pattern Facade
 */

public class Commands {
    Database database = Database.getInstance();
    public Commands(String usersFile, String streamersFile, String streamsFile) {

        database.storeStreamsData(streamsFile);
        database.storePersonsData(streamersFile);
        database.storePersonsData(usersFile);

    }

    public void list(Integer id) {
        database.getPerson(id).printStreams(database);
    }

    public void listen(Integer id, Integer streamId) {
        Person person = database.getPerson(id);
        Stream stream = database.getStream(streamId);

        if(!person.hasStream(stream))
            person.getIterator().addStream(stream);

        stream.incrementNoOfStreams();
    }
    public void end() {
        database.resetDatabase();
    }

    public void add(String[] data) {
        Stream stream = new Stream(new StreamBuilder()
                                        .withStreamType(Integer.parseInt(data[2]))
                                        .withId(Integer.parseInt(data[3]))
                                        .withStreamGenre(Integer.parseInt(data[4]))
                                        .withNoOfStreams(0)
                                        .withStreamerId(Integer.parseInt(data[0]))
                                        .withLength(Long.parseLong(data[5]))
                                        .withDateAdded(java.time.Instant.now().getEpochSecond())
                                        .withName(data[6]));
        database.addStream(stream);
    }

    private int compareDate(String data1, String data2) {
        String[] date1 = data1.split("-");
        String[] date2 = data2.split("-");
        if(Integer.parseInt(date1[2]) > Integer.parseInt(date2[2]))
            return 1;
        else if(Integer.parseInt(date1[2]) < Integer.parseInt(date2[2]))
            return -1;
        else {
            if(Integer.parseInt(date1[1]) > Integer.parseInt(date2[1]))
                return 1;
            else if(Integer.parseInt(date1[1]) < Integer.parseInt(date2[1]))
                return -1;
            else {
                if(Integer.parseInt(date1[0]) > Integer.parseInt(date2[0]))
                    return 1;
                else if(Integer.parseInt(date1[0]) < Integer.parseInt(date2[0]))
                    return -1;
                else
                    return 0;
            }
        }
    }

    public void surprise(int userID, String genre) {
        User person = (User) database.getPerson(userID);
        Integer streamType = getStreamType(genre);

        PriorityQueue<Stream> priorityQueue = new PriorityQueue<>(new Comparator<Stream>() {
            @Override
            public int compare(Stream o1, Stream o2) {
               if(compareDate(o1.getDateAdded(), o2.getDateAdded()) == 0)
                   return (int) (o2.getNoOfStreams() - o1.getNoOfStreams());
               else
                     return compareDate(o2.getDateAdded(), o1.getDateAdded());
            }
        });

        for(Stream stream : database.getStreams(streamType)) {
            if(!((UserIterator)person.getIterator()).hasStreamer(stream.getStreamerId()))
                priorityQueue.add(stream);
        }

        if(priorityQueue.size() == 0)
            System.out.println("No streams found");
        else {
            printQueue(priorityQueue, 3);
        }
        priorityQueue.clear();
    }

    private int getStreamType(String type) {
        if(type.equals("SONG"))
            return 1;
        else if(type.equals("PODCAST"))
            return 2;
        else
            return 3;
    }

    public void recommend(int id, String genre) {
        int streamType = getStreamType(genre);
        PriorityQueue<Stream> priorityQueue = new PriorityQueue<>(new Comparator<Stream>() {
            @Override
            public int compare(Stream o1, Stream o2) {
                return (int) (o2.getNoOfStreams() - o1.getNoOfStreams());
            }
        });

        User user = (User) database.getPerson(id);
        user.getIterator().resetIndex();
        while(user.getIterator().hasNext()) {

            Stream stream = user.getIterator().next();
            if(stream.getStreamType() == streamType) {
                Streamer streamer = (Streamer) database.getPerson(stream.getStreamerId());
                streamer.getIterator().resetIndex();

                while(streamer.getIterator().hasNext()) {
                    Stream stream1 = streamer.getIterator().next();
                    if(stream1.getStreamType() == streamType && !user.hasStream(stream1))
                        priorityQueue.add(stream1);
                }
            }
        }
        user.getIterator().resetIndex();

        if(priorityQueue.isEmpty())
            System.out.println("No recommendations available at the moment.");
        else {
            printQueue(priorityQueue, 5);
        }
        priorityQueue.clear();
    }

    private void printQueue(PriorityQueue<Stream> priorityQueue, int maxStreams) {
        int numberOfStreams = 0;
        StringBuilder output = new StringBuilder("[");
        while(!priorityQueue.isEmpty() && numberOfStreams < maxStreams) {
            Stream stream = (Stream) priorityQueue.poll();
            output.append(stream.toString(database.getPerson(stream.getStreamerId()).getName()));
            output.append(",");
            numberOfStreams++;
        }
        output = new StringBuilder(output.substring(0, output.length() - 1));
        System.out.println(output + "]");
    }

}
