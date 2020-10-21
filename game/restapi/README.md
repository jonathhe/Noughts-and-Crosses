# Api

Denne modulen inneholder **REST-API** for [game](../README.md) der logikken er lagt i klassen GameService.

API-et følger [JAX-RS-standarden](https://en.wikipedia.org/wiki/Java_API_for_RESTful_Web_Services).

Ett **Board**-objekt gjøres tilgjengelig til API-et ved bruk av **@Inject**. 

API-et tilbyr 12 metoder:

* Oppnå kontakt: **GET** til **tjeneste-URL**, returnerer en dummy-streng som kan brukest til å sjekke statuskode.
* Se alle square-objekter: **GET** til **tjeneste-URL/squares**, returnerer alle square-objekter i board.
* Sjekke om spillet er over: **GET** til **tjeneste-URL/over**, returnerer en streng som kan brukes til å sjekke om spillet er over.
* Sjekke om spillet er startet: **GET** til **tjeneste-URL/started**, returnerer en streng som kan brukes til å sjekke om spillet er startet.
* Få spiller en eller to: **GET** til **tjeneste-URL/started/num**, returnerer spiller en eller to basert på num parameteren.
* Få vinner: **GET** til **tjeneste-URL/winner**, returnerer vinnende spiller.
* Få taper: **GET** til **tjeneste-URL/looser**, returnerer tapende spiller.
* Velg en brikke: **GET** til **tjeneste-URL/move/num**, gjør et trekk ved å velge en square (brikke) som velges basert på num parameteren.
* Opprett ett brett: **PUT** til **tjeneste-URL/board**, Et en list med square-objekter sendes som JSON-kodet data og et spillbrett opprettes.
* Sett en spiller: **POST** til **tjeneste-URL/player/num**, en JSON-kodet spiller sendes med. Denne blir satt som spiller en eller to i board klassen basert på num parameteren.
* Hvem sin tur: **GET** til **tjeneste-URL/playersTurn**, returnerer en streng som sier hvilken spiller sin tur det er.

## Dataformat
All data sendes og returneres i **JSON**-format som gjøres mulig ved at konvertering av data gjøres med **SquareSerializer** og **PlayerSerializer** for henholdsvis **Square-** og **Player**-klassen.
