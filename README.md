# Shortly

A URL shortener built with Spring Boot, Spring Data JPA, PostgreSQL, and a vanilla HTML/CSS/JavaScript frontend.

## Project structure

- `backend/` — Spring Boot REST API
- `frontend/` — static frontend files

## Requirements

- Java 17 or newer
- PostgreSQL for normal application runs

## Run the backend

From the project root (the directory containing `backend/` and `frontend/`):

```bash
cd backend
./mvnw test
./mvnw spring-boot:run
```

Open `http://localhost:8080` only if the frontend has been copied/served by a web server. Otherwise, serve the `frontend/` directory with VS Code Live Server (port 5500), then use the page at `http://localhost:5500`.

The API listens on port `8080`.

## PostgreSQL configuration

The defaults are:

- Database: `shortly_db`
- Username: `postgres`
- Password: `root`

Override them with environment variables:

```bash
export DB_URL=jdbc:postgresql://localhost:5432/shortly_db
export DB_USERNAME=postgres
export DB_PASSWORD='your-password'
export SHORT_URL_BASE=http://localhost:8080
```

## Test

`./mvnw test` uses an in-memory H2 database and does not require PostgreSQL to be running.


## Correct terminal location and Java version

Run these commands from the project root (where `backend/` and `frontend/` are visible):

```bash
cd backend
chmod +x mvnw
./mvnw clean test
./mvnw spring-boot:run
```

The Maven compiler target is Java 17. Verify the JDK compiler with `javac -version`; `java -version` alone is not sufficient.
