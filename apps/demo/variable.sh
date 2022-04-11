# Build variables
APP_MODULE_PATH=api
APP_PROFILE=demo

# Docker Image variables
VERSION=demo
IMAGE=spring-web-api
CONTAINER=$IMAGE
REGISTRY=registry.kh.cubetiqs.com
EXPOSE_PORT=8080
ROOT_HUB=$REGISTRY/$IMAGE:$VERSION