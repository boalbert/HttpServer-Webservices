# Http server - web services
## PROJEKTARBETE | Web services och integrationer | JU20 | ITHS

I detta projektarbete byggde vi en Http server som en modulär applikation i Java. Servern kan hantera enklare GET,  HEAD och POST förfrågningar och kan serva statiska filer från disk samt bildformat med rätt content-type i svaret. 

Servern är  multitrådad och kan hantera flera förfrågningar simultant.

Vi har också flera url-sökväger som ger oss möjlighet att skicka in information till webbservern via url parametrar tillsammans med GET och som body text tillsammans med POST förfrågan. Den inskickade informationen lagras i en MSQL-databas och kan skickas tillbaka som ett json dokument när det efterfrågas via en GET mot URL "/contacts/". 

Inskickningen av information hanteras med hjälp av ett HTML formulär.

 ## Specifikation

Alla förfrågningar hanteras med hjälp av plugins som hanterar olika typer av filer och data till databasen.  Plugins laddas in dynamiskt och routing har vi löst med med hjälp av annotations i runtime.

Alla pluginklasser extendas från Interfacet IoHandler som ligger i modul SPI.

- `GetContact.java` hanterar GET request som hämtar data från databasen
- `GetContactInsert.java` hanteres en insert till databasen via GET (Url-parametrar)
- `GetFile.java` hanterar GET request för filer
- `PostContact.java` hanterar insert till Databasen via POST

<img src="https://tva1.sinaimg.cn/large/008eGmZEgy1gnq02hla4aj30ku0d674k.jpg" alt="Skärmavbild 2021-02-16 kl. 20.34.37" style="zoom: 50%;" />

Följande url-sökvägar är tillgängliga:

- `/postviaget.html`: skapa kontakt i databas via GET-metod; kontakt returneras som Json objekt
- `/postviapost.html`:  skapa kontakt i databas via POST-metod; kontakt returneras som Json objekt
- `/contacts/`:  returnerar alla kontakter från databas som Json 
- `/contacts/id`: returnerar respektive kontakt från databas som Json 

## ULM

![UML](https://tva1.sinaimg.cn/large/008eGmZEgy1gnquaurbdgj30ye0u07qz.jpg)

## Installation

Klona repository och kör programmet från terminalen eller i en IDE (till ex. Intellji). 

1. `$ git clone https://github.com/boalbert/HttpServerWebservices.git`

2. Packa upp 'Webservices.zip' och filer under: `.../users/documents/Webservices`.
   (Exempelvis: `C:\Users\fornamn.efternamn\Documents\Webservices`)
   
3. Compile och kör programmet med Maven 

Implementation av databasen hanteras i persistence-filen.

## Licens

[MIT License](https://opensource.org/licenses/MIT)

