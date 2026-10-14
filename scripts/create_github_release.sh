#!/usr/bin/env bash
# Usage: GITHUB_TOKEN=xxxx ./create_github_release.sh v0.1.0 "v0.1.0" "Release notes"
TAG="$1"
TITLE="$2"
NOTES="$3"
if [ -z "$GITHUB_TOKEN" ]; then
  echo "Please set GITHUB_TOKEN environment variable with repo scope"
  exit 1
fi
if [ -z "$TAG" ]; then
  echo "Usage: $0 <tag> <title> <notes>"
  exit 1
fi
REPO="maxlo245/VRCX-mobile-version"
API_URL="https://api.github.com/repos/${REPO}/releases"

json=$(jq -n --arg tag "$TAG" --arg title "$TITLE" --arg notes "$NOTES" '{tag_name:$tag, name:$title, body:$notes, draft:false, prerelease:false}')

curl -s -H "Authorization: token $GITHUB_TOKEN" -H "Content-Type: application/json" -d "$json" "$API_URL"

echo

echo "Done"
