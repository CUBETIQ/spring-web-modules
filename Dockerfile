# Builder
FROM cubetiq/openjdk:11u-ubuntu as builder
LABEL maintainer="sombochea@cubetiqs.com"

WORKDIR /app

RUN apt-get update && apt-get install -y git

COPY . .

RUN sh gradlew clean bootJar
RUN rm -f build/libs/*-plain.jar

# Build for container image
FROM cubetiq/openjdk:jre-11u-debian
LABEL maintainer="sombochea@cubetiqs.com"

# Setup timezone to Phnom Penh
RUN ln -sf /usr/share/zoneinfo/Asia/Phnom_Penh /etc/localtime
RUN echo "Asia/Phnom_Penh" > /etc/timezone

# App root path
WORKDIR /opt/cubetiq

# App volumn
VOLUME ["/opt/cubetiq", "/data"]

# Api Module Build Directory
ARG API_BUILD_DIR=api/build

# Copy the app bundle to the workdir
COPY --from=builder /app/${API_BUILD_DIR}/libs/api-0.0.1-SNAPSHOT.jar ./api.jar

# App profile will run with
ENV PROFILE=dev
ENV APP_DATA_DIR=/data

# Entrypoint to app
CMD ["java","-jar", "./api.jar"]