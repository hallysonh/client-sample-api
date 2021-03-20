# Client API Sample

## Getting started

This app is just a sample API implementation using Spring Boot with Spring Web, Spring Data JPA and an H2 Database.

This app aims to implement the following requirements:

- Allow creation of new clients;
- Allow update existent clients;
- Allow listing clients in a paginated way;
- Allow search for clients attributes;
- Mandatory to return the age from each element of the list;
- The API should be very well documented with a Postman file available for easy use of the API.

## Running this app

Use one of the follow methods to run the application

### From source using gradle

Just execute the follow command:

```sh
./gradlew bootRun
```

### Using Docker

From built image on DockerHub

```sh
# With Docker Compose
docker-compose up

# Or directly with docker
docker run --rm -it \
  -e apiKey=xTQCkVJGWlIDQ0g1JMFMMj8tUyLdNX09 \
  -p 3000:3000 \
  hallysonh/client-sample-api
```

From source

```sh
# First: Build the app image
docker build -t client-api .

# Then: Run built image
docker run --rm -it \
  -e apiKey=xTQCkVJGWlIDQ0g1JMFMMj8tUyLdNX09 \
  -p 3000:3000 \
  client-api
```

## Testing API

### Swagger UI

After run the api you can test it using Swagger UI on: [http://localhost:3000/api/v1/docs/swagger-ui/](http://localhost:3000/api/v1/docs/swagger-ui/)

### Postman

Just import the <a href="https://raw.githubusercontent.com/hallysonh/client-sample-api/main/docs/postman_collection.json" target="_blank">Postman Collection file</a> from docs folder.
