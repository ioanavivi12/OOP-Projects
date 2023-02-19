import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Toate streamurile unui utilizator
 * Design pattern: Iterator
 */

public class UserIterator implements StreamIterator {

    private List<Stream> streams;

    private static int index = 0;

    public UserIterator() {
        streams = new ArrayList<>();
    }

    public void addStream(Stream stream) {
        streams.add(stream);
    }

    public boolean hasStreamer(int streamerID) {
        for(Stream stream : streams) {
            if(stream.getStreamerId() == streamerID) {
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean hasStream(Stream stream) {
        return streams.contains(stream);
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
    public int size() {
        return streams.size();
    }

}
