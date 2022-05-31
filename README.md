# CUBETIQ Web Modules (Template)

- Setup and Default Web Configuration
- Swagger UI and API's Documentation (SpringFox)
- General Purpose for External and Internal use-cases
- Dockerfile and Docker profile build support
- Kubernetes support

### Language and Framework

- Spring Boot: 2.7.0
- Kotlin: 1.6.21
- Gradle: 7.4.1

# Modules

- API (Default Module)

### Spring Data

- Add `spring-data-jpa` dependency in `build.gradle.kts`

```kotlin
implementation("org.springframework.boot:spring-boot-starter-data-jpa")
```

- Add `spring-data-redis` dependency in `build.gradle.kts` (Redis + Driver)

```kotlin
implementation("org.springframework.boot:spring-boot-starter-data-redis")
```

### Spring Boot Properties

- Recommend

```yaml
spring:
  application:
    name: ${APP_NAME:spring-web-api}
app:
  data-dir: ${APP_DATA_DIR:${user.home}/${spring.application.name}}
```

- Upload File Properties

```yaml
server:
  tomcat:
    max-http-form-post-size: ${SERVER_MAX_HTTP_FORM_POST_SIZE:50MB}

spring:
  servlet:
    multipart:
      max-file-size: 256MB
      max-request-size: 256MB
      enabled: true
```

- Logging

```yaml
logging:
  file:
    path: ${LOGGING_FILE_PATH:${app.data-dir}/logs/}
    name: ${logging.file.path}/app.log
```

### Spring Data Redis

- Redis Properties

```yaml
spring:
  redis:
    host: ${REDIS_HOST:localhost}
    port: ${REDIS_PORT:6379}
    password: ${REDIS_PASSWORD:your_password}
```

### Spring Data JPA Properties

- Datasource Enhancement (Default: PostgresSQL)

```yaml
spring:
  datasource:
    driverClassName: ${DATA_SOURCE_DRIVER_CLASS_NAME:org.postgresql.Driver}
    url: ${DATA_SOURCE_URL:jdbc:postgresql://localhost:5432/demo}
    username: ${POSTGRES_USERNAME:postgres}
    password: ${POSTGRES_PASSWORD:postgres}
    hikari:
      max-lifetime: ${DATA_SOURCE_MAX_LIFETIME:1800000}
      connection-timeout: ${DATA_SOURCE_CONNECTION_TIMEOUT:30000}
      idle-timeout: ${DATA_SOURCE_IDLE_TIMEOUT:600000}
      maximum-pool-size: ${DATA_SOURCE_MAXIMUM_POOL_SIZE:10}
      allow-pool-suspension: ${DATA_SOURCE_ALLOW_POOL_SUSPENSION:true}
    tomcat:
      max_active: ${DATA_SOURCE_TOMCAT_MAX_ACTIVE:100}
      max_idle: ${DATA_SOURCE_TOMCAT_MAX_IDLE:10}
      min-idle: ${DATA_SOURCE_TOMCAT_MIN_IDLE:10}
      initial_size: ${DATA_SOURCE_TOMCAT_INITIAL_SIZE:10}
      remove_abandoned: ${DATA_SOURCE_TOMCAT_REMOVE_ABANDONED:true}
  jpa:
    database-platform: ${JPA_DATABASE_PLATFORM:org.hibernate.dialect.PostgreSQLDialect}
    show-sql: ${JPA_SHOW_SQL:false}
    hibernate:
      ddl-auto: ${JPA_HIBERNATE_DDL_AUTO:update}
    properties:
      hibernate:
        dialect: ${JPA_HIBERNATE_DIALECT:org.hibernate.dialect.PostgreSQLDialect}
    open-in-view: ${JPA_OPEN_IN_VIEW:false}
```

- PostgreSQL

```yaml
spring:
  datasource:
    driverClassName: ${DATA_SOURCE_DRIVER_CLASS_NAME:org.postgresql.Driver}
    url: jdbc:postgresql://${POSTGRES_HOST:localhost}:${POSTGRES_PORT:5432}/${POSTGRES_DB:demo}
    username: ${POSTGRES_USERNAME:postgres}
    password: ${POSTGRES_PASSWORD:postgres}
```

- MySQL

```yaml
spring:
  datasource:
    driverClassName: ${DATA_SOURCE_DRIVER_CLASS_NAME:com.mysql.cj.jdbc.Driver}
    url: jdbc:mysql://${MYSQL_HOST:localhost}:${MYSQL_PORT:3306}/${MYSQL_DB:demo}?createDatabaseIfNotExist=true&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
    username: ${MYSQL_USERNAME:demo}
    password: ${MYSQL_PASSWORD:demo}
  jpa:
    database-platform: ${JPA_DATABASE_PLATFORM:org.hibernate.dialect.MySQLDialect}
    properties:
      hibernate:
        dialect: ${JPA_HIBERNATE_DIALECT:org.hibernate.dialect.MySQLDialect}
```

- H2 (Embedded)
-

```yaml
spring:
  datasource:
    driverClassName: ${DATA_SOURCE_DRIVER_CLASS_NAME:org.h2.Driver}
    url: jdbc:h2:file:${H2_DB_PATH:./data/db};DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
    username: ${H2_USERNAME:sa}
    password: ${H2_PASSWORD:password}
  jpa:
    database-platform: ${JPA_DATABASE_PLATFORM:org.hibernate.dialect.H2Dialect}
  h2:
    console:
      enabled: ${H2_CONSOLE_ENABLED:true}
```

- Avoid the Lazy Initialization Problem

```yaml
spring:
  jpa:
    properties:
      hibernate:
        enable_lazy_load_no_trans: ${HIBERNATE_LAZY_NO_TRANS:true}
```

# Contributors

- Sambo Chea <sombochea@cubetiqs.com>
