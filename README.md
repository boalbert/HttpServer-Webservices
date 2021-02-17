# Http server - web services
## PROJEKTARBETE | Web services och integrationer | JU20 | ITHS

I detta projektarbete byggde vi en Http server som en modulär applikation i Java. Servern kan hantera enklare GET,  HEAD och POST förfrågningar och kan serva statiska filer från disk samt bildformat med rätt content-type i svaret. 

Servern är  multitrådad och kan hantera flera förfrågningar samtidigt.

Vi har okså flera url som ger oss möjlighet att skicka in information till webbservern via url parametrar tillsammans med GET och som body text tillsammans med POST förfrågan. Den inskickade informationen lagras i en MSQL-databas och kan skickas tillbaka som ett json dokument när det efterfrågas via en GET mot  URL "get/contact". 

Inskickningen av information hanteras med hjälp av en html formulär.

 ## Specifikation

Alla förfrågan hanteras med hjålp av plugins som hantera olika typa av filer och data till databasen.  Plugins laddas in dynamiskt och routing har vi löst med med hjälp av annotations i runtime.

Alla pluginklasser extender från Interfacet IOHanlder som ligger i modul spi.

- `GetContact.java` hanteras GET request som hämtas data från databasen
- `GetContactInsert.java` hanteres en insert till Databasen via GET
- `GetFile.java` hanteras GET request för filer
- `PostContact.java` hanteres en insert till Databasen via POST

![Skärmavbild 2021-02-16 kl. 20.34.37](https://tva1.sinaimg.cn/large/008eGmZEgy1gnq02hla4aj30ku0d674k.jpg)


*
## Installation

**Klona repository**

Klona repository och kör programmet från terminalen eller i en IDE (till ex. Intellji). 

1. `$ git clone https://github.com/boalbert/HttpServerWebservices.git`

2. Compile och kör programet med Maven eller via terminalen

OBS: implementation av databasen görs i persistence.xml filen.

## Licens

[MIT License](

