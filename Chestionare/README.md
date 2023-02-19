# tema-1-ioanavivi12
tema-1-ioanavivi12 created by GitHub Classroom

# Cerinta
Programul isi propune implementarea unui Generator simplu de chestionare. Utilizatorii acestui program se vor autentifica in aplicatie la orice apel de sistem, în afară de acela de creare de utilizator. 
Utilizatorii autentificati in sistem vor putea crea:
  * intrebari (cu unul sau mai multe raspunsuri corecte)
  * chestionare in baza intrebarilor adaugate anterior
 Mai mult, utilizatorii vor putea raspunde chestionarelor celorlalti, doar o singura data.
 
 # Abordare
 Pentru fiecare comanda pe care utilizatorul o poate da am creat o metoda specifica care sa indeplineasca cerinta
 
 ## __1. Creare utilizator__ 

Din lista de parametrii pe care ar putea sa ii aiba comanda "-create-user" am cautat sa verific daca "-u", respectiv "-p" exista. In caz contrar afisam mesajele de eroare specifice. 

Daca totusi existau, pastram username-ul si parola si verificam daca acestea se afla deja in "baza de date", folosind metoda __checkUser()__ din cadrul clasei User. Daca utilizatorul nu se gasea, il adaugam. 

Pentru asta, cream un obiect al clasei __User__ si apelam metoda __addUser()__ care deschidea fisierul *user.csv* si scria in el informatiile despre urmatorul utilizator in aceasta ordine:
    
* Username:   
* Password:    
* My_quizz: (quizurile create de acest utilizator) 
* Ans_quizz: (quizurile la care a raspuns acest utilizator)
* Score: (scorul pentru fiecare quiz la care a raspuns)
## __2. Creare intrebare__

Pentru a verifica daca in comanda s-au trimis corect parametrii am creat metoda __checkCommand()__ in clasele User, Question si Answer, avand urmatoarele roluri:
* In clasa User verifica daca utilizatorul a introdus corect parametrii necesari (-u sau -p) si daca acesta se poate conecta (atat user-ul cat si parola sunt corecte). In caz pozitiv, metoda o sa imi returneze un vector de tip String, unde se va gasi username-ul si password-ul obtinute.
* In clasa Question verifica daca utilizatorul a introdus parametrul pentru text, iar in caz afirmativ intoarce textul intrebarii
* In clasa Answer verifica daca raspunsurile au fost date asa cum se specifica in cerinta (maxim 5 raspunsuri, intrebarile de tipul "single" au exact un raspun corect etc.). In cazul in care nu s-au gasit erori, metoda imi creaza pentru fiecare raspuns cate un obiect de tip __Answer__ si cu ajutorul metodei __addAnswer()__ imi adauga raspunsurile in "baza de date" *answer.csv*, in urmatoarea forma:
     * ID: (un id unic pentru fiecare raspuns)
     * Text: 
     * Correct:
     * Score: (cat valoreaza raspunsul)
     
  Aceasta metoda intoarce id-urile unice ale fiecarei intrebari.
  
Daca cele 3 metode au trecut fara eroare, se creaza un obiect de tipul __Question__ si se apeleaza metoda __addQuestion()__ care primeste ca si parametru lista de id-uri ale raspunsurilor si adauga in "baza de date" *question.csv* intrebarile in urmatoarea forma:

* Id: (id-ul unic al intrebarii)
* Text:
* Type: 
* Answers: (id-urile raspunsurilor)

## __3. Intoarce identificatorul intrebare dupa nume__

Folosindu-ma de metodele __checkCommand()__ din clasele User si Question verific daca parametrii au fost introdusi corect de catre utilizator.

Am creat metoda __searchText()__ a clasei Question, care primeste ca parametru un String si imi cauta in question.csv daca pe liniile "Text: " se gaseste stringul meu. In caz afirmativ, functia o sa returneze id-ul pastrat in linia anterioara. 

## __4. Intoarce toate intrebarile din sistem__

