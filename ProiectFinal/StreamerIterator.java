import java.util.*;

/**
 * Toate streamurile create de un streamer
 * Design pattern: Iterator
 */

public class StreamerIterator implements StreamIterator {
    private List<Stream> streams;

    private static int index = 0;

    public StreamerIterator() {
        streams = new ArrayList<>();

    }

    @Override
    public boolean hasNext() {
        return index < streams.size();
    }

    @Override
    public Stream next() {
        return streams.get(index++);
    }

    @Override
    public void resetIndex() {
        index = 0;
    }

    @Override
    public void addStream(Stream stream) {
        streams.add(stream);
    }

    @Override
    public boolean hasStream(Stream stream) {
        return streams.contains(stream);
    }

    @Override
    public int size() {
        return streams.size();
    }

}

