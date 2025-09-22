> 🇬🇧 **English Version** | 🇧🇷 [Leia esta página em Português](README.md)

# 🎵 Game Soundtracks API

A RESTful API to catalog and explore video game soundtracks. This project was developed as part of a study portfolio, focusing on backend development best practices with the Spring ecosystem.

## 📖 About the Project

The goal of the Game Soundtracks API is to provide a service where users can search for information about games, their soundtrack albums, the songs they contain, and their artists. It will also include authentication features so users can save their favorite songs and albums.

## ✨ Features

- [ ] Full CRUD for Games, Albums, Songs, and Artists.
- [ ] Flexible search system to find songs by game, album, or artist.
- [ ] User authentication via JWT (JSON Web Token).
- [ ] Endpoint for users to favorite their preferred songs.
- [ ] Automatically generated and interactive API documentation with Swagger UI.

## 🛠️ Technologies Used

This API was built using the following technologies:

- **Language:** [Kotlin](https://kotlinlang.org/) / [Java 21](https://www.oracle.com/java/)
- **Main Framework:** [Spring Boot 3](https://spring.io/projects/spring-boot)
  - **Spring Web:** For building RESTful endpoints.
  - **Spring Data JPA:** For data persistence.
  - **Spring Security:** For authentication and authorization.
- **Build Tool:** [Gradle](https://gradle.org/)
- **Utilities:** [Lombok](https://projectlombok.org/)
- **Authentication:** [JWT (jjwt)](https://github.com/jwtk/jjwt)
- **Database:**
  - [PostgreSQL](https://www.postgresql.org/): For production environment.
  - [H2 Database](https://www.h2database.com/): For development and testing environments.
- **Documentation:** [Springdoc OpenAPI (Swagger UI)](https://springdoc.org/)

## 🚀 How to Run the Project

To run this project locally, follow the steps below.

### Prerequisites

- Java (JDK) 21 or higher.
- Git.
- (Optional) A running PostgreSQL instance.

### Steps

1. **Clone the repository:**

    ```bash
    git clone [https://github.com/manojohnsons/game-soundtracks-api.git](https://github.com/manojohnsons/game-soundtracks-api.git)
    cd game-soundtracks-api
    ```

2. **Run the application:**
    The project uses the Gradle Wrapper, so you don’t need Gradle installed on your machine.

    ```bash
    # On Linux or macOS
    ./gradlew bootRun

    # On Windows
    .\gradlew.bat bootRun
    ```

3. The API will be available at `http://localhost:8080`.

## 📚 API Documentation

Thanks to Springdoc OpenAPI, the complete and interactive API documentation is automatically generated. After starting the application, you can access it at:

- **[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)**

On this page, you can see all available endpoints, their parameters, request/response formats, and even test the API directly from your browser.

## 📝 License

This project is under the MIT license. See the [LICENSE](LICENSE) file for more details.
