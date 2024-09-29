# Video Management Project

This project is a Java application based on Spring Boot that allows for the management of videos and categories. The system offers functionalities to register, list, search, modify, and remove videos and categories.

## Features

- **Video Registration**: Allows adding new videos to the application, associating them with a category.
- **Video Listing**: Lists all registered videos with options to filter by title and category.
- **Search by ID**: Provides functionality to search for videos or categories by ID.
- **Video Update**: Allows modifying the information of existing videos.
- **Removal of Videos and Categories**: Includes functionality to remove videos and categories from the database.

## Technologies Used

- **Java**: Programming language used.
- **Spring Boot**: Framework for building web applications.
- **JPA (Java Persistence API)**: For interacting with the database.
- **JUnit**: For unit testing.
- **Mockito**: To mock objects in unit tests.
- **Jakarta Validation**: For data validation.

## Project Structure

- `src/main/java/com/semana/demo`: Contains the application source code.
  - `categories`: Package responsible for functionalities related to categories.
  - `videos`: Package responsible for functionalities related to videos.
  - `controller`: Package that contains controllers managing HTTP requests.
  - `exceptions`: Package for handling custom exceptions.
- `src/test/java/com/semana/demo`: Contains unit tests for the application.

## How to Run the Project

1. Clone this repository to your local machine:
   ```bash
   git clone https://github.com/github.com/gu1334/VideosApi.git
   cd VideosApi

2. Open the project in your preferred IDE (such as IntelliJ or Eclipse).

3. Ensure you have JDK 17 or higher installed.

4. Run the application using the following Maven command:

  ```bash
  mvn spring-boot:run
  ```
5. Access the application in your browser at http://localhost:8080.

##Tests

Unit tests can be executed using Maven. To do this, use the following command:

  ```bash
  mvn test
  ```

##Contribution

Contributions are welcome! Feel free to open issues or pull requests.




