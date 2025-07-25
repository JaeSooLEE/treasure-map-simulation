# Treasure Map Simulation

Un simulateur d’exploration de carte en Spring Boot.

1. Lit un fichier d'entrée qui décrit:
    - la **carte** (dimensions, montagnes, trésors)
    - des **aventuriers** (position, orientation, séquence d'actions)
2. Exécute tour par tour les déplacements
3. Ecrit un fichier de sortie  au même format (état final de la carte)


## Organisation:

**Architecture hexagonale**
- `domain.model` → entités & règles métier
- `application.port.*` → interfaces de cas d’usage & I/O
- `application.service` → implémentations des use cases
- `adapter.out.file` → parsing/fichier → DTO → domaine

**Tests**
- Unit tests (JUnit5 + AssertJ, Mockito)
- Integration test

**Build & run**
- ./mvnw clean package
- java -jar target/treasuremap-0.0.1-SNAPSHOT.jar input.txt output.txt