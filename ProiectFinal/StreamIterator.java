/**
 * Interfata pentru iteratori de tip stream.
 */

public interface StreamIterator {
    public boolean hasNext();
    public Stream next();

    public void resetIndex();

    void addStream(Stream stream);

    boolean hasStream(Stream stream);

    int size();
}
