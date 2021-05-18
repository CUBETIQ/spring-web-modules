#!/bin/sh

# Get Execute Script Directory
SCRIPT_DIR=$(dirname "$0")

# shellcheck disable=SC2039
source "$SCRIPT_DIR"/variable.sh

echo "===> Clean Gradle Build <==="
bash gradlew clean

echo "===> Moving App File <==="
bash "$SCRIPT_DIR"/move-file.sh

echo "===> Gradle Building Application <==="
bash gradlew build -x test

echo "===> Docker Building Image <==="
docker build . -t "$ROOT_HUB"

echo "===> Docker Pushing Image <==="
docker push "$ROOT_HUB"
