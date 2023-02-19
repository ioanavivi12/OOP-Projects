/**
* Clasa Person pastreaza informatiile generale despre orice persoana din aplicatie
 * Pe langa nume si id, aceasta mai are si un iterator pentru a parcurge streamurile si o strategie pentru comanda de afisare, List
**/
public class Person {
    private String name;
    private final int id;
    private StreamIterator iterator;


    public Person(PersonBuilder builder) {
        this.name = builder.getName();
        this.id = builder.getId();
        this.iterator = builder.getIterator();
    }

    public void printStreams(Database database) {
        StringBuilder output = new StringBuilder("[");
        iterator.resetIndex();
        while(iterator.hasNext()) {
            Stream stream = iterator.next();
            output.append(stream.toString(database.getPerson(stream.getStreamerId()).getName()));
            output.append(",");
        }
        output = new StringBuilder(output.substring(0, output.length() - 1));
        System.out.println(output + "]");

        iterator.resetIndex();
    }

    public boolean hasStream(Stream stream) {
        return iterator.hasStream(stream);
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


}
