# Group gr1907 repository

## Gruppemedlemmer
  - Benjamin Aune Brekken
  - Emil Hjelle
  - Jonathan Hermansen
  - Lars-Olav Vågene

## Mapper
Hoveddelen av prosjektet ligger i mappen /game/, og er satt opp til å bygges og kjøres med Gradle. Webappen ligger i mappen /frontend/.

## Kjøring
Vi har laget 3 versjoner av spillet, som kjøres slik fra en terminal:
* Lokal JavaFX-app: bruk kommandoen `gradle run` fra mappen /game/
* JavaFX-app som bruker REST API: bruk kommandoen `gradle run --args="http://localhost:8080/"` fra mappen /game/
* Web-app skrevet med React: bruk kommandoen `gradle run --args="serverOnly"` fra mappen /game/ og bruk så kommandoen `npm start` fra mappen /frontend/ i en annen terminal.

For å kunne kjøre web-versjonen av appen må man kanskje først installere [node.js](https://nodejs.org/en/) og kjøre kommandoen `npm install` fra mappen /frontend/.

Det er også mulig å kjøre appen ved hjelp av scriptet `oneClickStart.sh` (scriptet er kun testet for Windows).

## Testing
Prosjektet inneholder mange tester, og disse kan kjøres slik fra en terminal/kommandolinje:
* For å teste Java-delen av kodebasen, gå til mappen /game/ og bruk kommandoen `gradle build`. 
Da genereres også rapporter om kodekvalitet fra CheckStyle, SpotBugs og Jacoco i mappene /game/core/build/reports og /game/fxui/build/reports.
* Webappen bruker to forskjellige typer tester. Gjør følgende for å kjøre disse:
    1. Fra mappen /game/ kjør `gradle run --args="serverOnly"`
    2. Åpne en ny terminal og gå til mappen /frontend/. Kjør `npm install`, `npm test` og `npm start`.
    3. Åpne enda en ny terminal i mappen /frontend/ og kjør `npm run cypress`. Velg så "Run all specs" i Cypress-vinduet som åpner seg.


