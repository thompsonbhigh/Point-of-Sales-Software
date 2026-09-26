# Point of Sales Software

A JavaFX point of sale prototype for a Panda Express-style restaurant, backed by PostgreSQL. The desktop interface supports meal ordering, employee and manager sign-on, inventory restocking, and sales, product usage, and X/Z reports.

## Requirements

- A JDK compatible with the included JavaFX 25 SDK
- A JavaFX SDK for your operating system and architecture (a copy is under `javafx-sdk-25/`)
- PostgreSQL with the restaurant schema and sample data needed by the DAO queries
- The PostgreSQL JDBC driver (the included JavaFX `lib/` directory contains a driver JAR)

Set `DB_URL`, `DB_USER`, and `DB_PASSWORD` in your environment. `DB_URL` must be a JDBC URL such as `jdbc:postgresql://localhost:5432/restaurant`; the defaults in `GUI/dao/Db.java` are placeholders.

## Build and run

From this repository's root, on a system where the bundled JavaFX SDK is compatible:

```bash
mkdir -p bin
javac --module-path javafx-sdk-25/lib --add-modules javafx.controls,javafx.fxml \
  -cp 'javafx-sdk-25/lib/postgresql-42.7.8.jar' \
  -d bin GUI/model/*.java GUI/dao/*.java GUI/controller/*.java GUI/view/MainApp.java
cp GUI/view/*.fxml bin/view/
java --module-path javafx-sdk-25/lib --add-modules javafx.controls,javafx.fxml \
  -cp 'bin:javafx-sdk-25/lib/postgresql-42.7.8.jar' view.MainApp
```

On Windows, use `;` instead of `:` in the runtime classpath and a JavaFX SDK built for Windows. The launch class is `view.MainApp`, which loads `view/Main_Screen.fxml` from the classpath.

The SQL scripts in `Database/` define portions of the schema and sample/demo queries. They are separate scripts, not a single migration or one-command database installer; inspect them before applying them to a database.

| Path | Purpose |
| --- | --- |
| `GUI/view/` | JavaFX entry point and FXML screens |
| `GUI/controller/` | UI actions and navigation |
| `GUI/model/` and `GUI/dao/` | Data models and PostgreSQL access |
| `GUI/Tests/` | Java model tests |
| `Database/` | SQL schema fragments, queries, and data generators |
| `javadoc/` | Generated API documentation |