Dupa ce verific posibilele erori produse de conectarea utilizatorului, apelez metoda __getQuestionsId()__ din clasa Question. Aceasta extrage din *question.csv* toate id-urile si le insereaza intr-o lista, pe care o returneaza la final.

## __5. Creare de chestionar__

Verific posibilele erori produse de conectarea utilizatorului.

Pentru a verifica daca au fost introdusi corect parametrii pentru crearea quizului, am implementat metoda __checkCommand()__ in clasa Quizz care imi verifica atat numele quizului cat si id-urile intrebarilor folosindu-se de urmatoarele metode:

* __searchName()__ din clasa Quizz verifica daca numele acestui quizz se regaseste in "baza de date" *quizz.csv*
* __getTextbyId(id)__ din clasa Question cauta in *question.csv* daca se gaseste id-ul dat ca parametru si, in caz afirmativ, returneaza textul aflat pe urmatoarea linie in fisier.

Daca nicio eroare nu s-a produs, se creaza un nou obiect de tipul Quizz, si cu ajutorul metodei __addQuizz()__ care primeste ca si parametru lista id-urilor intrebarilor, se adauga in "baza de date" *quizz.csv* informatiile in urmatoarea ordine:

* Name:
* Id: (un id unic pentru fiecare quiz)
* Questions: (lista de id-uri al intrebarilor, fiecare id separat de un spatiu)

La final, __checkCommand()__ ar trebui sa returneze id-ul quizului creat, sau -1 in cazul in care nu a fost creat un quiz.

Daca id-ul este pozitiv, se apeleaza metoda __addQuizz__ din clasa User, care primeste ca parametrii username-ul, password-ul si id-ul quizz ului. Metoda actualizeaza in fisierul *user.csv* linia My_quizz specifica utilizatorului cu datele date, adaugand id-ul noului quiz la final. 

## __6. Intoarce identificatorul chestionar dupa nume__

Verific posibilele erori produse de conectarea utilizatorului.

Extrag din lista de argumente numele quizului si cu ajutorul metodei __searchName()__ din clasa Quizz caut in *quizz.csv* daca acest nume exista. Metoda ar trebui ar trebui sa returneze id-ul quizului, sau -1 in cazul in care nu exista acest quiz.


## __7. Intoarce toate chestionarele din sistem__

Verific posibilele erori produse de conectarea utilizatorului.

mac mac mac 

## __8. Intoarce detaliile unui chestionar in functie de identificator__

Verific posibilele erori produse de conectarea utilizatorului.

Verific daca a fost introdus corect id-ul si, in caz afirmativ, apelez metoda __getTextbyId()__ din clasa Quizz ca sa imi returneze numele quizului sau null in cazul in care quizului nu exista.

## __10. Sterge chestionar__

Verific posibilele erori produse de conectarea utilizatorului.

Verific daca a fost introdus corect id-ul si, in caz afirmativ, apelez metoda __getTextbyId()__ din clasa Quizz ca sa imi returneze numele quizului sau null in cazul in care quizului nu exista.

Apelez metoda __isMyQuizz()__ din clasa User, careia ii dau ca parametrii username-ul, password-ul si id-ul quizului si verific daca pe linia "My_quizz: " specifica utilizatorului din fisierul *user.csv* se regaseste id-ului quizului meu. 

In cazul in care nu se gaseste apelez metodele __deleteQuizz()__ din cadrul claselor User si Quizz care au urmatorul scop:

* __Quizz.deleteQuizz()__ primeste ca parametru un id si sterge din *quizz.csv* liniile specifice acelui quizz
* __User.deleteQuizz()__ primeste ca parametrii username-ul, password-ul si id-ul quizului si actualizeaza linia "My_quizz: " specifica utilizatorului din fisierul *user.csv*, stergand id-ul respectiv.

## __12. Curuta date din aplicatie__

Apelez metodei __eraseFile()__ din cadrul clasei Files, care primeste ca parametru numele unui fisier si sterge toate informatiile din fisierul respectiv. 

Mai folosesc si metoda __resetId()__ gasita in clasele Question, Answer si Quizz care imi reseteaza id-urile. 



