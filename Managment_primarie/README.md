# Tema2 - Dabelea Ioana-Viviana
## Grupa 323CB

### Descriere
Tema 2 a constat in implementarea unui sistem de management al unei primarii.
Sistemul trebuia sa gestioneze cererile cetatenilor de catre functionarii publici, in asa fel incat sa se evite cozile pentru inregistrare.

### Implementare

Am creat o clasa **ManagementPrimarie** care are ca atribute:
   - fisierul de intrare de unde o sa se citeasca comenzile
   - fisierul de iesire unde o sa se scrie rezultatele
   - o colectie cu toti utilizatorii din sistem
   - cate un birou specializat pentru fiecare tip de utilizator

Am creat o clasa abstracta **Utilizatori** care are ca atribut numele utilizatorului, o colectie cu cererile in asteptare si una cu cererile rezolvate. Aceasta clasa este mostenita de clasele _Angajat_, _Elev_, _EntitateJuridica_, _FunctionarPublic_, _Pensionar_ si _Persoana_.

Cele 2 colectii au fost implementate folosind **PriorityQueue**, care compara elementele cu ajutorul metodei **compareTo** suprascrisa in clasa **Cerere**. Astfel, mi-a fost mult mai usor sa inserez si sa afisez informatiile despre cererile unui utilizator, in functie de data.

Mai mult, am creat o enumeratie pentru a ma putea ajuta la modelarea cererilor

In clasa Utilizator am implementat metodele:
   - **getTipCerere**  - returneaza tipul cereri din enumeratie
   - **textCerere**  - o metoda abstracta, implementata de fiecare din cele 5 clase mentionate anterior, care imi intoarce textul cererii, personalizat in functie de utilizator
   - **creareCerere** - verifca daca utilizatorul poate crea cererea respectiva. In cazul in care se poate, se creaza un obiect de tip Cerere care se adauga atat in coada cererilor in asteptare cat si in coada cererilor din biroul dat ca parametru
   - **adaugaCerereRezolvata** - adauga o cerere rezolvata in coada de cereri rezolvate a utilizatorului
   - **verificareTipCerere** - metoda abstracta, implementata de fiecare clasa, care verfica daca utilizatorul poate crea cererea respectiva
   - **afiseazaCereriInAsteptare** - parcurge coada de cereri in asteptare si afiseaza cererile utilizatorului
   - **afiseazaCereriFinalizate** - parcurge coada de cereri finalizate si afiseaza cererile utilizatorului
   - **retrageCerere** - parcurge coada de prioritati a cererilor in asteptare si elimina cerea facuta la data oferita ca parametru
   - **getTipUtilizator** - returneaza tipul utilizatorului 

Am creat o clasa **Cerere** care are ca atribute:
   - textul cererii
   - data la care a fost facuta cererea
   - prioritatea cererii

Am creat o clasa generica **Birou** care are ca atribute:
   - o colectie de cereri
   - o colectie de functionari publici
   - o colectie pentru a pastra utilizatorul care a creat fiecare cerere

Colectia pentru cereri a fost implementata folosind **PriorityQueue**, cu ajutorul clasei CerereComparator, care suprascrie metoda compare din interfata Comparator. Astfel, am putut sa sortez cererile in functie de prioritate si data.

Colectia pentru functionari publici a fost implementata folosind **ArrayList**, intrucat nu conteaza ordinea in care se mentin functionarii.

Colecta pentru cereri si utilizatori a fost implementata folosind **HashMap**, intrucat am nevoie de o relatie 1:1 intre cerere si utilizator.

In clasa Birou am urmatoarele metode:
   - **adaugaCerere** - adauga o cerere in coada de prioritati a cererilor si mapeaza cererea cu utilizatorul care a creat-o
   - **adaugaFunctionarPublic** - adauga un functionar public in colectia de functionari 
   - **afiseazaCereri** - parcurge coada de prioritati a cererilor si afiseaza cererile
   - **retragereCerere** - parcurge coada de prioritati a cererilor si elimina cererea facuta la data oferita ca parametru
   - **rezolvaCerere** - scrie in fisierul functionarului care a rezolvat cererea si elimina cererea din coada si din mapare

### Bonus

Mi-am imaginat ca evenimentele din oras gestionate de primarie se pot tine in parc, centru sau la casa de cultura. Un eveniment nu poate fi programat doar pe un numar de ore, ci pe cel putin o zi. In aceasi zi si in acelasi loc nu se pot tine mai multe evenimente.

Astfel, comenzile pentru acest bonus sunt:
   - _adauga_eveniment_; _nume_; _dataInceput_; _dataSfarsit_; _locatie_ - verifica daca evenimentul poate fi adaugat si il adauga in coada de prioritati a evenimentelor in caz afirmativ. In caz contrar, afiseaza un mesaj de eroare
   - _sterge_eveniment_; _nume_; _locatie_ - sterge evenimentul cu numele dat, care trebuia organizat in locatia respectiva. Se garanteaza ca evenimentul exista
   - _afiseaza_toate_evenimentele_ - afiseaza toate evenimentele de la toate locatiile
   - _afiseaza_evenimente_dupa_locatie_; _locatie_ - afiseaza toate evenimentele din locatia respectiva
   - _afiseaza_evenimente_dupa_data_; _data_ - afiseaza toate evenimentele din ziua respectiva, de la toate locatiile

Am creat si 4 teste care verifica functionalitatea comenzilor: _17_bonus_1.txt_, _18_bonus_2.txt_, _19_bonus_3.txt_, _20_bonus_4.txt_ 