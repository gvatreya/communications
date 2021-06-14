# Simple Communications API

This project contains the backend written in Java 11(LTS) for a sample communications API.
The supported operations are `Contact, Channel and Message CRUD`.

### System Requirements
- Java 11
- Maven
- Postgres 11+

### Instructions to run the application manually using the codebase
Please refer to the system requirements above, before running these commands. <br/><br/>

1. Clone the repository
1. From the directory, where the project is cloned, run the project by executing the command `./mvnw spring-boot:run`
1. To run the tests, use the command `./mvnw test`
1. Please update the `application.properties` to reflect the DB username and password that you will use. 
1. For the app to run successfully, it expects certain tables to exist. The SQL files are in `etc/sql` folder.
   1. One can use postgres on docker - `docker run --name postgres -v ~/dockerVolumes/postgres/:/var/lib/postgresql/data -e POSTGRES_PASSWORD=secret1 --publish 5432:5432 --detach postgres`
   1. After the container runs, `docker exec -it postgres psql -Upostgres` and run the below file to setup roles and database.
   1. `etc/sql/0001_initial_setup.sql` contains the necessary queries to create users/roles and databases.
   1. **NOTE** Run the below file after switching to your database, and not the default postgres database.   
   1. `etc/sql/0002.sql` contains the DDL statements for creating necessary tables and setting up constraints.
   1. Please note that the tests will require the schema setup in the tests database - `communications_test`
1. To start the APP - run `CommunicationsApplication.java` file or use the command ` ./mvnw spring-boot:run`

### API Specification
The project exposes it's API specification using the OpenAPI standards.
One can access the API doc at `http://<base-url>/swagger-ui.html`.
For example: `http://localhost:8080/swagger-ui.html`

## Overview of objects and Supported Operations 

#### Contact
Contacts contain the user data such as name, email, phoneNumber etc.,

###### Operations
|Operation | HTTP Verb | endpoint | Explanation |
|---------|----|---|--------|
| Create| POST | /api/v1/contacts | Create a contact |
| Get contact| GET | /api/v1/contacts/{uuid} | Get contact by UUID |
| Get All contacts| GET | /api/v1/contacts | Get all contacts |

#### Channel
Channel encapsulates the different modes/channels a message can be delivered to /received from.
Channles include twitter, telegram, email and the like. The API stores the configuration to 
communicate via the channel, for example `API Keys, Tokens` etc.,

Note that every operation in this API needs privilege access.(Future implementation).
###### Operations
|Operation | HTTP Verb | endpoint | Explanation |
|---------|----|---|--------|
| Create| POST | /api/v1/channels | Create a channel |
| Get All channels| GET | /api/v1/channels | Get all channels |

#### Message
Message encapsulates the message details. It captures message details 
including, but not limited to - sender, receiver, channel, message body etc., 
###### Operations
|Operation | HTTP Verb | endpoint | Explanation |
|---------|----|---|--------|
| Send message | POST | /api/v1/messages | Send message with given details <span style="color:red">**$**</span>|
| Receive external message | POST | /api/v1/messages/external | The API accepts an object (because external API response are diverse) and logs it <span style="color:red">**#**</span>|
| Get All messages| GET | /api/v1/messages/contact/{uuid} | Get all messages where the contact with `uuid` is a participant |
| Get message| GET | /api/v1/messages/{uuid} | Get message with `uuid`|

<span style="color:red">**$**</span>This API currently stores the message body and subject in the DB, 
irrespective of the channel type. The `delivery_status` attribute of the message object gives the delivery 
status of the message.

The messages start out in _**IN_TRANSITION**_ state when the creat message request is accepted.
A thread tries to deliver the message using the channel specified. Once the thread completes its
work, using callback, the `delivery_status` is updated as either _**COMPLETED**_ or _**ERROR**_. For now
the callback randomly marks a message as wither having completed or errored out.

<span style="color:red">**#**</span> This API currenlty just logs the incoming object. In the future, it will make 
a service call, that will check the incoming object and parses it to an internal message object, saves it
and performs any further actions if necessary.

## Addendums

This section contains details of some developer tools that can be used to hit the ground fast 
and start exploring the API.

### Postman Collection of sample API requests and saved responses

one can make use of the postman collection attached **_Simple_Communications.postman_collection.json_**, 
to start playing with the APIs, once the dev server is started locally.

## Additional Notes

###### lombok
This project uses [Project Lombok](https://projectlombok.org) to help with bolierplate code.


### Future Improvements

- Dockerizing the app is WIP - the `Dockerfile` and `docker-compose.yml` needs some tweaking to get it running
- Support channel delivery
- Support handling of external messages as per their message body  
- Handle Postgres JSON object in POJO
- Hibernate @Where clause not working with spring JPA repository. Need to find a workaround using Filter and FilterRefs?
- Restrict access to Channel Service to Privileged user (Spring Security)
- Add JSON Validation
- Add support to store original web-hook messages as an attribute or separate table
- Support for multiple channel message delivery
- Refactor the threads max from environment/properties