# Proiect
## Design Patterns
*1. Facade*

Am creat o clasa Commands care implementeaza fiecare comanda pe care poate utilizatorul sa o dea. 
Astfel, in fisierul ProiectPOO doar creez un obiect de acest tip si apelez metodele din el.

Clasa Command are ca si atribute o baza de date unde se retin Utilizatorii, Streamerii si Streamurile.

*2. Singleton*

Am creat o clasa Database care sa retine toti utilizatorii, streamerii si streamurile in hasmapuri astfel:
* Atat utilizatorii cat si streamerii sunt retinuti in acelasi hashmap unde cheia este id-ul, iar valoarea este un obiect de tipul User/Streamer
* Streamurile sunt retinute 3 hashmap-uri, in functie de tipul de stream, unde cheia este id-ul streamului iar valoarea este un obiect de tipul Stream

Pentru a retine atat utilizatorii cat si streamerii in acelasi hashmap am creat o clasa abstracta Person care extinde clasa User si clasa Streamer.

*3. Builder*

Am creat doua clase Builder, una pentru a crea un stream nou si una pentru a crea o persoana noua.

*4. Iterator*

Am creat o interfata StreamIterator care este implementata de clasele StreamerIterator si UserIterator.

UserIterator parcurge streamurile pe care utilizatorul resprectiv le-a ascultat.

StreamerIterator parcurge streamurile pe care streamerul respectiv le-a creat.


