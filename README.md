# Spring Security in Microservice Architecture

In this section we will dive deep into the springboot security in Microservices and go through the detailed lifecycle of a request and response for.

1 Authentication and Authorization Flow

### Auth Flow

- Every request to our backend will route through API Gateway which is registered as a client in eureka.
- Api gateway has the authentication filter which checks if the request has the JWT token for the routes that are not in route validator.
- If the request have the JWT token it validates it using JWT util method validate.
- Suppose if the token is valid then the request will propogate to the respective service.
- If the token is not valid our global exception handler will catch the unauthorized exception and send the response.
- The authentication service is responsible for the actual authentication and is the copy of [SpringSecurityOAuthJWT](https://github.com/TalhaRizwan093/Springboot-Security-Starter-OAuth-JWT "repo").
- It has login and register endpoints which are responsible for registeration and giving JWT tokens.

![Microservice Auth Architecture](https://github.com/user-attachments/assets/1a002306-3f24-4d29-8868-3b0e2e304d09)

### Spinning up the backend

Before building and running the backend we have to ensure that the application properties files has setup correctly and accordingly.

Following are the must have properties.

```bash
spring.application.name=OAuthSecurity

spring.security.oauth2.client.registration.google.client-id = {your_google_client_id}
spring.security.oauth2.client.registration.google.client-secret = {your_google_client_secret}

spring.security.oauth2.client.registration.github.client-id = {your_github_client_id}
spring.security.oauth2.client.registration.github.client-secret = {your_github_client_secret}

security.jwt.token.secret-key: {your-jwt-token-secret}
security.jwt.token.expire-length: {you-token-expiry-in-milliseconds}

spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url = jdbc:mysql://localhost:3306/{your-desired-db}
spring.datasource.username = {your-mysql-username}
spring.datasource.password = {your-mysql-password}

spring.jpa.hibernate.ddl-auto = update
spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
```

Please add these app properties in your application and donot forget to replace the configurations {your-configs}.

After adding the application properties. Use the following commands to spin up the spring security project.

```bash
  cd {all microservices}
  mvn spring-boot:run
```

- Backend is now ready to accept the requests.

Access the frontend to start using the proejct

### Other Startes

- SpringSecurityStarter (OAuth2, JWT, Username password) [SpringSecurityOAuthJWT](https://github.com/TalhaRizwan093/Springboot-Security-Starter-OAuth-JWT "repo").

## Regards

For support, email talharizwan.me@gmail.com
If you like and using this project please donot Forget to Star it as it means alot to me. Thanks.

## Authors

- [@TalhaRizwan093](https://www.github.com/TalhaRizwan093)
