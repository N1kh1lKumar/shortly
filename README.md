# Shortly

A simple and lightweight URL shortener built with **Java, Spring Boot, Spring Data JPA, PostgreSQL, and vanilla JavaScript**.

Shortly converts long URLs into short, shareable links and redirects users to their original destinations.

## Features

- Shorten long URLs into unique 6-character codes.
- Redirect short links to their original URLs.
- Store URL mappings in PostgreSQL.
- Track link clicks.
- Validate URLs and support HTTP/HTTPS destinations.
- Reuse existing short links for previously shortened URLs.
- Responsive frontend with HTML, CSS, and JavaScript.

## Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java 21, Spring Boot |
| Data Access | Spring Data JPA, Hibernate |
| Database | PostgreSQL |
| Frontend | HTML, CSS, JavaScript |
| Build Tool | Maven |
| Deployment | Railway |

## Project Structure

```text
shortly/
├── backend/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/shortly/
│   │       │   ├── controller/
│   │       │   ├── dto/
│   │       │   ├── entity/
│   │       │   ├── exception/
│   │       │   ├── repository/
│   │       │   └── service/
│   │       └── resources/
│   │           ├── application.yaml
│   │           └── static/
│   │               ├── index.html
│   │               ├── script.js
│   │               └── style.css
│   ├── mvnw
│   └── pom.xml
├── frontend/
│   ├── index.html
│   ├── script.js
│   └── style.css
├── scripts/
│   └── sync-frontend.sh
├── .gitignore
├── LICENSE
└── README.md
```

## Prerequisites

Make sure you have installed:

- Java 21 or newer
- PostgreSQL
- Git

## Run Locally

### 1. Clone the repository

```bash
git clone https://github.com/N1kh1lKumar/shortly.git
cd shortly
```

### 2. Create the PostgreSQL database

Open PostgreSQL and execute:

```sql
CREATE DATABASE shortly_db;
```

### 3. Configure environment variables

From the project root, configure your database credentials:

```bash
export DB_URL=jdbc:postgresql://localhost:5432/shortly_db
export DB_USERNAME=postgres
export DB_PASSWORD='your-postgres-password'
export SHORT_URL_BASE=http://localhost:8080
```

Replace `your-postgres-password` with your actual PostgreSQL password.

### 4. Run the application

```bash
cd backend
./mvnw test
./mvnw spring-boot:run
```

### 5. Open Shortly

Visit:

```text
http://localhost:8080
```

The Spring Boot application serves the frontend and backend from the same origin.

## API Endpoints

### Shorten a URL

**Endpoint**

```http
POST /api/v1/url/shorten
```

**Request Body**

```json
{
  "longUrl": "https://www.example.com"
}
```

**Successful Response**

```http
HTTP/1.1 201 Created
```

```json
{
  "shortUrl": "http://localhost:8080/Ab12Cd"
}
```

### Redirect to Original URL

**Endpoint**

```http
GET /{shortCode}
```

Example:

```http
GET /Ab12Cd
```

The application redirects the visitor to the original URL.

## Frontend Development

The frontend source files are maintained in the `frontend/` directory.

Spring Boot serves the frontend from:

```text
backend/src/main/resources/static/
```

Whenever you modify the frontend, synchronize the files before committing:

```bash
./scripts/sync-frontend.sh
```

To verify that both directories match:

```bash
diff -r frontend backend/src/main/resources/static
```

No output means the files are synchronized.

## Deployment

Shortly is deployed using Railway.

The deployment uses:

- Railway for hosting the Spring Boot application.
- Railway PostgreSQL for persistent database storage.
- Railway environment variables for database and application configuration.

Configure the following variables in the backend service:

| Variable | Description |
|---|---|
| `DB_URL` | PostgreSQL JDBC connection URL |
| `DB_USERNAME` | Database username |
| `DB_PASSWORD` | Database password |
| `SHORT_URL_BASE` | Public URL of the deployed application |
| `PORT` | Application port provided by Railway |

Set `SHORT_URL_BASE` to your Railway public domain so generated short links point to the deployed application rather than localhost.

## Future Improvements

- Custom short URLs
- URL expiration
- QR code generation
- Improved analytics dashboard
- Rate limiting and abuse prevention
- Automated test coverage

## Author

**Nikhil Kumar**

- GitHub: [@N1kh1lKumar](https://github.com/N1kh1lKumar)

## License

This project is licensed under the [MIT License](LICENSE).
