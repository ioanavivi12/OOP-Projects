import java.text.SimpleDateFormat;
import java.time.Duration;

public class StreamBuilder {
    private int streamType;
    private int id;
    private int streamGenre;
    private long noOfStreams;
    private int streamerId;
    private String length;
    private String dateAdded;
    private String name;

    public StreamBuilder withStreamType(int streamType) {
        this.streamType = streamType;
        return this;
    }

    public StreamBuilder withId(int id) {
        this.id = id;
        return this;
    }

    public StreamBuilder withStreamGenre(int streamGenre) {
        this.streamGenre = streamGenre;
        return this;
    }

    public StreamBuilder withNoOfStreams(long noOfStreams) {
        this.noOfStreams = noOfStreams;
        return this;
    }

    public StreamBuilder withStreamerId(int streamerId) {
        this.streamerId = streamerId;
        return this;
    }

    public StreamBuilder withLength(long length) {
        this.length = durationToString(Duration.ofSeconds(length));
        return this;
    }

    public StreamBuilder withDateAdded(long dateAdded) {
        SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy");
        formater.setTimeZone(java.util.TimeZone.getTimeZone("GMT+1"));
        this.dateAdded = formater.format(dateAdded * 1000);
        return this;
    }

    public StreamBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public int getStreamType() {
        return streamType;
    }

    public int getId() {
        return id;
    }

    public int getStreamGenre() {
        return streamGenre;
    }

    public long getNoOfStreams() {
        return noOfStreams;
    }

    public int getStreamerId() {
        return streamerId;
    }

    private String durationToString(Duration ofSeconds) {
        int hours = (int) ofSeconds.toHours();
        int minutes = (int) ofSeconds.toMinutes() % 60;
        int seconds = (int) ofSeconds.getSeconds() % 60;
        if(hours == 0) {
            return String.format("%02d:%02d", minutes, seconds);
        }

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public String getLength() {
        return length;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public String getName() {
        return name;
    }
}