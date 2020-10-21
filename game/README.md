# Bondesjakk

Dette prosjektet implementerer spillet bondesjakk (Tic-tac-toe). Prosjektet er satt opp i 4 adskilte moduler
som bygges med **Gradle**. Hovedmappen og hver modul inneholder en build.gradle som gir full oversikt over hvilke tillegg vi bruker.
Vi har brukt versjon 12.0.2 for både **Java** og **JavaFX**. JavaFX-pluginen vi har brukt er versjon 0.0.8 av **OpenJFX**.
Testing gjøres med versjon 4.12 av **JUnit**.

Disse tre tilleggene kan kjøres via Gradle for å produsere rapporter, som legges i mappen `\build\reports\` for hver modul:
*  **SpotBugs** for å avdekke feil.
*  **CheckStyle** for å sjekke at vi bruker standard syntaks/kodestil.
*  **Jacoco** for å sjekke testdekningsgrad.
 
 Applikasjonen er satt opp  med 
 støtte både for å spille i en lokal JavaFX-app og med støtte for REST-API slik at man i teorien kan spille 
 over internett via HTTP. Vi har implementert en REST-versjon av spillet som kjører mot en lokal HTTP-server.
 
 Man kan spille mot datamaskinen eller i en lokal flerspiller-versjon. Spillet lagrer
 info om hver spiller (navn og seiersstatistikk). Man bytter spiller ved å skrive inn et nytt navn i navnefeltet og trykke enter.
 
 ![Ferdig Applikasjon](fxui/game.jpg) 
 
 ## Moduler
 
 ### core
 
 Kjernelogikken som brukes (indirekte) av alle versjoner av spillet, uavhengig av plattform.
 
 ### fxui
 
 Brukergrensesnittet som brukes både av den lokale JavaFX-appen og den REST-baserte versjonen.
 REST-versjonen bruker et API for å kommunisere med `core` via `restserver`.
 
 ### restapi
 
 Grensesnittet som lar frontend kommunisere med serveren via et API. Det oversetter forespørsler
 mellom `restserver` og de to versjonene av appen som bruker APIet.
 
 ### restserver
 
 Selve HTTP-serveren som tar imot og besvarer forespørsler fra klienten. 

 ### frontend
 
 Brukergrensesnittet som brukes av web-versjonen av appen. Denne er bygd med React og kan kjøres uavhengig
 av de andre modulene, den må bare ha en server å kommunisere med.




## Brukerhistorier
- Som bruker skal jeg kunne kjøre applikasjonen og spille mot andre.
- Som bruker vil jeg velge om jeg skal spille mot en annen spiller eller mot datamaskinen.
- Som bruker vil jeg se spillbrettet når jeg kjører applikasjonen.
- Som bruker skal jeg kunne plassere ut et merke i en av rutene på spillbrettet.
- Som bruker vil jeg at spillet skal huske meg og hvor mange ganger jeg har vunnet.


## Pakkediagram

 ```plantuml
 scale 1.25
 skinparam nodesep 50

package "core" {
    [Board]
    [Player] -up- [Board]
    [Square] -down- [Board]
}

package "fxui" {
    () AbstractController as Controller
    [LocalController] -right- Controller
    [LocalController] -down- [LocalGameDataAccess]
    [RestController] -left- Controller
    [RestController] -down- [RestGameDataAccess]
    [RestController] -[hidden]left- GameDataAccess
    GameDataAccess -right- [RestGameDataAccess]
    GameDataAccess -left- [LocalGameDataAccess]
}

cloud "restapi" {
    [GameService]
}

package "restserver" {
    [GameGrizzlyApp]
}

() HTTP

package "frontend" {
    [WebApp]
}

Board -up- LocalGameDataAccess
GameService -right- HTTP
WebApp -up- HTTP
RestGameDataAccess -down- HTTP
GameService -up- GameGrizzlyApp
GameGrizzlyApp -up- Board
 ```
 
 ## Sekvensdiagram
 
Dette sekvensdiagrammet tar utgangspunkt i webappen (skrevet med React) og går
gjennom et typisk scenario der brukeren klikker på et av feltene på 
spillbrettet. Diagrammet viser de viktigste kallene som skjer i bakgrunnen fram
til brukeren igjen får muligheten til å gjøre et trekk.

**FrontEnd** er da selve webappen, mens **GameService** representerer APIet
som front-end bruker for å kommunisere med back-end. Back-end er her bare
representert ved **Board**, men dette er da en webserver som holder på Board
og alle andre objekter vi bruker.

Merk at i de to JavaFX-versjonene av appen så ligger det et GameDataAccess-lag
mellom frontend (Controller-klassene) og GameService for å oversette
forespørslene.

**Player1** er her da objektet i Board som representerer **Player** i front-end,
mens **Player2** er motspilleren (en type Opponent, f.eks. MediumOpponent).

  ```plantuml
  scale 1.25
  
 
actor Player
participant FrontEnd
boundary GameService
collections Board
actor Player1
actor Player2

Player -> FrontEnd : Click square
note right of Player
    The player manually clicks on the top right square.
end note
FrontEnd -> GameService : doMove(2)
GameService -> Board : doMove(2)
Board -> Board : doMove(this.squares[2])
Board -> Board : getCurrentPlayer() -> Player1
Board -> Player1 : getSymbol()
Player1 --> Board : "X"
Board -> Board : this.squares[2].setState("X")
Board -> Board : this.isFirstPlayersTurn = false

Board -> Board : isGameOver() -> false
Board -> Board : getCurrentPlayer() -> Player2
Board -> Player2 : is instanceof Opponent
Player2 --> Board : true
Board -> Player2 : chooseSquare(this.squares)
note left of Player2
    The opponent chooses a valid square
    (an unused int [0-8]), with a degree
    of randomness depending on the
    difficulty setting.
end note

Player2 -> Board : doMove(4)
Board -> Player2 : getSymbol()¨
Player2 --> Board : "O"
Board -> Board : this.squares[4].setState("O")
Board -> Board : this.isFirstPlayersTurn = true

GameService -> GameService : getState()
note left of GameService
    GameService automatically returns the game
    state to the front-end for every request
    received. To do this, it collects the info
    from Board and packages it as a JSON string.
end note
GameService -> Board : getSquares()
Board --> GameService : List<Square> squares
GameService -> Board : isFirstPlayersTurn
Board --> GameService : true
GameService -> Board : isGameOver()
Board --> GameService : false
GameService -> Board : getPlayer1()
Board --> GameService : Player1
GameService -> Board : getPlayer2()
Board --> GameService : Player2
GameService --> FrontEnd : A JSON string
FrontEnd -> FrontEnd : Update the GUI
note left of FrontEnd
    The GUI is now updated with the new game state,
    showing that the player has clicked the top right square and
    the AI opponent has clicked the middle square.
end note
  ```