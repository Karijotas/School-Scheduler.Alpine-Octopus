# School timetable scheduler 

School timetable scheduler is designed to manage teachers, classrooms, student groups, programs and other relative information within the school. It facilitates and expedites schedule creation by overlooking teacher, classroom availability and helping to solve overbooking or time management problems.

#### Technologies used: 
- React 18.0.0, Semantic-UI-css 2.5.0, Semantic-UI-react 2.1.4
- Spring Boot 2.7.8, Java 11
- H2 database
- Apache Tomcat 9.0.40 server
- Swagger-UI, Maven
- Selenium 3.141.59

## Getting Started

- Clone the repository `git clone https://github.com/Karijotas/School-Scheduler.Alpine-Octopus.git`
  
### Run with Spring boot and npm/yarn

- go to project folder `cd .../School-Scheduler.Alpine-Octopus/AlpineOctopus-Scheduler`
- Run `mvn spring-boot:run` (application will start on port 8080)
- go to project folder `cd .../School-Scheduler.Alpine-Octopus/frontend`
- run `npm install` or `yarn install`
- run `npm run start` or `yarn start`
- application will open on your browser at http://localhost:3000
  
### Accessing the database

http://localhost:8081/console

```
JDBC URL:jdbc:h2:file:~/AlpineOctopus-Scheduler.db
User Name:sa
Password:

```

### Accessing API documentation

http://localhost:8080/swagger-ui/


## Running the tests


### Break down into end to end tests



## Authors

Team Alpine Octopus
List of [contributors](https://github.com/Karijotas/School-Scheduler.Alpine-Octopus/graphs/contributors) who participated in this project.
Akademija.it project
Copyright ©️ 2023
