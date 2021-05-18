#!/bin/bash

# Get Execute Script Directory
SCRIPT_DIR=$(dirname "$0")

# shellcheck disable=SC2039
source "$SCRIPT_DIR"/variable.sh

CONTAINER_NAME="$CONTAINER"
APP_DATA_DIR=$(passwd)/"$CONTAINER_NAME"

echo "===> Docker Pulling New Image <==="
docker pull "$ROOT_HUB"

echo "===> Docker Removing Container <==="
docker rm -f "$CONTAINER_NAME"

echo "===> Docker Run Container: $CONTAINER_NAME <==="
docker run  -d \
  -p "$EXPOSE_PORT":8090 \
  --env-file "$SCRIPT_DIR"/.env \
  -e HIBERNATE_DDL="${HIBERNATE_DDL:-update}" \
  -v "$APP_DATA_DIR"/data:/opt/cubetiq/data \
  --restart=always \
  --name "$CONTAINER_NAME" \
  "$ROOT_HUB"
