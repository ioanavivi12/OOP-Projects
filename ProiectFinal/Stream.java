import java.text.SimpleDateFormat;
import java.time.Duration;
public class Stream {
    private final int id;
    private final String name;
    private final int streamType;
    private final int streamerId;
    private long noOfStreams;
    private final String dateAdded;
    private final String length;
    private final int streamGenre;

    public Stream(StreamBuilder builder) {
        this.id = builder.getId();
        this.name = builder.getName();
        this.streamType = builder.getStreamType();
        this.streamerId = builder.getStreamerId();
        this.noOfStreams = builder.getNoOfStreams();
        this.dateAdded = builder.getDateAdded();
        this.length = builder.getLength();
        this.streamGenre = builder.getStreamGenre();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getStreamType() {
        return streamType;
    }

    public int getStreamerId() {
        return streamerId;
    }

    public long getNoOfStreams() {
        return noOfStreams;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public String getLength() {
        return length;
    }

    public int getStreamGenre() {
        return streamGenre;
    }

    public void incrementNoOfStreams() {
        noOfStreams++;
    }


    public String toString(String streamerName) {
        return "{\"id\" : \"" + id + "\", \"name\" : \"" + name
                + "\", \"streamerName\" : \"" + streamerName + "\", \"noOfListenings\" : \"" + noOfStreams
                + "\", \"length\" : \"" + length + "\", \"dateAdded\" : \"" + dateAdded + "\"}";
    }
}
