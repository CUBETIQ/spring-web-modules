FROM cubetiq/calpine-openjdk11:latest
LABEL maintainer="sombochea@cubetiqs.com"

# Working directory for application
WORKDIR /opt/cubetiq

# Define mount volumn for data path
VOLUME ["/opt/cubetiq/data"]

# Copy build source to container
COPY api/build/libs/*.jar ./api.jar

ENV APP_DATA_DIR "/opt/cubetiq/data"

# Entrypoint to app
CMD ["java","-jar", "./api.jar"]