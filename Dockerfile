# Builder
FROM bellsoft/liberica-openjdk-alpine-musl as builder
LABEL maintainer="sombochea@cubetiqs.com"

WORKDIR /app

RUN apk update && apk add git

COPY gradlew ./
COPY gradle ./gradle
RUN ./gradlew


COPY settings.gradle.kts ./
COPY build.gradle.kts ./
COPY api/build.gradle.kts ./api/build.gradle.kts
COPY api/src ./api/src
COPY .git ./.git

RUN ./gradlew api:bootJar

# Build for container image
FROM bellsoft/liberica-openjre-alpine-musl
LABEL maintainer="sombochea@cubetiqs.com"

# App root path
WORKDIR /opt/cubetiq

# App volumn
VOLUME ["/opt/cubetiq", "/data"]

# Api Module Build Directory
ARG API_BUILD_DIR=api/build

# Copy the app bundle to the workdir
COPY --from=builder /app/${API_BUILD_DIR}/libs/api.jar ./api.jar

# App profile will run with
ENV PROFILE=dev
ENV APP_DATA_DIR=/data

# Entrypoint to app
CMD ["java","-jar", "./api.jar"]