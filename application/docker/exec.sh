#!/bin/sh

# Deploying Telestion in a docker image
# by WüSpace e. V.

# fail on first unsuccessful command
set -e

# the name of the compressed image
PROJECT_IMAGE="9DOF-Groundstation"
# relative path to the config file
CONFIG_PATH="config.json"

printf "Deploying Telestion..."

# extract and setup folder structure
tar -xf "$PROJECT_IMAGE.tar"
mkdir -p "$PROJECT_IMAGE/conf"
cp "$CONFIG_PATH" "$PROJECT_IMAGE/conf/"

# execute binary
cd "$PROJECT_IMAGE"
sh -c "./bin/$PROJECT_IMAGE"
