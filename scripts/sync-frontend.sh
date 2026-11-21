#!/bin/bash

set -e

PROJECT_ROOT="$(cd "$(dirname "$0")/.." && pwd)"

SOURCE="$PROJECT_ROOT/frontend"
DEST="$PROJECT_ROOT/backend/src/main/resources/static"

mkdir -p "$DEST"

cp "$SOURCE/index.html" "$DEST/index.html"
cp "$SOURCE/script.js" "$DEST/script.js"
cp "$SOURCE/style.css" "$DEST/style.css"

echo "Frontend synced successfully!"
