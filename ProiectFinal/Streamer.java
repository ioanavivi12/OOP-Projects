/**
 * Streamer-ul din aplicatie
 */

public class Streamer extends Person{

    private final int streamType;

    public Streamer(PersonBuilder builder) {
        super(builder);
        this.streamType = builder.getStreamType();
    }

    public int getStreamType() {
        return streamType;
    }
}
