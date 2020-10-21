
# Web-server
Denne modulen inneholder **web-serveren** for [game](../README.md).

Dette er webserveren til [restapi-modulen](../restapi/README.md) med støtte for [JAX-RS-standarden] der [Jersey](https://eclipse-ee4j.github.io/jersey/) er brukt.

Som **HTTP-tjener** har vi brukt [grizzly2](https://javaee.github.io/grizzly/).

To klasser er laget. **GameConfig** registrerer klassene til **REST**-tjenesten, mens **GameGrizzlyApp** inneholder koden for å starte serveren.

* **GameConfig**: Registrerer hvilke klasser og objekter som skal brukes sammen med REST-tjenesten.
* **GameGrizzlyApp**: Klasse som brukes til å starte opp og eventuelt stoppe serveren.
