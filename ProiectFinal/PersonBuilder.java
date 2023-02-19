/**
 * Builder pentru clasa Person.
 */

public class PersonBuilder {
    // Required parameters
    private String name;
    private int id;
    private StreamIterator iterator;
    // Optional parameters
    private int streamType;

    public PersonBuilder() {
    }

    public PersonBuilder setStreamType(int streamType) {
        this.streamType = streamType;
        return this;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public StreamIterator getIterator() {
        return iterator;
    }

    public int getStreamType() {
        return streamType;
    }

    public PersonBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public PersonBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public PersonBuilder setIterator(StreamIterator iterator) {
        this.iterator = iterator;
        return this;
    }

}
