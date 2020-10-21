# Kjernelogikken

Denne modulen inneholder domenelaget (**core**) og persistenslaget (**persistence**). Disse vil være felles for alle versjoner av spillet.

## Domenelaget
Domenelaget inneholder kjernelogikken i spillet. Det er hovedsaklig klassen **Board** som håndterer spillets gang og har kontroll på hva som skjer. 
Klassene **Player** og **Square** er rene dataklasser, mens **Opponent**-klassene er en utvidelse av Player som inneholder litt logikk for å la datamaskinen spille selv.
Opponent et et grensesnitt med 3 implementasjoner i varierende vanskelighetsgrad.

## Persistenslaget
Persistenslaget tar seg av all filhåndtering og serialisering/deserialisering av objekter. 
Vi bruker versjon 2.8.5 av biblioteket **GSON** for å serialisere/deserialisere objekter i JSON-format.
Dette brukes både til lokal fillagring og for å sende data over HTTP.

## Klassediagram

 ```plantuml
class Board
interface Opponent
class EasyOpponent
class MediumOpponent
class HardOpponent
class Player
class Square

EasyOpponent ..|> Opponent
MediumOpponent ..|> Opponent
HardOpponent ..|> Opponent

EasyOpponent --|> Player
MediumOpponent --|> Player
HardOpponent --|> Player

Player --> Board
Square --> Board
 ```