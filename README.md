# Project Documentation for Technical Evaluation

## Project description

The project is developed in Spring Boot 2.7.17.RELEASE on Java 17. It exposes two Full REST services that allow queries on an H2 database, 
which is loaded with data obtained from spoonacular API

## Previous requirements

Make sure you have Java and Maven installed on your system before running this project.

- [Java](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)

## Project Configuration

1. **Clone the Repository:**
   ```bash
   git clone https://gitlab.com/marianocamarzana/challengue-recipes.git
   ```

2. **Compile the Project:**
   ```bash
   mvn clean install
   ```

## Project execution

1. **Run the Application:**
   ```bash
   mvn spring-boot:run
   ```

   The application will be available in `http://localhost:8080`.

2. **Available Endpoints:**
   - `POST /api/challengue/recipe`
     - `{ "query": "burger"}`: In this endpoint, the first thing that is done is to retrieve the recipes according to the QUERY filter through the spponacular API. Subsequently, they are persisted in the H2 database as long as they have not been previously persisted.  
   - `GET /api/challengue/recipe?ids=640266`
     - `ids`: ids is a list of the recipe identifiers that will be used to retrieve from the database. In this endpoint, the first thing that is done is to retrieve the recipes according to the QUERY filter through the spponacular API. Subsequently, they are persisted in the H2 database as long as they have not been previously persisted.  
   - `PATCH /api/challengue/recipe?ids=640266`
     - `{ "score": "11" }`: Update a specific recipe with the indicated score.
## Project Structure

The project structure follows the conventions of a Spring Boot project:

- `src/main/java`: Contains the source code of the application.
  - `com.evaluacion.tecnica`: Main package.
    - `configuration`: Application Settings.
    - `controller`: Endpoint drivers.
    - `Model`: Contains the models used for the persistence layer.
     - `dto`: Common models used as responses in own services.
    - `repository`: JPA repository.
    - `service`: Interface where the methods that will carry out the business logic are declared.
    - `service.impl`: Implementation of the methods that will carry out the business logic.


## Swagger

The project is documented using Swagger. To access its documentation with the possibility of carrying out invocation tests, go to:
http://localhost:8080/swagger-ui/index.html

## Main Technologies Used

- [Spring Boot](https://spring.io/projects/spring-boot): Framework for the development of Java applications based on the principle of convention over configuration.
- [ModelMapper](https://modelmapper.org/): Allows mapping between entities and DTOs automatically.
- [H2 Database](https://www.h2database.com/): Embedded database written in Java.

## License

This project is licensed [MIT](LICENSE).
