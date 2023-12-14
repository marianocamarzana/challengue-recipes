# Project Documentation for Technical Evaluation

## Project description

The project is developed in Spring Boot 2.7.17.RELEASE on Java 17. It exposes two Full REST services that allow queries on an H2 database, 
which is loaded with data obtained from spoonacular API.

## Previous requirements

Make sure you have Java and Maven installed on your system before running this project.

- [Java](https://www.oracle.com/java/technologies/javase-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)

## Project Configuration

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/marianocamarzana/challengue-recipes
   
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

2. ### Available Endpoints
    **Task 1. API for Data Sourcing:**
   - `POST /api/challengue/recipe`
     - `{ "query": "burger"}`: 
     - Steps
       - 1 - Retrieve the recipes according to the QUERY filter through the spoonacular API
       - 2 - Recipes are persisted in the H2 database as long as they have not been previously persisted.  
   <br>
   
   **Task 2. APIs to Serve Data to a Recipes App:**
   - `GET /api/challengue/recipe`
     - `ids`: ids is a list of recipe identifiers that will be used to retrieve recipes from the database. 
     -  Retrieves the list of recipes from the H2 Database filtering by the list of ids. If ids are not sent, all recipes are recovered.
 
   - `PATCH /api/challengue/recipe/{id}/score`
     - `{ "score": "4" }`
     -  Update a specific recipe with the indicated score.
     
     <br>
     
   **Task 3. APIs to Serve Data to a Recipes App:**
   - `PATCH /api/challengue/recipe/{id}/detail`
       - 1 - The recipe detail is retrieved from the API and instead of mapping the model that involves many relationships, it is
     uses the ResponseEntity object, where the response body is of type String. To extract the desired field from the JSON response Jackson library is used.
       - 2 - The specific recipe is recovered from the database
       - 3 - The recipe is updated and subsequently persisted in the database

3. ### Additional configuration
    - Configuration was added to the project so that the solution can be embedded within the following docker image openjdk:11.0.6-jre (available in docker hub).

3. ### Security
    - For the challenge, authentication was added to the application through Spring Security using Basic Auth, for commercial applications OAuth2 is used since it provides a more robust level of security compared to Basic Authentication. Instead of sending credentials (username and password) in each request, OAuth2 uses access tokens that have a limited lifespan and do not contain sensitive information. This reduces the risk of credential exposure and improves the overall security of the application.

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

## Uso de Lombok

This project uses Lombok to reduce code verbosity. Make sure you have the Lombok plugin installed in your IDE for proper functioning.

## Exception handling and validations.

Different use cases were considered in which exceptions and validations must be treated correctly.

## Swagger

The project is documented using Swagger. To access its documentation with the possibility of carrying out invocation tests, go to:
http://localhost:8080/swagger-ui/index.html

## Postman

The Postman collection is available with the developed endpoints codeChallengueRecipes.postman_collection

## Main Technologies Used

- [Spring Boot](https://spring.io/projects/spring-boot): Framework for the development of Java applications based on the principle of convention over configuration.
- [Lombok](https://projectlombok.org/): Library that speeds up development by eliminating the need to write boilerplate code.
- [ModelMapper](https://modelmapper.org/): Allows mapping between entities and DTOs automatically.
- [H2 Database](https://www.h2database.com/): Embedded database written in Java.

## License

This project is licensed [MIT](LICENSE).
