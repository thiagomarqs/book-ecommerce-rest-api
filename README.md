<p float="left">
  <img src="https://img.shields.io/badge/status-development-yellow"/>
  <img src="https://img.shields.io/github/last-commit/thiagomarqs/book-ecommerce-rest-api"/>
  <img src="https://img.shields.io/github/commit-activity/m/thiagomarqs/book-ecommerce-rest-api"/>
</p>

# Book e-commerce REST API
REST API built with Spring Boot for a fictional online book store called "Nozama" (yes, "Amazon" but backwards).  
I'm using this project to study back-end development with Java and Spring Boot, as well as to practice some other development concepts (such as TDD, API documentation, Clean Code...) and strength my skills.

## The Idea
This project's intent is to simulate an e-commerce, by allowing the Admins to manage the store's products catalog, and by allowing users to view the catalog, add items to their cart, make orders and purchase the books.

As this project consists of a **REST** API, one of the goals is to follow the <a href="https://www.ics.uci.edu/~fielding/pubs/dissertation/rest_arch_style.htm">REST</a> principles described by Roy Fielding, such as Hyperlinking, Statelessness, Uniform Interface, etc.

Also, the transactions performed by this project will be entirely fake (some simple database operations to simulate order registration and payment making) and thus there will be no interactions with no real payment API.

## Features
These are the features that either I have already implemented or I intend to implement:
| Feature | Status |
| :------ | :-----: |
| CRUD of the main entities (Book, Author, Category and Publisher) | Implemented |
| Authentication and Authorization (Admin and Customer users and protected endpoints)| Pending |
| Order making (Users be able to add items to their cart and purchase) | Pending |

## Architecture
The architecture of this project was somewhat inspired by the Clean Architecture. I tried to create something myself and attempted to follow a more simple structure, as this project is more simple and does not required a so neat and advanced organization. Also, as the intent __is__ to use Spring Boot as __the__ framework, I intentionally didn't worry about making the project decoupled from Spring.  
Basically, this is the structure:  
- Application:
  * Interaction with the external world and utilization of the domain layer. Basically the controllers.
- Domain:
  * Business-specific stuff, such as the entities and the use cases.
- Infrastructure:
  * Any configuration required by the Spring and the libraries used.

## Technologies Used
So far, these technologies have been used:  
- Java 17 LTS with Maven
- Spring Boot
- Hibernate
- Spring Data JPA
- Spring Security
- JUnit 5
- Mockito
- Spring HATEOAS
- Flyway
- Springdoc

The full list of dependencies can be checked out in this project's pom.xml file.
