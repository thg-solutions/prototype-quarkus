# prototype-quarkus
Diese Anwendung ist eine möglichst genaue Nachbildung von 
[prototype](https://github.com/thg-solutions/prototype), 
allerdings unter Verwendung des Micro-Frameworks 
[Quarkus](https://quarkus.io) anstelle von Spring Boot.

Spring Boot übernimmt weite Teile der Konfiguration einer Anwendung durch Einsatz von Reflection. So führt zum
 Beispiel die Verwendung des Interfaces `CrudRepository` dazu, dass sämtliche in diesem Interface deklarierten
  Methoden erst zur Laufzeit, genauer gesagt beim Start der Anwendung implementiert werden.
  
Da Reflections besonders "teuer" sind versucht Quarkus, weitestgehend auf deren Verwendung zu verzichten. 
Das bedeutet zum einen, dass diese Methoden bei Quarkus wirklich eigenhändig implementiert werden müssen, zum anderen,
dass einige gängige Annotationen nicht ausgewertet werden (z.B. `@Table`).

## Artefakt und Container Image bauen

Da Quarkus (zumindest in frühen Versionen) die `@Table`-Annotation nicht unterstützt und MariaDB "case sensitive" ist, 
wird für dieses Beispiel PostGreSQL verwendet. Die Konfiguration geschieht wieder in 
`main/resources/application.properties`. Quarkus benötigt keine zweite Datei für die Konfiguration der Tests. 
Stattdessen wird den Properties, die nur die Tests betreffen, der Prefix `%test` vorangestellt. Auch in diesem
Beispiel wird H2 als In Memory DB verwendet.

Nur weil Quarkus das anbietet, wird zum Aufbau der Produktionsdatenbank Flyway verwendet. Die versionierten Skripte
liegen in `src/main/resources/db/migration`. Die Testdatenbank wird zu Beginn der Tests neu erzeugt und 
Hibernate-typisch per `import.sql` befüllt.

```
~/prototype-quarkus$ ./mvnw package -Pnative -Dnative-image.docker-build=true
```
testet und baut die Applikation und erzeugt daraus eine ausführbare Datei.
```
~/prototype-quarkus$ docker build -f src/main/docker/Dockerfile.native -t quarkus/prototype-quarkus:v1 .
```
erzeugt daraus ein Docker-Image.

## Container starten mit docker-compose

docker-compose Version 3 unterstützt es nicht mehr, darauf zu warten, dass der Health Check eines zuvor gestarteten
Containers erfolgreich ist (`condition: service_healthy`). Deshalb wird Version 2.4 verwendet.

```
~/prototype$ cd src/main/docker
~/prototype/src/main/docker$ docker-compose up -d
```
erzeugt Container-Instanzen für `postgres` und `prototype-quarkus`, zusätzlich ein Netzwerk `docker-default`von Type 
`bridge`, über das beide Container per DNS verbunden sind. An dieser Stelle kann es zu einem Port-Konflikt kommen, 
wenn auf dem Rechner bereits eine lokale PostGreSQL auf den Port 5432 lauscht. Diese ist dann mit 
 
```
systemctl stop postgresql
```

oder einem vergleichbaren Befehl zu stoppen.

Das Befüllen der Datenbank geschieht bei PostGreSQL genau wie bei MariaDB durch das mounten eines Volumes am mountpoint
`/docker-entrypoint-initdb.d`. Dies ist in `docker-compose.yml` aber auskommentiert, da für das Quarkus-Beispiel
 Flyway zum Einsatz kommt.

```
~/prototype/src/main/docker$ docker-compose down
```
stoppt und löscht Container und Netzwerk. 

## Ausführung der Anwendung

Nach erfolgreichem Start der Container (s.o.) wird ein beliebiger Browser auf `localhost:8080` gerichtet. 
`localhost:8080/people` zeigt alle in der Datenbank gespeicherten `Person`-Entities an, 
`localhost:8080/people/2` die Entity mit der Id 2. Die möglichen Suchanfragen sind der Klasse `PeopleResource` zu
 entnehmen. So liefert z.B.  `localhost:8080/people/search/lastnamestartingwith?abbr=Bo` die Namen, die
 mit "Bo" anfangen. Für weitere Beispiele sei auf den Quellcode verwiesen.
 
## OpenAPI und Swagger

Durch Hinzufügen des Artefacts `quarkus-smallrye-openapi` unterstützt Quarkus OpenAPI und Swagger. Dabei können die
 Endpoints des REST-Interfaces nur während der Entwicklung (`quarkus:dev`) bestimmt werden. Die so erzeugte Datei
 `openapi.yml` wird vor dem Bauen der Applikation im Verzeichnis META-INF abgelegt. Nach dem Starten der
 Applikation (s.o.) können die Endpoints unter `localhost:8080/swagger-ui` angezeigt und getestet werden.