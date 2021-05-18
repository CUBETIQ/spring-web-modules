#!/bin/bash

# Get Execute Script Directory
SCRIPT_DIR=$(dirname "$0")

source "$SCRIPT_DIR"/variable.sh

echo "===> Copy & Replace Application Profile <==="
rm -rf "$APP_MODULE_PATH"/src/main/resources/application-dev.yml
cp -f "$SCRIPT_DIR"/application-"$APP_PROFILE".yml "$APP_MODULE_PATH"/src/main/resources/