# README

The Project runs with Java 17 Spring Boot 3.4 and local file h2 database

Default server address is:

http://localhost:8080

Postman Collection(Inside the project):

[productionPlannerApi.postman_collection.json](https://github.com/muratkul/production_planner/blob/master/productionPlannerApi.postman_collection.json)

Swagger Address is:

http://localhost:8080/swagger-ui/index.html

Docker:

[Docker Link](https://hub.docker.com/layers/muratkul/production_planner/v1/images/sha256:e42cb3bde0db23e08dabaa4ab8af3db3e1d15ec014fdaf1655301d3a1a4ad911?tab=layers)

Docker Commands:

`docker build --platform linux/amd64,linux/arm64 . -t muratkul/production_planner:v1`

`docker run -p 8080:8080 muratkul/production_planner:v1`


DB Connection:

* url=jdbc:h2:file:./h2/production_planner;AUTO_SERVER=TRUE
* username=sa
* password=password

The user need to use services through `/api` link. Postman collection is provided as attachment. (In project as productionPlannerApi.postman_collection.json)


## APPLICATION PROPERTIES

* planner.api.create.test.data=true

**loan.api.create.test.data** should be set to false for prod environment
when it is true. DataLoader.class creates example Components and Models for quick testing.

## ENDPOINTS

Endpoints can be tested with postman collection. You can find information about endpoints in swagger document.

Example:


[Get Model REST API Example](http://18.184.216.185:8080/api/model/get?id=2)
